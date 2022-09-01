import dayjs from 'dayjs/esm';

import { ITeam, NewTeam } from './team.model';

export const sampleWithRequiredData: ITeam = {
  id: 88024,
  title: 'Chief programming',
  shortTitle: 'O3c3l',
  official: true,
  createdAt: dayjs('2021-05-09T11:46'),
  updatedAt: dayjs('2021-05-09T17:54'),
};

export const sampleWithPartialData: ITeam = {
  id: 93252,
  title: 'relationships Metal',
  shortTitle: '_X',
  slogan: 'Grocery Berkshire',
  official: true,
  createdAt: dayjs('2021-05-09T21:35'),
  updatedAt: dayjs('2021-05-10T02:30'),
};

export const sampleWithFullData: ITeam = {
  id: 55058,
  title: 'Intranet Rhode',
  shortTitle: '2yJaA',
  slogan: 'Refined Borders 1080p',
  contact: 'Pakistan JSON',
  expirationDate: dayjs('2021-05-10T02:13'),
  official: false,
  createdAt: dayjs('2021-05-10T08:20'),
  updatedAt: dayjs('2021-05-09T22:28'),
};

export const sampleWithNewData: NewTeam = {
  title: 'Salad',
  shortTitle: 'wh-B',
  official: false,
  createdAt: dayjs('2021-05-10T03:37'),
  updatedAt: dayjs('2021-05-10T07:05'),
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
