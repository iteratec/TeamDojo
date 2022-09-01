import dayjs from 'dayjs/esm';

import { ISkill, NewSkill } from './skill.model';

export const sampleWithRequiredData: ISkill = {
  id: 54014,
  titleEN: 'feed Montana capacitor',
  score: 46267,
  rateCount: 83593,
  createdAt: dayjs('2021-05-09T23:31'),
  updatedAt: dayjs('2021-05-10T00:50'),
};

export const sampleWithPartialData: ISkill = {
  id: 16881,
  titleEN: 'Clothing',
  titleDE: 'compressing',
  descriptionDE: 'quantify Human',
  implementationEN: 'EXE',
  implementationDE: 'Computers SMTP',
  score: 6297,
  rateCount: 71478,
  createdAt: dayjs('2021-05-09T19:30'),
  updatedAt: dayjs('2021-05-09T15:52'),
};

export const sampleWithFullData: ISkill = {
  id: 88252,
  titleEN: 'bypassing Islands Outdoors',
  titleDE: 'Intranet Orchestrator',
  descriptionEN: 'primary Extended Awesome',
  descriptionDE: 'Fresh Manager Personal',
  implementationEN: 'next-generation Parkways',
  implementationDE: 'up Platinum',
  validationEN: 'Developer Way Lakes',
  validationDE: 'orchid Practical recontextualize',
  expiryPeriod: 87056,
  contact: 'Corporate index Dinar',
  score: 71399,
  rateScore: 1,
  rateCount: 64427,
  createdAt: dayjs('2021-05-09T18:50'),
  updatedAt: dayjs('2021-05-09T12:38'),
};

export const sampleWithNewData: NewSkill = {
  titleEN: 'Monitored',
  score: 38843,
  rateCount: 27684,
  createdAt: dayjs('2021-05-09T15:43'),
  updatedAt: dayjs('2021-05-10T04:14'),
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
