import dayjs from 'dayjs/esm';
import { IImage } from 'app/entities/image/image.model';
import { IDimension } from 'app/entities/dimension/dimension.model';

export interface IBadge {
  id: number;
  titleEN?: string | null;
  titleDE?: string | null;
  descriptionEN?: string | null;
  descriptionDE?: string | null;
  availableUntil?: dayjs.Dayjs | null;
  availableAmount?: number | null;
  requiredScore?: number | null;
  instantMultiplier?: number | null;
  completionBonus?: number | null;
  createdAt?: dayjs.Dayjs | null;
  updatedAt?: dayjs.Dayjs | null;
  image?: Pick<IImage, 'id' | 'title'> | null;
  dimensions?: Pick<IDimension, 'id' | 'titleEN'>[] | null;
}

export type NewBadge = Omit<IBadge, 'id'> & { id: null };
