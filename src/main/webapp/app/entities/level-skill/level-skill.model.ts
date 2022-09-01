import { ISkill } from 'app/entities/skill/skill.model';
import { ILevel } from 'app/entities/level/level.model';

export interface ILevelSkill {
  id: number;
  skill?: Pick<ISkill, 'id' | 'titleEN'> | null;
  level?: Pick<ILevel, 'id' | 'titleEN'> | null;
}

export type NewLevelSkill = Omit<ILevelSkill, 'id'> & { id: null };
