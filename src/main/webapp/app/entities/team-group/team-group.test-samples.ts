import dayjs from 'dayjs/esm';

import { ITeamGroup, NewTeamGroup } from './team-group.model';

export const sampleWithRequiredData: ITeamGroup = {
  id: 89303,
  title: 'optical Intelligent orange',
  createdAt: dayjs('2022-04-07T11:31'),
  updatedAt: dayjs('2022-04-07T07:19'),
};

export const sampleWithPartialData: ITeamGroup = {
  id: 22887,
  title: 'Optimized Oman',
  createdAt: dayjs('2022-04-07T15:23'),
  updatedAt: dayjs('2022-04-06T19:55'),
};

export const sampleWithFullData: ITeamGroup = {
  id: 9729,
  title: 'feed THX Uzbekistan',
  description: 'Streamlined',
  createdAt: dayjs('2022-04-07T12:17'),
  updatedAt: dayjs('2022-04-07T15:44'),
};

export const sampleWithNewData: NewTeamGroup = {
  title: 'content-based Keyboard',
  createdAt: dayjs('2022-04-06T18:30'),
  updatedAt: dayjs('2022-04-07T00:48'),
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
