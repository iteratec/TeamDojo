import { inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of, EMPTY, Observable } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IActivity } from '../activity.model';
import { ActivityService } from '../service/activity.service';

export const activityResolve = (route: ActivatedRouteSnapshot): Observable<null | IActivity> => {
  const id = route.params['id'];
  if (id) {
    return inject(ActivityService)
      .find(id)
      .pipe(
        mergeMap((activity: HttpResponse<IActivity>) => {
          if (activity.body) {
            return of(activity.body);
          } else {
            inject(Router).navigate(['404']);
            return EMPTY;
          }
        }),
      );
  }
  return of(null);
};

export default activityResolve;
