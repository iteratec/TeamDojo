import dayjs from 'dayjs/esm';
import { IBadge } from 'app/entities/badge/badge.model';
import { ITeam } from 'app/entities/team/team.model';

export interface IDimension {
  id: number;
  titleEN?: string | null;
  titleDE?: string | null;
  descriptionEN?: string | null;
  descriptionDE?: string | null;
  createdAt?: dayjs.Dayjs | null;
  updatedAt?: dayjs.Dayjs | null;
  badges?: Pick<IBadge, 'id'>[] | null;
  participants?: Pick<ITeam, 'id'>[] | null;
}

export type NewDimension = Omit<IDimension, 'id'> & { id: null };
