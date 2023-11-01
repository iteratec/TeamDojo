import { inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of, EMPTY, Observable } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { ITeamSkill } from '../team-skill.model';
import { TeamSkillService } from '../service/team-skill.service';

export const teamSkillResolve = (route: ActivatedRouteSnapshot): Observable<null | ITeamSkill> => {
  const id = route.params['id'];
  if (id) {
    return inject(TeamSkillService)
      .find(id)
      .pipe(
        mergeMap((teamSkill: HttpResponse<ITeamSkill>) => {
          if (teamSkill.body) {
            return of(teamSkill.body);
          } else {
            inject(Router).navigate(['404']);
            return EMPTY;
          }
        }),
      );
  }
  return of(null);
};

export default teamSkillResolve;
