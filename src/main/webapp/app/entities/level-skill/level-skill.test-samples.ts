import { ILevelSkill, NewLevelSkill } from './level-skill.model';

export const sampleWithRequiredData: ILevelSkill = {
  id: 2429,
};

export const sampleWithPartialData: ILevelSkill = {
  id: 6548,
};

export const sampleWithFullData: ILevelSkill = {
  id: 26399,
};

export const sampleWithNewData: NewLevelSkill = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
