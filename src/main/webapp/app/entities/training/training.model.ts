import dayjs from 'dayjs/esm';
import { ISkill } from 'app/entities/skill/skill.model';

export interface ITraining {
  id: number;
  titleEN?: string | null;
  titleDE?: string | null;
  descriptionEN?: string | null;
  descriptionDE?: string | null;
  contact?: string | null;
  link?: string | null;
  validUntil?: dayjs.Dayjs | null;
  isOfficial?: boolean | null;
  suggestedBy?: string | null;
  createdAt?: dayjs.Dayjs | null;
  updatedAt?: dayjs.Dayjs | null;
  skills?: Pick<ISkill, 'id' | 'titleEN'>[] | null;
}

export type NewTraining = Omit<ITraining, 'id'> & { id: null };
