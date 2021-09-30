import { Route } from '@angular/router';

import { OverviewComponent } from 'app/custom/overview/overview.component';
import { OverviewSkillDetailsComponent } from 'app/custom/overview/skills/skill-details/overview-skill-details.component';
import { TeamsSelectionResolve } from 'app/custom/teams-selection/teams-selection.resolve';
import {
  AllCommentsResolve,
  AllDimensionsResolve,
  AllSkillsResolve,
  AllTrainingsResolve,
  DojoModelResolve,
  SkillResolve,
} from 'app/custom/common.resolver';

export const OVERVIEW_ROUTE: Route[] = [
  {
    path: '',
    component: OverviewComponent,
    data: {
      authorities: [],
      pageTitle: 'teamDojoApp.teams.home.title',
    },
    resolve: {
      dojoModel: DojoModelResolve,
      skills: AllSkillsResolve,
      selectedTeam: TeamsSelectionResolve,
      dimensions: AllDimensionsResolve,
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
