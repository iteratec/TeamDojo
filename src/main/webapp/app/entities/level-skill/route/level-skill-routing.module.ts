import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { LevelSkillComponent } from '../list/level-skill.component';
import { LevelSkillDetailComponent } from '../detail/level-skill-detail.component';
import { LevelSkillUpdateComponent } from '../update/level-skill-update.component';
import { LevelSkillRoutingResolveService } from './level-skill-routing-resolve.service';
import { ASC } from 'app/config/navigation.constants';

const levelSkillRoute: Routes = [
  {
    path: '',
    component: LevelSkillComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: LevelSkillDetailComponent,
    resolve: {
      levelSkill: LevelSkillRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: LevelSkillUpdateComponent,
    resolve: {
      levelSkill: LevelSkillRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: LevelSkillUpdateComponent,
    resolve: {
      levelSkill: LevelSkillRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(levelSkillRoute)],
  exports: [RouterModule],
})
export class LevelSkillRoutingModule {}
