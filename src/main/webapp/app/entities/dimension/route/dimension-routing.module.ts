import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { DimensionComponent } from '../list/dimension.component';
import { DimensionDetailComponent } from '../detail/dimension-detail.component';
import { DimensionUpdateComponent } from '../update/dimension-update.component';
import { DimensionRoutingResolveService } from './dimension-routing-resolve.service';
import { ASC } from 'app/config/navigation.constants';

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
      dimension: DimensionRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: DimensionUpdateComponent,
    resolve: {
      dimension: DimensionRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: DimensionUpdateComponent,
    resolve: {
      dimension: DimensionRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(dimensionRoute)],
  exports: [RouterModule],
})
export class DimensionRoutingModule {}
