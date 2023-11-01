import dayjs from 'dayjs/esm';

import { IComment, NewComment } from './comment.model';

export const sampleWithRequiredData: IComment = {
  id: 16782,
  text: 'a an',
  createdAt: dayjs('2021-05-09T19:41'),
  updatedAt: dayjs('2021-05-09T13:22'),
};

export const sampleWithPartialData: IComment = {
  id: 8109,
  text: 'joshingly um',
  createdAt: dayjs('2021-05-09T21:41'),
  updatedAt: dayjs('2021-05-09T19:13'),
};

export const sampleWithFullData: IComment = {
  id: 32156,
  text: 'venture how ah',
  createdAt: dayjs('2021-05-10T03:44'),
  updatedAt: dayjs('2021-05-09T12:11'),
};

export const sampleWithNewData: NewComment = {
  text: 'how brown who',
  createdAt: dayjs('2021-05-10T09:52'),
  updatedAt: dayjs('2021-05-09T19:23'),
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
