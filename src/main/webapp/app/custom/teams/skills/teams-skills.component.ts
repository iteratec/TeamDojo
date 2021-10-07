/*import { Component, EventEmitter, Input, OnChanges, OnInit, Output, SimpleChanges } from '@angular/core';
import { SessionStorageService } from 'ngx-webstorage';

import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Location } from '@angular/common';
import { ActivatedRoute, ParamMap, Router } from '@angular/router';
import * as moment from 'moment';

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

const ROLES_ALLOWED_TO_UPDATE = ['ROLE_ADMIN'];

@Component({
  selector: 'jhi-teams-skills',
  templateUrl: './teams-skills.component.html',
  styleUrls: ['teams-skills.scss'],
})
export class TeamsSkillsComponent implements OnInit, OnChanges {
  @Input() team?: ITeam;
  @Input() skill?: IAchievableSkill;
  @Output() onSkillClicked = new EventEmitter<{ iSkill: ISkill | null; aSkill: AchievableSkill }>();
  @Output() onSkillChanged = new EventEmitter<{ iSkill: ISkill | null; aSkill: AchievableSkill }>();
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
  search: string = "";
  orderBy = 'title';
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
    private accountService: AccountService
  ) {}

  ngOnInit() {
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
      this.accountService.identity().then(identity => {
        this.hasAuthority = this.accountService.hasAnyAuthority(ROLES_ALLOWED_TO_UPDATE);
      });
    }, 0);
  }

  ngOnChanges(changes: SimpleChanges) {
    if (changes.team && changes.team.previousValue && changes.team.previousValue.id !== changes.team.currentValue.id) {
      this.loadAll();
    }
  }

  private getParamAsNumber(name: string, params: ParamMap) {
    return Number.parseInt(params.get(name), 10);
  }

  loadAll() {
    this.activeBadge = null;
    this.activeLevel = null;
    this.activeDimension = null;
    this.activeSkill = null;

    if (this.dimensionId) {
      this.dimensionService.find(this.dimensionId).subscribe(dimensionResponse => {
        this.activeDimension = dimensionResponse.body;
        this.updateBreadcrumb();
      });
      this.teamsSkillsService
        .queryAchievableSkillsByDimension(this.team.id, {
          filter: this.filters,
          dimensionId: this.dimensionId,
        })
        .subscribe(
          (res: HttpResponse<IAchievableSkill[]>) => (this.skills = res.body),
          (res: HttpErrorResponse) => this.onError(res.message)
        );
    } else {
      this.teamsSkillsService
        .queryAchievableSkills(this.team.id, {
          filter: this.filters,
          levelId: this.levelId || null,
          badgeId: this.badgeId || null,
        })
        .subscribe(
          (res: HttpResponse<IAchievableSkill[]>) => (this.skills = res.body),
          (res: HttpErrorResponse) => this.onError(res.message)
        );
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
        this.dimensionService.find(this.activeLevel.dimensionId).subscribe(dimensionResponse => {
          this.activeDimension = dimensionResponse.body;
          this.updateBreadcrumb();
        });
      });
    }

    if (this.skill && this.skill.skillId) {
      this.skillService.find(this.skill.skillId).subscribe(skillRes => {
        this.activeSkill = skillRes.body;
        this.updateBreadcrumb();
      });
    }

    this.updateBreadcrumb();
  }

  getQueryParams() {
    const queryParams = {};
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

  goToDetails(skill: IAchievableSkill) {
    const queryParams = this.getQueryParams();
    this.router.navigate(['teams', this.team.shortName, 'skills', skill.skillId], {
      queryParams,
    });
  }

  onDimensionChange(activeDimension) {
    this.activeDimension = activeDimension;
    this.router.navigate([], {
      queryParams: { dimension: activeDimension ? activeDimension.id : null },
    });
  }

  private updateBreadcrumb() {
    this.breadcrumbService.setBreadcrumb(this.team, this.activeDimension, this.activeLevel, this.activeBadge, this.activeSkill);
  }

  setComplete(skill: IAchievableSkill) {
    if (!skill.irrelevant) {
      skill.achievedAt = moment();
      this.updateSkill(skill);
    }
  }

  setIncomplete(skill: IAchievableSkill) {
    if (!skill.irrelevant) {
      skill.achievedAt = null;
      this.updateSkill(skill);
    }
  }

  getStatusClass(skill: IAchievableSkill): string {
    return SkillStatusUtils.getLowerCaseValue(skill.skillStatus);
  }

  getSkillStatusTranslationKey(skill: AchievableSkill): string {
    return SkillStatusUtils.getLowerCaseValue(skill.skillStatus);
  }

  clickSkillStatus(skill: IAchievableSkill) {
    if (SkillStatusUtils.isValid(skill.skillStatus)) {
      this.setIncomplete(skill);
    } else if (SkillStatusUtils.isInvalid(skill.skillStatus)) {
      this.setComplete(skill);
    }
  }

  setIrrelevant(skill: IAchievableSkill) {
    skill.irrelevant = true;
    skill.achievedAt = null;
    this.updateSkill(skill);
  }

  setRelevant(skill: IAchievableSkill) {
    skill.irrelevant = false;
    this.updateSkill(skill);
  }

  toggleRelevance(skill: IAchievableSkill) {
    if (skill.irrelevant) {
      this.setRelevant(skill);
    } else {
      this.setIrrelevant(skill);
    }
  }

  private updateSkill(skill: IAchievableSkill) {
    this.teamsSkillsService.updateAchievableSkill(this.team.id, skill).subscribe(
      (res: HttpResponse<IAchievableSkill>) => {
        skill = res.body;
        this.skillService.find(skill.skillId).subscribe(skillRes => {
          this.onSkillChanged.emit({
            iSkill: skillRes.body,
            aSkill: skill,
          });
        });
        this.loadAll();
      },
      (res: HttpErrorResponse) => {
        console.log(res);
      }
    );
  }

  onFilterClicked(filterName: string) {
    const index = this.filters.indexOf(filterName);
    if (index > -1) {
      this.filters.splice(index, 1);
    } else {
      this.filters.push(filterName);
    }
    this.storage.store('filterKey', this.filters);
    this.loadAll();
  }

  isSameTeamSelected() {
    const selectedTeam = this.teamsSelectionService.selectedTeam;
    return selectedTeam && selectedTeam.id === this.team.id;
  }

  isTeamVoteAble(s: IAchievableSkill) {
    const selectedTeam = this.teamsSelectionService.selectedTeam;
    if (selectedTeam && (!s.voters || (s.voters && !s.voters.split('||').includes(selectedTeam.id.toString())))) {
      return true;
    }
    return false;
  }

  isVoteAble(s: IAchievableSkill) {
    return s.achievedAt && !s.verifiedAt && s.vote > -5 && this.isTeamVoteAble(s);
  }

  isSuggestAble(s: IAchievableSkill) {
    return !s.achievedAt && !s.irrelevant && (!s.vote || (s.vote && s.vote !== 1)) && this.isTeamVoteAble(s);
  }

  private onError(errorMessage: string) {
    this.alertService.error(errorMessage, null, null);
  }

  isInSkillDetails() {
    return typeof this.skill !== 'undefined' && this.skill !== null;
  }

  handleSkillClicked(s: IAchievableSkill) {
    if (this.isInSkillDetails()) {
      const url = this.router
        .createUrlTree(['/teams', this.team.shortName, 'skills', s.skillId], {
          queryParams: { level: this.levelId || '', badge: this.badgeId || '' },
        })
        .toString();
      this.location.replaceState(url);
      this.skillService.find(s.skillId).subscribe(skill => {
        this.onSkillClicked.emit({
          iSkill: skill.body,
          aSkill: s,
        });
        this.breadcrumbService.setBreadcrumb(this.team, this.activeDimension, this.activeLevel, this.activeBadge, skill.body);
      });
    } else {
      this.goToDetails(s);
    }
  }

  isActiveSkill(s: IAchievableSkill) {
    return this.skill && this.skill.skillId === s.skillId;
  }

  handleSkillChanged(s: IAchievableSkill) {
    this.updateSkill(s);
    this.skills = this.skills.map(skill => {
      return skill.skillId === s.skillId ? s : skill;
    });
    this.loadAll();
  }

  getRateCount(rateCount: number) {
    return rateCount !== null && typeof rateCount !== 'undefined' ? rateCount : 0;
  }

  private getFiltersFromStorage(): string[] {
    return this.storage.retrieve('filterKey') || [];
  }

  upVote(s: IAchievableSkill) {
    console.log('Upvote TeamSkill');
    s.vote = s.vote + 1;
    const array = s.voters ? s.voters.split('||') : [];
    array.push(this.teamsSelectionService.selectedTeam.id.toString());
    s.voters = array.join('||');
    console.log(s);
    this.updateSkill(s);
  }

  downVote(s: IAchievableSkill) {
    console.log('downvote TeamSkill');
    s.vote = s.vote - 1;
    const array = s.voters ? s.voters.split('||') : [];
    array.push(this.teamsSelectionService.selectedTeam.id.toString());
    s.voters = array.join('||');
    this.updateSkill(s);
  }

  suggest(s: IAchievableSkill) {
    console.log('suggest TeamSkill');
    s.vote = 1;
    const array = s.voters ? s.voters.split('||') : [];
    array.push(this.teamsSelectionService.selectedTeam.id.toString());
    s.voters = array.join('||');
    this.updateSkill(s);
  }
}
*/
