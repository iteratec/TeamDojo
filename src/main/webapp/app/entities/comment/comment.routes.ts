import { Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ASC } from 'app/config/navigation.constants';
import { CommentComponent } from './list/comment.component';
import { CommentDetailComponent } from './detail/comment-detail.component';
import { CommentUpdateComponent } from './update/comment-update.component';
import CommentResolve from './route/comment-routing-resolve.service';

const commentRoute: Routes = [
  {
    path: '',
    component: CommentComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: CommentDetailComponent,
    resolve: {
      comment: CommentResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: CommentUpdateComponent,
    resolve: {
      comment: CommentResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: CommentUpdateComponent,
    resolve: {
      comment: CommentResolve,
    },
    canActivate: [UserRouteAccessService],
  },
];

export default commentRoute;
