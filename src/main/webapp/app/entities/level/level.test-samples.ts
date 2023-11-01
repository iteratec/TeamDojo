import dayjs from 'dayjs/esm';

import { ILevel, NewLevel } from './level.model';

export const sampleWithRequiredData: ILevel = {
  id: 17041,
  titleEN: 'waver huge',
  requiredScore: 0.26,
  instantMultiplier: 30376.73,
  createdAt: dayjs('2021-05-09T14:21'),
  updatedAt: dayjs('2021-05-09T21:40'),
};

export const sampleWithPartialData: ILevel = {
  id: 17056,
  titleEN: 'ugh prefix lest',
  requiredScore: 0.74,
  instantMultiplier: 23000.2,
  createdAt: dayjs('2021-05-10T08:41'),
  updatedAt: dayjs('2021-05-10T06:25'),
};

export const sampleWithFullData: ILevel = {
  id: 20888,
  titleEN: 'roast ulcerate credibility',
  titleDE: 'upward dissimulate flight',
  descriptionEN: 'boost',
  descriptionDE: 'smudge long',
  requiredScore: 0.94,
  instantMultiplier: 7493.08,
  completionBonus: 3276,
  createdAt: dayjs('2021-05-09T18:01'),
  updatedAt: dayjs('2021-05-09T12:23'),
};

export const sampleWithNewData: NewLevel = {
  titleEN: 'hence eek stigmatize',
  requiredScore: 0.82,
  instantMultiplier: 18754.8,
  createdAt: dayjs('2021-05-10T01:32'),
  updatedAt: dayjs('2021-05-10T08:05'),
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
