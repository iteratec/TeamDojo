import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { ILevelSkill, NewLevelSkill } from '../level-skill.model';

export type PartialUpdateLevelSkill = Partial<ILevelSkill> & Pick<ILevelSkill, 'id'>;

export type EntityResponseType = HttpResponse<ILevelSkill>;
export type EntityArrayResponseType = HttpResponse<ILevelSkill[]>;

@Injectable({ providedIn: 'root' })
export class LevelSkillService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/level-skills');

  constructor(
    protected http: HttpClient,
    protected applicationConfigService: ApplicationConfigService,
  ) {}

  create(levelSkill: NewLevelSkill): Observable<EntityResponseType> {
    return this.http.post<ILevelSkill>(this.resourceUrl, levelSkill, { observe: 'response' });
  }

  update(levelSkill: ILevelSkill): Observable<EntityResponseType> {
    return this.http.put<ILevelSkill>(`${this.resourceUrl}/${this.getLevelSkillIdentifier(levelSkill)}`, levelSkill, {
      observe: 'response',
    });
  }

  partialUpdate(levelSkill: PartialUpdateLevelSkill): Observable<EntityResponseType> {
    return this.http.patch<ILevelSkill>(`${this.resourceUrl}/${this.getLevelSkillIdentifier(levelSkill)}`, levelSkill, {
      observe: 'response',
    });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ILevelSkill>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ILevelSkill[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getLevelSkillIdentifier(levelSkill: Pick<ILevelSkill, 'id'>): number {
    return levelSkill.id;
  }

  compareLevelSkill(o1: Pick<ILevelSkill, 'id'> | null, o2: Pick<ILevelSkill, 'id'> | null): boolean {
    return o1 && o2 ? this.getLevelSkillIdentifier(o1) === this.getLevelSkillIdentifier(o2) : o1 === o2;
  }

  addLevelSkillToCollectionIfMissing<Type extends Pick<ILevelSkill, 'id'>>(
    levelSkillCollection: Type[],
    ...levelSkillsToCheck: (Type | null | undefined)[]
  ): Type[] {
    const levelSkills: Type[] = levelSkillsToCheck.filter(isPresent);
    if (levelSkills.length > 0) {
      const levelSkillCollectionIdentifiers = levelSkillCollection.map(levelSkillItem => this.getLevelSkillIdentifier(levelSkillItem)!);
      const levelSkillsToAdd = levelSkills.filter(levelSkillItem => {
        const levelSkillIdentifier = this.getLevelSkillIdentifier(levelSkillItem);
        if (levelSkillCollectionIdentifiers.includes(levelSkillIdentifier)) {
          return false;
        }
        levelSkillCollectionIdentifiers.push(levelSkillIdentifier);
        return true;
      });
      return [...levelSkillsToAdd, ...levelSkillCollection];
    }
    return levelSkillCollection;
  }
}
