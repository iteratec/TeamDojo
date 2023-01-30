import { IBadgeSkill, NewBadgeSkill } from './badge-skill.model';

export const sampleWithRequiredData: IBadgeSkill = {
  id: 75506,
};

export const sampleWithPartialData: IBadgeSkill = {
  id: 99609,
};

export const sampleWithFullData: IBadgeSkill = {
  id: 58186,
};

export const sampleWithNewData: NewBadgeSkill = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
