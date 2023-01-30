import { ILevelSkill, NewLevelSkill } from './level-skill.model';

export const sampleWithRequiredData: ILevelSkill = {
  id: 45691,
};

export const sampleWithPartialData: ILevelSkill = {
  id: 6703,
};

export const sampleWithFullData: ILevelSkill = {
  id: 52202,
};

export const sampleWithNewData: NewLevelSkill = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
