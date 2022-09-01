import dayjs from 'dayjs/esm';

import { IImage, NewImage } from './image.model';

export const sampleWithRequiredData: IImage = {
  id: 41829,
  title: 'Fish',
  createdAt: dayjs('2021-05-09T14:54'),
  updatedAt: dayjs('2021-05-09T12:42'),
};

export const sampleWithPartialData: IImage = {
  id: 58620,
  title: 'value-added',
  small: '../fake-data/blob/hipster.png',
  smallContentType: 'unknown',
  createdAt: dayjs('2021-05-09T22:11'),
  updatedAt: dayjs('2021-05-09T16:48'),
};

export const sampleWithFullData: IImage = {
  id: 36194,
  title: 'Sleek',
  small: '../fake-data/blob/hipster.png',
  smallContentType: 'unknown',
  medium: '../fake-data/blob/hipster.png',
  mediumContentType: 'unknown',
  large: '../fake-data/blob/hipster.png',
  largeContentType: 'unknown',
  hash: 'Down-sized Health',
  createdAt: dayjs('2021-05-09T12:55'),
  updatedAt: dayjs('2021-05-10T07:58'),
};

export const sampleWithNewData: NewImage = {
  title: 'Applications instruction mission-critical',
  createdAt: dayjs('2021-05-09T15:11'),
  updatedAt: dayjs('2021-05-09T12:16'),
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
