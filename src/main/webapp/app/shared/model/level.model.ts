import { ILevelSkill } from '@/shared/model/level-skill.model';
import { IImage } from '@/shared/model/image.model';
import { IDimension } from '@/shared/model/dimension.model';

export interface ILevel {
  id?: number;
  title?: string;
  description?: string | null;
  requiredScore?: number;
  instantMultiplier?: number;
  completionBonus?: number | null;
  createdAt?: Date;
  updatedAt?: Date;
  dependsOn?: ILevel | null;
  skills?: ILevelSkill[] | null;
  image?: IImage | null;
  dimension?: IDimension;
}

export class Level implements ILevel {
  constructor(
    public id?: number,
    public title?: string,
    public description?: string | null,
    public requiredScore?: number,
    public instantMultiplier?: number,
    public completionBonus?: number | null,
    public createdAt?: Date,
    public updatedAt?: Date,
    public dependsOn?: ILevel | null,
    public skills?: ILevelSkill[] | null,
    public image?: IImage | null,
    public dimension?: IDimension
  ) {}
}
