import { Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ASC } from 'app/config/navigation.constants';
import { LevelComponent } from './list/level.component';
import { LevelDetailComponent } from './detail/level-detail.component';
import { LevelUpdateComponent } from './update/level-update.component';
import LevelResolve from './route/level-routing-resolve.service';

const levelRoute: Routes = [
  {
    path: '',
    component: LevelComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: LevelDetailComponent,
    resolve: {
      level: LevelResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: LevelUpdateComponent,
    resolve: {
      level: LevelResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: LevelUpdateComponent,
    resolve: {
      level: LevelResolve,
    },
    canActivate: [UserRouteAccessService],
  },
];

export default levelRoute;
