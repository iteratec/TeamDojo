import { IDimension } from 'app/entities/dimension/dimension.model';
import { ILevel } from 'app/entities/level/level.model';

export interface IHighestLevel {
  dimension?: IDimension;
  level?: ILevel;
  ordinal?: number;
}

export class HighestLevel implements IHighestLevel {
  constructor(public dimension?: IDimension, public level?: ILevel, public ordinal?: number) {}
}
