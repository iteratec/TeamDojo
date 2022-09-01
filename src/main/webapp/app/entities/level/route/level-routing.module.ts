import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { LevelComponent } from '../list/level.component';
import { LevelDetailComponent } from '../detail/level-detail.component';
import { LevelUpdateComponent } from '../update/level-update.component';
import { LevelRoutingResolveService } from './level-routing-resolve.service';
import { ASC } from 'app/config/navigation.constants';

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
      level: LevelRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: LevelUpdateComponent,
    resolve: {
      level: LevelRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: LevelUpdateComponent,
    resolve: {
      level: LevelRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(levelRoute)],
  exports: [RouterModule],
})
export class LevelRoutingModule {}
