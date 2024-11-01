import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { ILevelSkill, LevelSkill } from '../level-skill.model';
import { LevelSkillService } from '../service/level-skill.service';

@Injectable({ providedIn: 'root' })
export class LevelSkillRoutingResolveService implements Resolve<ILevelSkill> {
  constructor(protected service: LevelSkillService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ILevelSkill> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((levelSkill: HttpResponse<LevelSkill>) => {
          if (levelSkill.body) {
            return of(levelSkill.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new LevelSkill());
  }
}
