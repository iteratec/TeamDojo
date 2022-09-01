import dayjs from 'dayjs/esm';

import { ActivityType } from 'app/entities/enumerations/activity-type.model';

import { IActivity, NewActivity } from './activity.model';

export const sampleWithRequiredData: IActivity = {
  id: 68021,
  createdAt: dayjs('2021-05-09T20:18'),
  updatedAt: dayjs('2021-05-09T12:53'),
};

export const sampleWithPartialData: IActivity = {
  id: 89487,
  data: 'pixel Industrial Buckinghamshire',
  createdAt: dayjs('2021-05-09T18:21'),
  updatedAt: dayjs('2021-05-10T07:24'),
};

export const sampleWithFullData: IActivity = {
  id: 94524,
  type: ActivityType['BADGE_COMPLETED'],
  data: 'Fresh',
  createdAt: dayjs('2021-05-10T05:40'),
  updatedAt: dayjs('2021-05-09T15:59'),
};

export const sampleWithNewData: NewActivity = {
  createdAt: dayjs('2021-05-10T04:42'),
  updatedAt: dayjs('2021-05-09T16:32'),
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
