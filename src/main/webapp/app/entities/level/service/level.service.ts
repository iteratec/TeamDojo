import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import dayjs from 'dayjs/esm';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { ILevel, getLevelIdentifier } from '../level.model';

export type EntityResponseType = HttpResponse<ILevel>;
export type EntityArrayResponseType = HttpResponse<ILevel[]>;

@Injectable({ providedIn: 'root' })
export class LevelService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/levels');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(level: ILevel): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(level);
    return this.http
      .post<ILevel>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(level: ILevel): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(level);
    return this.http
      .put<ILevel>(`${this.resourceUrl}/${getLevelIdentifier(level) as number}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  partialUpdate(level: ILevel): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(level);
    return this.http
      .patch<ILevel>(`${this.resourceUrl}/${getLevelIdentifier(level) as number}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<ILevel>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<ILevel[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addLevelToCollectionIfMissing(levelCollection: ILevel[], ...levelsToCheck: (ILevel | null | undefined)[]): ILevel[] {
    const levels: ILevel[] = levelsToCheck.filter(isPresent);
    if (levels.length > 0) {
      const levelCollectionIdentifiers = levelCollection.map(levelItem => getLevelIdentifier(levelItem)!);
      const levelsToAdd = levels.filter(levelItem => {
        const levelIdentifier = getLevelIdentifier(levelItem);
        if (levelIdentifier == null || levelCollectionIdentifiers.includes(levelIdentifier)) {
          return false;
        }
        levelCollectionIdentifiers.push(levelIdentifier);
        return true;
      });
      return [...levelsToAdd, ...levelCollection];
    }
    return levelCollection;
  }

  protected convertDateFromClient(level: ILevel): ILevel {
    return Object.assign({}, level, {
      createdAt: level.createdAt?.isValid() ? level.createdAt.toJSON() : undefined,
      updatedAt: level.updatedAt?.isValid() ? level.updatedAt.toJSON() : undefined,
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
      res.body.forEach((level: ILevel) => {
        level.createdAt = level.createdAt ? dayjs(level.createdAt) : undefined;
        level.updatedAt = level.updatedAt ? dayjs(level.updatedAt) : undefined;
      });
    }
    return res;
  }
}
