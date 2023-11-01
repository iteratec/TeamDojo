import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { map } from 'rxjs/operators';

import dayjs from 'dayjs/esm';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { ILevel, NewLevel } from '../level.model';

export type PartialUpdateLevel = Partial<ILevel> & Pick<ILevel, 'id'>;

type RestOf<T extends ILevel | NewLevel> = Omit<T, 'createdAt' | 'updatedAt'> & {
  createdAt?: string | null;
  updatedAt?: string | null;
};

export type RestLevel = RestOf<ILevel>;

export type NewRestLevel = RestOf<NewLevel>;

export type PartialUpdateRestLevel = RestOf<PartialUpdateLevel>;

export type EntityResponseType = HttpResponse<ILevel>;
export type EntityArrayResponseType = HttpResponse<ILevel[]>;

@Injectable({ providedIn: 'root' })
export class LevelService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/levels');

  constructor(
    protected http: HttpClient,
    protected applicationConfigService: ApplicationConfigService,
  ) {}

  create(level: NewLevel): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(level);
    return this.http.post<RestLevel>(this.resourceUrl, copy, { observe: 'response' }).pipe(map(res => this.convertResponseFromServer(res)));
  }

  update(level: ILevel): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(level);
    return this.http
      .put<RestLevel>(`${this.resourceUrl}/${this.getLevelIdentifier(level)}`, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  partialUpdate(level: PartialUpdateLevel): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(level);
    return this.http
      .patch<RestLevel>(`${this.resourceUrl}/${this.getLevelIdentifier(level)}`, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<RestLevel>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<RestLevel[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map(res => this.convertResponseArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getLevelIdentifier(level: Pick<ILevel, 'id'>): number {
    return level.id;
  }

  compareLevel(o1: Pick<ILevel, 'id'> | null, o2: Pick<ILevel, 'id'> | null): boolean {
    return o1 && o2 ? this.getLevelIdentifier(o1) === this.getLevelIdentifier(o2) : o1 === o2;
  }

  addLevelToCollectionIfMissing<Type extends Pick<ILevel, 'id'>>(
    levelCollection: Type[],
    ...levelsToCheck: (Type | null | undefined)[]
  ): Type[] {
    const levels: Type[] = levelsToCheck.filter(isPresent);
    if (levels.length > 0) {
      const levelCollectionIdentifiers = levelCollection.map(levelItem => this.getLevelIdentifier(levelItem)!);
      const levelsToAdd = levels.filter(levelItem => {
        const levelIdentifier = this.getLevelIdentifier(levelItem);
        if (levelCollectionIdentifiers.includes(levelIdentifier)) {
          return false;
        }
        levelCollectionIdentifiers.push(levelIdentifier);
        return true;
      });
      return [...levelsToAdd, ...levelCollection];
    }
    return levelCollection;
  }

  protected convertDateFromClient<T extends ILevel | NewLevel | PartialUpdateLevel>(level: T): RestOf<T> {
    return {
      ...level,
      createdAt: level.createdAt?.toJSON() ?? null,
      updatedAt: level.updatedAt?.toJSON() ?? null,
    };
  }

  protected convertDateFromServer(restLevel: RestLevel): ILevel {
    return {
      ...restLevel,
      createdAt: restLevel.createdAt ? dayjs(restLevel.createdAt) : undefined,
      updatedAt: restLevel.updatedAt ? dayjs(restLevel.updatedAt) : undefined,
    };
  }

  protected convertResponseFromServer(res: HttpResponse<RestLevel>): HttpResponse<ILevel> {
    return res.clone({
      body: res.body ? this.convertDateFromServer(res.body) : null,
    });
  }

  protected convertResponseArrayFromServer(res: HttpResponse<RestLevel[]>): HttpResponse<ILevel[]> {
    return res.clone({
      body: res.body ? res.body.map(item => this.convertDateFromServer(item)) : null,
    });
  }
}
