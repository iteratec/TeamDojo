/*
 SPDX-FileCopyrightText: the TeamDojo authors
 SPDX-License-Identifier: Apache-2.0
 */
import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';

import { Observable } from 'rxjs';

import moment from 'moment';
import { map } from 'rxjs/operators';
import { AchievableSkill, IAchievableSkill } from 'app/custom/entities/achievable-skill/achievable-skill.model';
import { createRequestOption } from 'app/core/request/request-util';

export type EntityArrayResponseType = HttpResponse<IAchievableSkill[]>;
export type EntityResponseType = HttpResponse<IAchievableSkill>;

@Injectable()
export class TeamsSkillsService {
  private resourceUrl = SERVER_API_URL + 'api/teams';

  constructor(private http: HttpClient) {}

  findAchievableSkill(teamId: number, skillId: number, req?: any): Observable<IAchievableSkill> {
    const options = createRequestOption(req);
    return this.http
      .get<IAchievableSkill>(`${this.resourceUrl}/${teamId}/achievable-skills/${skillId}`, {
        params: options,
        observe: 'response',
      })
      .pipe(
        map(res => {
          if (res.body) {
            return this.convertDateFromServer(res.body);
          }

          return new AchievableSkill();
        })
      );
  }

  queryAchievableSkills(teamId: number, req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IAchievableSkill[]>(`${this.resourceUrl}/${teamId}/achievable-skills`, {
        params: options,
        observe: 'response',
      })
      .pipe(map((res: EntityArrayResponseType) => this.convertArrayResponse(res)));
  }

  queryAchievableSkillsByDimension(teamId: number, req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IAchievableSkill[]>(`${this.resourceUrl}/${teamId}/achievable-skills-by-dimension`, {
        params: options,
        observe: 'response',
      })
      .pipe(map((res: EntityArrayResponseType) => this.convertArrayResponse(res)));
  }

  updateAchievableSkill(teamId: number, skill: IAchievableSkill): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(skill);
    return this.http
      .put<IAchievableSkill>(`${this.resourceUrl}/${teamId}/achievable-skills`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertResponse(res)));
  }

  private convertResponse(res: EntityResponseType): EntityResponseType {
    const body: IAchievableSkill = res.body ? this.convertDateFromServer(res.body) : new AchievableSkill();
    return res.clone({ body });
  }

  private convertArrayResponse(res: EntityArrayResponseType): EntityArrayResponseType {
    const jsonResponse: IAchievableSkill[] = res.body ? res.body : [];
    const body: IAchievableSkill[] = [];
    for (let i = 0; i < jsonResponse.length; i++) {
      body.push(this.convertDateFromServer(jsonResponse[i]));
    }
    return res.clone({ body });
  }

  /**
   * Convert a returned JSON object to Skill.
   */
  private convertDateFromServer(achievableSkill: IAchievableSkill): IAchievableSkill {
    return Object.assign({}, achievableSkill, {
      achievedAt: achievableSkill.achievedAt != null ? moment(achievableSkill.achievedAt) : achievableSkill.achievedAt,
      verifiedAt: achievableSkill.verifiedAt != null ? moment(achievableSkill.verifiedAt) : achievableSkill.verifiedAt,
    });
  }

  /**
   * Convert a Skill to a JSON which can be sent to the server.
   */
  private convertDateFromClient(achievableSkill: IAchievableSkill): IAchievableSkill {
    return Object.assign({}, achievableSkill, {
      completedAt: achievableSkill.achievedAt?.isValid() ? achievableSkill.achievedAt.toJSON() : null,
      verifiedAt: achievableSkill.verifiedAt?.isValid() ? achievableSkill.verifiedAt.toJSON() : null,
    });
  }
}
