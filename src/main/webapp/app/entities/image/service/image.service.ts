import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import dayjs from 'dayjs/esm';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IImage, getImageIdentifier } from '../image.model';

export type EntityResponseType = HttpResponse<IImage>;
export type EntityArrayResponseType = HttpResponse<IImage[]>;

@Injectable({ providedIn: 'root' })
export class ImageService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/images');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(image: IImage): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(image);
    return this.http
      .post<IImage>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(image: IImage): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(image);
    return this.http
      .put<IImage>(`${this.resourceUrl}/${getImageIdentifier(image) as number}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  partialUpdate(image: IImage): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(image);
    return this.http
      .patch<IImage>(`${this.resourceUrl}/${getImageIdentifier(image) as number}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IImage>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IImage[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addImageToCollectionIfMissing(imageCollection: IImage[], ...imagesToCheck: (IImage | null | undefined)[]): IImage[] {
    const images: IImage[] = imagesToCheck.filter(isPresent);
    if (images.length > 0) {
      const imageCollectionIdentifiers = imageCollection.map(imageItem => getImageIdentifier(imageItem)!);
      const imagesToAdd = images.filter(imageItem => {
        const imageIdentifier = getImageIdentifier(imageItem);
        if (imageIdentifier == null || imageCollectionIdentifiers.includes(imageIdentifier)) {
          return false;
        }
        imageCollectionIdentifiers.push(imageIdentifier);
        return true;
      });
      return [...imagesToAdd, ...imageCollection];
    }
    return imageCollection;
  }

  getHash(id: number): Observable<HttpResponse<string>> {
    return this.http.get(`${this.resourceUrl}/${id}/hash`, { observe: 'response', responseType: 'text' });
  }

  protected convertDateFromClient(image: IImage): IImage {
    return Object.assign({}, image, {
      createdAt: image.createdAt?.isValid() ? image.createdAt.toJSON() : undefined,
      updatedAt: image.updatedAt?.isValid() ? image.updatedAt.toJSON() : undefined,
    });
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.createdAt = res.body.createdAt ? dayjs(res.body.createdAt) : undefined;
      res.body.updatedAt = res.body.updatedAt ? dayjs(res.body.updatedAt) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((image: IImage) => {
        image.createdAt = image.createdAt ? dayjs(image.createdAt) : undefined;
        image.updatedAt = image.updatedAt ? dayjs(image.updatedAt) : undefined;
      });
    }
    return res;
  }
}
