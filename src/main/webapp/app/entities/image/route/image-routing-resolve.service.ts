import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IImage } from '../image.model';
import { ImageService } from '../service/image.service';

@Injectable({ providedIn: 'root' })
export class ImageRoutingResolveService implements Resolve<IImage | null> {
  constructor(protected service: ImageService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IImage | null | never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((image: HttpResponse<IImage>) => {
          if (image.body) {
            return of(image.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(null);
  }
}
