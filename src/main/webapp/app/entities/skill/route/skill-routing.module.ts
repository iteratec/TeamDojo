import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { SkillComponent } from '../list/skill.component';
import { SkillDetailComponent } from '../detail/skill-detail.component';
import { SkillUpdateComponent } from '../update/skill-update.component';
import { SkillRoutingResolveService } from './skill-routing-resolve.service';

const skillRoute: Routes = [
  {
    path: '',
    component: SkillComponent,
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: SkillDetailComponent,
    resolve: {
      skill: SkillRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: SkillUpdateComponent,
    resolve: {
      skill: SkillRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: SkillUpdateComponent,
    resolve: {
      skill: SkillRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(skillRoute)],
  exports: [RouterModule],
})
export class SkillRoutingModule {}
