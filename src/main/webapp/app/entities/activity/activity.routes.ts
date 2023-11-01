import { Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ASC } from 'app/config/navigation.constants';
import { ActivityComponent } from './list/activity.component';
import { ActivityDetailComponent } from './detail/activity-detail.component';
import { ActivityUpdateComponent } from './update/activity-update.component';
import ActivityResolve from './route/activity-routing-resolve.service';

const activityRoute: Routes = [
  {
    path: '',
    component: ActivityComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: ActivityDetailComponent,
    resolve: {
      activity: ActivityResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: ActivityUpdateComponent,
    resolve: {
      activity: ActivityResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: ActivityUpdateComponent,
    resolve: {
      activity: ActivityResolve,
    },
    canActivate: [UserRouteAccessService],
  },
];

export default activityRoute;
