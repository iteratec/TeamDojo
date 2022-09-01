import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { ITeamGroup } from '../team-group.model';
import { TeamGroupService } from '../service/team-group.service';

@Injectable({ providedIn: 'root' })
export class TeamGroupRoutingResolveService implements Resolve<ITeamGroup | null> {
  constructor(protected service: TeamGroupService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ITeamGroup | null | never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((teamGroup: HttpResponse<ITeamGroup>) => {
          if (teamGroup.body) {
            return of(teamGroup.body);
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
