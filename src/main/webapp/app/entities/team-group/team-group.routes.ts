import { Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ASC } from 'app/config/navigation.constants';
import { TeamGroupComponent } from './list/team-group.component';
import { TeamGroupDetailComponent } from './detail/team-group-detail.component';
import { TeamGroupUpdateComponent } from './update/team-group-update.component';
import TeamGroupResolve from './route/team-group-routing-resolve.service';

const teamGroupRoute: Routes = [
  {
    path: '',
    component: TeamGroupComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: TeamGroupDetailComponent,
    resolve: {
      teamGroup: TeamGroupResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: TeamGroupUpdateComponent,
    resolve: {
      teamGroup: TeamGroupResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: TeamGroupUpdateComponent,
    resolve: {
      teamGroup: TeamGroupResolve,
    },
    canActivate: [UserRouteAccessService],
  },
];

export default teamGroupRoute;
