import { ISkill } from '@/shared/model/skill.model';
import { ITeam } from '@/shared/model/team.model';

export interface ITeamSkill {
  id?: number;
  completedAt?: Date | null;
  verifiedAt?: Date | null;
  irrelevant?: boolean | null;
  note?: string | null;
  vote?: number;
  voters?: string | null;
  createdAt?: Date;
  updatedAt?: Date;
  skill?: ISkill;
  team?: ITeam;
}

export class TeamSkill implements ITeamSkill {
  constructor(
    public id?: number,
    public completedAt?: Date | null,
    public verifiedAt?: Date | null,
    public irrelevant?: boolean | null,
    public note?: string | null,
    public vote?: number,
    public voters?: string | null,
    public createdAt?: Date,
    public updatedAt?: Date,
    public skill?: ISkill,
    public team?: ITeam
  ) {
    this.irrelevant = this.irrelevant ?? false;
  }
}
