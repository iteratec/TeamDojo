import { IBadgeSkill } from '@/shared/model/badge-skill.model';
import { IImage } from '@/shared/model/image.model';
import { IDimension } from '@/shared/model/dimension.model';

export interface IBadge {
  id?: number;
  title?: string;
  description?: string | null;
  availableUntil?: Date | null;
  availableAmount?: number | null;
  requiredScore?: number;
  instantMultiplier?: number;
  completionBonus?: number | null;
  createdAt?: Date;
  updatedAt?: Date;
  skills?: IBadgeSkill[] | null;
  image?: IImage | null;
  dimensions?: IDimension[] | null;
}

export class Badge implements IBadge {
  constructor(
    public id?: number,
    public title?: string,
    public description?: string | null,
    public availableUntil?: Date | null,
    public availableAmount?: number | null,
    public requiredScore?: number,
    public instantMultiplier?: number,
    public completionBonus?: number | null,
    public createdAt?: Date,
    public updatedAt?: Date,
    public skills?: IBadgeSkill[] | null,
    public image?: IImage | null,
    public dimensions?: IDimension[] | null
  ) {}
}
