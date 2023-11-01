import { Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ASC } from 'app/config/navigation.constants';
import { TeamSkillComponent } from './list/team-skill.component';
import { TeamSkillDetailComponent } from './detail/team-skill-detail.component';
import { TeamSkillUpdateComponent } from './update/team-skill-update.component';
import TeamSkillResolve from './route/team-skill-routing-resolve.service';

const teamSkillRoute: Routes = [
  {
    path: '',
    component: TeamSkillComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: TeamSkillDetailComponent,
    resolve: {
      teamSkill: TeamSkillResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: TeamSkillUpdateComponent,
    resolve: {
      teamSkill: TeamSkillResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: TeamSkillUpdateComponent,
    resolve: {
      teamSkill: TeamSkillResolve,
    },
    canActivate: [UserRouteAccessService],
  },
];

export default teamSkillRoute;
