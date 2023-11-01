import dayjs from 'dayjs/esm';
import { IImage } from 'app/entities/image/image.model';
import { IDimension } from 'app/entities/dimension/dimension.model';
import { ITeamGroup } from 'app/entities/team-group/team-group.model';

export interface ITeam {
  id: number;
  title?: string | null;
  shortTitle?: string | null;
  slogan?: string | null;
  contact?: string | null;
  expirationDate?: dayjs.Dayjs | null;
  official?: boolean | null;
  createdAt?: dayjs.Dayjs | null;
  updatedAt?: dayjs.Dayjs | null;
  image?: Pick<IImage, 'id' | 'title'> | null;
  participations?: Pick<IDimension, 'id' | 'titleEN'>[] | null;
  group?: Pick<ITeamGroup, 'id' | 'title'> | null;
}

export type NewTeam = Omit<ITeam, 'id'> & { id: null };
