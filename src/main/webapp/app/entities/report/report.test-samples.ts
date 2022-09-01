import dayjs from 'dayjs/esm';

import { ReportType } from 'app/entities/enumerations/report-type.model';

import { IReport, NewReport } from './report.model';

export const sampleWithRequiredData: IReport = {
  id: 35851,
  title: 'protocol Liberia expedite',
  description: 'compress Loan',
  type: ReportType['COMPLIMENT'],
  createdAt: dayjs('2021-05-10T04:53'),
  updatedAt: dayjs('2021-05-09T20:19'),
};

export const sampleWithPartialData: IReport = {
  id: 10834,
  title: 'Fish Towels invoice',
  description: 'Table orange',
  type: ReportType['COMPLIMENT'],
  createdAt: dayjs('2021-05-09T18:15'),
  updatedAt: dayjs('2021-05-09T14:57'),
};

export const sampleWithFullData: IReport = {
  id: 32053,
  title: 'Mouse',
  description: 'Accounts invoice',
  type: ReportType['COMPLIMENT'],
  createdAt: dayjs('2021-05-09T21:47'),
  updatedAt: dayjs('2021-05-09T20:05'),
};

export const sampleWithNewData: NewReport = {
  title: 'program',
  description: 'cross-platform Canada',
  type: ReportType['BUG'],
  createdAt: dayjs('2021-05-09T16:02'),
  updatedAt: dayjs('2021-05-09T22:22'),
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
