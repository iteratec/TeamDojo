import dayjs from 'dayjs/esm';
import { ITeamSkill } from 'app/entities/team-skill/team-skill.model';
import { IImage } from 'app/entities/image/image.model';
import { IDimension } from 'app/entities/dimension/dimension.model';

export interface ITeam {
  id?: number;
  title?: string;
  shortTitle?: string;
  slogan?: string | null;
  contact?: string | null;
  validUntil?: dayjs.Dayjs | null;
  pureTrainingTeam?: boolean;
  official?: boolean;
  createdAt?: dayjs.Dayjs;
  updatedAt?: dayjs.Dayjs;
  daysUntilExpiration?: number;
  expired?: boolean;
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
    public validUntil?: dayjs.Dayjs | null,
    public pureTrainingTeam?: boolean,
    public official?: boolean,
    public createdAt?: dayjs.Dayjs,
    public updatedAt?: dayjs.Dayjs,
    public daysUntilExpiration?: number,
    public expired?: boolean,
    public skills?: ITeamSkill[] | null,
    public image?: IImage | null,
    public participations?: IDimension[] | null
  ) {
    this.pureTrainingTeam = this.pureTrainingTeam ?? false;
    this.official = this.official ?? false;
    this.expired = this.expired ?? false;
  }
}

export function getTeamIdentifier(team: ITeam): number | undefined {
  return team.id;
}
