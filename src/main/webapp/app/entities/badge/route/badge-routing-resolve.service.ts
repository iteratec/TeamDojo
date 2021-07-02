import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IBadge, Badge } from '../badge.model';
import { BadgeService } from '../service/badge.service';

@Injectable({ providedIn: 'root' })
export class BadgeRoutingResolveService implements Resolve<IBadge> {
  constructor(protected service: BadgeService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IBadge> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((badge: HttpResponse<Badge>) => {
          if (badge.body) {
            return of(badge.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Badge());
  }
}
