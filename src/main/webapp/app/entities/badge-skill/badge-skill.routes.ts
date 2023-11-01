import { Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ASC } from 'app/config/navigation.constants';
import { BadgeSkillComponent } from './list/badge-skill.component';
import { BadgeSkillDetailComponent } from './detail/badge-skill-detail.component';
import { BadgeSkillUpdateComponent } from './update/badge-skill-update.component';
import BadgeSkillResolve from './route/badge-skill-routing-resolve.service';

const badgeSkillRoute: Routes = [
  {
    path: '',
    component: BadgeSkillComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: BadgeSkillDetailComponent,
    resolve: {
      badgeSkill: BadgeSkillResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: BadgeSkillUpdateComponent,
    resolve: {
      badgeSkill: BadgeSkillResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: BadgeSkillUpdateComponent,
    resolve: {
      badgeSkill: BadgeSkillResolve,
    },
    canActivate: [UserRouteAccessService],
  },
];

export default badgeSkillRoute;
