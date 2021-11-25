import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { TeamSkillVoteComponent } from 'app/custom/entities/team-skill/team-skill-vote/team-skill-vote.component';
import { TeamSkillRoutingResolveService } from 'app/entities/team-skill/route/team-skill-routing-resolve.service';

const teamSkillRoute: Routes = [
  {
    path: ':id/vote',
    component: TeamSkillVoteComponent,
    resolve: {
      teamSkill: TeamSkillRoutingResolveService,
    },
    data: {
      pageTitle: 'teamDojoApp.teamSkill.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(teamSkillRoute)],
  exports: [RouterModule],
})
export class CustomTeamSkillRoutingModule {}
