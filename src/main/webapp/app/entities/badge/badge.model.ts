import dayjs from 'dayjs/esm';
import { IBadgeSkill } from 'app/entities/badge-skill/badge-skill.model';
import { IImage } from 'app/entities/image/image.model';
import { IDimension } from 'app/entities/dimension/dimension.model';

export interface IBadge {
  id?: number;
  titleEN?: string;
  titleDE?: string | null;
  descriptionEN?: string | null;
  descriptionDE?: string | null;
  availableUntil?: dayjs.Dayjs | null;
  availableAmount?: number | null;
  requiredScore?: number;
  instantMultiplier?: number;
  completionBonus?: number | null;
  createdAt?: dayjs.Dayjs;
  updatedAt?: dayjs.Dayjs;
  skills?: IBadgeSkill[] | null;
  image?: IImage | null;
  dimensions?: IDimension[] | null;
}

export class Badge implements IBadge {
  constructor(
    public id?: number,
    public titleEN?: string,
    public titleDE?: string | null,
    public descriptionEN?: string | null,
    public descriptionDE?: string | null,
    public availableUntil?: dayjs.Dayjs | null,
    public availableAmount?: number | null,
    public requiredScore?: number,
    public instantMultiplier?: number,
    public completionBonus?: number | null,
    public createdAt?: dayjs.Dayjs,
    public updatedAt?: dayjs.Dayjs,
    public skills?: IBadgeSkill[] | null,
    public image?: IImage | null,
    public dimensions?: IDimension[] | null
  ) {}
}

export function getBadgeIdentifier(badge: IBadge): number | undefined {
  return badge.id;
}
