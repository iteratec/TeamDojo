import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { ILevelSkill, getLevelSkillIdentifier } from '../level-skill.model';

export type EntityResponseType = HttpResponse<ILevelSkill>;
export type EntityArrayResponseType = HttpResponse<ILevelSkill[]>;

@Injectable({ providedIn: 'root' })
export class LevelSkillService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/level-skills');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(levelSkill: ILevelSkill): Observable<EntityResponseType> {
    return this.http.post<ILevelSkill>(this.resourceUrl, levelSkill, { observe: 'response' });
  }

  update(levelSkill: ILevelSkill): Observable<EntityResponseType> {
    return this.http.put<ILevelSkill>(`${this.resourceUrl}/${getLevelSkillIdentifier(levelSkill) as number}`, levelSkill, {
      observe: 'response',
    });
  }

  partialUpdate(levelSkill: ILevelSkill): Observable<EntityResponseType> {
    return this.http.patch<ILevelSkill>(`${this.resourceUrl}/${getLevelSkillIdentifier(levelSkill) as number}`, levelSkill, {
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

  addLevelSkillToCollectionIfMissing(
    levelSkillCollection: ILevelSkill[],
    ...levelSkillsToCheck: (ILevelSkill | null | undefined)[]
  ): ILevelSkill[] {
    const levelSkills: ILevelSkill[] = levelSkillsToCheck.filter(isPresent);
    if (levelSkills.length > 0) {
      const levelSkillCollectionIdentifiers = levelSkillCollection.map(levelSkillItem => getLevelSkillIdentifier(levelSkillItem)!);
      const levelSkillsToAdd = levelSkills.filter(levelSkillItem => {
        const levelSkillIdentifier = getLevelSkillIdentifier(levelSkillItem);
        if (levelSkillIdentifier == null || levelSkillCollectionIdentifiers.includes(levelSkillIdentifier)) {
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
