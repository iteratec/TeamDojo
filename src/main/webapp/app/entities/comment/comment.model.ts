import * as dayjs from 'dayjs';
import { ITeam } from 'app/entities/team/team.model';
import { ISkill } from 'app/entities/skill/skill.model';

export interface IComment {
  id?: number;
  text?: string;
  createdAt?: dayjs.Dayjs;
  updatedAt?: dayjs.Dayjs;
  team?: ITeam;
  skill?: ISkill;
}

export class Comment implements IComment {
  constructor(
    public id?: number,
    public text?: string,
    public createdAt?: dayjs.Dayjs,
    public updatedAt?: dayjs.Dayjs,
    public team?: ITeam,
    public skill?: ISkill
  ) {}
}

export function getCommentIdentifier(comment: IComment): number | undefined {
  return comment.id;
}
