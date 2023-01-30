import dayjs from 'dayjs/esm';

import { IComment, NewComment } from './comment.model';

export const sampleWithRequiredData: IComment = {
  id: 78899,
  text: 'Coordinator',
  createdAt: dayjs('2021-05-09T16:21'),
  updatedAt: dayjs('2021-05-10T02:56'),
};

export const sampleWithPartialData: IComment = {
  id: 56305,
  text: 'invoice',
  createdAt: dayjs('2021-05-09T21:36'),
  updatedAt: dayjs('2021-05-10T00:53'),
};

export const sampleWithFullData: IComment = {
  id: 2467,
  text: 'repurpose deposit',
  createdAt: dayjs('2021-05-10T06:48'),
  updatedAt: dayjs('2021-05-09T13:33'),
};

export const sampleWithNewData: NewComment = {
  text: 'Investment Macedonia Creative',
  createdAt: dayjs('2021-05-09T14:11'),
  updatedAt: dayjs('2021-05-10T00:17'),
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
