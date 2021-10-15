import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';

import { map } from 'rxjs/operators';
import { IBadge } from 'app/entities/badge/badge.model';
import { ILevel } from 'app/entities/level/level.model';
import { createRequestOption } from 'app/core/request/request-util';

export type EntityBadgeArrayResponseType = HttpResponse<IBadge[]>;
export type EntityLevelArrayResponseType = HttpResponse<ILevel[]>;

@Injectable()
export class TeamsAchievementsService {
  private badgeResourceUrl = SERVER_API_URL + 'api/badges';
  private levelResourceUrl = SERVER_API_URL + 'api/levels';

  constructor(private http: HttpClient) {}

  queryBadges(req?: any): Observable<EntityBadgeArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IBadge[]>(this.badgeResourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityBadgeArrayResponseType) => this.convertBadgeArrayResponse(res)));
  }

  queryLevels(req?: any): Observable<EntityLevelArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<ILevel[]>(this.levelResourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityLevelArrayResponseType) => this.convertLevelArrayResponse(res)));
  }

  private convertBadgeArrayResponse(res: EntityBadgeArrayResponseType): EntityBadgeArrayResponseType {
    const jsonResponse: IBadge[] = res.body ? res.body : [];
    const body: IBadge[] = [];
    for (let i = 0; i < jsonResponse.length; i++) {
      body.push(this.convertBadgeItemFromServer(jsonResponse[i]));
    }
    return res.clone({ body });
  }

  private convertLevelArrayResponse(res: EntityLevelArrayResponseType): EntityLevelArrayResponseType {
    const jsonResponse: ILevel[] = res.body ? res.body : [];
    const body: ILevel[] = [];
    for (let i = 0; i < jsonResponse.length; i++) {
      body.push(this.convertLevelItemFromServer(jsonResponse[i]));
    }
    return res.clone({ body });
  }

  /**
   * Convert a returned JSON object to Badge.
   */
  private convertBadgeItemFromServer(badge: IBadge): IBadge {
    const copy: IBadge = Object.assign({}, badge, {
      availableUntil: badge.availableUntil,
    });
    return copy;
  }

  /**
   * Convert a returned JSON object to Level.
   */
  private convertLevelItemFromServer(level: ILevel): ILevel {
    const copy: ILevel = Object.assign({}, level, {});
    return copy;
  }
}
