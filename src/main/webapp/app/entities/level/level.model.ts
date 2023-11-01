import dayjs from 'dayjs/esm';
import { IImage } from 'app/entities/image/image.model';
import { IDimension } from 'app/entities/dimension/dimension.model';

export interface ILevel {
  id: number;
  titleEN?: string | null;
  titleDE?: string | null;
  descriptionEN?: string | null;
  descriptionDE?: string | null;
  requiredScore?: number | null;
  instantMultiplier?: number | null;
  completionBonus?: number | null;
  createdAt?: dayjs.Dayjs | null;
  updatedAt?: dayjs.Dayjs | null;
  dependsOn?: Pick<ILevel, 'id' | 'titleEN'> | null;
  image?: Pick<IImage, 'id' | 'title'> | null;
  dimension?: Pick<IDimension, 'id' | 'titleEN'> | null;
}

export type NewLevel = Omit<ILevel, 'id'> & { id: null };
