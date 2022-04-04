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
  expirationDate?: dayjs.Dayjs | null;
  official?: boolean;
  createdAt?: dayjs.Dayjs;
  updatedAt?: dayjs.Dayjs;
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
    public expirationDate?: dayjs.Dayjs | null,
    public official?: boolean,
    public createdAt?: dayjs.Dayjs,
    public updatedAt?: dayjs.Dayjs,
    public skills?: ITeamSkill[] | null,
    public image?: IImage | null,
    public participations?: IDimension[] | null
  ) {
    this.official = this.official ?? false;
  }
}

export function getTeamIdentifier(team: ITeam): number | undefined {
  return team.id;
}
