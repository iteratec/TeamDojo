import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { ILevel } from '../level.model';
import { LevelService } from '../service/level.service';

@Injectable({ providedIn: 'root' })
export class LevelRoutingResolveService implements Resolve<ILevel | null> {
  constructor(protected service: LevelService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ILevel | null | never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((level: HttpResponse<ILevel>) => {
          if (level.body) {
            return of(level.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(null);
  }
}
