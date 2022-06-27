/*
 SPDX-FileCopyrightText: the TeamDojo authors
 SPDX-License-Identifier: Apache-2.0
 */
import { Pipe, PipeTransform } from '@angular/core';
import { ISkill } from 'app/entities/skill/skill.model';
import { TranslateModelService } from '../translate-model/translate-model.service';

@Pipe({
  name: 'skillFilter',
  pure: false, // Must not be pure so that already rendered components will be rendered again after language switch.
})
export class SkillFilterPipe implements PipeTransform {
  constructor(private translation: TranslateModelService) {}

  transform(skills: ISkill[], searchString: string): ISkill[] {
    const normalizedSearchString = searchString.toLowerCase();
    return skills.filter(skill => {
      const normalizedTitle = this.translation.translateProperty(skill, 'title').toLowerCase();
      return normalizedTitle.includes(normalizedSearchString);
    });
  }
}
