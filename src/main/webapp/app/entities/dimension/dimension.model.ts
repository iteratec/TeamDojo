import * as dayjs from 'dayjs';
import { ILevel } from 'app/entities/level/level.model';
import { IBadge } from 'app/entities/badge/badge.model';
import { ITeam } from 'app/entities/team/team.model';

export interface IDimension {
  id?: number;
  title?: string;
  description?: string | null;
  createdAt?: dayjs.Dayjs;
  updatedAt?: dayjs.Dayjs;
  levels?: ILevel[] | null;
  badges?: IBadge[] | null;
  participants?: ITeam[] | null;
}

export class Dimension implements IDimension {
  constructor(
    public id?: number,
    public title?: string,
    public description?: string | null,
    public createdAt?: dayjs.Dayjs,
    public updatedAt?: dayjs.Dayjs,
    public levels?: ILevel[] | null,
    public badges?: IBadge[] | null,
    public participants?: ITeam[] | null
  ) {}
}

export function getDimensionIdentifier(dimension: IDimension): number | undefined {
  return dimension.id;
}