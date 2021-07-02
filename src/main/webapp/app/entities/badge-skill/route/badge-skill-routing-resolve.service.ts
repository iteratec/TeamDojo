import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IBadgeSkill, BadgeSkill } from '../badge-skill.model';
import { BadgeSkillService } from '../service/badge-skill.service';

@Injectable({ providedIn: 'root' })
export class BadgeSkillRoutingResolveService implements Resolve<IBadgeSkill> {
  constructor(protected service: BadgeSkillService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IBadgeSkill> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((badgeSkill: HttpResponse<BadgeSkill>) => {
          if (badgeSkill.body) {
            return of(badgeSkill.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new BadgeSkill());
  }
}
