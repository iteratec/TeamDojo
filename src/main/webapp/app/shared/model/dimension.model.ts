import { ILevel } from '@/shared/model/level.model';
import { IBadge } from '@/shared/model/badge.model';
import { ITeam } from '@/shared/model/team.model';

export interface IDimension {
  id?: number;
  title?: string;
  description?: string | null;
  createdAt?: Date;
  updatedAt?: Date;
  levels?: ILevel[] | null;
  badges?: IBadge[] | null;
  participants?: ITeam[] | null;
}

export class Dimension implements IDimension {
  constructor(
    public id?: number,
    public title?: string,
    public description?: string | null,
    public createdAt?: Date,
    public updatedAt?: Date,
    public levels?: ILevel[] | null,
    public badges?: IBadge[] | null,
    public participants?: ITeam[] | null
  ) {}
}
