import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import dayjs from 'dayjs/esm';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { ITeamGroup, getTeamGroupIdentifier } from '../team-group.model';

export type EntityResponseType = HttpResponse<ITeamGroup>;
export type EntityArrayResponseType = HttpResponse<ITeamGroup[]>;

@Injectable({ providedIn: 'root' })
export class TeamGroupService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/team-groups');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(teamGroup: ITeamGroup): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(teamGroup);
    return this.http
      .post<ITeamGroup>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(teamGroup: ITeamGroup): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(teamGroup);
    return this.http
      .put<ITeamGroup>(`${this.resourceUrl}/${getTeamGroupIdentifier(teamGroup) as number}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  partialUpdate(teamGroup: ITeamGroup): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(teamGroup);
    return this.http
      .patch<ITeamGroup>(`${this.resourceUrl}/${getTeamGroupIdentifier(teamGroup) as number}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<ITeamGroup>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<ITeamGroup[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addTeamGroupToCollectionIfMissing(
    teamGroupCollection: ITeamGroup[],
    ...teamGroupsToCheck: (ITeamGroup | null | undefined)[]
  ): ITeamGroup[] {
    const teamGroups: ITeamGroup[] = teamGroupsToCheck.filter(isPresent);
    if (teamGroups.length > 0) {
      const teamGroupCollectionIdentifiers = teamGroupCollection.map(teamGroupItem => getTeamGroupIdentifier(teamGroupItem)!);
      const teamGroupsToAdd = teamGroups.filter(teamGroupItem => {
        const teamGroupIdentifier = getTeamGroupIdentifier(teamGroupItem);
        if (teamGroupIdentifier == null || teamGroupCollectionIdentifiers.includes(teamGroupIdentifier)) {
          return false;
        }
        teamGroupCollectionIdentifiers.push(teamGroupIdentifier);
        return true;
      });
      return [...teamGroupsToAdd, ...teamGroupCollection];
    }
    return teamGroupCollection;
  }

  protected convertDateFromClient(teamGroup: ITeamGroup): ITeamGroup {
    return Object.assign({}, teamGroup, {
      createdAt: teamGroup.createdAt?.isValid() ? teamGroup.createdAt.toJSON() : undefined,
      updatedAt: teamGroup.updatedAt?.isValid() ? teamGroup.updatedAt.toJSON() : undefined,
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
      res.body.forEach((teamGroup: ITeamGroup) => {
        teamGroup.createdAt = teamGroup.createdAt ? dayjs(teamGroup.createdAt) : undefined;
        teamGroup.updatedAt = teamGroup.updatedAt ? dayjs(teamGroup.updatedAt) : undefined;
      });
    }
    return res;
  }
}
