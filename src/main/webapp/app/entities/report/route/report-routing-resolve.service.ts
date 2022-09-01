import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IReport } from '../report.model';
import { ReportService } from '../service/report.service';

@Injectable({ providedIn: 'root' })
export class ReportRoutingResolveService implements Resolve<IReport | null> {
  constructor(protected service: ReportService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IReport | null | never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((report: HttpResponse<IReport>) => {
          if (report.body) {
            return of(report.body);
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
