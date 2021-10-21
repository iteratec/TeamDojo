import { Component, EventEmitter, Input, OnChanges, OnInit, Output, SimpleChanges } from '@angular/core';
import { ActivatedRoute, ParamMap } from '@angular/router';

import 'simplebar';
import { Subject } from 'rxjs';
import { debounceTime, distinctUntilChanged } from 'rxjs/operators';

import { IDimension } from 'app/entities/dimension/dimension.model';
import { AlertService } from 'app/core/util/alert.service';
import { ILevel } from 'app/entities/level/level.model';
import { ITeam } from 'app/entities/team/team.model';
import { IBadge } from 'app/entities/badge/badge.model';
import { IBadgeSkill } from 'app/entities/badge-skill/badge-skill.model';
import { ITeamSkill } from 'app/entities/team-skill/team-skill.model';
import { ILevelSkill } from 'app/entities/level-skill/level-skill.model';
import { ISkill, Skill } from 'app/entities/skill/skill.model';
import { BreadcrumbService } from 'app/custom/layouts/navbar/breadcrumb.service';
import { DimensionService } from 'app/entities/dimension/service/dimension.service';
import { SkillSortPipe } from 'app/custom/shared/pipe/skill-sort.pipe';
import { SkillService } from 'app/entities/skill/service/skill.service';
import { SkillStatusUtils } from 'app/custom/entities/skill-status';
import { AccountService } from 'app/core/auth/account.service';
import { Progress } from 'app/custom/entities/progress/progress.model';

const ROLES_ALLOWED_TO_UPDATE = ['ROLE_ADMIN'];

@Component({
  selector: 'jhi-overview-skills',
  templateUrl: './overview-skills.component.html',
  styleUrls: ['./overview-skills.scss'],
})
export class OverviewSkillsComponent implements OnInit, OnChanges {
  @Input() activeSkill?: ISkill;
  @Output() skillChanged = new EventEmitter<ISkill>();
  // @Fixme Issue #35 original line in V1:    @Output() skillClicked = new EventEmitter<{ iSkill: ISkill; aSkill: IAchievableSkill }>();
  @Output() skillClicked = new EventEmitter<{ storedSkill: ISkill; activeSkill: ISkill | undefined }>();
  // data from backend
  teams: ITeam[] = [];
  levels: ILevel[] = [];
  levelSkills: ILevelSkill[] = [];
  badges: IBadge[] = [];
  badgeSkills: IBadgeSkill[] = [];
  skills: ISkill[] = [];
  dimensions: IDimension[] = [];
  // component state
  activeSkills: ISkill[] = [];
  activeLevel?: ILevel | null;
  activeBadge?: IBadge | null;
  activeDimension?: IDimension | null;
  dimensionsBySkillId: any;
  generalSkillsIds: number[] = [];
  search$: Subject<string> = new Subject();
  search = '';
  orderBy: keyof Skill = 'title';
  hasAuthority = false;

  constructor(
    private alertService: AlertService,
    private route: ActivatedRoute,
    private breadcrumbService: BreadcrumbService,
    private dimensionService: DimensionService,
    private accountService: AccountService,
    private skillService: SkillService
  ) {}

  ngOnInit(): void {
    this.route.data.subscribe(({ dojoModel: { teams, levels, levelSkills, badges, badgeSkills }, skills, dimensions }) => {
      this.teams = teams || [];
      this.levels = levels || [];
      this.levelSkills = levelSkills || [];
      this.badges = badges || [];
      this.badgeSkills = badgeSkills || [];
      this.skills = (skills?.body ? skills.body : skills) || [];
      this.dimensions = (dimensions?.body ? dimensions.body : dimensions) || [];
      this.route.queryParamMap.subscribe((params: ParamMap) => {
        this.activeLevel = null;
        this.activeBadge = null;
        this.activeDimension = null;
        const paramLevelId = params.get('level');
        const paramBadgeId = params.get('badge');
        const paramDimensionId = params.get('dimension');
        if (paramLevelId) {
          this.activeLevel = this.levels.find((level: ILevel) => level.id === Number.parseInt(paramLevelId, 10));
          if (this.activeLevel) {
            this.activeSkills = this.sortActiveSkills(this.findSkills(this.activeLevel.skills));
          } else {
            this.activeSkills = [];
          }

          this.updateBreadcrumb();
        } else if (paramBadgeId) {
          this.activeBadge = this.badges.find((badge: IBadge) => badge.id === Number.parseInt(paramBadgeId, 10));
          this.activeSkills = this.activeBadge ? this.sortActiveSkills(this.findSkills(this.activeBadge.skills)) : [];
          this.updateBreadcrumb();
        } else if (paramDimensionId) {
          this.activeDimension = this.dimensions.find((dimension: IDimension) => dimension.id === Number.parseInt(paramDimensionId, 10));

          if (this.activeDimension?.id) {
            const levelsOfActiveDimension: ILevel[] = this.levels.filter(
              (level: ILevel) => level.dimension?.id === this.activeDimension?.id
            );

            const skillsOfActiveDimension: Array<ISkill[]> = levelsOfActiveDimension.map((level: ILevel) => {
              const levelSkillsOfLevel: ILevelSkill[] = this.levelSkills.filter(
                (levelSkill: ILevelSkill) => levelSkill.level?.id === level.id
              );

              return levelSkillsOfLevel
                .map((levelSkill: ILevelSkill) => this.skills.find((skill: ISkill) => skill.id === levelSkill.skill?.id))
                .filter(this.isDefined);
            });

            this.activeSkills = this.sortActiveSkills(([] as Skill[]).concat.apply([], skillsOfActiveDimension));
            this.updateBreadcrumb();
          }
        } else {
          this.activeSkills = this.sortActiveSkills(this.skills);
          this.updateBreadcrumb();
        }
      });
      this.loadAll();
    });
    this.search = '';
    this.search$ = new Subject<string>();
    this.search$.pipe(debounceTime(400), distinctUntilChanged()).subscribe(value => {
      this.search = value;
      return value;
    });
    this.accountService
      .identity()
      .toPromise()
      .then(identity => {
        this.hasAuthority = this.accountService.hasAnyAuthority(ROLES_ALLOWED_TO_UPDATE);
      });
  }

  loadAll(): void {
    this.generalSkillsIds = [];
    this.dimensionsBySkillId = {};
    this.levels.forEach(level => {
      (level.skills ?? []).forEach((levelSkill: ILevelSkill) => {
        const skillId = levelSkill.skill?.id;
        if (skillId) {
          this.dimensionsBySkillId[skillId] = this.dimensionsBySkillId[skillId] || [];
          if (this.dimensionsBySkillId[skillId].indexOf(level.dimension?.id) === -1) {
            this.dimensionsBySkillId[skillId].push(level.dimension?.id);
          }
        }
      });
    });

    this.badges.forEach(badge => {
      if (badge.dimensions?.length === 0) {
        this.generalSkillsIds = this.generalSkillsIds.concat((badge.skills ?? []).map(bs => bs.skill?.id).filter(this.isDefined));
      }

      (badge.dimensions ?? []).forEach(dimension => {
        (badge.skills ?? []).forEach((badgeSkill: IBadgeSkill) => {
          const skillId = badgeSkill.skill?.id;
          if (skillId) {
            this.dimensionsBySkillId[skillId] = this.dimensionsBySkillId[skillId] || [];

            if (this.dimensionsBySkillId[skillId].indexOf(dimension.id) === -1) {
              this.dimensionsBySkillId[skillId].push(dimension.id);
            }
          }
        });
      });
    });
  }

  ngOnChanges(changes: SimpleChanges): void {
    this.updateBreadcrumb();
    this.skillChanged.emit(this.activeSkill);
    if (this.activeSkill?.id) {
      this.skillService.find(this.activeSkill.id).subscribe(response => {
        if (response.body) {
          this.skillClicked.emit({
            storedSkill: response.body,
            // @Fixme Issue #35
            activeSkill: this.activeSkill,
          });
        }
      });
    }
  }

  getRelevantTeams(skill: ISkill): string {
    const countProgress = new Progress(0, 0, 0);
    for (const team of this.teams) {
      const teamSkill = this.findTeamSkill(team, skill);
      if (this.isRelevantSkill(team, teamSkill, skill)) {
        countProgress.required++;
        if (this.isTeamSkillCompleted(teamSkill)) {
          countProgress.achieved++;
        }
      }
    }
    if (skill.id && this.generalSkillsIds.indexOf(skill.id) !== -1) {
      countProgress.required = this.teams.length;
    }
    return `${countProgress.achieved}  / ${countProgress.required}`;
  }

  findSkill(skillId: number): ISkill {
    const foundSkill = this.skills.find(skill => skill.id === skillId);
    if (foundSkill) {
      return foundSkill;
    }

    return new Skill();
  }

  findSkills(itemSkills: ILevelSkill[] | IBadgeSkill[] | null | undefined): ISkill[] {
    if (itemSkills) {
      return (itemSkills as Array<ILevelSkill | IBadgeSkill>)
        .map((itemSkill: ILevelSkill | IBadgeSkill) => this.skills.find(skill => itemSkill.skill?.id === skill.id))
        .filter(this.isDefined);
    }

    return [];
  }

  isActiveSkill(skill: ISkill): boolean {
    return this.activeSkill?.id === skill.id;
  }

  getRateCount(rateCount: number | undefined): number {
    return rateCount ? rateCount : 0;
  }

  onSkillSort(): void {
    this.activeSkills = this.sortActiveSkills(this.activeSkills);
  }

  sortActiveSkills(activeSkills: ISkill[]): ISkill[] {
    return new SkillSortPipe()
      .transform(
        activeSkills.map(activeSkill => {
          if (activeSkill.id) {
            return this.findSkill(activeSkill.id);
          }

          return new Skill();
        }),
        this.orderBy
      )
      .map(skill => activeSkills.find(activeSkill => activeSkill.id === skill.id))
      .filter(this.isDefined);
  }

  private updateBreadcrumb(): void {
    if (this.activeLevel) {
      if (this.activeLevel.dimension?.id) {
        this.dimensionService.find(this.activeLevel.dimension.id).subscribe(dimension => {
          this.breadcrumbService.setBreadcrumb(
            null,
            dimension.body,
            this.undefinedGuard(this.activeLevel),
            this.undefinedGuard(this.activeBadge),
            this.undefinedGuard(this.activeSkill)
          );
        });
      }
    } else {
      this.breadcrumbService.setBreadcrumb(
        null,
        this.undefinedGuard(this.activeDimension),
        this.undefinedGuard(this.activeLevel),
        this.undefinedGuard(this.activeBadge),
        this.undefinedGuard(this.activeSkill)
      );
    }
  }

  private undefinedGuard<T>(value?: T): null | T {
    if (value) {
      return value;
    }

    return null;
  }

  private onError(errorMessage: string): void {
    this.alertService.addAlert({
      type: 'danger',
      message: errorMessage,
    });
  }

  private isRelevantSkill(team: ITeam, teamSkill: ITeamSkill | undefined, skill: ISkill): boolean {
    if (teamSkill?.irrelevant) {
      return false;
    }
    let skillDimensionIds: any[] = [];
    if (skill.id) {
      skillDimensionIds = this.dimensionsBySkillId[skill.id] || [];
    }

    if (team.participations) {
      team.participations.some(dimension => skillDimensionIds.indexOf(dimension.id) !== -1);
    }

    return false;
  }

  private isDefined<T>(val: T | undefined): val is T {
    return val !== undefined;
  }

  private findTeamSkill(team: ITeam, skill: ISkill): ITeamSkill | undefined {
    return team.skills?.find((teamSkill: ITeamSkill) => teamSkill.skill?.id === skill.id);
  }

  private isTeamSkillCompleted(teamSkill: ITeamSkill | undefined): boolean {
    if (teamSkill?.skillStatus) {
      return SkillStatusUtils.isValid(teamSkill.skillStatus);
    }

    return false;
  }
}
