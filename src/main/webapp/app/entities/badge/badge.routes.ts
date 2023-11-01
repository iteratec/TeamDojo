import { Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ASC } from 'app/config/navigation.constants';
import { BadgeComponent } from './list/badge.component';
import { BadgeDetailComponent } from './detail/badge-detail.component';
import { BadgeUpdateComponent } from './update/badge-update.component';
import BadgeResolve from './route/badge-routing-resolve.service';

const badgeRoute: Routes = [
  {
    path: '',
    component: BadgeComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: BadgeDetailComponent,
    resolve: {
      badge: BadgeResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: BadgeUpdateComponent,
    resolve: {
      badge: BadgeResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: BadgeUpdateComponent,
    resolve: {
      badge: BadgeResolve,
    },
    canActivate: [UserRouteAccessService],
  },
];

export default badgeRoute;
