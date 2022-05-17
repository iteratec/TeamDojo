import dayjs from 'dayjs/esm';
import { ILevel } from 'app/entities/level/level.model';
import { IBadge } from 'app/entities/badge/badge.model';
import { ITeam } from 'app/entities/team/team.model';

export interface IDimension {
  id?: number;
  titleEN?: string;
  descriptionEN?: string | null;
  createdAt?: dayjs.Dayjs;
  updatedAt?: dayjs.Dayjs;
  levels?: ILevel[] | null;
  badges?: IBadge[] | null;
  participants?: ITeam[] | null;
}

export class Dimension implements IDimension {
  constructor(
    public id?: number,
    public titleEN?: string,
    public descriptionEN?: string | null,
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
