import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { TrainingComponent } from '../list/training.component';
import { TrainingDetailComponent } from '../detail/training-detail.component';
import { TrainingUpdateComponent } from '../update/training-update.component';
import { TrainingRoutingResolveService } from './training-routing-resolve.service';
import { ASC } from 'app/config/navigation.constants';

const trainingRoute: Routes = [
  {
    path: '',
    component: TrainingComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: TrainingDetailComponent,
    resolve: {
      training: TrainingRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: TrainingUpdateComponent,
    resolve: {
      training: TrainingRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: TrainingUpdateComponent,
    resolve: {
      training: TrainingRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(trainingRoute)],
  exports: [RouterModule],
})
export class TrainingRoutingModule {}
