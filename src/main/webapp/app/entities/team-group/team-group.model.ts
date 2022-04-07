import dayjs from 'dayjs/esm';
import { ITeam } from 'app/entities/team/team.model';

export interface ITeamGroup {
  id?: number;
  title?: string;
  description?: string | null;
  createdAt?: dayjs.Dayjs;
  updatedAt?: dayjs.Dayjs;
  teams?: ITeam[] | null;
  parent?: ITeamGroup | null;
}

export class TeamGroup implements ITeamGroup {
  constructor(
    public id?: number,
    public title?: string,
    public description?: string | null,
    public createdAt?: dayjs.Dayjs,
    public updatedAt?: dayjs.Dayjs,
    public teams?: ITeam[] | null,
    public parent?: ITeamGroup | null
  ) {}
}

export function getTeamGroupIdentifier(teamGroup: ITeamGroup): number | undefined {
  return teamGroup.id;
}
