import dayjs from 'dayjs/esm';

import { ILevel, NewLevel } from './level.model';

export const sampleWithRequiredData: ILevel = {
  id: 21386,
  titleEN: 'Wooden',
  requiredScore: 0,
  instantMultiplier: 14994,
  createdAt: dayjs('2021-05-10T03:12'),
  updatedAt: dayjs('2021-05-10T03:51'),
};

export const sampleWithPartialData: ILevel = {
  id: 752,
  titleEN: 'deposit multi-state Escudo',
  titleDE: 'Implementation deposit',
  descriptionEN: 'red',
  descriptionDE: 'deposit clear-thinking',
  requiredScore: 1,
  instantMultiplier: 54880,
  completionBonus: 18551,
  createdAt: dayjs('2021-05-09T23:38'),
  updatedAt: dayjs('2021-05-10T04:36'),
};

export const sampleWithFullData: ILevel = {
  id: 54916,
  titleEN: 'Dakota Home invoice',
  titleDE: 'envisioneer',
  descriptionEN: 'Personal help-desk',
  descriptionDE: 'TCP',
  requiredScore: 1,
  instantMultiplier: 94922,
  completionBonus: 78845,
  createdAt: dayjs('2021-05-10T05:07'),
  updatedAt: dayjs('2021-05-09T16:28'),
};

export const sampleWithNewData: NewLevel = {
  titleEN: 'virtual back-end Incredible',
  requiredScore: 0,
  instantMultiplier: 87202,
  createdAt: dayjs('2021-05-09T12:43'),
  updatedAt: dayjs('2021-05-10T11:03'),
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
