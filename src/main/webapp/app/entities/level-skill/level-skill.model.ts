import { ISkill } from 'app/entities/skill/skill.model';
import { ILevel } from 'app/entities/level/level.model';

export interface ILevelSkill {
  id?: number;
  skill?: ISkill;
  level?: ILevel;
}

export class LevelSkill implements ILevelSkill {
  constructor(public id?: number, public skill?: ISkill, public level?: ILevel) {}
}

export function getLevelSkillIdentifier(levelSkill: ILevelSkill): number | undefined {
  return levelSkill.id;
}
