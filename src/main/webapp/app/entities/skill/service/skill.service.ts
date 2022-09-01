import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import dayjs from 'dayjs/esm';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { ISkill, NewSkill } from '../skill.model';

export type PartialUpdateSkill = Partial<ISkill> & Pick<ISkill, 'id'>;

type RestOf<T extends ISkill | NewSkill> = Omit<T, 'createdAt' | 'updatedAt'> & {
  createdAt?: string | null;
  updatedAt?: string | null;
};

export type RestSkill = RestOf<ISkill>;

export type NewRestSkill = RestOf<NewSkill>;

export type PartialUpdateRestSkill = RestOf<PartialUpdateSkill>;

export type EntityResponseType = HttpResponse<ISkill>;
export type EntityArrayResponseType = HttpResponse<ISkill[]>;

@Injectable({ providedIn: 'root' })
export class SkillService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/skills');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(skill: NewSkill): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(skill);
    return this.http.post<RestSkill>(this.resourceUrl, copy, { observe: 'response' }).pipe(map(res => this.convertResponseFromServer(res)));
  }

  update(skill: ISkill): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(skill);
    return this.http
      .put<RestSkill>(`${this.resourceUrl}/${this.getSkillIdentifier(skill)}`, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  partialUpdate(skill: PartialUpdateSkill): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(skill);
    return this.http
      .patch<RestSkill>(`${this.resourceUrl}/${this.getSkillIdentifier(skill)}`, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<RestSkill>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<RestSkill[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map(res => this.convertResponseArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getSkillIdentifier(skill: Pick<ISkill, 'id'>): number {
    return skill.id;
  }

  compareSkill(o1: Pick<ISkill, 'id'> | null, o2: Pick<ISkill, 'id'> | null): boolean {
    return o1 && o2 ? this.getSkillIdentifier(o1) === this.getSkillIdentifier(o2) : o1 === o2;
  }

  addSkillToCollectionIfMissing<Type extends Pick<ISkill, 'id'>>(
    skillCollection: Type[],
    ...skillsToCheck: (Type | null | undefined)[]
  ): Type[] {
    const skills: Type[] = skillsToCheck.filter(isPresent);
    if (skills.length > 0) {
      const skillCollectionIdentifiers = skillCollection.map(skillItem => this.getSkillIdentifier(skillItem)!);
      const skillsToAdd = skills.filter(skillItem => {
        const skillIdentifier = this.getSkillIdentifier(skillItem);
        if (skillCollectionIdentifiers.includes(skillIdentifier)) {
          return false;
        }
        skillCollectionIdentifiers.push(skillIdentifier);
        return true;
      });
      return [...skillsToAdd, ...skillCollection];
    }
    return skillCollection;
  }

  protected convertDateFromClient<T extends ISkill | NewSkill | PartialUpdateSkill>(skill: T): RestOf<T> {
    return {
      ...skill,
      createdAt: skill.createdAt?.toJSON() ?? null,
      updatedAt: skill.updatedAt?.toJSON() ?? null,
    };
  }

  protected convertDateFromServer(restSkill: RestSkill): ISkill {
    return {
      ...restSkill,
      createdAt: restSkill.createdAt ? dayjs(restSkill.createdAt) : undefined,
      updatedAt: restSkill.updatedAt ? dayjs(restSkill.updatedAt) : undefined,
    };
  }

  protected convertResponseFromServer(res: HttpResponse<RestSkill>): HttpResponse<ISkill> {
    return res.clone({
      body: res.body ? this.convertDateFromServer(res.body) : null,
    });
  }

  protected convertResponseArrayFromServer(res: HttpResponse<RestSkill[]>): HttpResponse<ISkill[]> {
    return res.clone({
      body: res.body ? res.body.map(item => this.convertDateFromServer(item)) : null,
    });
  }
}
