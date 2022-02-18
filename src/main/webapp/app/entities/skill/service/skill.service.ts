import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import dayjs from 'dayjs/esm';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { ISkill, getSkillIdentifier } from '../skill.model';

export type EntityResponseType = HttpResponse<ISkill>;
export type EntityArrayResponseType = HttpResponse<ISkill[]>;

@Injectable({ providedIn: 'root' })
export class SkillService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/skills');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(skill: ISkill): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(skill);
    return this.http
      .post<ISkill>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(skill: ISkill): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(skill);
    return this.http
      .put<ISkill>(`${this.resourceUrl}/${getSkillIdentifier(skill) as number}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  partialUpdate(skill: ISkill): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(skill);
    return this.http
      .patch<ISkill>(`${this.resourceUrl}/${getSkillIdentifier(skill) as number}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<ISkill>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<ISkill[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addSkillToCollectionIfMissing(skillCollection: ISkill[], ...skillsToCheck: (ISkill | null | undefined)[]): ISkill[] {
    const skills: ISkill[] = skillsToCheck.filter(isPresent);
    if (skills.length > 0) {
      const skillCollectionIdentifiers = skillCollection.map(skillItem => getSkillIdentifier(skillItem)!);
      const skillsToAdd = skills.filter(skillItem => {
        const skillIdentifier = getSkillIdentifier(skillItem);
        if (skillIdentifier == null || skillCollectionIdentifiers.includes(skillIdentifier)) {
          return false;
        }
        skillCollectionIdentifiers.push(skillIdentifier);
        return true;
      });
      return [...skillsToAdd, ...skillCollection];
    }
    return skillCollection;
  }

  protected convertDateFromClient(skill: ISkill): ISkill {
    return Object.assign({}, skill, {
      createdAt: skill.createdAt?.isValid() ? skill.createdAt.toJSON() : undefined,
      updatedAt: skill.updatedAt?.isValid() ? skill.updatedAt.toJSON() : undefined,
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
      res.body.forEach((skill: ISkill) => {
        skill.createdAt = skill.createdAt ? dayjs(skill.createdAt) : undefined;
        skill.updatedAt = skill.updatedAt ? dayjs(skill.updatedAt) : undefined;
      });
    }
    return res;
  }
}
