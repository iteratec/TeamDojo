import { SkillStatus } from 'app/entities/enumerations/skill-status.model';

export class SkillStatusUtils {
  public static isValid(skillStatus: SkillStatus): boolean {
    return skillStatus === SkillStatus.ACHIEVED || skillStatus === SkillStatus.EXPIRING;
  }

  public static isInvalid(skillStatus: SkillStatus): boolean {
    return skillStatus === SkillStatus.OPEN || skillStatus === SkillStatus.EXPIRED;
  }

  public static getLowerCaseValue(skillStatus: SkillStatus): string {
    return skillStatus.toLowerCase();
  }
}
