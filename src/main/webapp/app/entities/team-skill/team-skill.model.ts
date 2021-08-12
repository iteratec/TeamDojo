import * as dayjs from 'dayjs';
import { ISkill } from 'app/entities/skill/skill.model';
import { ITeam } from 'app/entities/team/team.model';
import { SkillStatus } from 'app/entities/enumerations/skill-status.model';

export interface ITeamSkill {
  id?: number;
  completedAt?: dayjs.Dayjs | null;
  verifiedAt?: dayjs.Dayjs | null;
  irrelevant?: boolean | null;
  skillStatus?: SkillStatus;
  note?: string | null;
  vote?: number;
  voters?: string | null;
  createdAt?: dayjs.Dayjs;
  updatedAt?: dayjs.Dayjs;
  skill?: ISkill;
  team?: ITeam;
}

export class TeamSkill implements ITeamSkill {
  constructor(
    public id?: number,
    public completedAt?: dayjs.Dayjs | null,
    public verifiedAt?: dayjs.Dayjs | null,
    public irrelevant?: boolean | null,
    public skillStatus?: SkillStatus,
    public note?: string | null,
    public vote?: number,
    public voters?: string | null,
    public createdAt?: dayjs.Dayjs,
    public updatedAt?: dayjs.Dayjs,
    public skill?: ISkill,
    public team?: ITeam
  ) {
    this.irrelevant = this.irrelevant ?? false;
  }
}

export function getTeamSkillIdentifier(teamSkill: ITeamSkill): number | undefined {
  return teamSkill.id;
}
