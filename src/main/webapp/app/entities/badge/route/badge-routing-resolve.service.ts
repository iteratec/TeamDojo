import { inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of, EMPTY, Observable } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IBadge } from '../badge.model';
import { BadgeService } from '../service/badge.service';

export const badgeResolve = (route: ActivatedRouteSnapshot): Observable<null | IBadge> => {
  const id = route.params['id'];
  if (id) {
    return inject(BadgeService)
      .find(id)
      .pipe(
        mergeMap((badge: HttpResponse<IBadge>) => {
          if (badge.body) {
            return of(badge.body);
          } else {
            inject(Router).navigate(['404']);
            return EMPTY;
          }
        }),
      );
  }
  return of(null);
};

export default badgeResolve;
