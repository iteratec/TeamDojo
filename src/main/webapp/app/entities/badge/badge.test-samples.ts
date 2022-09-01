import dayjs from 'dayjs/esm';

import { IBadge, NewBadge } from './badge.model';

export const sampleWithRequiredData: IBadge = {
  id: 22734,
  titleEN: 'Refined gold Mouse',
  requiredScore: 0,
  instantMultiplier: 58863,
  createdAt: dayjs('2021-05-10T07:11'),
  updatedAt: dayjs('2021-05-10T07:20'),
};

export const sampleWithPartialData: IBadge = {
  id: 6723,
  titleEN: 'Uganda platforms Tactics',
  descriptionDE: 'Strategist Loan',
  availableAmount: 27237,
  requiredScore: 1,
  instantMultiplier: 94863,
  createdAt: dayjs('2021-05-10T05:49'),
  updatedAt: dayjs('2021-05-09T13:04'),
};

export const sampleWithFullData: IBadge = {
  id: 68986,
  titleEN: 'Unbranded Designer Kansas',
  titleDE: 'withdrawal',
  descriptionEN: 'AGP dot-com',
  descriptionDE: 'Chair Human',
  availableUntil: dayjs('2021-05-09T15:18'),
  availableAmount: 98446,
  requiredScore: 0,
  instantMultiplier: 93274,
  completionBonus: 47030,
  createdAt: dayjs('2021-05-09T21:12'),
  updatedAt: dayjs('2021-05-10T06:26'),
};

export const sampleWithNewData: NewBadge = {
  titleEN: 'invoice Shirt Buckinghamshire',
  requiredScore: 0,
  instantMultiplier: 76795,
  createdAt: dayjs('2021-05-10T04:29'),
  updatedAt: dayjs('2021-05-09T12:37'),
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
