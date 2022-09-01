import dayjs from 'dayjs/esm';

export interface ITeamGroup {
  id: number;
  title?: string | null;
  description?: string | null;
  createdAt?: dayjs.Dayjs | null;
  updatedAt?: dayjs.Dayjs | null;
  parent?: Pick<ITeamGroup, 'id' | 'title'> | null;
}

export type NewTeamGroup = Omit<ITeamGroup, 'id'> & { id: null };
