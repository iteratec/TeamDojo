import { ITeamSkill } from '@/shared/model/team-skill.model';
import { IImage } from '@/shared/model/image.model';
import { IDimension } from '@/shared/model/dimension.model';

export interface ITeam {
  id?: number;
  title?: string;
  shortTitle?: string;
  slogan?: string | null;
  contact?: string | null;
  validUntil?: Date | null;
  pureTrainingTeam?: boolean;
  official?: boolean;
  createdAt?: Date;
  updatedAt?: Date;
  skills?: ITeamSkill[] | null;
  image?: IImage | null;
  participations?: IDimension[] | null;
}

export class Team implements ITeam {
  constructor(
    public id?: number,
    public title?: string,
    public shortTitle?: string,
    public slogan?: string | null,
    public contact?: string | null,
    public validUntil?: Date | null,
    public pureTrainingTeam?: boolean,
    public official?: boolean,
    public createdAt?: Date,
    public updatedAt?: Date,
    public skills?: ITeamSkill[] | null,
    public image?: IImage | null,
    public participations?: IDimension[] | null
  ) {
    this.pureTrainingTeam = this.pureTrainingTeam ?? false;
    this.official = this.official ?? false;
  }
}
