import dayjs from 'dayjs/esm';

import { IReport, NewReport } from './report.model';

export const sampleWithRequiredData: IReport = {
  id: 3942,
  title: 'soon greedy chide',
  description: 'ugh',
  type: 'WISH',
  createdAt: dayjs('2021-05-09T23:32'),
  updatedAt: dayjs('2021-05-09T19:13'),
};

export const sampleWithPartialData: IReport = {
  id: 7254,
  title: 'complete heavily',
  description: 'airfield',
  type: 'BUG',
  createdAt: dayjs('2021-05-10T10:50'),
  updatedAt: dayjs('2021-05-09T22:24'),
};

export const sampleWithFullData: IReport = {
  id: 6875,
  title: 'nor including tabulate',
  description: 'long',
  type: 'COMPLIMENT',
  createdAt: dayjs('2021-05-10T02:57'),
  updatedAt: dayjs('2021-05-09T15:34'),
};

export const sampleWithNewData: NewReport = {
  title: 'period',
  description: 'sublimate after creamy',
  type: 'REVIEW',
  createdAt: dayjs('2021-05-09T20:08'),
  updatedAt: dayjs('2021-05-10T01:45'),
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
