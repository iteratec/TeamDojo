import dayjs from 'dayjs/esm';

import { ITeamGroup, NewTeamGroup } from './team-group.model';

export const sampleWithRequiredData: ITeamGroup = {
  id: 29478,
  title: 'transliterate',
  createdAt: dayjs('2022-04-06T18:08'),
  updatedAt: dayjs('2022-04-07T08:49'),
};

export const sampleWithPartialData: ITeamGroup = {
  id: 11540,
  title: 'beyond bewitch',
  description: 'console',
  createdAt: dayjs('2022-04-07T04:03'),
  updatedAt: dayjs('2022-04-07T05:47'),
};

export const sampleWithFullData: ITeamGroup = {
  id: 21797,
  title: 'frantically supposing athwart',
  description: 'unfortunately moist',
  createdAt: dayjs('2022-04-06T21:12'),
  updatedAt: dayjs('2022-04-07T09:28'),
};

export const sampleWithNewData: NewTeamGroup = {
  title: 'pink needily up',
  createdAt: dayjs('2022-04-07T09:29'),
  updatedAt: dayjs('2022-04-07T06:18'),
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
