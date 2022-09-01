import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { BadgeSkillComponent } from '../list/badge-skill.component';
import { BadgeSkillDetailComponent } from '../detail/badge-skill-detail.component';
import { BadgeSkillUpdateComponent } from '../update/badge-skill-update.component';
import { BadgeSkillRoutingResolveService } from './badge-skill-routing-resolve.service';
import { ASC } from 'app/config/navigation.constants';

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
      badgeSkill: BadgeSkillRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: BadgeSkillUpdateComponent,
    resolve: {
      badgeSkill: BadgeSkillRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: BadgeSkillUpdateComponent,
    resolve: {
      badgeSkill: BadgeSkillRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(badgeSkillRoute)],
  exports: [RouterModule],
})
export class BadgeSkillRoutingModule {}
