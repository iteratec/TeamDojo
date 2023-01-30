import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import dayjs from 'dayjs/esm';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IImage, NewImage } from '../image.model';

export type PartialUpdateImage = Partial<IImage> & Pick<IImage, 'id'>;

type RestOf<T extends IImage | NewImage> = Omit<T, 'createdAt' | 'updatedAt'> & {
  createdAt?: string | null;
  updatedAt?: string | null;
};

export type RestImage = RestOf<IImage>;

export type NewRestImage = RestOf<NewImage>;

export type PartialUpdateRestImage = RestOf<PartialUpdateImage>;

export type EntityResponseType = HttpResponse<IImage>;
export type EntityArrayResponseType = HttpResponse<IImage[]>;

@Injectable({ providedIn: 'root' })
export class ImageService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/images');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(image: NewImage): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(image);
    return this.http.post<RestImage>(this.resourceUrl, copy, { observe: 'response' }).pipe(map(res => this.convertResponseFromServer(res)));
  }

  update(image: IImage): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(image);
    return this.http
      .put<RestImage>(`${this.resourceUrl}/${this.getImageIdentifier(image)}`, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  partialUpdate(image: PartialUpdateImage): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(image);
    return this.http
      .patch<RestImage>(`${this.resourceUrl}/${this.getImageIdentifier(image)}`, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<RestImage>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<RestImage[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map(res => this.convertResponseArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getImageIdentifier(image: Pick<IImage, 'id'>): number {
    return image.id;
  }

  compareImage(o1: Pick<IImage, 'id'> | null, o2: Pick<IImage, 'id'> | null): boolean {
    return o1 && o2 ? this.getImageIdentifier(o1) === this.getImageIdentifier(o2) : o1 === o2;
  }

  addImageToCollectionIfMissing<Type extends Pick<IImage, 'id'>>(
    imageCollection: Type[],
    ...imagesToCheck: (Type | null | undefined)[]
  ): Type[] {
    const images: Type[] = imagesToCheck.filter(isPresent);
    if (images.length > 0) {
      const imageCollectionIdentifiers = imageCollection.map(imageItem => this.getImageIdentifier(imageItem)!);
      const imagesToAdd = images.filter(imageItem => {
        const imageIdentifier = this.getImageIdentifier(imageItem);
        if (imageCollectionIdentifiers.includes(imageIdentifier)) {
          return false;
        }
        imageCollectionIdentifiers.push(imageIdentifier);
        return true;
      });
      return [...imagesToAdd, ...imageCollection];
    }
    return imageCollection;
  }

  protected convertDateFromClient<T extends IImage | NewImage | PartialUpdateImage>(image: T): RestOf<T> {
    return {
      ...image,
      createdAt: image.createdAt?.toJSON() ?? null,
      updatedAt: image.updatedAt?.toJSON() ?? null,
    };
  }

  protected convertDateFromServer(restImage: RestImage): IImage {
    return {
      ...restImage,
      createdAt: restImage.createdAt ? dayjs(restImage.createdAt) : undefined,
      updatedAt: restImage.updatedAt ? dayjs(restImage.updatedAt) : undefined,
    };
  }

  protected convertResponseFromServer(res: HttpResponse<RestImage>): HttpResponse<IImage> {
    return res.clone({
      body: res.body ? this.convertDateFromServer(res.body) : null,
    });
  }

  protected convertResponseArrayFromServer(res: HttpResponse<RestImage[]>): HttpResponse<IImage[]> {
    return res.clone({
      body: res.body ? res.body.map(item => this.convertDateFromServer(item)) : null,
    });
  }
}
