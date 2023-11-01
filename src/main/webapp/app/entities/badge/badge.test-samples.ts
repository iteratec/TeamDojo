import dayjs from 'dayjs/esm';

import { IBadge, NewBadge } from './badge.model';

export const sampleWithRequiredData: IBadge = {
  id: 21312,
  titleEN: 'where',
  requiredScore: 0.88,
  instantMultiplier: 18280.4,
  createdAt: dayjs('2021-05-09T15:47'),
  updatedAt: dayjs('2021-05-09T13:49'),
};

export const sampleWithPartialData: IBadge = {
  id: 2759,
  titleEN: 'pace',
  titleDE: 'um upbeat tambour',
  descriptionDE: 'channel',
  requiredScore: 0.57,
  instantMultiplier: 3570.67,
  completionBonus: 2583,
  createdAt: dayjs('2021-05-10T09:59'),
  updatedAt: dayjs('2021-05-09T22:50'),
};

export const sampleWithFullData: IBadge = {
  id: 20841,
  titleEN: 'clone typeface meadow',
  titleDE: 'overlooked ache',
  descriptionEN: 'yowza blah except',
  descriptionDE: 'following',
  availableUntil: dayjs('2021-05-10T04:03'),
  availableAmount: 30645,
  requiredScore: 0.3,
  instantMultiplier: 17211.18,
  completionBonus: 11589,
  createdAt: dayjs('2021-05-10T02:25'),
  updatedAt: dayjs('2021-05-09T12:23'),
};

export const sampleWithNewData: NewBadge = {
  titleEN: 'adventurously rappel gee',
  requiredScore: 0.98,
  instantMultiplier: 22329.48,
  createdAt: dayjs('2021-05-09T11:42'),
  updatedAt: dayjs('2021-05-09T16:01'),
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
