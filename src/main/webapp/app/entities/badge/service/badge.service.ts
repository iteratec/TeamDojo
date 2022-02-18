import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import dayjs from 'dayjs/esm';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IBadge, getBadgeIdentifier } from '../badge.model';

export type EntityResponseType = HttpResponse<IBadge>;
export type EntityArrayResponseType = HttpResponse<IBadge[]>;

@Injectable({ providedIn: 'root' })
export class BadgeService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/badges');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(badge: IBadge): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(badge);
    return this.http
      .post<IBadge>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(badge: IBadge): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(badge);
    return this.http
      .put<IBadge>(`${this.resourceUrl}/${getBadgeIdentifier(badge) as number}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  partialUpdate(badge: IBadge): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(badge);
    return this.http
      .patch<IBadge>(`${this.resourceUrl}/${getBadgeIdentifier(badge) as number}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IBadge>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IBadge[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addBadgeToCollectionIfMissing(badgeCollection: IBadge[], ...badgesToCheck: (IBadge | null | undefined)[]): IBadge[] {
    const badges: IBadge[] = badgesToCheck.filter(isPresent);
    if (badges.length > 0) {
      const badgeCollectionIdentifiers = badgeCollection.map(badgeItem => getBadgeIdentifier(badgeItem)!);
      const badgesToAdd = badges.filter(badgeItem => {
        const badgeIdentifier = getBadgeIdentifier(badgeItem);
        if (badgeIdentifier == null || badgeCollectionIdentifiers.includes(badgeIdentifier)) {
          return false;
        }
        badgeCollectionIdentifiers.push(badgeIdentifier);
        return true;
      });
      return [...badgesToAdd, ...badgeCollection];
    }
    return badgeCollection;
  }

  protected convertDateFromClient(badge: IBadge): IBadge {
    return Object.assign({}, badge, {
      availableUntil: badge.availableUntil?.isValid() ? badge.availableUntil.toJSON() : undefined,
      createdAt: badge.createdAt?.isValid() ? badge.createdAt.toJSON() : undefined,
      updatedAt: badge.updatedAt?.isValid() ? badge.updatedAt.toJSON() : undefined,
    });
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.availableUntil = res.body.availableUntil ? dayjs(res.body.availableUntil) : undefined;
      res.body.createdAt = res.body.createdAt ? dayjs(res.body.createdAt) : undefined;
      res.body.updatedAt = res.body.updatedAt ? dayjs(res.body.updatedAt) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((badge: IBadge) => {
        badge.availableUntil = badge.availableUntil ? dayjs(badge.availableUntil) : undefined;
        badge.createdAt = badge.createdAt ? dayjs(badge.createdAt) : undefined;
        badge.updatedAt = badge.updatedAt ? dayjs(badge.updatedAt) : undefined;
      });
    }
    return res;
  }
}
