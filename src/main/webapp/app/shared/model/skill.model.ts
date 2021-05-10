import { IBadgeSkill } from '@/shared/model/badge-skill.model';
import { ILevelSkill } from '@/shared/model/level-skill.model';
import { ITeamSkill } from '@/shared/model/team-skill.model';
import { ITraining } from '@/shared/model/training.model';

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
  createdAt?: Date;
  updatedAt?: Date;
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
    public createdAt?: Date,
    public updatedAt?: Date,
    public badges?: IBadgeSkill[] | null,
    public levels?: ILevelSkill[] | null,
    public teams?: ITeamSkill[] | null,
    public trainings?: ITraining[] | null
  ) {}
}
