import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { ITraining, Training } from '../training.model';
import { TrainingService } from '../service/training.service';

@Injectable({ providedIn: 'root' })
export class TrainingRoutingResolveService implements Resolve<ITraining> {
  constructor(protected service: TrainingService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ITraining> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((training: HttpResponse<Training>) => {
          if (training.body) {
            return of(training.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Training());
  }
}
