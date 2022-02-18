import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import dayjs from 'dayjs/esm';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { ITeamSkill, getTeamSkillIdentifier } from '../team-skill.model';

export type EntityResponseType = HttpResponse<ITeamSkill>;
export type EntityArrayResponseType = HttpResponse<ITeamSkill[]>;

@Injectable({ providedIn: 'root' })
export class TeamSkillService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/team-skills');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(teamSkill: ITeamSkill): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(teamSkill);
    return this.http
      .post<ITeamSkill>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(teamSkill: ITeamSkill): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(teamSkill);
    return this.http
      .put<ITeamSkill>(`${this.resourceUrl}/${getTeamSkillIdentifier(teamSkill) as number}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  partialUpdate(teamSkill: ITeamSkill): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(teamSkill);
    return this.http
      .patch<ITeamSkill>(`${this.resourceUrl}/${getTeamSkillIdentifier(teamSkill) as number}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<ITeamSkill>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<ITeamSkill[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addTeamSkillToCollectionIfMissing(
    teamSkillCollection: ITeamSkill[],
    ...teamSkillsToCheck: (ITeamSkill | null | undefined)[]
  ): ITeamSkill[] {
    const teamSkills: ITeamSkill[] = teamSkillsToCheck.filter(isPresent);
    if (teamSkills.length > 0) {
      const teamSkillCollectionIdentifiers = teamSkillCollection.map(teamSkillItem => getTeamSkillIdentifier(teamSkillItem)!);
      const teamSkillsToAdd = teamSkills.filter(teamSkillItem => {
        const teamSkillIdentifier = getTeamSkillIdentifier(teamSkillItem);
        if (teamSkillIdentifier == null || teamSkillCollectionIdentifiers.includes(teamSkillIdentifier)) {
          return false;
        }
        teamSkillCollectionIdentifiers.push(teamSkillIdentifier);
        return true;
      });
      return [...teamSkillsToAdd, ...teamSkillCollection];
    }
    return teamSkillCollection;
  }

  protected convertDateFromClient(teamSkill: ITeamSkill): ITeamSkill {
    return Object.assign({}, teamSkill, {
      completedAt: teamSkill.completedAt?.isValid() ? teamSkill.completedAt.toJSON() : undefined,
      verifiedAt: teamSkill.verifiedAt?.isValid() ? teamSkill.verifiedAt.toJSON() : undefined,
      createdAt: teamSkill.createdAt?.isValid() ? teamSkill.createdAt.toJSON() : undefined,
      updatedAt: teamSkill.updatedAt?.isValid() ? teamSkill.updatedAt.toJSON() : undefined,
    });
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.completedAt = res.body.completedAt ? dayjs(res.body.completedAt) : undefined;
      res.body.verifiedAt = res.body.verifiedAt ? dayjs(res.body.verifiedAt) : undefined;
      res.body.createdAt = res.body.createdAt ? dayjs(res.body.createdAt) : undefined;
      res.body.updatedAt = res.body.updatedAt ? dayjs(res.body.updatedAt) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((teamSkill: ITeamSkill) => {
        teamSkill.completedAt = teamSkill.completedAt ? dayjs(teamSkill.completedAt) : undefined;
        teamSkill.verifiedAt = teamSkill.verifiedAt ? dayjs(teamSkill.verifiedAt) : undefined;
        teamSkill.createdAt = teamSkill.createdAt ? dayjs(teamSkill.createdAt) : undefined;
        teamSkill.updatedAt = teamSkill.updatedAt ? dayjs(teamSkill.updatedAt) : undefined;
      });
    }
    return res;
  }
}
