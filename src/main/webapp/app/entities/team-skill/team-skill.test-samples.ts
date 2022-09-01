import dayjs from 'dayjs/esm';

import { SkillStatus } from 'app/entities/enumerations/skill-status.model';

import { ITeamSkill, NewTeamSkill } from './team-skill.model';

export const sampleWithRequiredData: ITeamSkill = {
  id: 53579,
  skillStatus: SkillStatus['ACHIEVED'],
  vote: 73653,
  createdAt: dayjs('2021-05-10T07:47'),
  updatedAt: dayjs('2021-05-09T15:16'),
};

export const sampleWithPartialData: ITeamSkill = {
  id: 10742,
  completedAt: dayjs('2021-05-10T06:45'),
  verifiedAt: dayjs('2021-05-09T19:28'),
  irrelevant: true,
  skillStatus: SkillStatus['EXPIRED'],
  vote: 43908,
  createdAt: dayjs('2021-05-09T15:24'),
  updatedAt: dayjs('2021-05-10T04:01'),
};

export const sampleWithFullData: ITeamSkill = {
  id: 13665,
  completedAt: dayjs('2021-05-10T04:44'),
  verifiedAt: dayjs('2021-05-10T08:12'),
  irrelevant: false,
  skillStatus: SkillStatus['ACHIEVED'],
  note: 'transform',
  vote: 39140,
  voters: 'indigo',
  createdAt: dayjs('2021-05-10T06:47'),
  updatedAt: dayjs('2021-05-09T20:17'),
};

export const sampleWithNewData: NewTeamSkill = {
  skillStatus: SkillStatus['EXPIRING'],
  vote: 75022,
  createdAt: dayjs('2021-05-10T05:06'),
  updatedAt: dayjs('2021-05-09T22:03'),
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
