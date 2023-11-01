import { IBadgeSkill, NewBadgeSkill } from './badge-skill.model';

export const sampleWithRequiredData: IBadgeSkill = {
  id: 15205,
};

export const sampleWithPartialData: IBadgeSkill = {
  id: 17390,
};

export const sampleWithFullData: IBadgeSkill = {
  id: 10852,
};

export const sampleWithNewData: NewBadgeSkill = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
