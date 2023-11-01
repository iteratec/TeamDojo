import dayjs from 'dayjs/esm';

import { IImage, NewImage } from './image.model';

export const sampleWithRequiredData: IImage = {
  id: 29909,
  title: 'cart toward as',
  createdAt: dayjs('2021-05-09T14:08'),
  updatedAt: dayjs('2021-05-10T04:42'),
};

export const sampleWithPartialData: IImage = {
  id: 408,
  title: 'by',
  medium: '../fake-data/blob/hipster.png',
  mediumContentType: 'unknown',
  large: '../fake-data/blob/hipster.png',
  largeContentType: 'unknown',
  createdAt: dayjs('2021-05-09T17:47'),
  updatedAt: dayjs('2021-05-09T17:10'),
};

export const sampleWithFullData: IImage = {
  id: 9139,
  title: 'behind terrible',
  small: '../fake-data/blob/hipster.png',
  smallContentType: 'unknown',
  medium: '../fake-data/blob/hipster.png',
  mediumContentType: 'unknown',
  large: '../fake-data/blob/hipster.png',
  largeContentType: 'unknown',
  hash: 'perfect',
  createdAt: dayjs('2021-05-10T10:26'),
  updatedAt: dayjs('2021-05-10T01:06'),
};

export const sampleWithNewData: NewImage = {
  title: 'incidentally',
  createdAt: dayjs('2021-05-09T21:31'),
  updatedAt: dayjs('2021-05-10T01:22'),
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
