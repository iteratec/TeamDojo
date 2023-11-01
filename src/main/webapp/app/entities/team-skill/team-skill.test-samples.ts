import dayjs from 'dayjs/esm';

import { ITeamSkill, NewTeamSkill } from './team-skill.model';

export const sampleWithRequiredData: ITeamSkill = {
  id: 26081,
  skillStatus: 'IRRELEVANT',
  vote: 16968,
  createdAt: dayjs('2021-05-09T22:35'),
  updatedAt: dayjs('2021-05-09T15:42'),
};

export const sampleWithPartialData: ITeamSkill = {
  id: 5674,
  completedAt: dayjs('2021-05-10T02:39'),
  verifiedAt: dayjs('2021-05-10T07:40'),
  irrelevant: true,
  skillStatus: 'OPEN',
  note: 'greatly trifling',
  vote: 5373,
  createdAt: dayjs('2021-05-09T18:51'),
  updatedAt: dayjs('2021-05-10T08:55'),
};

export const sampleWithFullData: ITeamSkill = {
  id: 335,
  completedAt: dayjs('2021-05-10T03:15'),
  verifiedAt: dayjs('2021-05-10T06:04'),
  irrelevant: false,
  skillStatus: 'IRRELEVANT',
  note: 'interlink',
  vote: 20577,
  voters: 'monumental neuropsychiatry',
  createdAt: dayjs('2021-05-10T09:36'),
  updatedAt: dayjs('2021-05-09T17:28'),
};

export const sampleWithNewData: NewTeamSkill = {
  skillStatus: 'IRRELEVANT',
  vote: 28810,
  createdAt: dayjs('2021-05-10T03:24'),
  updatedAt: dayjs('2021-05-10T07:52'),
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
