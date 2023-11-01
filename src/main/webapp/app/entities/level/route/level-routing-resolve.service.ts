import { inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of, EMPTY, Observable } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { ILevel } from '../level.model';
import { LevelService } from '../service/level.service';

export const levelResolve = (route: ActivatedRouteSnapshot): Observable<null | ILevel> => {
  const id = route.params['id'];
  if (id) {
    return inject(LevelService)
      .find(id)
      .pipe(
        mergeMap((level: HttpResponse<ILevel>) => {
          if (level.body) {
            return of(level.body);
          } else {
            inject(Router).navigate(['404']);
            return EMPTY;
          }
        }),
      );
  }
  return of(null);
};

export default levelResolve;
