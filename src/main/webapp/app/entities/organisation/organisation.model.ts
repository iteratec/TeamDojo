import * as dayjs from 'dayjs';
import { ApplicationMode } from 'app/entities/enumerations/application-mode.model';

export interface IOrganisation {
  id?: number;
  title?: string;
  description?: string | null;
  levelUpScore?: number | null;
  applicationMode?: ApplicationMode;
  countOfConfirmations?: number | null;
  createdAt?: dayjs.Dayjs;
  updatedAt?: dayjs.Dayjs;
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
    public createdAt?: dayjs.Dayjs,
    public updatedAt?: dayjs.Dayjs,
    public parent?: IOrganisation | null
  ) {}
}

export function getOrganisationIdentifier(organisation: IOrganisation): number | undefined {
  return organisation.id;
}
