import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IBadgeSkill, getBadgeSkillIdentifier } from '../badge-skill.model';

export type EntityResponseType = HttpResponse<IBadgeSkill>;
export type EntityArrayResponseType = HttpResponse<IBadgeSkill[]>;

@Injectable({ providedIn: 'root' })
export class BadgeSkillService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/badge-skills');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(badgeSkill: IBadgeSkill): Observable<EntityResponseType> {
    return this.http.post<IBadgeSkill>(this.resourceUrl, badgeSkill, { observe: 'response' });
  }

  update(badgeSkill: IBadgeSkill): Observable<EntityResponseType> {
    return this.http.put<IBadgeSkill>(`${this.resourceUrl}/${getBadgeSkillIdentifier(badgeSkill) as number}`, badgeSkill, {
      observe: 'response',
    });
  }

  partialUpdate(badgeSkill: IBadgeSkill): Observable<EntityResponseType> {
    return this.http.patch<IBadgeSkill>(`${this.resourceUrl}/${getBadgeSkillIdentifier(badgeSkill) as number}`, badgeSkill, {
      observe: 'response',
    });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IBadgeSkill>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IBadgeSkill[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addBadgeSkillToCollectionIfMissing(
    badgeSkillCollection: IBadgeSkill[],
    ...badgeSkillsToCheck: (IBadgeSkill | null | undefined)[]
  ): IBadgeSkill[] {
    const badgeSkills: IBadgeSkill[] = badgeSkillsToCheck.filter(isPresent);
    if (badgeSkills.length > 0) {
      const badgeSkillCollectionIdentifiers = badgeSkillCollection.map(badgeSkillItem => getBadgeSkillIdentifier(badgeSkillItem)!);
      const badgeSkillsToAdd = badgeSkills.filter(badgeSkillItem => {
        const badgeSkillIdentifier = getBadgeSkillIdentifier(badgeSkillItem);
        if (badgeSkillIdentifier == null || badgeSkillCollectionIdentifiers.includes(badgeSkillIdentifier)) {
          return false;
        }
        badgeSkillCollectionIdentifiers.push(badgeSkillIdentifier);
        return true;
      });
      return [...badgeSkillsToAdd, ...badgeSkillCollection];
    }
    return badgeSkillCollection;
  }
}
