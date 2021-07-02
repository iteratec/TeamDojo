import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { ITeamSkill, TeamSkill } from '../team-skill.model';
import { TeamSkillService } from '../service/team-skill.service';

@Injectable({ providedIn: 'root' })
export class TeamSkillRoutingResolveService implements Resolve<ITeamSkill> {
  constructor(protected service: TeamSkillService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ITeamSkill> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((teamSkill: HttpResponse<TeamSkill>) => {
          if (teamSkill.body) {
            return of(teamSkill.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new TeamSkill());
  }
}
