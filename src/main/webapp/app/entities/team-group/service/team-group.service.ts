import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { map } from 'rxjs/operators';

import dayjs from 'dayjs/esm';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { ITeamGroup, NewTeamGroup } from '../team-group.model';

export type PartialUpdateTeamGroup = Partial<ITeamGroup> & Pick<ITeamGroup, 'id'>;

type RestOf<T extends ITeamGroup | NewTeamGroup> = Omit<T, 'createdAt' | 'updatedAt'> & {
  createdAt?: string | null;
  updatedAt?: string | null;
};

export type RestTeamGroup = RestOf<ITeamGroup>;

export type NewRestTeamGroup = RestOf<NewTeamGroup>;

export type PartialUpdateRestTeamGroup = RestOf<PartialUpdateTeamGroup>;

export type EntityResponseType = HttpResponse<ITeamGroup>;
export type EntityArrayResponseType = HttpResponse<ITeamGroup[]>;

@Injectable({ providedIn: 'root' })
export class TeamGroupService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/team-groups');

  constructor(
    protected http: HttpClient,
    protected applicationConfigService: ApplicationConfigService,
  ) {}

  create(teamGroup: NewTeamGroup): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(teamGroup);
    return this.http
      .post<RestTeamGroup>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  update(teamGroup: ITeamGroup): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(teamGroup);
    return this.http
      .put<RestTeamGroup>(`${this.resourceUrl}/${this.getTeamGroupIdentifier(teamGroup)}`, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  partialUpdate(teamGroup: PartialUpdateTeamGroup): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(teamGroup);
    return this.http
      .patch<RestTeamGroup>(`${this.resourceUrl}/${this.getTeamGroupIdentifier(teamGroup)}`, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<RestTeamGroup>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<RestTeamGroup[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map(res => this.convertResponseArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getTeamGroupIdentifier(teamGroup: Pick<ITeamGroup, 'id'>): number {
    return teamGroup.id;
  }

  compareTeamGroup(o1: Pick<ITeamGroup, 'id'> | null, o2: Pick<ITeamGroup, 'id'> | null): boolean {
    return o1 && o2 ? this.getTeamGroupIdentifier(o1) === this.getTeamGroupIdentifier(o2) : o1 === o2;
  }

  addTeamGroupToCollectionIfMissing<Type extends Pick<ITeamGroup, 'id'>>(
    teamGroupCollection: Type[],
    ...teamGroupsToCheck: (Type | null | undefined)[]
  ): Type[] {
    const teamGroups: Type[] = teamGroupsToCheck.filter(isPresent);
    if (teamGroups.length > 0) {
      const teamGroupCollectionIdentifiers = teamGroupCollection.map(teamGroupItem => this.getTeamGroupIdentifier(teamGroupItem)!);
      const teamGroupsToAdd = teamGroups.filter(teamGroupItem => {
        const teamGroupIdentifier = this.getTeamGroupIdentifier(teamGroupItem);
        if (teamGroupCollectionIdentifiers.includes(teamGroupIdentifier)) {
          return false;
        }
        teamGroupCollectionIdentifiers.push(teamGroupIdentifier);
        return true;
      });
      return [...teamGroupsToAdd, ...teamGroupCollection];
    }
    return teamGroupCollection;
  }

  protected convertDateFromClient<T extends ITeamGroup | NewTeamGroup | PartialUpdateTeamGroup>(teamGroup: T): RestOf<T> {
    return {
      ...teamGroup,
      createdAt: teamGroup.createdAt?.toJSON() ?? null,
      updatedAt: teamGroup.updatedAt?.toJSON() ?? null,
    };
  }

  protected convertDateFromServer(restTeamGroup: RestTeamGroup): ITeamGroup {
    return {
      ...restTeamGroup,
      createdAt: restTeamGroup.createdAt ? dayjs(restTeamGroup.createdAt) : undefined,
      updatedAt: restTeamGroup.updatedAt ? dayjs(restTeamGroup.updatedAt) : undefined,
    };
  }

  protected convertResponseFromServer(res: HttpResponse<RestTeamGroup>): HttpResponse<ITeamGroup> {
    return res.clone({
      body: res.body ? this.convertDateFromServer(res.body) : null,
    });
  }

  protected convertResponseArrayFromServer(res: HttpResponse<RestTeamGroup[]>): HttpResponse<ITeamGroup[]> {
    return res.clone({
      body: res.body ? res.body.map(item => this.convertDateFromServer(item)) : null,
    });
  }
}
