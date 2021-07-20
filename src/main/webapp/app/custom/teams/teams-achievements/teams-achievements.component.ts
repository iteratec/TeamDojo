import { Component, OnInit, Input } from '@angular/core';
import { ActivatedRoute, ParamMap, Router } from '@angular/router';
import { NgbPanelChangeEvent } from '@ng-bootstrap/ng-bootstrap';

import { IBadge } from 'app/entities/badge/badge.model';
import { ITeam } from 'app/entities/team/team.model';
import { ILevel } from 'app/entities/level/level.model';
import { IDimension } from 'app/entities/dimension/dimension.model';
import { ITeamSkill } from 'app/entities/team-skill/team-skill.model';
import { ISkill } from 'app/entities/skill/skill.model';
import { ILevelSkill } from 'app/entities/level-skill/level-skill.model';

import { AccountService } from 'app/core/auth/account.service';

const ROLES_ALLOWED_TO_UPDATE = ['ROLE_ADMIN'];

@Component({
  selector: 'jhi-teams-achievements',
  templateUrl: './teams-achievements.component.html',
  styleUrls: ['./teams-achievements.component.scss'],
})
export class TeamsAchievementsComponent implements OnInit {
  @Input() team!: ITeam; // ! after the variable name signals the compiler that the variable is initialized  elsewhere. See Migration.md
  @Input() teamSkills!: ITeamSkill[];
  @Input() badges!: IBadge[];
  @Input() skills!: ISkill[];
  generalBadges!: IBadge[];
  activeItemIds!: { badge: number | null; level: number | null; dimension: number | null };
  expandedDimensions!: string[];
  hasAuthority = false;

  constructor(private route: ActivatedRoute, private router: Router, private accountService: AccountService) {}

  ngOnInit(): void {
    this.generalBadges = this.badges.filter((badge: IBadge) => !badge.dimensions || !badge.dimensions.length);
    this.expandedDimensions = [];
    this.team.skills = this.teamSkills;

    this.route.queryParamMap.subscribe((params: ParamMap) => {
      const dimensionId = this.getParamAsNumber('dimension', params);
      const levelId = this.getParamAsNumber('level', params);
      const badgeId = this.getParamAsNumber('badge', params);
      this.activeItemIds = {
        badge: null,
        level: null,
        dimension: null,
      };

      if (dimensionId) {
        const dimension = this.team.participations!.find((d: IDimension) => d.id === dimensionId);

        if (dimension) {
          this.activeItemIds.dimension = dimensionId;
          this.setExpandedDimensionId(dimensionId);
        }
      }
      if (levelId) {
        const dimension: IDimension | undefined = this.team.participations?.find((d: IDimension) =>
          d.levels?.some((l: ILevel) => l.id === levelId)
        );
        if (dimension?.id && dimension.levels) {
          this.setExpandedDimensionId(dimension.id);
          const level = dimension.levels.find((l: ILevel) => l.id === levelId);
          if (level?.id) {
            this.activeItemIds.level = level.id;
          }
        }
      } else if (badgeId) {
        const dimension = this.team.participations?.find((d: IDimension) => d.badges?.some((b: IBadge) => b.id === badgeId));
        let badge;
        if (dimension?.id) {
          this.activeItemIds.dimension = dimension.id;
          this.setExpandedDimensionId(dimension.id);
          badge = dimension.badges?.find((b: IBadge) => b.id === badgeId);
        } else {
          badge = this.generalBadges.find((b: IBadge) => b.id === badgeId);
        }
        if (badge?.id) {
          this.activeItemIds.badge = badge.id;
        }
      } else if (this.team.participations?.length) {
        const completedSkills: Array<ITeamSkill> | undefined = this.teamSkills.filter(teamSkill => teamSkill.completedAt);
        const dimensions: Array<IDimension | undefined> | undefined = completedSkills
          .map(completedSkill =>
            this.team.participations?.find((dimension: IDimension) =>
              dimension.levels?.some((level: ILevel) =>
                level.skills?.some((skill: ILevelSkill) => skill.skill?.id === completedSkill.skill?.id)
              )
            )
          )
          .filter(dimension => dimension !== undefined);
        const dimensionIds: Array<number | undefined> | undefined = dimensions.map(dimension => dimension?.id);
        const uniqueDimensionIds = dimensionIds.filter((id): id is number => !!id).filter((el, i, a) => i === a.indexOf(el)); // filter duplicates
        uniqueDimensionIds.forEach(id => this.setExpandedDimensionId(id));
      }
    });

    this.accountService
      .identity()
      .toPromise()
      .then(() => {
        this.hasAuthority = this.accountService.hasAnyAuthority(ROLES_ALLOWED_TO_UPDATE);
      });
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

  private setExpandedDimensionId(dimensionId: number): void {
    this.expandedDimensions.push(`achievements-dimension-${dimensionId}`);
  }

  private getParamAsNumber(name: string, params: ParamMap): number | undefined {
    const res = params.get(name);
    if (res) {
      return Number.parseInt(res, 10);
    }
    return undefined;
  }
}
