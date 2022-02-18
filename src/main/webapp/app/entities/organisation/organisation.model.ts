import dayjs from 'dayjs/esm';

export interface IOrganisation {
  id?: number;
  title?: string;
  description?: string | null;
  levelUpScore?: number | null;
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
    public countOfConfirmations?: number | null,
    public createdAt?: dayjs.Dayjs,
    public updatedAt?: dayjs.Dayjs,
    public parent?: IOrganisation | null
  ) {}
}

export function getOrganisationIdentifier(organisation: IOrganisation): number | undefined {
  return organisation.id;
}
