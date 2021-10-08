import { Pipe, PipeTransform } from '@angular/core';
import { IAchievableSkill } from 'app/custom/entities/achievable-skill/achievable-skill.model';

@Pipe({ name: 'achievableSkillFilter' })
export class AchievableSkillFilterPipe implements PipeTransform {
  transform(skills: IAchievableSkill[], searchString: string): IAchievableSkill[] {
    return skills.filter(skill => (skill.title ?? '').toLowerCase().includes(searchString.toLowerCase()));
  }
}
