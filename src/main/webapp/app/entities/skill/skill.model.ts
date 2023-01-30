import dayjs from 'dayjs/esm';
import { ITraining } from 'app/entities/training/training.model';

export interface ISkill {
  id: number;
  titleEN?: string | null;
  titleDE?: string | null;
  descriptionEN?: string | null;
  descriptionDE?: string | null;
  implementationEN?: string | null;
  implementationDE?: string | null;
  validationEN?: string | null;
  validationDE?: string | null;
  expiryPeriod?: number | null;
  contact?: string | null;
  score?: number | null;
  rateScore?: number | null;
  rateCount?: number | null;
  createdAt?: dayjs.Dayjs | null;
  updatedAt?: dayjs.Dayjs | null;
  trainings?: Pick<ITraining, 'id'>[] | null;
}

export type NewSkill = Omit<ISkill, 'id'> & { id: null };
