import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { SkillService } from '../skill.service';
import { ISkillRate } from '../../../../custom/entities/skill-rate/skill-rate.model';
import { ISkill } from '../../skill.model';

export type EntityResponseType = HttpResponse<ISkill>;
export type EntityArrayResponseType = HttpResponse<ISkill[]>;

@Injectable({ providedIn: 'root' })
export class ExtendedSkillService extends SkillService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/skills');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {
    super(http, applicationConfigService);
  }

  createVote(skillRate: ISkillRate): Observable<EntityResponseType> {
    return this.http.post<ISkill>(`${this.resourceUrl}/${skillRate.skillId as number}/vote`, skillRate, { observe: 'response' });
  }
}
