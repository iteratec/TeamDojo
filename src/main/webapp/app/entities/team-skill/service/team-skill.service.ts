import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import dayjs from 'dayjs/esm';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { ITeamSkill, NewTeamSkill } from '../team-skill.model';

export type PartialUpdateTeamSkill = Partial<ITeamSkill> & Pick<ITeamSkill, 'id'>;

type RestOf<T extends ITeamSkill | NewTeamSkill> = Omit<T, 'completedAt' | 'verifiedAt' | 'createdAt' | 'updatedAt'> & {
  completedAt?: string | null;
  verifiedAt?: string | null;
  createdAt?: string | null;
  updatedAt?: string | null;
};

export type RestTeamSkill = RestOf<ITeamSkill>;

export type NewRestTeamSkill = RestOf<NewTeamSkill>;

export type PartialUpdateRestTeamSkill = RestOf<PartialUpdateTeamSkill>;

export type EntityResponseType = HttpResponse<ITeamSkill>;
export type EntityArrayResponseType = HttpResponse<ITeamSkill[]>;

@Injectable({ providedIn: 'root' })
export class TeamSkillService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/team-skills');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(teamSkill: NewTeamSkill): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(teamSkill);
    return this.http
      .post<RestTeamSkill>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  update(teamSkill: ITeamSkill): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(teamSkill);
    return this.http
      .put<RestTeamSkill>(`${this.resourceUrl}/${this.getTeamSkillIdentifier(teamSkill)}`, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  partialUpdate(teamSkill: PartialUpdateTeamSkill): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(teamSkill);
    return this.http
      .patch<RestTeamSkill>(`${this.resourceUrl}/${this.getTeamSkillIdentifier(teamSkill)}`, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<RestTeamSkill>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<RestTeamSkill[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map(res => this.convertResponseArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getTeamSkillIdentifier(teamSkill: Pick<ITeamSkill, 'id'>): number {
    return teamSkill.id;
  }

  compareTeamSkill(o1: Pick<ITeamSkill, 'id'> | null, o2: Pick<ITeamSkill, 'id'> | null): boolean {
    return o1 && o2 ? this.getTeamSkillIdentifier(o1) === this.getTeamSkillIdentifier(o2) : o1 === o2;
  }

  addTeamSkillToCollectionIfMissing<Type extends Pick<ITeamSkill, 'id'>>(
    teamSkillCollection: Type[],
    ...teamSkillsToCheck: (Type | null | undefined)[]
  ): Type[] {
    const teamSkills: Type[] = teamSkillsToCheck.filter(isPresent);
    if (teamSkills.length > 0) {
      const teamSkillCollectionIdentifiers = teamSkillCollection.map(teamSkillItem => this.getTeamSkillIdentifier(teamSkillItem)!);
      const teamSkillsToAdd = teamSkills.filter(teamSkillItem => {
        const teamSkillIdentifier = this.getTeamSkillIdentifier(teamSkillItem);
        if (teamSkillCollectionIdentifiers.includes(teamSkillIdentifier)) {
          return false;
        }
        teamSkillCollectionIdentifiers.push(teamSkillIdentifier);
        return true;
      });
      return [...teamSkillsToAdd, ...teamSkillCollection];
    }
    return teamSkillCollection;
  }

  protected convertDateFromClient<T extends ITeamSkill | NewTeamSkill | PartialUpdateTeamSkill>(teamSkill: T): RestOf<T> {
    return {
      ...teamSkill,
      completedAt: teamSkill.completedAt?.toJSON() ?? null,
      verifiedAt: teamSkill.verifiedAt?.toJSON() ?? null,
      createdAt: teamSkill.createdAt?.toJSON() ?? null,
      updatedAt: teamSkill.updatedAt?.toJSON() ?? null,
    };
  }

  protected convertDateFromServer(restTeamSkill: RestTeamSkill): ITeamSkill {
    return {
      ...restTeamSkill,
      completedAt: restTeamSkill.completedAt ? dayjs(restTeamSkill.completedAt) : undefined,
      verifiedAt: restTeamSkill.verifiedAt ? dayjs(restTeamSkill.verifiedAt) : undefined,
      createdAt: restTeamSkill.createdAt ? dayjs(restTeamSkill.createdAt) : undefined,
      updatedAt: restTeamSkill.updatedAt ? dayjs(restTeamSkill.updatedAt) : undefined,
    };
  }

  protected convertResponseFromServer(res: HttpResponse<RestTeamSkill>): HttpResponse<ITeamSkill> {
    return res.clone({
      body: res.body ? this.convertDateFromServer(res.body) : null,
    });
  }

  protected convertResponseArrayFromServer(res: HttpResponse<RestTeamSkill[]>): HttpResponse<ITeamSkill[]> {
    return res.clone({
      body: res.body ? res.body.map(item => this.convertDateFromServer(item)) : null,
    });
  }
}
