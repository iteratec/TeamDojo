import dayjs from 'dayjs/esm';

import { ITraining, NewTraining } from './training.model';

export const sampleWithRequiredData: ITraining = {
  id: 91438,
  titleEN: 'withdrawal Salad',
  isOfficial: true,
  createdAt: dayjs('2021-05-10T09:09'),
  updatedAt: dayjs('2021-05-10T04:58'),
};

export const sampleWithPartialData: ITraining = {
  id: 86720,
  titleEN: 'holistic',
  titleDE: 'Towels Turkmenistan',
  descriptionEN: 'Iowa',
  descriptionDE: 'Steel Home grey',
  contact: 'infomediaries Soft Canyon',
  validUntil: dayjs('2021-05-10T06:09'),
  isOfficial: true,
  suggestedBy: 'optical system e-tailers',
  createdAt: dayjs('2021-05-09T17:35'),
  updatedAt: dayjs('2021-05-10T00:13'),
};

export const sampleWithFullData: ITraining = {
  id: 99612,
  titleEN: 'Handmade',
  titleDE: 'bypass Hong',
  descriptionEN: 'blue Engineer',
  descriptionDE: 'next-generation',
  contact: 'Future Multi-lateral Loan',
  link: 'open-source African',
  validUntil: dayjs('2021-05-10T01:55'),
  isOfficial: false,
  suggestedBy: 'Function-based',
  createdAt: dayjs('2021-05-10T00:36'),
  updatedAt: dayjs('2021-05-10T07:13'),
};

export const sampleWithNewData: NewTraining = {
  titleEN: 'Account Causeway',
  isOfficial: false,
  createdAt: dayjs('2021-05-09T18:20'),
  updatedAt: dayjs('2021-05-09T20:22'),
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
