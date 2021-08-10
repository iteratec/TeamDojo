import { IBadge } from 'app/entities/badge/badge.model';
import { ILevel } from 'app/entities/level/level.model';
import { ILevelSkill } from 'app/entities/level-skill/level-skill.model';
import { IBadgeSkill } from 'app/entities/badge-skill/badge-skill.model';
import { ITeam } from 'app/entities/team/team.model';
import { IProgress, Progress } from 'app/entities/achievement/model/progress.model';
import { ITeamSkill } from 'app/entities/tean-skill/team-skill.model';
import { ISkill } from 'app/entities/skill/skill.model';
import { SkillStatusUtils } from 'app/entities/skill-status/skill-status';

export class CompletionCheck {
  constructor(private team: ITeam, private item: ILevel | IBadge, private allSkills: ISkill[]) {}

  public isCompleted(): boolean {
    return this.getProgress().isCompleted();
  }

  public getProgress(): IProgress {
    let score = 0;
    let totalScore = 0;
    if (this.item.skills) {
      for (const itemSkill of this.item.skills) {
        const teamSkill: ITeamSkill = this.findTeamSkill(itemSkill);
        if (teamSkill && teamSkill.irrelevant) {
          continue;
        }
        const skill = this.findSkill(itemSkill.skillId);
        totalScore += skill.score;
        if (this.isTeamSkillCompleted(teamSkill)) {
          score += skill.score;
        }
      }
    }
    const requiredScore = totalScore * this.item.requiredScore;
    return new Progress(score, requiredScore, totalScore);
  }

  public getIrrelevancy(): number {
    let irrelevantScore = 0;
    let totalScore = 0;
    if (this.item.skills) {
      for (const itemSkill of this.item.skills) {
        const teamSkill = this.findTeamSkill(itemSkill);
        const skill = this.findSkill(itemSkill.skillId);
        if (teamSkill && teamSkill.irrelevant) {
          irrelevantScore += skill.score;
        }
        totalScore += skill.score;
      }
    }
    return totalScore !== 0 ? (irrelevantScore / totalScore) * 100.0 : 0;
  }

  private isTeamSkillCompleted(teamSkill: ITeamSkill): boolean {
    return teamSkill && SkillStatusUtils.isValid(teamSkill.skillStatus);
  }

  private findTeamSkill(itemSkill: ILevelSkill | IBadgeSkill): ITeamSkill {
    return this.team.skills ? this.team.skills.find((s: ITeamSkill) => s.skillId === itemSkill.skillId) : null;
  }

  private findSkill(skillId: number): ISkill {
    return this.allSkills.find(s => s.id === skillId);
  }
}
