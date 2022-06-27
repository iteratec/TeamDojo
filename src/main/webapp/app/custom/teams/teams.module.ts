/*
 SPDX-FileCopyrightText: the TeamDojo authors
 SPDX-License-Identifier: Apache-2.0
 */
import { CUSTOM_ELEMENTS_SCHEMA, NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { MarkdownModule } from 'ngx-markdown';

import { TeamAndTeamSkillResolve, TEAMS_ROUTES } from 'app/custom/teams/teams.route';
import { TeamsComponent } from 'app/custom/teams/teams.component';
import { TeamsAchievementsComponent } from 'app/custom/teams/teams-achievements/teams-achievements.component';
import { TeamsEditComponent } from 'app/custom/teams/teams-edit/teams-edit.component';
import { AllCommentsResolve, AllSkillsResolve, DojoModelResolve, SkillResolve } from 'app/custom/common.resolver';
import { TeamService } from 'app/entities/team/service/team.service';
import { CustomSharedModule } from 'app/custom/shared/custom-shared.module';
import { FormsModule } from '@angular/forms';
import { SharedLibsModule } from 'app/shared/shared-libs.module';
import { SkillDetailsRatingComponent } from 'app/custom/teams/skill-details/skill-details-rating/skill-details-rating.component';
import { TeamsSkillsComponent } from 'app/custom/teams/skills/teams-skills.component';
import { SkillDetailsInfoComponent } from 'app/custom/shared/skill-details/info/skill-details-info.component';
import { SkillDetailsCommentsComponent } from 'app/custom/shared/skill-details/comments/skill-details-comments.component';
import { TeamsSkillsService } from 'app/custom/teams/teams-skills.service';
import { TeamsAchievementsService } from 'app/custom/teams/services/teams-achievements.service';
import { SkillDetailsComponent } from 'app/custom/teams/skill-details/skill-details.component';
import { TeamsSelectionComponent } from 'app/custom/teams-selection/teams-selection.component';
import { SkillDetailsBaseComponent } from 'app/custom/shared/skill-details/skill-details-base.component';

@NgModule({
  imports: [FormsModule, CustomSharedModule, RouterModule.forChild(TEAMS_ROUTES), NgbModule, MarkdownModule.forChild(), SharedLibsModule],
  declarations: [
    TeamsComponent,
    TeamsAchievementsComponent,
    TeamsEditComponent,
    SkillDetailsRatingComponent,
    TeamsSkillsComponent,
    SkillDetailsInfoComponent,
    SkillDetailsCommentsComponent,
    SkillDetailsBaseComponent,
    SkillDetailsComponent,
    TeamsSelectionComponent,
  ],
  entryComponents: [TeamsEditComponent, TeamsSelectionComponent],
  providers: [
    DojoModelResolve,
    TeamService,
    TeamsSkillsService,
    TeamsAchievementsService,
    TeamAndTeamSkillResolve,
    SkillResolve,
    AllSkillsResolve,
    AllCommentsResolve,
  ],
  exports: [SkillDetailsInfoComponent, SkillDetailsCommentsComponent],
  schemas: [CUSTOM_ELEMENTS_SCHEMA],
})
export class TeamsModule {}
