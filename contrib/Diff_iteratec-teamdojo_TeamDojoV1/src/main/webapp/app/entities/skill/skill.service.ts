8d7
< import { ISkillRate } from 'app/shared/model/skill-rate.model';
38,41d36
<     }
< 
<     createVote(skillRate: ISkillRate): Observable<EntityResponseType> {
<         return this.http.post<ISkill>(`${this.resourceUrl}/${skillRate.skillId}/vote`, skillRate, { observe: 'response' });
