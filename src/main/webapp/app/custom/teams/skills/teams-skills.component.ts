/*
 SPDX-FileCopyrightText: the TeamDojo authors
 SPDX-License-Identifier: Apache-2.0
 */
import { Component, EventEmitter, Input, OnChanges, OnInit, Output, SimpleChanges, SimpleChange } from '@angular/core';
import { SessionStorageService } from 'ngx-webstorage';

import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Location } from '@angular/common';
import { ActivatedRoute, ParamMap, Router } from '@angular/router';
import moment from 'moment';

import 'simplebar';
import { Subject } from 'rxjs';
import { debounceTime, distinctUntilChanged } from 'rxjs/operators';

import { AccountService } from 'app/core/auth/account.service';
import { AchievableSkill, IAchievableSkill } from 'app/custom/entities/achievable-skill/achievable-skill.model';
import { IBadge } from 'app/entities/badge/badge.model';
import { ILevel } from 'app/entities/level/level.model';
import { IDimension } from 'app/entities/dimension/dimension.model';
import { BreadcrumbService } from 'app/custom/layouts/navbar/breadcrumb.service';
import { LevelService } from 'app/entities/level/service/level.service';
import { BadgeService } from 'app/entities/badge/service/badge.service';
import { DimensionService } from 'app/entities/dimension/service/dimension.service';
import { SkillStatusUtils } from 'app/custom/entities/skill-status';
import { TeamsSkillsService } from 'app/custom/teams/teams-skills.service';
import { SkillService } from 'app/entities/skill/service/skill.service';
import { ISkill } from 'app/entities/skill/skill.model';
import { TeamsSelectionService } from 'app/custom/teams-selection/teams-selection.service';
import { ITeam } from 'app/entities/team/team.model';
import { AlertService } from 'app/core/util/alert.service';
import { ParseLinks } from 'app/core/util/parse-links.service';
import { ISkillObjects } from 'app/custom/entities/skill-objects/skill-objects.model';
import { TranslateModelService } from '../../shared/translate-model/translate-model.service';
import { SkillStatus } from '../../../entities/enumerations/skill-status.model';
import { TEAM_SKILLS_PER_PAGE } from '../../../config/pagination.constants';

const ROLES_ALLOWED_TO_UPDATE = ['ROLE_ADMIN'];

@Component({
  selector: 'jhi-teams-skills',
  templateUrl: './teams-skills.component.html',
  styleUrls: ['./teams-skills.scss'],
})
export class TeamsSkillsComponent implements OnInit, OnChanges {
  static readonly DEFAULT_ORDER_BY: string = 'title';
  eSkillStatus = SkillStatus;
  @Input() team?: ITeam;
  @Input() skill?: IAchievableSkill;
  @Output() skillClicked = new EventEmitter<ISkillObjects>();
  @Output() skillChanged = new EventEmitter<ISkillObjects>();
  skills: IAchievableSkill[] = [];
  filters: string[] = [];
  levelId: number | null = null;
  badgeId: number | null = null;
  dimensionId: number | null = null;
  activeBadge: IBadge | null = null;
  activeLevel: ILevel | null = null;
  activeDimension: IDimension | null = null;
  activeSkill: ISkill | null = null;
  search$: Subject<string> = new Subject<string>();
  search = '';
  orderBy = TeamsSkillsComponent.DEFAULT_ORDER_BY;
  hasAuthority = false;

  constructor(
    private teamsSkillsService: TeamsSkillsService,
    private skillService: SkillService,
    private alertService: AlertService,
    private parseLinks: ParseLinks,
    private teamsSelectionService: TeamsSelectionService,
    private storage: SessionStorageService,
    private route: ActivatedRoute,
    private location: Location,
    private router: Router,
    private breadcrumbService: BreadcrumbService,
    private levelService: LevelService,
    private badgeService: BadgeService,
    private dimensionService: DimensionService,
    private accountService: AccountService,
    private translateModelService: TranslateModelService
  ) {}

  ngOnInit(): void {
    this.filters = this.getFiltersFromStorage();
    this.skills = [];
    this.route.queryParamMap.subscribe((params: ParamMap) => {
      const levelId = this.getParamAsNumber('level', params);
      const badgeId = this.getParamAsNumber('badge', params);
      const dimensionId = this.getParamAsNumber('dimension', params);
      this.levelId = levelId ? levelId : null;
      this.badgeId = badgeId ? badgeId : null;
      this.dimensionId = dimensionId ? dimensionId : null;
      this.loadAll();
    });
    this.search = '';
    this.search$ = new Subject<string>();
    this.search$.pipe(debounceTime(400), distinctUntilChanged()).subscribe(value => {
      this.search = value;
      return value;
    });

    setTimeout(() => {
      this.accountService
        .identity()
        .toPromise()
        .then(() => {
          this.hasAuthority = this.accountService.hasAnyAuthority(ROLES_ALLOWED_TO_UPDATE);
        });
    }, 0);
  }

  ngOnChanges(changes: SimpleChanges): void {
    if (Object.prototype.hasOwnProperty.call(changes, 'team')) {
      const teamChange: SimpleChange = changes['team'];

      if (teamChange.previousValue && teamChange.previousValue.id !== teamChange.currentValue.id) {
        this.loadAll();
      }
    }
  }

  loadAll(): void {
    this.activeBadge = null;
    this.activeLevel = null;
    this.activeDimension = null;
    this.activeSkill = null;

    if (this.dimensionId) {
      this.dimensionService.find(this.dimensionId).subscribe(dimensionResponse => {
        this.activeDimension = dimensionResponse.body;
        this.updateBreadcrumb();
      });
      if (this.team?.id) {
        this.teamsSkillsService
          .queryAchievableSkillsByDimension(this.team.id, {
            page: 0,
            size: TEAM_SKILLS_PER_PAGE,
            filter: this.filters,
            dimensionId: this.dimensionId,
          })
          .subscribe(
            (res: HttpResponse<IAchievableSkill[]>) => {
              if (res.body) {
                this.skills = res.body;
              }
            },
            (res: HttpErrorResponse) => this.onError(res.message)
          );
      }
    } else {
      if (this.team?.id) {
        const reqParams: { page: number; size: number; filter: string[]; levelId?: number; badgeId?: number } = {
          page: 0,
          size: TEAM_SKILLS_PER_PAGE,
          filter: this.filters,
        };

        if (this.levelId) {
          reqParams.levelId = this.levelId;
        }

        if (this.badgeId) {
          reqParams.badgeId = this.badgeId;
        }

        this.teamsSkillsService.queryAchievableSkills(this.team.id, reqParams).subscribe(
          (res: HttpResponse<IAchievableSkill[]>) => {
            if (res.body) {
              this.skills = res.body;
            }
          },
          (res: HttpErrorResponse) => this.onError(res.message)
        );
      }
    }

    if (this.badgeId) {
      this.badgeService.find(this.badgeId).subscribe(badge => {
        this.activeBadge = badge.body;
        this.updateBreadcrumb();
      });
    }

    if (this.levelId) {
      this.levelService.find(this.levelId).subscribe(level => {
        this.activeLevel = level.body;
        if (this.activeLevel?.dimension?.id) {
          this.dimensionService.find(this.activeLevel.dimension.id).subscribe(dimensionResponse => {
            this.activeDimension = dimensionResponse.body;
            this.updateBreadcrumb();
          });
        }
      });
    }

    if (this.skill?.skillId) {
      this.skillService.find(this.skill.skillId).subscribe(skillRes => {
        this.activeSkill = skillRes.body;
        this.updateBreadcrumb();
      });
    }

    this.updateBreadcrumb();
  }

  getQueryParams(): { [index: string]: number } {
    const queryParams: { [index: string]: number } = {};
    if (this.levelId) {
      queryParams['level'] = this.levelId;
    }
    if (this.badgeId) {
      queryParams['badge'] = this.badgeId;
    }
    if (this.dimensionId) {
      queryParams['dimension'] = this.dimensionId;
    }
    return queryParams;
  }

  goToDetails(skill: IAchievableSkill): void {
    const queryParams = this.getQueryParams();
    this.router.navigate(['teams', this.team?.shortTitle, 'skills', skill.skillId], {
      queryParams,
    });
  }

  onDimensionChange(activeDimension: IDimension): void {
    this.activeDimension = activeDimension;
    this.router.navigate([], {
      queryParams: { dimension: activeDimension.id },
    });
  }

  setComplete(skill: IAchievableSkill): void {
    if (!skill.irrelevant) {
      skill.achievedAt = moment();
      skill.skillStatus = SkillStatus.ACHIEVED;
      this.updateSkill(skill);
    }
  }

  setIncomplete(skill: IAchievableSkill): void {
    if (!skill.irrelevant) {
      skill.achievedAt = undefined;
      skill.skillStatus = SkillStatus.OPEN;
      this.updateSkill(skill);
    }
  }

  getStatusClass(skill: IAchievableSkill): string {
    if (skill.skillStatus) {
      return SkillStatusUtils.getLowerCaseValue(skill.skillStatus);
    }

    return '';
  }

  getSkillStatusTranslationKey(skill: AchievableSkill): string {
    if (skill.skillStatus) {
      return SkillStatusUtils.getLowerCaseValue(skill.skillStatus);
    }

    return '';
  }

  clickSkillStatus(skill: IAchievableSkill): void {
    if (skill.skillStatus) {
      if (SkillStatusUtils.isValid(skill.skillStatus)) {
        this.setIncomplete(skill);
      } else if (SkillStatusUtils.isInvalid(skill.skillStatus)) {
        this.setComplete(skill);
      }
    }
  }

  setIrrelevant(skill: IAchievableSkill): void {
    skill.irrelevant = true;
    skill.achievedAt = undefined;
    this.updateSkill(skill);
  }

  setRelevant(skill: IAchievableSkill): void {
    skill.irrelevant = false;
    this.updateSkill(skill);
  }

  toggleRelevance(skill: IAchievableSkill): void {
    if (skill.irrelevant) {
      this.setRelevant(skill);
    } else {
      this.setIrrelevant(skill);
    }
  }

  onFilterClicked(filterName: string): void {
    const index = this.filters.indexOf(filterName);
    if (index > -1) {
      this.filters.splice(index, 1);
    } else {
      this.filters.push(filterName);
    }
    this.storage.store('filterKey', this.filters);
    this.loadAll();
  }

  isSameTeamSelected(): boolean {
    const selectedTeam = this.teamsSelectionService.selectedTeam;
    return selectedTeam?.id === this.team?.id;
  }

  isTeamVoteAble(s: IAchievableSkill): boolean {
    const selectedTeam = this.teamsSelectionService.selectedTeam;
    return !!(selectedTeam?.id && (!s.voters || (s.voters && !s.voters.split('||').includes(selectedTeam.id.toString()))));
  }

  isVoteAble(s: IAchievableSkill): boolean {
    return !!s.achievedAt && !s.verifiedAt && !!s.vote && s.vote > -5 && this.isTeamVoteAble(s);
  }

  isSuggestAble(s: IAchievableSkill): boolean {
    return !s.achievedAt && !s.irrelevant && (!s.vote || (!!s.vote && s.vote !== 1)) && this.isTeamVoteAble(s);
  }

  isInSkillDetails(): boolean {
    return !!this.skill;
  }

  handleSkillClicked(s: IAchievableSkill): void {
    if (this.isInSkillDetails()) {
      const url = this.router
        .createUrlTree(['/teams', this.team?.shortTitle, 'skills', s.skillId], {
          queryParams: { level: this.levelId ?? '', badge: this.badgeId ?? '' },
        })
        .toString();
      this.location.replaceState(url);
      if (s.skillId) {
        this.skillService.find(s.skillId).subscribe(skill => {
          if (skill.body) {
            this.skillClicked.emit({
              skill: skill.body,
              achievableSkill: s,
            });
          }

          this.breadcrumbService.setBreadcrumb(
            this.team ? this.team : null,
            this.activeDimension,
            this.activeLevel,
            this.activeBadge,
            skill.body
          );
        });
      }
    } else {
      this.goToDetails(s);
    }
  }

  isActiveSkill(s: IAchievableSkill): boolean {
    return this.skill?.skillId === s.skillId;
  }

  handleSkillChanged(s: IAchievableSkill): void {
    this.updateSkill(s);
    this.skills = this.skills.map(skill => (skill.skillId === s.skillId ? s : skill));
    this.loadAll();
  }

  getRateCount(rateCount?: number): number {
    return rateCount ? rateCount : 0;
  }

  upVote(s: IAchievableSkill): void {
    if (s.vote) {
      s.vote = s.vote + 1;
    }

    const array = s.voters ? s.voters.split('||') : [];
    if (this.teamsSelectionService.selectedTeam?.id) {
      array.push(this.teamsSelectionService.selectedTeam.id.toString());
    }

    s.voters = array.join('||');
    this.updateSkill(s);
  }

  downVote(s: IAchievableSkill): void {
    if (s.vote) {
      s.vote = s.vote - 1;
    }

    const array = s.voters ? s.voters.split('||') : [];
    if (this.teamsSelectionService.selectedTeam?.id) {
      array.push(this.teamsSelectionService.selectedTeam.id.toString());
    }

    s.voters = array.join('||');
    this.updateSkill(s);
  }

  suggest(s: IAchievableSkill): void {
    s.vote = 1;
    const array = s.voters ? s.voters.split('||') : [];
    if (this.teamsSelectionService.selectedTeam?.id) {
      array.push(this.teamsSelectionService.selectedTeam.id.toString());
    }

    s.voters = array.join('||');
    this.updateSkill(s);
  }

  onSkillSort(): void {
    this.skills = this.sortSkills(this.skills);
  }

  sortSkills(skills: IAchievableSkill[]): IAchievableSkill[] {
    const nullSafePropertyName = this.defaultString(this.orderBy);
    const localizedPropertyName = this.localizeOrderBy(nullSafePropertyName);
    const sortOrder = this.shouldSortReverse(localizedPropertyName) ? -1 : 1;

    return Array.from(skills).sort((leftSkill, rightSkill) => {
      const leftValue = this.defaultString(leftSkill[localizedPropertyName]);
      const rightValue = this.defaultString(rightSkill[localizedPropertyName]);

      return sortOrder * leftValue.localeCompare(rightValue);
    });
  }

  private defaultString(smth: any): string {
    return smth || smth === 0 ? String(smth) : '';
  }

  private shouldSortReverse(propertyName: string): boolean {
    return ['score', 'rateCount'].includes(propertyName);
  }

  private localizeOrderBy(propertyName: string): keyof AchievableSkill {
    type AchievableSkillObjectKey = keyof AchievableSkill;

    if (this.isLocalizedModelProperty(propertyName)) {
      return this.translateModelService.localizePropertyName(propertyName) as AchievableSkillObjectKey;
    }

    return propertyName as AchievableSkillObjectKey;
  }

  private isLocalizedModelProperty(propertyName: string): boolean {
    return propertyName === TeamsSkillsComponent.DEFAULT_ORDER_BY;
  }

  private getFiltersFromStorage(): string[] {
    const filters = this.storage.retrieve('filterKey');
    return filters ? (filters as string[]) : [];
  }

  private onError(errorMessage: string): void {
    this.alertService.addAlert({ type: 'danger', message: errorMessage });
  }

  private getParamAsNumber(name: string, params: ParamMap): number | undefined {
    const param = params.get(name);
    if (param) {
      return Number.parseInt(param, 10);
    }

    return undefined;
  }

  private updateBreadcrumb(): void {
    this.breadcrumbService.setBreadcrumb(
      this.team ? this.team : null,
      this.activeDimension,
      this.activeLevel,
      this.activeBadge,
      this.activeSkill
    );
  }

  private updateSkill(skill: IAchievableSkill): void {
    if (this.team?.id) {
      this.teamsSkillsService.updateAchievableSkill(this.team.id, skill).subscribe((res: HttpResponse<IAchievableSkill>) => {
        if (res.body?.skillId) {
          this.skillService.find(res.body.skillId).subscribe(skillRes => {
            const updatedAchievableSkill = res.body;

            if (skillRes.body && updatedAchievableSkill) {
              this.skillChanged.emit({
                skill: skillRes.body,
                achievableSkill: updatedAchievableSkill,
              });
            }
          });

          this.loadAll();
        }
      });
    }
  }
}
