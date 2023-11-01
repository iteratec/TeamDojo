import { inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of, EMPTY, Observable } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { ILevelSkill } from '../level-skill.model';
import { LevelSkillService } from '../service/level-skill.service';

export const levelSkillResolve = (route: ActivatedRouteSnapshot): Observable<null | ILevelSkill> => {
  const id = route.params['id'];
  if (id) {
    return inject(LevelSkillService)
      .find(id)
      .pipe(
        mergeMap((levelSkill: HttpResponse<ILevelSkill>) => {
          if (levelSkill.body) {
            return of(levelSkill.body);
          } else {
            inject(Router).navigate(['404']);
            return EMPTY;
          }
        }),
      );
  }
  return of(null);
};

export default levelSkillResolve;
