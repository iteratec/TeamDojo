import { ISkill } from '@/shared/model/skill.model';
import { ILevel } from '@/shared/model/level.model';

export interface ILevelSkill {
  id?: number;
  skill?: ISkill;
  level?: ILevel;
}

export class LevelSkill implements ILevelSkill {
  constructor(public id?: number, public skill?: ISkill, public level?: ILevel) {}
}
