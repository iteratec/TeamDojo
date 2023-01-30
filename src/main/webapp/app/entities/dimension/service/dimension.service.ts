import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import dayjs from 'dayjs/esm';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IDimension, NewDimension } from '../dimension.model';

export type PartialUpdateDimension = Partial<IDimension> & Pick<IDimension, 'id'>;

type RestOf<T extends IDimension | NewDimension> = Omit<T, 'createdAt' | 'updatedAt'> & {
  createdAt?: string | null;
  updatedAt?: string | null;
};

export type RestDimension = RestOf<IDimension>;

export type NewRestDimension = RestOf<NewDimension>;

export type PartialUpdateRestDimension = RestOf<PartialUpdateDimension>;

export type EntityResponseType = HttpResponse<IDimension>;
export type EntityArrayResponseType = HttpResponse<IDimension[]>;

@Injectable({ providedIn: 'root' })
export class DimensionService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/dimensions');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(dimension: NewDimension): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(dimension);
    return this.http
      .post<RestDimension>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  update(dimension: IDimension): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(dimension);
    return this.http
      .put<RestDimension>(`${this.resourceUrl}/${this.getDimensionIdentifier(dimension)}`, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  partialUpdate(dimension: PartialUpdateDimension): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(dimension);
    return this.http
      .patch<RestDimension>(`${this.resourceUrl}/${this.getDimensionIdentifier(dimension)}`, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<RestDimension>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<RestDimension[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map(res => this.convertResponseArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getDimensionIdentifier(dimension: Pick<IDimension, 'id'>): number {
    return dimension.id;
  }

  compareDimension(o1: Pick<IDimension, 'id'> | null, o2: Pick<IDimension, 'id'> | null): boolean {
    return o1 && o2 ? this.getDimensionIdentifier(o1) === this.getDimensionIdentifier(o2) : o1 === o2;
  }

  addDimensionToCollectionIfMissing<Type extends Pick<IDimension, 'id'>>(
    dimensionCollection: Type[],
    ...dimensionsToCheck: (Type | null | undefined)[]
  ): Type[] {
    const dimensions: Type[] = dimensionsToCheck.filter(isPresent);
    if (dimensions.length > 0) {
      const dimensionCollectionIdentifiers = dimensionCollection.map(dimensionItem => this.getDimensionIdentifier(dimensionItem)!);
      const dimensionsToAdd = dimensions.filter(dimensionItem => {
        const dimensionIdentifier = this.getDimensionIdentifier(dimensionItem);
        if (dimensionCollectionIdentifiers.includes(dimensionIdentifier)) {
          return false;
        }
        dimensionCollectionIdentifiers.push(dimensionIdentifier);
        return true;
      });
      return [...dimensionsToAdd, ...dimensionCollection];
    }
    return dimensionCollection;
  }

  protected convertDateFromClient<T extends IDimension | NewDimension | PartialUpdateDimension>(dimension: T): RestOf<T> {
    return {
      ...dimension,
      createdAt: dimension.createdAt?.toJSON() ?? null,
      updatedAt: dimension.updatedAt?.toJSON() ?? null,
    };
  }

  protected convertDateFromServer(restDimension: RestDimension): IDimension {
    return {
      ...restDimension,
      createdAt: restDimension.createdAt ? dayjs(restDimension.createdAt) : undefined,
      updatedAt: restDimension.updatedAt ? dayjs(restDimension.updatedAt) : undefined,
    };
  }

  protected convertResponseFromServer(res: HttpResponse<RestDimension>): HttpResponse<IDimension> {
    return res.clone({
      body: res.body ? this.convertDateFromServer(res.body) : null,
    });
  }

  protected convertResponseArrayFromServer(res: HttpResponse<RestDimension[]>): HttpResponse<IDimension[]> {
    return res.clone({
      body: res.body ? res.body.map(item => this.convertDateFromServer(item)) : null,
    });
  }
}
