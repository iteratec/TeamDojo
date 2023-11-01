import { Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ASC } from 'app/config/navigation.constants';
import { TrainingComponent } from './list/training.component';
import { TrainingDetailComponent } from './detail/training-detail.component';
import { TrainingUpdateComponent } from './update/training-update.component';
import TrainingResolve from './route/training-routing-resolve.service';

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
      training: TrainingResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: TrainingUpdateComponent,
    resolve: {
      training: TrainingResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: TrainingUpdateComponent,
    resolve: {
      training: TrainingResolve,
    },
    canActivate: [UserRouteAccessService],
  },
];

export default trainingRoute;
