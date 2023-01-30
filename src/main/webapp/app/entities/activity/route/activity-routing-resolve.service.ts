import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IActivity } from '../activity.model';
import { ActivityService } from '../service/activity.service';

@Injectable({ providedIn: 'root' })
export class ActivityRoutingResolveService implements Resolve<IActivity | null> {
  constructor(protected service: ActivityService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IActivity | null | never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((activity: HttpResponse<IActivity>) => {
          if (activity.body) {
            return of(activity.body);
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
