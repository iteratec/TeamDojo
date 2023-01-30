import dayjs from 'dayjs/esm';
import { ITeam } from 'app/entities/team/team.model';
import { ISkill } from 'app/entities/skill/skill.model';

export interface IComment {
  id: number;
  text?: string | null;
  createdAt?: dayjs.Dayjs | null;
  updatedAt?: dayjs.Dayjs | null;
  team?: Pick<ITeam, 'id' | 'shortTitle'> | null;
  skill?: Pick<ISkill, 'id' | 'titleEN'> | null;
}

export type NewComment = Omit<IComment, 'id'> & { id: null };
