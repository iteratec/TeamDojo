import { Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ASC } from 'app/config/navigation.constants';
import { SkillComponent } from './list/skill.component';
import { SkillDetailComponent } from './detail/skill-detail.component';
import { SkillUpdateComponent } from './update/skill-update.component';
import SkillResolve from './route/skill-routing-resolve.service';

const skillRoute: Routes = [
  {
    path: '',
    component: SkillComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: SkillDetailComponent,
    resolve: {
      skill: SkillResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: SkillUpdateComponent,
    resolve: {
      skill: SkillResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: SkillUpdateComponent,
    resolve: {
      skill: SkillResolve,
    },
    canActivate: [UserRouteAccessService],
  },
];

export default skillRoute;
