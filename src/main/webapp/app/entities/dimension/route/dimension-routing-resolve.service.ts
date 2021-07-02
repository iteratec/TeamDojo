import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IDimension, Dimension } from '../dimension.model';
import { DimensionService } from '../service/dimension.service';

@Injectable({ providedIn: 'root' })
export class DimensionRoutingResolveService implements Resolve<IDimension> {
  constructor(protected service: DimensionService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IDimension> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((dimension: HttpResponse<Dimension>) => {
          if (dimension.body) {
            return of(dimension.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Dimension());
  }
}
