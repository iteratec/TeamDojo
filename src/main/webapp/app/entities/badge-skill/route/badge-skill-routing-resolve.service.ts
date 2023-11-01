import { inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of, EMPTY, Observable } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IBadgeSkill } from '../badge-skill.model';
import { BadgeSkillService } from '../service/badge-skill.service';

export const badgeSkillResolve = (route: ActivatedRouteSnapshot): Observable<null | IBadgeSkill> => {
  const id = route.params['id'];
  if (id) {
    return inject(BadgeSkillService)
      .find(id)
      .pipe(
        mergeMap((badgeSkill: HttpResponse<IBadgeSkill>) => {
          if (badgeSkill.body) {
            return of(badgeSkill.body);
          } else {
            inject(Router).navigate(['404']);
            return EMPTY;
          }
        }),
      );
  }
  return of(null);
};

export default badgeSkillResolve;
