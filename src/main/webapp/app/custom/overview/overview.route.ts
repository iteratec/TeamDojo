import { Route } from '@angular/router';

import { OverviewComponent } from 'app/custom/overview/overview.component';
import { OverviewSkillDetailsComponent } from 'app/custom/overview/skills/skill-details/overview-skill-details.component';
import { TeamsSelectionResolve } from 'app/custom/teams-selection/teams-selection.resolve';
import { Authority } from 'app/config/authority.constants';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import {
  AllCommentsResolve,
  AllDimensionsResolve,
  AllSkillsResolve,
  AllTeamGroupsResolve,
  AllTrainingsResolve,
  DojoModelResolve,
  SkillResolve,
} from 'app/custom/common.resolver';

export const OVERVIEW_ROUTE: Route[] = [
  {
    path: '',
    component: OverviewComponent,
    data: {
      authorities: [Authority.USER],
      pageTitle: 'teamDojoApp.teams.home.title',
    },
    canActivate: [UserRouteAccessService],
    resolve: {
      dojoModel: DojoModelResolve,
      skills: AllSkillsResolve,
      selectedTeam: TeamsSelectionResolve,
      dimensions: AllDimensionsResolve,
      teamGroups: AllTeamGroupsResolve,
    },
  },
  {
    path: 'overview/:teamGroup',
    component: OverviewComponent,
    data: {
      authorities: [Authority.USER],
      pageTitle: 'teamDojoApp.teams.home.title',
    },
    canActivate: [UserRouteAccessService],
    resolve: {
      dojoModel: DojoModelResolve,
      skills: AllSkillsResolve,
      selectedTeam: TeamsSelectionResolve,
      dimensions: AllDimensionsResolve,
      teamGroups: AllTeamGroupsResolve,
    },
  },

  {
    path: 'overview/skills/:skillId',
    component: OverviewSkillDetailsComponent,
    resolve: {
      dojoModel: DojoModelResolve,
      skill: SkillResolve,
      comments: AllCommentsResolve,
      selectedTeam: TeamsSelectionResolve,
      skills: AllSkillsResolve,
      trainings: AllTrainingsResolve,
      dimensions: AllDimensionsResolve,
    },
    data: {
      authorities: [],
      pageTitle: 'teamDojoApp.teams.skills.title',
    },
  },
];
