import { Component, Input, OnInit } from '@angular/core';
import { ActivatedRoute, ParamMap } from '@angular/router';
import 'simplebar';

import { ITeam } from 'app/entities/team/team.model';
import { ILevel } from 'app/entities/level/level.model';
import { IBadge } from 'app/entities/badge/badge.model';
import { ISkill } from 'app/entities/skill/skill.model';
import { RelevanceCheck } from 'app/custom/helper/relevance-check';
import { CompletionCheck } from 'app/custom/helper/completion-check';
import { IDimension } from 'app/entities/dimension/dimension.model';
import { TeamScoreCalculation } from 'app/custom/helper/team-score-calculation';
import { TeamScore } from 'app/custom/entities/team-score/team-score.model';
import dayjs from 'dayjs/esm';

@Component({
  selector: 'jhi-overview-teams',
  templateUrl: './overview-teams.component.html',
  styleUrls: ['./overview-teams.scss'],
})
export class OverviewTeamsComponent implements OnInit {
  static readonly EXPIRATION_GRACE_PERIOD_IN_DAYS = 30;
  @Input() teams: ITeam[] = [];
  @Input() levels: ILevel[] = [];
  @Input() badges: IBadge[] = [];
  @Input() skills: ISkill[] = [];
  teamScores: TeamScore[] = [];
  private relevantTeamIds: number[] = [];
  private completedTeamIds: number[] = [];
  private filtered = false;

  constructor(private route: ActivatedRoute) {}

  ngOnInit(): void {
    this.teamScores = [];
    this.route.queryParamMap.subscribe((params: ParamMap) => {
      const badgeId: number | undefined = this.getParamAsNumber('badge', params);
      const levelId: number | undefined = this.getParamAsNumber('level', params);
      const dimensionId: number | undefined = this.getParamAsNumber('dimension', params);

      this.filtered = !!badgeId || !!levelId || !!dimensionId;
      const relevantTeams = this.getRelevantTeams(badgeId, levelId, dimensionId);
      this.completedTeamIds = this.getCompletedTeamIds(relevantTeams, badgeId, levelId, dimensionId);
      this.relevantTeamIds = this.getRelevantTeamIds(relevantTeams);
    });

    const validTeams = this.teams.filter(team => this.isValidTeam(team)).filter(team => this.isNotPureTrainingTeam(team));
    for (const team of validTeams) {
      this.teamScores.push(new TeamScore(team, this._calcTeamScore(team)));
    }
    this.teamScores = this.teamScores.sort(this.compareTeamScores);
    this.teamScores = this.teamScores.reverse();
  }

  // FIXME: #41 Understand the use case and fix it.
  isValidTeam(team: ITeam): boolean {
    // return team.daysUntilExpiration && team.daysUntilExpiration > -90
    return true;
  }

  // FIXME: #41 Fix naming: What is the opposite of a pure training team?
  isNotPureTrainingTeam(team: ITeam): boolean {
    return !team.pureTrainingTeam;
  }

  showAsComplete(team?: ITeam): boolean {
    if (team) {
      return this.filtered && this.isRelevant(team) && this.isCompleted(team);
    }

    return false;
  }

  showAsIncomplete(team?: ITeam): boolean {
    if (team) {
      return this.filtered && this.isRelevant(team) && !this.isCompleted(team);
    }

    return false;
  }

  showAsIrrelevant(team?: ITeam): boolean {
    if (team) {
      return this.filtered && !this.isRelevant(team);
    }

    return false;
  }

  /**
   * Determines whether the team's expiration date should be shown in the UI.
   *
   * @param team the team to decide on
   * @return true if expirationDate should be shown, else false
   */
  showExpirationDate(team?: ITeam): boolean {
    if (team?.expirationDate != null) {
      const now = dayjs();
      const gracePeriodStart = team.expirationDate.subtract(OverviewTeamsComponent.EXPIRATION_GRACE_PERIOD_IN_DAYS, 'day');

      return now.isBefore(gracePeriodStart);
    }

    return false;
  }

  calcLevelBase(team?: ITeam): number {
    const relevantDimensionIds = team?.participations?.map(d => d.id);
    return this.levels.filter(l => relevantDimensionIds?.indexOf(l.dimension?.id) !== -1).length;
  }

  calcCompletedLevel(team?: ITeam): number {
    let count = 0;
    team?.participations?.forEach(dimension => {
      if (dimension.levels) {
        for (const level of dimension.levels) {
          if (!this.isLevelOrBadgeCompleted(team, level)) {
            break;
          }
          count++;
        }
      }
    });
    return count;
  }

  calcCompletedBadges(team?: ITeam): number {
    let count = 0;
    if (team) {
      this.badges.forEach(badge => {
        if (this.isLevelOrBadgeCompleted(team, badge)) {
          count++;
        }
      });
    }
    return count;
  }

  calcTotalCompletedLevel(): number {
    let totalCompletedLevel = 0;
    for (const team of this.teams) {
      totalCompletedLevel += this.calcCompletedLevel(team);
    }
    return totalCompletedLevel;
  }

  calcTotalCompletedBadges(): number {
    let totalCompletedBadges = 0;
    for (const team of this.teams) {
      totalCompletedBadges += this.calcCompletedBadges(team);
    }
    return totalCompletedBadges;
  }

  calcTotalTeamScore(): number {
    let totalTeamScore = 0;
    for (const team of this.teams) {
      totalTeamScore += this._calcTeamScore(team);
    }
    return totalTeamScore;
  }

  getTotalLevelBase(): number {
    let totalLevelBase = 0;
    this.teams.forEach((team: ITeam) => {
      team.participations?.forEach((dimension: IDimension) => {
        if (dimension.levels?.length) {
          totalLevelBase += dimension.levels.length;
        }
      });
    });
    return totalLevelBase;
  }

  public compareTeamScores(left: TeamScore, right: TeamScore): number {
    const rightScore = right.score ? right.score : 0;
    const leftScore = left.score ? left.score : 0;

    if (leftScore > rightScore) {
      return 1;
    }

    if (leftScore < rightScore) {
      return -1;
    }

    return 0;
  }

  private getRelevantTeams(badgeId: number | undefined, levelId: number | undefined, dimensionId: number | undefined): ITeam[] {
    return this.teams.filter((team: ITeam) => {
      const relevanceCheck = new RelevanceCheck(team);
      if (badgeId) {
        const badge = this.badges.find((b: IBadge) => b.id === badgeId);
        return badge && relevanceCheck.isRelevantBadge(badge);
      } else if (levelId) {
        const level = this.levels.find((l: ILevel) => l.id === levelId);
        return level && relevanceCheck.isRelevantLevel(level);
      } else if (dimensionId) {
        return relevanceCheck.isRelevantDimensionId(dimensionId);
      }
      return false;
    });
  }

  private isDefined<T>(input: T | undefined | null): input is T {
    return typeof input !== 'undefined' && input !== null;
  }

  private getCompletedTeamIds(
    relevantTeams: ITeam[],
    badgeId: number | undefined,
    levelId: number | undefined,
    dimensionId: number | undefined
  ): number[] {
    return relevantTeams
      .filter((team: ITeam) => {
        if (badgeId) {
          const badge = this.badges.find((b: IBadge) => b.id === badgeId);
          return badge && new CompletionCheck(team, badge, this.skills).isCompleted();
        } else if (levelId) {
          const level = this.levels.find((l: ILevel) => l.id === levelId);
          return level && new CompletionCheck(team, level, this.skills).isCompleted();
        } else if (dimensionId) {
          const dimensions = team.participations?.find((d: IDimension) => d.id === dimensionId);
          return dimensions?.levels?.every((level: ILevel) => new CompletionCheck(team, level, this.skills).isCompleted());
        }
        return false;
      })
      .map((team: ITeam) => team.id)
      .filter(this.isDefined);
  }

  private getRelevantTeamIds(relevantTeams: ITeam[]): number[] {
    return relevantTeams.map((team: ITeam) => team.id).filter(this.isDefined);
  }

  private isRelevant(team: ITeam): boolean {
    if (team.id) {
      return this.relevantTeamIds.indexOf(team.id) !== -1;
    }

    return false;
  }

  private isCompleted(team: ITeam): boolean {
    if (team.id) {
      return this.completedTeamIds.indexOf(team.id) !== -1;
    }

    return false;
  }

  private _calcTeamScore(team: ITeam): number {
    return TeamScoreCalculation.calcTeamScore(team, this.skills, this.badges);
  }

  private isLevelOrBadgeCompleted(team: ITeam, item: ILevel | IBadge): boolean {
    return new CompletionCheck(team, item, this.skills).isCompleted();
  }

  private getParamAsNumber(name: string, params: ParamMap): number | undefined {
    const val = params.get(name);
    if (val) {
      return Number.parseInt(val, 10);
    }

    return undefined;
  }
}
