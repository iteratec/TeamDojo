import dayjs from 'dayjs/esm';

import { IDimension, NewDimension } from './dimension.model';

export const sampleWithRequiredData: IDimension = {
  id: 39319,
  titleEN: 'Electronics navigating mission-critical',
  createdAt: dayjs('2021-05-10T05:36'),
  updatedAt: dayjs('2021-05-10T11:02'),
};

export const sampleWithPartialData: IDimension = {
  id: 2480,
  titleEN: 'Versatile Soft Right-sized',
  titleDE: 'Regional communities',
  createdAt: dayjs('2021-05-09T14:45'),
  updatedAt: dayjs('2021-05-09T20:28'),
};

export const sampleWithFullData: IDimension = {
  id: 45907,
  titleEN: 'Chips Bedfordshire CFA',
  titleDE: 'Ranch',
  descriptionEN: 'Cambridgeshire Outdoors',
  descriptionDE: 'tan parse Berkshire',
  createdAt: dayjs('2021-05-10T08:18'),
  updatedAt: dayjs('2021-05-09T20:14'),
};

export const sampleWithNewData: NewDimension = {
  titleEN: 'Avon invoice schemas',
  createdAt: dayjs('2021-05-10T00:47'),
  updatedAt: dayjs('2021-05-09T15:20'),
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
