import dayjs from 'dayjs/esm';

import { ISkill, NewSkill } from './skill.model';

export const sampleWithRequiredData: ISkill = {
  id: 1243,
  titleEN: 'anenst till from',
  score: 14731,
  rateCount: 19074,
  createdAt: dayjs('2021-05-09T15:32'),
  updatedAt: dayjs('2021-05-09T20:32'),
};

export const sampleWithPartialData: ISkill = {
  id: 11829,
  titleEN: 'for unless',
  titleDE: 'vault besides',
  descriptionEN: 'innocently',
  validationEN: 'spurn stench absent',
  validationDE: 'assimilate yuck oof',
  expiryPeriod: 23167,
  score: 14269,
  rateScore: 0.83,
  rateCount: 8871,
  createdAt: dayjs('2021-05-09T19:39'),
  updatedAt: dayjs('2021-05-09T15:26'),
};

export const sampleWithFullData: ISkill = {
  id: 8429,
  titleEN: 'cashew expedite whenever',
  titleDE: 'absent hm consult',
  descriptionEN: 'questioningly',
  descriptionDE: 'sprout',
  implementationEN: 'trespass facet bereave',
  implementationDE: 'questionably',
  validationEN: 'vengeful considering',
  validationDE: 'once midnight',
  expiryPeriod: 10010,
  contact: 'thump realm intent',
  score: 16825,
  rateScore: 1.33,
  rateCount: 4436,
  createdAt: dayjs('2021-05-09T11:16'),
  updatedAt: dayjs('2021-05-09T13:06'),
};

export const sampleWithNewData: NewSkill = {
  titleEN: 'smoggy',
  score: 8963,
  rateCount: 687,
  createdAt: dayjs('2021-05-09T17:02'),
  updatedAt: dayjs('2021-05-10T01:56'),
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
