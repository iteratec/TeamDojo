import dayjs from 'dayjs/esm';

import { IActivity, NewActivity } from './activity.model';

export const sampleWithRequiredData: IActivity = {
  id: 28527,
  createdAt: dayjs('2021-05-10T04:21'),
  updatedAt: dayjs('2021-05-09T16:53'),
};

export const sampleWithPartialData: IActivity = {
  id: 26000,
  type: 'SKILL_COMPLETED',
  data: 'fast ugh restfully',
  createdAt: dayjs('2021-05-09T18:18'),
  updatedAt: dayjs('2021-05-09T13:49'),
};

export const sampleWithFullData: IActivity = {
  id: 9627,
  type: 'BADGE_CREATED',
  data: 'dill',
  createdAt: dayjs('2021-05-09T19:29'),
  updatedAt: dayjs('2021-05-10T00:22'),
};

export const sampleWithNewData: NewActivity = {
  createdAt: dayjs('2021-05-09T11:42'),
  updatedAt: dayjs('2021-05-10T03:50'),
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
