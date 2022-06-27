/*
 SPDX-FileCopyrightText: the TeamDojo authors
 SPDX-License-Identifier: Apache-2.0
 */
import { Pipe, PipeTransform } from '@angular/core';
import { Skill } from 'app/entities/skill/skill.model';

type sortArg = keyof Skill;

@Pipe({ name: 'skillSort' })
export class SkillSortPipe implements PipeTransform {
  transform(skills: Skill[], sortProperty: sortArg): Skill[] {
    const sortPropertyNullSafe = this._defaultString(sortProperty);
    const reverse = ['score', 'rateCount'].includes(sortPropertyNullSafe) ? -1 : 1;
    return Array.from(skills).sort(
      (skill1, skill2) => reverse * this._defaultString(skill1[sortProperty]).localeCompare(this._defaultString(skill2[sortProperty]))
    );
  }

  _defaultString(smth: any): string {
    return smth || smth === 0 ? String(smth) : String();
  }
}
