import { inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of, EMPTY, Observable } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { ITraining } from '../training.model';
import { TrainingService } from '../service/training.service';

export const trainingResolve = (route: ActivatedRouteSnapshot): Observable<null | ITraining> => {
  const id = route.params['id'];
  if (id) {
    return inject(TrainingService)
      .find(id)
      .pipe(
        mergeMap((training: HttpResponse<ITraining>) => {
          if (training.body) {
            return of(training.body);
          } else {
            inject(Router).navigate(['404']);
            return EMPTY;
          }
        }),
      );
  }
  return of(null);
};

export default trainingResolve;
