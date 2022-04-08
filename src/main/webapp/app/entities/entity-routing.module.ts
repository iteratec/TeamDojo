import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'activity',
        data: { pageTitle: 'teamDojoApp.activity.home.title' },
        loadChildren: () => import('./activity/activity.module').then(m => m.ActivityModule),
      },
      {
        path: 'badge',
        data: { pageTitle: 'teamDojoApp.badge.home.title' },
        loadChildren: () => import('./badge/badge.module').then(m => m.BadgeModule),
      },
      {
        path: 'badge-skill',
        data: { pageTitle: 'teamDojoApp.badgeSkill.home.title' },
        loadChildren: () => import('./badge-skill/badge-skill.module').then(m => m.BadgeSkillModule),
      },
      {
        path: 'comment',
        data: { pageTitle: 'teamDojoApp.comment.home.title' },
        loadChildren: () => import('./comment/comment.module').then(m => m.CommentModule),
      },
      {
        path: 'dimension',
        data: { pageTitle: 'teamDojoApp.dimension.home.title' },
        loadChildren: () => import('./dimension/dimension.module').then(m => m.DimensionModule),
      },
      {
        path: 'image',
        data: { pageTitle: 'teamDojoApp.image.home.title' },
        loadChildren: () => import('./image/image.module').then(m => m.ImageModule),
      },
      {
        path: 'level',
        data: { pageTitle: 'teamDojoApp.level.home.title' },
        loadChildren: () => import('./level/level.module').then(m => m.LevelModule),
      },
      {
        path: 'level-skill',
        data: { pageTitle: 'teamDojoApp.levelSkill.home.title' },
        loadChildren: () => import('./level-skill/level-skill.module').then(m => m.LevelSkillModule),
      },
      {
        path: 'report',
        data: { pageTitle: 'teamDojoApp.report.home.title' },
        loadChildren: () => import('./report/report.module').then(m => m.ReportModule),
      },
      {
        path: 'skill',
        data: { pageTitle: 'teamDojoApp.skill.home.title' },
        loadChildren: () => import('./skill/skill.module').then(m => m.SkillModule),
      },
      {
        path: 'team',
        data: { pageTitle: 'teamDojoApp.team.home.title' },
        loadChildren: () => import('./team/team.module').then(m => m.TeamModule),
      },
      {
        path: 'team-skill',
        data: { pageTitle: 'teamDojoApp.teamSkill.home.title' },
        loadChildren: () => import('./team-skill/team-skill.module').then(m => m.TeamSkillModule),
      },
      {
        path: 'training',
        data: { pageTitle: 'teamDojoApp.training.home.title' },
        loadChildren: () => import('./training/training.module').then(m => m.TrainingModule),
      },
      {
        path: 'team-group',
        data: { pageTitle: 'teamDojoApp.teamGroup.home.title' },
        loadChildren: () => import('./team-group/team-group.module').then(m => m.TeamGroupModule),
      },
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ]),
  ],
})
export class EntityRoutingModule {}
