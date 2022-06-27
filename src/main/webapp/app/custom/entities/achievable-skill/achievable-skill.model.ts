/*
 SPDX-FileCopyrightText: the TeamDojo authors
 SPDX-License-Identifier: Apache-2.0
 */
import { Moment } from 'moment';
import { SkillStatus } from 'app/entities/enumerations/skill-status.model';

export interface IAchievableSkill {
  id?: number;
  teamSkillId?: number;
  skillId?: number;
  titleEN?: string;
  titleDE?: string | null;
  descriptionEN?: string | null;
  descriptionDE?: string | null;
  achievedAt?: Moment;
  verifiedAt?: Moment;
  vote?: number;
  voters?: string;
  irrelevant?: boolean;
  score?: number;
  skillStatus?: SkillStatus;
  rateScore?: number | null;
  rateCount?: number;
}

export class AchievableSkill implements IAchievableSkill {
  constructor(
    public id?: number,
    public skillId?: number,
    public titleEN?: string,
    public titleDE?: string | null,
    public descriptionEN?: string | null,
    public descriptionDE?: string | null,
    public achievedAt?: Moment,
    public verifiedAt?: Moment,
    public vote?: number,
    public voters?: string,
    public irrelevant?: boolean,
    public score?: number,
    public skillStatus?: SkillStatus,
    public rateScore?: number | null,
    public rateCount?: number
  ) {}
}
