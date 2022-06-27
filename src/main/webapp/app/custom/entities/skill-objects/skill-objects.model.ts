/*
 SPDX-FileCopyrightText: the TeamDojo authors
 SPDX-License-Identifier: Apache-2.0
 */
import { ISkill } from 'app/entities/skill/skill.model';
import { IAchievableSkill } from 'app/custom/entities/achievable-skill/achievable-skill.model';

export interface ISkillObjects {
  skill: ISkill;
  achievableSkill?: IAchievableSkill;
}

export class SkillObjects implements ISkillObjects {
  constructor(public skill: ISkill, public achievableSkill: IAchievableSkill) {}
}
