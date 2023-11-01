import { Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ASC } from 'app/config/navigation.constants';
import { LevelSkillComponent } from './list/level-skill.component';
import { LevelSkillDetailComponent } from './detail/level-skill-detail.component';
import { LevelSkillUpdateComponent } from './update/level-skill-update.component';
import LevelSkillResolve from './route/level-skill-routing-resolve.service';

const levelSkillRoute: Routes = [
  {
    path: '',
    component: LevelSkillComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: LevelSkillDetailComponent,
    resolve: {
      levelSkill: LevelSkillResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: LevelSkillUpdateComponent,
    resolve: {
      levelSkill: LevelSkillResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: LevelSkillUpdateComponent,
    resolve: {
      levelSkill: LevelSkillResolve,
    },
    canActivate: [UserRouteAccessService],
  },
];

export default levelSkillRoute;
