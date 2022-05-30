import { Pipe, PipeTransform } from '@angular/core';
import { ISkill } from 'app/entities/skill/skill.model';
import { TranslateModelService } from '../translate-model/translate-model.service';

@Pipe({ name: 'skillFilter' })
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
