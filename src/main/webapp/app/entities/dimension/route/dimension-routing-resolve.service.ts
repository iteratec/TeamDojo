import { inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of, EMPTY, Observable } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IDimension } from '../dimension.model';
import { DimensionService } from '../service/dimension.service';

export const dimensionResolve = (route: ActivatedRouteSnapshot): Observable<null | IDimension> => {
  const id = route.params['id'];
  if (id) {
    return inject(DimensionService)
      .find(id)
      .pipe(
        mergeMap((dimension: HttpResponse<IDimension>) => {
          if (dimension.body) {
            return of(dimension.body);
          } else {
            inject(Router).navigate(['404']);
            return EMPTY;
          }
        }),
      );
  }
  return of(null);
};

export default dimensionResolve;
