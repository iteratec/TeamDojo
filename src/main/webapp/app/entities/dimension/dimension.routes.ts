import { Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ASC } from 'app/config/navigation.constants';
import { DimensionComponent } from './list/dimension.component';
import { DimensionDetailComponent } from './detail/dimension-detail.component';
import { DimensionUpdateComponent } from './update/dimension-update.component';
import DimensionResolve from './route/dimension-routing-resolve.service';

const dimensionRoute: Routes = [
  {
    path: '',
    component: DimensionComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: DimensionDetailComponent,
    resolve: {
      dimension: DimensionResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: DimensionUpdateComponent,
    resolve: {
      dimension: DimensionResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: DimensionUpdateComponent,
    resolve: {
      dimension: DimensionResolve,
    },
    canActivate: [UserRouteAccessService],
  },
];

export default dimensionRoute;
