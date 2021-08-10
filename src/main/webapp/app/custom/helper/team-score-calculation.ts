import { ITeam } from 'app/entities/team/team.model';
import { ISkill } from 'app/entities/skill/skill.model';
import { IBadge } from 'app/entities/badge/badge.model';
import { ILevel } from 'app/entities/level/level.model';
import { ITeamSkill } from 'app/entities/team-skill/team-skill.model';
import { SkillStatusUtils } from 'app/custom/entities/skill-status';
import { RelevanceCheck } from 'app/custom/helper/relevance-check';
import { CompletionCheck } from 'app/custom/helper/completion-check';

export class TeamScoreCalculation {
  static calcTeamScore(team: ITeam, skills: ISkill[], badges: IBadge[]): number {
    let score = this._calcSkillScore(team, skills);
    score += this._calcLevelBonus(team, skills);
    score += this._calcBadgeBonus(team, badges, skills);
    return score;
  }

  private static _calcSkillScore(team: ITeam, skills: ISkill[]): number {
    let score = 0;
    skills.forEach((skill: ISkill) => {
      if (this._isSkillCompleted(team, skill)) {
        if (skill.score) {
          score += skill.score;
        }
      }
    });
    return score;
  }

  private static _calcLevelBonus(team: ITeam, skills: ISkill[]): number {
    let score = 0;
    (team.participations ?? []).forEach(dimension => {
      (dimension.levels ?? []).forEach((level: ILevel) => {
        score += this._getBonus(team, level, skills);
      });
    });
    return score;
  }

  private static _calcBadgeBonus(team: ITeam, badges: IBadge[], skills: ISkill[]): number {
    let score = 0;
    badges.forEach((badge: IBadge) => {
      if (new RelevanceCheck(team).isRelevantLevelOrBadge(badge)) {
        score += this._getBonus(team, badge, skills);
      }
    });
    return score;
  }

  private static _isSkillCompleted(team: ITeam, skill: ISkill): boolean {
    const teamSkill = (team.skills ?? []).find((ts: ITeamSkill) => ts.skill?.id === skill.id);
    return teamSkill && SkillStatusUtils.isValid(teamSkill.skillStatus);
  }

  private static _getBonus(team: ITeam, item: ILevel | IBadge, skills: ISkill[]): number {
    if (!item.instantMultiplier && !item.completionBonus) {
      return 0;
    }
    const levelProgress = new CompletionCheck(team, item, skills).getProgress();

    let score: number = levelProgress.achieved;
    if (item.instantMultiplier) {
      score *= item.instantMultiplier;
    }
    if (levelProgress.isCompleted()) {
      if (item.completionBonus) {
        score += item.completionBonus;
      }
    }
    return score;
  }
}
