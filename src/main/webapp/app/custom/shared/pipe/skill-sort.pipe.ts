import { Pipe, PipeTransform } from '@angular/core';
import { Skill } from 'app/entities/skill/skill.model';

@Pipe({ name: 'skillSort' })
export class SkillSortPipe implements PipeTransform {
  transform(skills: Skill[], sortProperty: any): Skill[] {
    const sortPropertyNullSafe = this._defaultString(sortProperty);
    const reverse = ['score', 'rateCount'].includes(sortPropertyNullSafe) ? -1 : 1;
    return sortProperty
      ? Array.from(skills).sort(
          (skill1, skill2) =>
            reverse * this._defaultString(skill1[sortPropertyNullSafe]).localeCompare(this._defaultString(skill2[sortPropertyNullSafe]))
        )
      : skills;
  }

  _defaultString(smth: number): string {
    return smth || smth === 0 ? String(smth) : String();
  }
}
