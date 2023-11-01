import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'activity',
        data: { pageTitle: 'teamDojoApp.activity.home.title' },
        loadChildren: () => import('./activity/activity.routes'),
      },
      {
        path: 'badge',
        data: { pageTitle: 'teamDojoApp.badge.home.title' },
        loadChildren: () => import('./badge/badge.routes'),
      },
      {
        path: 'badge-skill',
        data: { pageTitle: 'teamDojoApp.badgeSkill.home.title' },
        loadChildren: () => import('./badge-skill/badge-skill.routes'),
      },
      {
        path: 'comment',
        data: { pageTitle: 'teamDojoApp.comment.home.title' },
        loadChildren: () => import('./comment/comment.routes'),
      },
      {
        path: 'dimension',
        data: { pageTitle: 'teamDojoApp.dimension.home.title' },
        loadChildren: () => import('./dimension/dimension.routes'),
      },
      {
        path: 'image',
        data: { pageTitle: 'teamDojoApp.image.home.title' },
        loadChildren: () => import('./image/image.routes'),
      },
      {
        path: 'level',
        data: { pageTitle: 'teamDojoApp.level.home.title' },
        loadChildren: () => import('./level/level.routes'),
      },
      {
        path: 'level-skill',
        data: { pageTitle: 'teamDojoApp.levelSkill.home.title' },
        loadChildren: () => import('./level-skill/level-skill.routes'),
      },
      {
        path: 'report',
        data: { pageTitle: 'teamDojoApp.report.home.title' },
        loadChildren: () => import('./report/report.routes'),
      },
      {
        path: 'skill',
        data: { pageTitle: 'teamDojoApp.skill.home.title' },
        loadChildren: () => import('./skill/skill.routes'),
      },
      {
        path: 'team',
        data: { pageTitle: 'teamDojoApp.team.home.title' },
        loadChildren: () => import('./team/team.routes'),
      },
      {
        path: 'team-skill',
        data: { pageTitle: 'teamDojoApp.teamSkill.home.title' },
        loadChildren: () => import('./team-skill/team-skill.routes'),
      },
      {
        path: 'training',
        data: { pageTitle: 'teamDojoApp.training.home.title' },
        loadChildren: () => import('./training/training.routes'),
      },
      {
        path: 'team-group',
        data: { pageTitle: 'teamDojoApp.teamGroup.home.title' },
        loadChildren: () => import('./team-group/team-group.routes'),
      },
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ]),
  ],
})
export class EntityRoutingModule {}
