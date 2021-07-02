import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { TeamSkillComponent } from '../list/team-skill.component';
import { TeamSkillDetailComponent } from '../detail/team-skill-detail.component';
import { TeamSkillUpdateComponent } from '../update/team-skill-update.component';
import { TeamSkillRoutingResolveService } from './team-skill-routing-resolve.service';

const teamSkillRoute: Routes = [
  {
    path: '',
    component: TeamSkillComponent,
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: TeamSkillDetailComponent,
    resolve: {
      teamSkill: TeamSkillRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: TeamSkillUpdateComponent,
    resolve: {
      teamSkill: TeamSkillRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: TeamSkillUpdateComponent,
    resolve: {
      teamSkill: TeamSkillRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(teamSkillRoute)],
  exports: [RouterModule],
})
export class TeamSkillRoutingModule {}
