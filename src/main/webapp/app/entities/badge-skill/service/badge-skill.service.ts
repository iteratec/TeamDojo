import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IBadgeSkill, NewBadgeSkill } from '../badge-skill.model';

export type PartialUpdateBadgeSkill = Partial<IBadgeSkill> & Pick<IBadgeSkill, 'id'>;

export type EntityResponseType = HttpResponse<IBadgeSkill>;
export type EntityArrayResponseType = HttpResponse<IBadgeSkill[]>;

@Injectable({ providedIn: 'root' })
export class BadgeSkillService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/badge-skills');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(badgeSkill: NewBadgeSkill): Observable<EntityResponseType> {
    return this.http.post<IBadgeSkill>(this.resourceUrl, badgeSkill, { observe: 'response' });
  }

  update(badgeSkill: IBadgeSkill): Observable<EntityResponseType> {
    return this.http.put<IBadgeSkill>(`${this.resourceUrl}/${this.getBadgeSkillIdentifier(badgeSkill)}`, badgeSkill, {
      observe: 'response',
    });
  }

  partialUpdate(badgeSkill: PartialUpdateBadgeSkill): Observable<EntityResponseType> {
    return this.http.patch<IBadgeSkill>(`${this.resourceUrl}/${this.getBadgeSkillIdentifier(badgeSkill)}`, badgeSkill, {
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

  getBadgeSkillIdentifier(badgeSkill: Pick<IBadgeSkill, 'id'>): number {
    return badgeSkill.id;
  }

  compareBadgeSkill(o1: Pick<IBadgeSkill, 'id'> | null, o2: Pick<IBadgeSkill, 'id'> | null): boolean {
    return o1 && o2 ? this.getBadgeSkillIdentifier(o1) === this.getBadgeSkillIdentifier(o2) : o1 === o2;
  }

  addBadgeSkillToCollectionIfMissing<Type extends Pick<IBadgeSkill, 'id'>>(
    badgeSkillCollection: Type[],
    ...badgeSkillsToCheck: (Type | null | undefined)[]
  ): Type[] {
    const badgeSkills: Type[] = badgeSkillsToCheck.filter(isPresent);
    if (badgeSkills.length > 0) {
      const badgeSkillCollectionIdentifiers = badgeSkillCollection.map(badgeSkillItem => this.getBadgeSkillIdentifier(badgeSkillItem)!);
      const badgeSkillsToAdd = badgeSkills.filter(badgeSkillItem => {
        const badgeSkillIdentifier = this.getBadgeSkillIdentifier(badgeSkillItem);
        if (badgeSkillCollectionIdentifiers.includes(badgeSkillIdentifier)) {
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
