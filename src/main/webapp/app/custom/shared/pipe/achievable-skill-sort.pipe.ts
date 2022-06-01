import { Pipe, PipeTransform } from '@angular/core';
import { IAchievableSkill } from 'app/custom/entities/achievable-skill/achievable-skill.model';
import { TranslateModelService } from '../translate-model/translate-model.service';

type SortableProperties = keyof IAchievableSkill;

@Pipe({
  name: 'achievableSkillSort',
  pure: false, // Must not be pure so that already rendered components will be rendered again after language switch.
})
export class AchievableSkillSortPipe implements PipeTransform {
  constructor(private translation: TranslateModelService) {}

  private static defaultString(smth: any): string {
    return smth || smth === 0 ? String(smth) : '';
  }

  private static shouldSortReverse(propertyName: SortableProperties): boolean {
    return ['score', 'rateCount'].includes(propertyName);
  }

  transform(skills: IAchievableSkill[], propertyName: SortableProperties): IAchievableSkill[] {
    const nullSafePropertyName = AchievableSkillSortPipe.defaultString(propertyName) as SortableProperties;
    const sortOrder = AchievableSkillSortPipe.shouldSortReverse(nullSafePropertyName) ? -1 : 1;

    return Array.from(skills).sort((leftSkill, rightSkill) => {
      const leftValue = AchievableSkillSortPipe.defaultString(leftSkill[nullSafePropertyName]);
      const rightValue = AchievableSkillSortPipe.defaultString(rightSkill[nullSafePropertyName]);

      return sortOrder * leftValue.localeCompare(rightValue);
    });
  }
}
