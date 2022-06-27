/*
 SPDX-FileCopyrightText: the TeamDojo authors
 SPDX-License-Identifier: Apache-2.0
 */
import { Pipe, PipeTransform } from '@angular/core';
import { IAchievableSkill } from 'app/custom/entities/achievable-skill/achievable-skill.model';
import { TranslateModelService } from '../translate-model/translate-model.service';

@Pipe({
  name: 'achievableSkillFilter',
  pure: false, // Must not be pure so that already rendered components will be rendered again after language switch.
})
export class AchievableSkillFilterPipe implements PipeTransform {
  constructor(private translation: TranslateModelService) {}

  transform(skills: IAchievableSkill[], searchString: string): IAchievableSkill[] {
    const normalizedSearchString = searchString.toLowerCase();
    return skills.filter(skill => {
      const normalizedTitle = this.translation.translateProperty(skill, 'title').toLowerCase();
      return normalizedTitle.includes(normalizedSearchString);
    });
  }
}
