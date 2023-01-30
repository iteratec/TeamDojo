import dayjs from 'dayjs/esm';
import { ISkill } from 'app/entities/skill/skill.model';
import { ITeam } from 'app/entities/team/team.model';
import { SkillStatus } from 'app/entities/enumerations/skill-status.model';

export interface ITeamSkill {
  id: number;
  completedAt?: dayjs.Dayjs | null;
  verifiedAt?: dayjs.Dayjs | null;
  irrelevant?: boolean | null;
  skillStatus?: SkillStatus | null;
  note?: string | null;
  vote?: number | null;
  voters?: string | null;
  createdAt?: dayjs.Dayjs | null;
  updatedAt?: dayjs.Dayjs | null;
  skill?: Pick<ISkill, 'id' | 'titleEN'> | null;
  team?: Pick<ITeam, 'id' | 'title'> | null;
}

export type NewTeamSkill = Omit<ITeamSkill, 'id'> & { id: null };
