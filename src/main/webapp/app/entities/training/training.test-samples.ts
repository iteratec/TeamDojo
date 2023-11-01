import dayjs from 'dayjs/esm';

import { ITraining, NewTraining } from './training.model';

export const sampleWithRequiredData: ITraining = {
  id: 7106,
  titleEN: 'poke',
  isOfficial: true,
  createdAt: dayjs('2021-05-10T01:14'),
  updatedAt: dayjs('2021-05-10T06:11'),
};

export const sampleWithPartialData: ITraining = {
  id: 21591,
  titleEN: 'aboard infill revel',
  titleDE: 'truthfully',
  descriptionEN: 'hard',
  descriptionDE: 'overrun worth',
  isOfficial: false,
  suggestedBy: 'upwardly officiate till',
  createdAt: dayjs('2021-05-09T16:53'),
  updatedAt: dayjs('2021-05-09T17:32'),
};

export const sampleWithFullData: ITraining = {
  id: 29062,
  titleEN: 'with',
  titleDE: 'adventurously intensity',
  descriptionEN: 'promptly sterilize',
  descriptionDE: 'greedy',
  contact: 'hmph generously',
  link: 'towards',
  validUntil: dayjs('2021-05-09T14:42'),
  isOfficial: false,
  suggestedBy: 'valiantly boohoo dearly',
  createdAt: dayjs('2021-05-10T01:35'),
  updatedAt: dayjs('2021-05-10T10:26'),
};

export const sampleWithNewData: NewTraining = {
  titleEN: 'biology gah',
  isOfficial: true,
  createdAt: dayjs('2021-05-09T21:50'),
  updatedAt: dayjs('2021-05-09T22:22'),
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
