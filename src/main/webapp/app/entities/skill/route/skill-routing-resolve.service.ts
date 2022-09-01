import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { ISkill } from '../skill.model';
import { SkillService } from '../service/skill.service';

@Injectable({ providedIn: 'root' })
export class SkillRoutingResolveService implements Resolve<ISkill | null> {
  constructor(protected service: SkillService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ISkill | null | never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((skill: HttpResponse<ISkill>) => {
          if (skill.body) {
            return of(skill.body);
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
