import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { map } from 'rxjs/operators';

import dayjs from 'dayjs/esm';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { ITeam, NewTeam } from '../team.model';

export type PartialUpdateTeam = Partial<ITeam> & Pick<ITeam, 'id'>;

type RestOf<T extends ITeam | NewTeam> = Omit<T, 'expirationDate' | 'createdAt' | 'updatedAt'> & {
  expirationDate?: string | null;
  createdAt?: string | null;
  updatedAt?: string | null;
};

export type RestTeam = RestOf<ITeam>;

export type NewRestTeam = RestOf<NewTeam>;

export type PartialUpdateRestTeam = RestOf<PartialUpdateTeam>;

export type EntityResponseType = HttpResponse<ITeam>;
export type EntityArrayResponseType = HttpResponse<ITeam[]>;

@Injectable({ providedIn: 'root' })
export class TeamService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/teams');

  constructor(
    protected http: HttpClient,
    protected applicationConfigService: ApplicationConfigService,
  ) {}

  create(team: NewTeam): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(team);
    return this.http.post<RestTeam>(this.resourceUrl, copy, { observe: 'response' }).pipe(map(res => this.convertResponseFromServer(res)));
  }

  update(team: ITeam): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(team);
    return this.http
      .put<RestTeam>(`${this.resourceUrl}/${this.getTeamIdentifier(team)}`, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  partialUpdate(team: PartialUpdateTeam): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(team);
    return this.http
      .patch<RestTeam>(`${this.resourceUrl}/${this.getTeamIdentifier(team)}`, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<RestTeam>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<RestTeam[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map(res => this.convertResponseArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getTeamIdentifier(team: Pick<ITeam, 'id'>): number {
    return team.id;
  }

  compareTeam(o1: Pick<ITeam, 'id'> | null, o2: Pick<ITeam, 'id'> | null): boolean {
    return o1 && o2 ? this.getTeamIdentifier(o1) === this.getTeamIdentifier(o2) : o1 === o2;
  }

  addTeamToCollectionIfMissing<Type extends Pick<ITeam, 'id'>>(
    teamCollection: Type[],
    ...teamsToCheck: (Type | null | undefined)[]
  ): Type[] {
    const teams: Type[] = teamsToCheck.filter(isPresent);
    if (teams.length > 0) {
      const teamCollectionIdentifiers = teamCollection.map(teamItem => this.getTeamIdentifier(teamItem)!);
      const teamsToAdd = teams.filter(teamItem => {
        const teamIdentifier = this.getTeamIdentifier(teamItem);
        if (teamCollectionIdentifiers.includes(teamIdentifier)) {
          return false;
        }
        teamCollectionIdentifiers.push(teamIdentifier);
        return true;
      });
      return [...teamsToAdd, ...teamCollection];
    }
    return teamCollection;
  }

  protected convertDateFromClient<T extends ITeam | NewTeam | PartialUpdateTeam>(team: T): RestOf<T> {
    return {
      ...team,
      expirationDate: team.expirationDate?.toJSON() ?? null,
      createdAt: team.createdAt?.toJSON() ?? null,
      updatedAt: team.updatedAt?.toJSON() ?? null,
    };
  }

  protected convertDateFromServer(restTeam: RestTeam): ITeam {
    return {
      ...restTeam,
      expirationDate: restTeam.expirationDate ? dayjs(restTeam.expirationDate) : undefined,
      createdAt: restTeam.createdAt ? dayjs(restTeam.createdAt) : undefined,
      updatedAt: restTeam.updatedAt ? dayjs(restTeam.updatedAt) : undefined,
    };
  }

  protected convertResponseFromServer(res: HttpResponse<RestTeam>): HttpResponse<ITeam> {
    return res.clone({
      body: res.body ? this.convertDateFromServer(res.body) : null,
    });
  }

  protected convertResponseArrayFromServer(res: HttpResponse<RestTeam[]>): HttpResponse<ITeam[]> {
    return res.clone({
      body: res.body ? res.body.map(item => this.convertDateFromServer(item)) : null,
    });
  }
}
