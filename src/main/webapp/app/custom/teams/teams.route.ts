import { ActivatedRouteSnapshot, Resolve, Route, Router, RouterStateSnapshot } from '@angular/router';
import { Injectable } from '@angular/core';
import { flatMap, map } from 'rxjs/operators';
import { TeamsService } from 'app/custom/teams/teams.service';
import { TeamSkillService } from 'app/entities/team-skill/service/team-skill.service';
import { Team } from 'app/entities/team/team.model';
import { TeamsComponent } from 'app/custom/teams/teams.component';
import { AllCommentsResolve, AllSkillsResolve, AllTrainingsResolve, DojoModelResolve, SkillResolve } from 'app/custom/common.resolver';
import { TeamsSelectionResolve } from 'app/custom/teams-selection/teams-selection.resolve';

@Injectable()
export class TeamAndTeamSkillResolve implements Resolve<any> {
  constructor(private teamService: TeamsService, private teamSkillService: TeamSkillService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
    const shortName = route.params['shortName'] ? route.params['shortName'] : null;
    if (shortName) {
      return this.teamService
        .query({
          'shortName.equals': shortName,
        })
        .pipe(
          flatMap(teamResponse => {
            if (teamResponse.body?.length === 0) {
              this.router.navigate(['/error']);
            }
            const team = teamResponse.body[0];
            return this.teamSkillService.query({ 'teamId.equals': team.id }).pipe(
              map(teamSkillResponse => {
                team.skills = teamSkillResponse.body;
                return team;
              })
            );
          })
        );
    }
    return new Team();
  }
}

export const TEAMS_ROUTES: Route[] = [
  {
    path: 'teams/:shortName',
    component: TeamsComponent,
    resolve: {
      dojoModel: DojoModelResolve,
      team: TeamAndTeamSkillResolve,
      skills: AllSkillsResolve,
    },
    data: {
      authorities: [],
      pageTitle: 'teamdojoApp.teams.home.title',
    },
  },
  /*{
    path: 'teams/:shortName/skills/:skillId',
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
      pageTitle: 'teamdojoApp.teams.skills.title',
    },
  }, */
];
