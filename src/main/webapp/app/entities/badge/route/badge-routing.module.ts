import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { BadgeComponent } from '../list/badge.component';
import { BadgeDetailComponent } from '../detail/badge-detail.component';
import { BadgeUpdateComponent } from '../update/badge-update.component';
import { BadgeRoutingResolveService } from './badge-routing-resolve.service';

const badgeRoute: Routes = [
  {
    path: '',
    component: BadgeComponent,
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: BadgeDetailComponent,
    resolve: {
      badge: BadgeRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: BadgeUpdateComponent,
    resolve: {
      badge: BadgeRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: BadgeUpdateComponent,
    resolve: {
      badge: BadgeRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(badgeRoute)],
  exports: [RouterModule],
})
export class BadgeRoutingModule {}
