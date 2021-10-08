import { ISkill } from 'app/entities/skill/skill.model';
import { IAchievableSkill } from 'app/custom/entities/achievable-skill/achievable-skill.model';

export interface ISkillObjects {
  iSkill: ISkill;
  aSkill: IAchievableSkill;
}

export class SkillObjects implements ISkillObjects {
  constructor(public iSkill: ISkill, public aSkill: IAchievableSkill) {}
}
