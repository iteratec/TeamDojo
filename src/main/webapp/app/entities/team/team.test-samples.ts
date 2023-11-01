import dayjs from 'dayjs/esm';

import { ITeam, NewTeam } from './team.model';

export const sampleWithRequiredData: ITeam = {
  id: 14539,
  title: 'whereas demerge parcel',
  shortTitle: undefined,
  official: false,
  createdAt: dayjs('2021-05-09T19:01'),
  updatedAt: dayjs('2021-05-10T03:53'),
};

export const sampleWithPartialData: ITeam = {
  id: 23604,
  title: 'apropos',
  shortTitle: 'eJ',
  slogan: 'aha',
  contact: 'aha',
  official: true,
  createdAt: dayjs('2021-05-09T11:54'),
  updatedAt: dayjs('2021-05-10T08:27'),
};

export const sampleWithFullData: ITeam = {
  id: 24399,
  title: 'helpfully envious',
  shortTitle: undefined,
  slogan: 'when basic',
  contact: 'amongst billion',
  expirationDate: dayjs('2021-05-10T00:55'),
  official: true,
  createdAt: dayjs('2021-05-10T07:06'),
  updatedAt: dayjs('2021-05-09T19:49'),
};

export const sampleWithNewData: NewTeam = {
  title: 'lest verifiable',
  shortTitle: '-yE',
  official: false,
  createdAt: dayjs('2021-05-09T11:21'),
  updatedAt: dayjs('2021-05-10T04:09'),
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
