import { ISkill } from '@/shared/model/skill.model';

export interface ITraining {
  id?: number;
  title?: string;
  description?: string | null;
  contact?: string | null;
  link?: string | null;
  validUntil?: Date | null;
  isOfficial?: boolean;
  suggestedBy?: string | null;
  createdAt?: Date;
  updatedAt?: Date;
  skills?: ISkill[] | null;
}

export class Training implements ITraining {
  constructor(
    public id?: number,
    public title?: string,
    public description?: string | null,
    public contact?: string | null,
    public link?: string | null,
    public validUntil?: Date | null,
    public isOfficial?: boolean,
    public suggestedBy?: string | null,
    public createdAt?: Date,
    public updatedAt?: Date,
    public skills?: ISkill[] | null
  ) {
    this.isOfficial = this.isOfficial ?? false;
  }
}
