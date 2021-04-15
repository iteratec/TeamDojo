import { ApplicationMode } from '@/shared/model/enumerations/application-mode.model';
export interface IOrganisation {
  id?: number;
  title?: string;
  description?: string | null;
  levelUpScore?: number | null;
  applicationMode?: ApplicationMode;
  countOfConfirmations?: number | null;
  createdAt?: Date;
  updatedAt?: Date;
  parent?: IOrganisation | null;
}

export class Organisation implements IOrganisation {
  constructor(
    public id?: number,
    public title?: string,
    public description?: string | null,
    public levelUpScore?: number | null,
    public applicationMode?: ApplicationMode,
    public countOfConfirmations?: number | null,
    public createdAt?: Date,
    public updatedAt?: Date,
    public parent?: IOrganisation | null
  ) {}
}
