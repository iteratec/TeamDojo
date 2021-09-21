import { Component, Input, OnInit } from '@angular/core';
import { ActivatedRoute, ParamMap, Router } from '@angular/router';
import { HttpResponse } from '@angular/common/http';
import { NgbPanelChangeEvent } from '@ng-bootstrap/ng-bootstrap';

import { ITeam } from 'app/entities/team/team.model';
import { ILevel } from 'app/entities/level/level.model';
import { IBadge } from 'app/entities/badge/badge.model';
import { IDimension } from 'app/entities/dimension/dimension.model';
import { DimensionService } from 'app/entities/dimension/service/dimension.service';
import { ISkill } from 'app/entities/skill/skill.model';
import { AccountService } from 'app/core/auth/account.service';
import { sortLevels } from 'app/custom/helper/level-util';
import { CompletionCheck } from 'app/custom/helper/completion-check';
import { RelevanceCheck } from 'app/custom/helper/relevance-check';

import 'simplebar';

const ROLES_ALLOWED_TO_UPDATE = ['ROLE_ADMIN'];

@Component({
  selector: 'jhi-overview-achievements',
  templateUrl: './overview-achievements.component.html',
  styleUrls: ['./overview-achievements.scss'],
})
export class OverviewAchievementsComponent implements OnInit {
  @Input() teams: ITeam[] = [];
  @Input() levels: ILevel[] = [];
  @Input() badges: IBadge[] = [];
  @Input() skills: ISkill[] = [];
  dimensions: IDimension[] = [];
  generalBadges: IBadge[] = [];
  activeItemIds: { [key: string]: number | null } = {};
  expandedDimensions: string[] = [];
  hasAuthority = false;

  constructor(
    private route: ActivatedRoute,
    private dimensionService: DimensionService,
    private router: Router,
    private accountService: AccountService
  ) {}

  ngOnInit(): void {
    this.dimensions = [];
    this.activeItemIds = {
      badge: null,
      level: null,
      dimension: null,
    };
    this.generalBadges = [];
    this.expandedDimensions = [];
    this.dimensionService.query().subscribe((res: HttpResponse<IDimension[]>) => {
      if (res.body) {
        this.dimensions = res.body;
      }

      const levelsByDimensionId: { [index: number]: ILevel[] } = {};
      this.levels.forEach((level: ILevel) => {
        if (level.dimension?.id) {
          levelsByDimensionId[level.dimension.id] = levelsByDimensionId[level.dimension.id] || [];
          levelsByDimensionId[level.dimension.id].push(Object.assign(level));
        }
      });

      const badgesByDimensionId: { [index: number]: IBadge[] } = {};
      this.badges.forEach((badge: IBadge) => {
        if (badge.dimensions?.length) {
          badge.dimensions.forEach((dimension: IDimension) => {
            if (dimension.id) {
              badgesByDimensionId[dimension.id] = badgesByDimensionId[dimension.id] || [];
              badgesByDimensionId[dimension.id].push(Object.assign(badge));
            }
          });
        } else {
          this.generalBadges.push(Object.assign(badge));
        }
      });

      this.dimensions.forEach((dimension: IDimension) => {
        if (dimension.id) {
          dimension.levels = (sortLevels(levelsByDimensionId[dimension.id]) || []).reverse();
          dimension.badges = badgesByDimensionId[dimension.id] || [];
        }
      });
    });

    this.route.queryParamMap.subscribe((params: ParamMap) => {
      const levelId = this.getParamAsNumber('level', params);
      const badgeId = this.getParamAsNumber('badge', params);
      const dimensionId = this.getParamAsNumber('dimension', params);
      this.activeItemIds = {
        badge: null,
        level: null,
        dimension: null,
      };

      if (levelId) {
        this.activeItemIds.level = levelId;
        this.levels
          .filter(l => l.id === levelId)
          .forEach(l => {
            if (l.dimension?.id) {
              this.setDimensionPanelActiveState(`achievements-dimension-${l.dimension.id}`, true);
            }
          });
      } else if (badgeId) {
        this.activeItemIds.badge = badgeId;
        const foundBadge = this.badges.find(b => b.id === badgeId);
        if (foundBadge) {
          foundBadge.dimensions?.forEach(d => {
            if (d.id) {
              this.setDimensionPanelActiveState(`achievements-dimension-${d.id}`, true);
            }
          });
        }
      } else if (dimensionId) {
        this.activeItemIds.dimension = dimensionId;
      }
    });

    setTimeout(() => {
      this.accountService
        .identity()
        .toPromise()
        .then(identity => {
          this.hasAuthority = this.accountService.hasAnyAuthority(ROLES_ALLOWED_TO_UPDATE);
        });
    }, 0);
  }

  handleDimensionToggle(event: NgbPanelChangeEvent): void {
    this.setDimensionPanelActiveState(event.panelId, event.nextState);
  }

  setDimensionPanelActiveState(panelId: string, expanded: boolean): void {
    if (expanded) {
      if (!this.expandedDimensions.includes(panelId)) {
        this.expandedDimensions.push(panelId);
      }
    } else {
      const idx = this.expandedDimensions.findIndex(d => panelId === d);
      if (idx !== -1) {
        this.expandedDimensions.splice(idx, 1);
      }
    }
  }

  getAchievementProgress(item: ILevel | IBadge): number {
    let baseCount = 0;
    let completedCount = 0;
    this.teams.forEach((team: ITeam) => {
      if (this.isRelevant(team, item)) {
        baseCount++;
        if (this.isLevelOrBadgeCompleted(team, item)) {
          completedCount++;
        }
      }
    });
    return baseCount === 0 ? 0 : (completedCount / baseCount) * 100;
  }

  selectItem(itemType: string, itemId: number | undefined): void {
    if (itemType && itemId && itemId >= 0) {
      for (const availableItemType in this.activeItemIds) {
        if (Object.prototype.hasOwnProperty.call(this.activeItemIds, availableItemType) && availableItemType !== itemType) {
          this.activeItemIds[availableItemType] = null;
        }
      }
      if (this.activeItemIds[itemType] === itemId) {
        this.activeItemIds[itemType] = null;
        this.router.navigate(['.']);
      } else {
        this.activeItemIds[itemType] = itemId;
        this.router.navigate(['.'], {
          queryParams: { [itemType]: this.activeItemIds[itemType] },
        });
      }
    }
  }

  private isLevelOrBadgeCompleted(team: ITeam, item: ILevel | IBadge): boolean {
    return new CompletionCheck(team, item, this.skills).isCompleted();
  }

  private isRelevant(team: ITeam, item: ILevel | IBadge): boolean {
    return new RelevanceCheck(team).isRelevantLevelOrBadge(item);
  }

  private getParamAsNumber(name: string, params: ParamMap): number | undefined {
    const param = params.get(name);
    if (param) {
      return Number.parseInt(param, 10);
    }

    return undefined;
  }
}
