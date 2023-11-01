import { inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of, EMPTY, Observable } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { ISkill } from '../skill.model';
import { SkillService } from '../service/skill.service';

export const skillResolve = (route: ActivatedRouteSnapshot): Observable<null | ISkill> => {
  const id = route.params['id'];
  if (id) {
    return inject(SkillService)
      .find(id)
      .pipe(
        mergeMap((skill: HttpResponse<ISkill>) => {
          if (skill.body) {
            return of(skill.body);
          } else {
            inject(Router).navigate(['404']);
            return EMPTY;
          }
        }),
      );
  }
  return of(null);
};

export default skillResolve;
