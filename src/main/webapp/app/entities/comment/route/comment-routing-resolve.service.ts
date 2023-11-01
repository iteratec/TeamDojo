import { inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of, EMPTY, Observable } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IComment } from '../comment.model';
import { CommentService } from '../service/comment.service';

export const commentResolve = (route: ActivatedRouteSnapshot): Observable<null | IComment> => {
  const id = route.params['id'];
  if (id) {
    return inject(CommentService)
      .find(id)
      .pipe(
        mergeMap((comment: HttpResponse<IComment>) => {
          if (comment.body) {
            return of(comment.body);
          } else {
            inject(Router).navigate(['404']);
            return EMPTY;
          }
        }),
      );
  }
  return of(null);
};

export default commentResolve;
