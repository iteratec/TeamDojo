import { Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ASC } from 'app/config/navigation.constants';
import { ImageComponent } from './list/image.component';
import { ImageDetailComponent } from './detail/image-detail.component';
import { ImageUpdateComponent } from './update/image-update.component';
import ImageResolve from './route/image-routing-resolve.service';

const imageRoute: Routes = [
  {
    path: '',
    component: ImageComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: ImageDetailComponent,
    resolve: {
      image: ImageResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: ImageUpdateComponent,
    resolve: {
      image: ImageResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: ImageUpdateComponent,
    resolve: {
      image: ImageResolve,
    },
    canActivate: [UserRouteAccessService],
  },
];

export default imageRoute;
