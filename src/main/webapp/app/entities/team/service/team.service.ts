import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as dayjs from 'dayjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { ITeam, getTeamIdentifier } from '../team.model';

export type EntityResponseType = HttpResponse<ITeam>;
export type EntityArrayResponseType = HttpResponse<ITeam[]>;

@Injectable({ providedIn: 'root' })
export class TeamService {
  public resourceUrl = this.applicationConfigService.getEndpointFor('api/teams');

  constructor(protected http: HttpClient, private applicationConfigService: ApplicationConfigService) {}

  create(team: ITeam): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(team);
    return this.http
      .post<ITeam>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(team: ITeam): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(team);
    return this.http
      .put<ITeam>(`${this.resourceUrl}/${getTeamIdentifier(team) as number}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  partialUpdate(team: ITeam): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(team);
    return this.http
      .patch<ITeam>(`${this.resourceUrl}/${getTeamIdentifier(team) as number}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<ITeam>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<ITeam[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addTeamToCollectionIfMissing(teamCollection: ITeam[], ...teamsToCheck: (ITeam | null | undefined)[]): ITeam[] {
    const teams: ITeam[] = teamsToCheck.filter(isPresent);
    if (teams.length > 0) {
      const teamCollectionIdentifiers = teamCollection.map(teamItem => getTeamIdentifier(teamItem)!);
      const teamsToAdd = teams.filter(teamItem => {
        const teamIdentifier = getTeamIdentifier(teamItem);
        if (teamIdentifier == null || teamCollectionIdentifiers.includes(teamIdentifier)) {
          return false;
        }
        teamCollectionIdentifiers.push(teamIdentifier);
        return true;
      });
      return [...teamsToAdd, ...teamCollection];
    }
    return teamCollection;
  }

  protected convertDateFromClient(team: ITeam): ITeam {
    return Object.assign({}, team, {
      validUntil: team.validUntil?.isValid() ? team.validUntil.toJSON() : undefined,
      createdAt: team.createdAt?.isValid() ? team.createdAt.toJSON() : undefined,
      updatedAt: team.updatedAt?.isValid() ? team.updatedAt.toJSON() : undefined,
    });
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.validUntil = res.body.validUntil ? dayjs(res.body.validUntil) : undefined;
      res.body.createdAt = res.body.createdAt ? dayjs(res.body.createdAt) : undefined;
      res.body.updatedAt = res.body.updatedAt ? dayjs(res.body.updatedAt) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((team: ITeam) => {
        team.validUntil = team.validUntil ? dayjs(team.validUntil) : undefined;
        team.createdAt = team.createdAt ? dayjs(team.createdAt) : undefined;
        team.updatedAt = team.updatedAt ? dayjs(team.updatedAt) : undefined;
      });
    }
    return res;
  }
}
