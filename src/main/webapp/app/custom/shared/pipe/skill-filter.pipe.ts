import { Pipe, PipeTransform } from '@angular/core';
import { ISkill } from 'app/entities/skill/skill.model';

@Pipe({ name: 'skillFilter' })
export class SkillFilterPipe implements PipeTransform {
  transform(skills: ISkill[], searchString: string): ISkill[] {
    return skills.filter(skill => (skill.title ?? '').toLowerCase().includes(searchString.toLowerCase()));
  }
}
