import { IBadge } from 'app/entities/badge/badge.model';
import { ILevel } from 'app/entities/level/level.model';
import { ILevelSkill } from 'app/entities/level-skill/level-skill.model';
import { IBadgeSkill } from 'app/entities/badge-skill/badge-skill.model';
import { ITeam } from 'app/entities/team/team.model';
import { ISkill, Skill } from 'app/entities/skill/skill.model';
import { ITeamSkill } from 'app/entities/team-skill/team-skill.model';
import { SkillStatusUtils } from 'app/custom/entities/skill-status';
import { IProgress, Progress } from 'app/custom/entities/progress/progress.model';

export class CompletionCheck {
  constructor(private team: ITeam | undefined, private item: ILevel | IBadge, private allSkills: ISkill[] | undefined) {}

  public isCompleted(): boolean {
    return this.getProgress().isCompleted();
  }

  public getProgress(): IProgress {
    let score = 0;
    let totalScore = 0;
    if (this.item.skills) {
      for (const itemSkill of this.item.skills) {
        const teamSkill: ITeamSkill | null = this.findTeamSkill(itemSkill);
        if (teamSkill?.irrelevant) {
          continue;
        }
        if (itemSkill.skill?.id) {
          const skill = this.findSkill(itemSkill.skill.id);
          if (skill.score !== undefined) {
            totalScore += skill.score;
            if (teamSkill) {
              if (this.isTeamSkillCompleted(teamSkill)) {
                score += skill.score;
              }
            }
          }
        }
      }
    }
    const requiredScore = this.item.requiredScore ? totalScore * this.item.requiredScore : totalScore;
    return new Progress(score, requiredScore, totalScore);
  }

  public getIrrelevancy(): number {
    let irrelevantScore = 0;
    let totalScore = 0;
    if (this.item.skills) {
      for (const itemSkill of this.item.skills) {
        const teamSkill = this.findTeamSkill(itemSkill);
        if (itemSkill.skill?.id !== undefined) {
          const skill = this.findSkill(itemSkill.skill.id);
          if (skill.score !== undefined) {
            if (teamSkill?.irrelevant) {
              irrelevantScore += skill.score;
            }
            totalScore += skill.score;
          }
        }
      }
    }
    return totalScore !== 0 ? (irrelevantScore / totalScore) * 100.0 : 0;
  }

  private isTeamSkillCompleted(teamSkill: ITeamSkill): boolean {
    if (teamSkill.skillStatus) {
      return SkillStatusUtils.isValid(teamSkill.skillStatus);
    }
    return false;
  }

  private findTeamSkill(itemSkill: ILevelSkill | IBadgeSkill): ITeamSkill | null {
    const res: ITeamSkill | undefined = this.team?.skills?.find((s: ITeamSkill) => s.skill?.id === itemSkill.skill?.id);
    return res ? res : null;
  }

  private findSkill(skillId: number): ISkill {
    const res: ISkill | undefined = this.allSkills?.find(s => s.id === skillId);
    return res ? res : new Skill();
  }
}
