import { Pipe, PipeTransform } from '@angular/core';
import { IAchievableSkill } from 'app/custom/entities/achievable-skill/achievable-skill.model';

type sortArg = keyof IAchievableSkill;

@Pipe({ name: 'achievableSkillSort' })
export class AchievableSkillSortPipe implements PipeTransform {
  transform(skills: IAchievableSkill[], sortProperty: sortArg): IAchievableSkill[] {
    const sortPropertyNullSafe = this.defaultString(sortProperty);
    const reverse = ['score', 'rateCount'].includes(sortPropertyNullSafe) ? -1 : 1;
    return Array.from(skills).sort(
      (skill1, skill2) => reverse * this.defaultString(skill1[sortProperty]).localeCompare(this.defaultString(skill2[sortProperty]))
    );
  }

  private defaultString(smth: any): string {
    return smth || smth === 0 ? String(smth) : String();
  }
}
