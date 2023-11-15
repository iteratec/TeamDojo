/*
 SPDX-FileCopyrightText: the TeamDojo authors
 SPDX-License-Identifier: Apache-2.0
 */
import { ActivatedRouteSnapshot, Resolve, Route, Router, RouterStateSnapshot } from '@angular/router';
import { Injectable } from '@angular/core';
import { flatMap, map } from 'rxjs/operators';
import { TeamService } from 'app/entities/team/service/team.service';
import { TeamSkillService } from 'app/entities/team-skill/service/team-skill.service';
import { TeamsComponent } from 'app/custom/teams/teams.component';
import { AllCommentsResolve, AllSkillsResolve, AllTrainingsResolve, DojoModelResolve, SkillResolve } from 'app/custom/common.resolver';
import { TeamsSelectionResolve } from 'app/custom/teams-selection/teams-selection.resolve';
import { Observable } from 'rxjs';
import { SkillDetailsComponent } from 'app/custom/teams/skill-details/skill-details.component';
import { TEAM_SKILLS_PER_PAGE } from '../../config/pagination.constants';
import { Team } from '../custom.types';

@Injectable()
export class TeamAndTeamSkillResolve implements Resolve<any> {
  constructor(private teamService: TeamService, private teamSkillService: TeamSkillService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<Team> | Team {
    const shortTitle = route.params['shortTitle'] ? route.params['shortTitle'] : null;
    if (shortTitle) {
      return this.teamService
        .query({
          'shortTitle.equals': shortTitle,
        })
        .pipe(
          flatMap(teamResponse => {
            if (teamResponse.body?.length === 0) {
              this.router.navigate(['/error']);
            }
            const team: Team = teamResponse.body ? teamResponse.body[0] : {};
            return this.teamSkillService.query({ page: 0, size: TEAM_SKILLS_PER_PAGE, 'teamId.equals': team.id }).pipe(
              map(teamSkillResponse => {
                team.skills = teamSkillResponse.body;
                return team;
              })
            );
          })
        );
    }
    return {};
  }
}

export const TEAMS_ROUTES: Route[] = [
  {
    path: 'teams/:shortTitle',
    component: TeamsComponent,
    resolve: {
      dojoModel: DojoModelResolve,
      team: TeamAndTeamSkillResolve,
      skills: AllSkillsResolve,
    },
    data: {
      authorities: [],
      pageTitle: 'teamDojoApp.teams.home.title',
    },
  },
  {
    path: 'teams/:shortTitle/skills/:skillId',
    component: SkillDetailsComponent,
    resolve: {
      dojoModel: DojoModelResolve,
      team: TeamAndTeamSkillResolve,
      skill: SkillResolve,
      skills: AllSkillsResolve,
      comments: AllCommentsResolve,
      selectedTeam: TeamsSelectionResolve,
      trainings: AllTrainingsResolve,
    },
    data: {
      authorities: [],
      pageTitle: 'teamDojoApp.teams.skills.title',
    },
  },
];
