import { IComment } from 'app/entities/comment/comment.model';

export interface ISkillRate {
  comment?: IComment;
  skillId?: number;
  rateScore?: number;
  rateCount?: number;
}

export class SkillRate implements ISkillRate {
  constructor(public skillId?: number, public rateScore?: number, public rateCount?: number, public comment?: IComment) {}
}
