import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import { ApplicationConfigService } from '../../core/config/application-config.service';
import { ITeamScore } from '../entities/team-score/team-score.model';

export type EntityResponseType = HttpResponse<ITeamScore>;
export type EntityArrayResponseType = HttpResponse<ITeamScore[]>;

@Injectable({ providedIn: 'root' })
export class TeamSkillService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/team-score');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ITeamScore>(`${this.resourceUrl}/${id}`, { observe: 'response' }).pipe(map((res: EntityResponseType) => res));
  }
}
