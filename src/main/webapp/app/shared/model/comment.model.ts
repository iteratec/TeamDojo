import { ITeam } from '@/shared/model/team.model';
import { ISkill } from '@/shared/model/skill.model';

export interface IComment {
  id?: number;
  text?: string;
  createdAt?: Date;
  updatedAt?: Date;
  team?: ITeam;
  skill?: ISkill;
}

export class Comment implements IComment {
  constructor(
    public id?: number,
    public text?: string,
    public createdAt?: Date,
    public updatedAt?: Date,
    public team?: ITeam,
    public skill?: ISkill
  ) {}
}
