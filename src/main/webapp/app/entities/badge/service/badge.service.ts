import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { map } from 'rxjs/operators';

import dayjs from 'dayjs/esm';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IBadge, NewBadge } from '../badge.model';

export type PartialUpdateBadge = Partial<IBadge> & Pick<IBadge, 'id'>;

type RestOf<T extends IBadge | NewBadge> = Omit<T, 'availableUntil' | 'createdAt' | 'updatedAt'> & {
  availableUntil?: string | null;
  createdAt?: string | null;
  updatedAt?: string | null;
};

export type RestBadge = RestOf<IBadge>;

export type NewRestBadge = RestOf<NewBadge>;

export type PartialUpdateRestBadge = RestOf<PartialUpdateBadge>;

export type EntityResponseType = HttpResponse<IBadge>;
export type EntityArrayResponseType = HttpResponse<IBadge[]>;

@Injectable({ providedIn: 'root' })
export class BadgeService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/badges');

  constructor(
    protected http: HttpClient,
    protected applicationConfigService: ApplicationConfigService,
  ) {}

  create(badge: NewBadge): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(badge);
    return this.http.post<RestBadge>(this.resourceUrl, copy, { observe: 'response' }).pipe(map(res => this.convertResponseFromServer(res)));
  }

  update(badge: IBadge): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(badge);
    return this.http
      .put<RestBadge>(`${this.resourceUrl}/${this.getBadgeIdentifier(badge)}`, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  partialUpdate(badge: PartialUpdateBadge): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(badge);
    return this.http
      .patch<RestBadge>(`${this.resourceUrl}/${this.getBadgeIdentifier(badge)}`, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<RestBadge>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<RestBadge[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map(res => this.convertResponseArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getBadgeIdentifier(badge: Pick<IBadge, 'id'>): number {
    return badge.id;
  }

  compareBadge(o1: Pick<IBadge, 'id'> | null, o2: Pick<IBadge, 'id'> | null): boolean {
    return o1 && o2 ? this.getBadgeIdentifier(o1) === this.getBadgeIdentifier(o2) : o1 === o2;
  }

  addBadgeToCollectionIfMissing<Type extends Pick<IBadge, 'id'>>(
    badgeCollection: Type[],
    ...badgesToCheck: (Type | null | undefined)[]
  ): Type[] {
    const badges: Type[] = badgesToCheck.filter(isPresent);
    if (badges.length > 0) {
      const badgeCollectionIdentifiers = badgeCollection.map(badgeItem => this.getBadgeIdentifier(badgeItem)!);
      const badgesToAdd = badges.filter(badgeItem => {
        const badgeIdentifier = this.getBadgeIdentifier(badgeItem);
        if (badgeCollectionIdentifiers.includes(badgeIdentifier)) {
          return false;
        }
        badgeCollectionIdentifiers.push(badgeIdentifier);
        return true;
      });
      return [...badgesToAdd, ...badgeCollection];
    }
    return badgeCollection;
  }

  protected convertDateFromClient<T extends IBadge | NewBadge | PartialUpdateBadge>(badge: T): RestOf<T> {
    return {
      ...badge,
      availableUntil: badge.availableUntil?.toJSON() ?? null,
      createdAt: badge.createdAt?.toJSON() ?? null,
      updatedAt: badge.updatedAt?.toJSON() ?? null,
    };
  }

  protected convertDateFromServer(restBadge: RestBadge): IBadge {
    return {
      ...restBadge,
      availableUntil: restBadge.availableUntil ? dayjs(restBadge.availableUntil) : undefined,
      createdAt: restBadge.createdAt ? dayjs(restBadge.createdAt) : undefined,
      updatedAt: restBadge.updatedAt ? dayjs(restBadge.updatedAt) : undefined,
    };
  }

  protected convertResponseFromServer(res: HttpResponse<RestBadge>): HttpResponse<IBadge> {
    return res.clone({
      body: res.body ? this.convertDateFromServer(res.body) : null,
    });
  }

  protected convertResponseArrayFromServer(res: HttpResponse<RestBadge[]>): HttpResponse<IBadge[]> {
    return res.clone({
      body: res.body ? res.body.map(item => this.convertDateFromServer(item)) : null,
    });
  }
}
