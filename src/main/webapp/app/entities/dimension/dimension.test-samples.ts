import dayjs from 'dayjs/esm';

import { IDimension, NewDimension } from './dimension.model';

export const sampleWithRequiredData: IDimension = {
  id: 25002,
  titleEN: 'mysteriously whoa aha',
  createdAt: dayjs('2021-05-09T13:52'),
  updatedAt: dayjs('2021-05-09T15:28'),
};

export const sampleWithPartialData: IDimension = {
  id: 1602,
  titleEN: 'circa',
  titleDE: 'binoculars zowie rutabaga',
  descriptionDE: 'diffract',
  createdAt: dayjs('2021-05-09T20:45'),
  updatedAt: dayjs('2021-05-09T19:36'),
};

export const sampleWithFullData: IDimension = {
  id: 2288,
  titleEN: 'reluctantly',
  titleDE: 'provided',
  descriptionEN: 'divorce hm',
  descriptionDE: 'truthfully except',
  createdAt: dayjs('2021-05-09T17:08'),
  updatedAt: dayjs('2021-05-09T15:57'),
};

export const sampleWithNewData: NewDimension = {
  titleEN: 'route um',
  createdAt: dayjs('2021-05-09T16:09'),
  updatedAt: dayjs('2021-05-10T00:41'),
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
