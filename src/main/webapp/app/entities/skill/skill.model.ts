import dayjs from 'dayjs/esm';
import { IBadgeSkill } from 'app/entities/badge-skill/badge-skill.model';
import { ILevelSkill } from 'app/entities/level-skill/level-skill.model';
import { ITeamSkill } from 'app/entities/team-skill/team-skill.model';
import { ITraining } from 'app/entities/training/training.model';

export interface ISkill {
  id?: number;
  title?: string;
  description?: string | null;
  implementation?: string | null;
  validation?: string | null;
  expiryPeriod?: number | null;
  contact?: string | null;
  score?: number;
  rateScore?: number | null;
  rateCount?: number;
  createdAt?: dayjs.Dayjs;
  updatedAt?: dayjs.Dayjs;
  badges?: IBadgeSkill[] | null;
  levels?: ILevelSkill[] | null;
  teams?: ITeamSkill[] | null;
  trainings?: ITraining[] | null;
}

export class Skill implements ISkill {
  constructor(
    public id?: number,
    public title?: string,
    public description?: string | null,
    public implementation?: string | null,
    public validation?: string | null,
    public expiryPeriod?: number | null,
    public contact?: string | null,
    public score?: number,
    public rateScore?: number | null,
    public rateCount?: number,
    public createdAt?: dayjs.Dayjs,
    public updatedAt?: dayjs.Dayjs,
    public badges?: IBadgeSkill[] | null,
    public levels?: ILevelSkill[] | null,
    public teams?: ITeamSkill[] | null,
    public trainings?: ITraining[] | null
  ) {}
}

export function getSkillIdentifier(skill: ISkill): number | undefined {
  return skill.id;
}
