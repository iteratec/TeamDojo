import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { TeamGroupComponent } from '../list/team-group.component';
import { TeamGroupDetailComponent } from '../detail/team-group-detail.component';
import { TeamGroupUpdateComponent } from '../update/team-group-update.component';
import { TeamGroupRoutingResolveService } from './team-group-routing-resolve.service';
import { ASC } from 'app/config/navigation.constants';

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
      teamGroup: TeamGroupRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: TeamGroupUpdateComponent,
    resolve: {
      teamGroup: TeamGroupRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: TeamGroupUpdateComponent,
    resolve: {
      teamGroup: TeamGroupRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(teamGroupRoute)],
  exports: [RouterModule],
})
export class TeamGroupRoutingModule {}
