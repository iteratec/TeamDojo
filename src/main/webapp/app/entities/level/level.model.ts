import dayjs from 'dayjs/esm';
import { ILevelSkill } from 'app/entities/level-skill/level-skill.model';
import { IImage } from 'app/entities/image/image.model';
import { IDimension } from 'app/entities/dimension/dimension.model';

export interface ILevel {
  id?: number;
  titleEN?: string;
  descriptionEN?: string | null;
  requiredScore?: number;
  instantMultiplier?: number;
  completionBonus?: number | null;
  createdAt?: dayjs.Dayjs;
  updatedAt?: dayjs.Dayjs;
  dependsOn?: ILevel | null;
  skills?: ILevelSkill[] | null;
  image?: IImage | null;
  dimension?: IDimension;
}

export class Level implements ILevel {
  constructor(
    public id?: number,
    public titleEN?: string,
    public descriptionEN?: string | null,
    public requiredScore?: number,
    public instantMultiplier?: number,
    public completionBonus?: number | null,
    public createdAt?: dayjs.Dayjs,
    public updatedAt?: dayjs.Dayjs,
    public dependsOn?: ILevel | null,
    public skills?: ILevelSkill[] | null,
    public image?: IImage | null,
    public dimension?: IDimension
  ) {}
}

export function getLevelIdentifier(level: ILevel): number | undefined {
  return level.id;
}
