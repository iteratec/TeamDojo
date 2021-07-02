import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as dayjs from 'dayjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IDimension, getDimensionIdentifier } from '../dimension.model';

export type EntityResponseType = HttpResponse<IDimension>;
export type EntityArrayResponseType = HttpResponse<IDimension[]>;

@Injectable({ providedIn: 'root' })
export class DimensionService {
  public resourceUrl = this.applicationConfigService.getEndpointFor('api/dimensions');

  constructor(protected http: HttpClient, private applicationConfigService: ApplicationConfigService) {}

  create(dimension: IDimension): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(dimension);
    return this.http
      .post<IDimension>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(dimension: IDimension): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(dimension);
    return this.http
      .put<IDimension>(`${this.resourceUrl}/${getDimensionIdentifier(dimension) as number}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  partialUpdate(dimension: IDimension): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(dimension);
    return this.http
      .patch<IDimension>(`${this.resourceUrl}/${getDimensionIdentifier(dimension) as number}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IDimension>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IDimension[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addDimensionToCollectionIfMissing(
    dimensionCollection: IDimension[],
    ...dimensionsToCheck: (IDimension | null | undefined)[]
  ): IDimension[] {
    const dimensions: IDimension[] = dimensionsToCheck.filter(isPresent);
    if (dimensions.length > 0) {
      const dimensionCollectionIdentifiers = dimensionCollection.map(dimensionItem => getDimensionIdentifier(dimensionItem)!);
      const dimensionsToAdd = dimensions.filter(dimensionItem => {
        const dimensionIdentifier = getDimensionIdentifier(dimensionItem);
        if (dimensionIdentifier == null || dimensionCollectionIdentifiers.includes(dimensionIdentifier)) {
          return false;
        }
        dimensionCollectionIdentifiers.push(dimensionIdentifier);
        return true;
      });
      return [...dimensionsToAdd, ...dimensionCollection];
    }
    return dimensionCollection;
  }

  protected convertDateFromClient(dimension: IDimension): IDimension {
    return Object.assign({}, dimension, {
      createdAt: dimension.createdAt?.isValid() ? dimension.createdAt.toJSON() : undefined,
      updatedAt: dimension.updatedAt?.isValid() ? dimension.updatedAt.toJSON() : undefined,
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
      res.body.forEach((dimension: IDimension) => {
        dimension.createdAt = dimension.createdAt ? dayjs(dimension.createdAt) : undefined;
        dimension.updatedAt = dimension.updatedAt ? dayjs(dimension.updatedAt) : undefined;
      });
    }
    return res;
  }
}
