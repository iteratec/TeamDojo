import { CUSTOM_ELEMENTS_SCHEMA, NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { MarkdownModule } from 'ngx-markdown';

import { TeamAndTeamSkillResolve, TEAMS_ROUTES } from 'app/custom/teams/teams.route';
import { TeamsComponent } from 'app/custom/teams/teams.component';
import { TeamsAchievementsComponent } from 'app/custom/teams/teams-achievements/teams-achievements.component';
import { TeamsEditComponent } from 'app/custom/teams/teams-edit/teams-edit.component';
import { AllCommentsResolve, AllSkillsResolve, DojoModelResolve, SkillResolve } from 'app/custom/common.resolver';
import { TeamsService } from 'app/custom/teams/teams.service';
import { CustomSharedModule } from 'app/custom/shared/custom-shared.module';
import { FormsModule } from '@angular/forms';
import { SharedLibsModule } from 'app/shared/shared-libs.module';
import { SkillDetailsRatingComponent } from 'app/custom/teams/skill-details/skill-details-rating/skill-details-rating.component';
import { TeamsSkillsComponent } from 'app/custom/teams/skills/teams-skills.component';
import { SkillDetailsInfoComponent } from 'app/custom/shared/skill-details/info/skill-details-info.component';
import { SkillDetailsCommentsComponent } from 'app/custom/shared/skill-details/comments/skill-details-comments.component';
import { TeamsSkillsService } from 'app/custom/teams/teams-skills.service';

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
    /*
    TeamsSelectionComponent,
    SkillDetailsComponent,
    */
  ],
  // @Fixme add this back to entrycomponents: TeamsSelectionComponent
  entryComponents: [TeamsEditComponent],
  providers: [
    DojoModelResolve,
    TeamsService,
    TeamsSkillsService,
    /*
    TeamsAchievementsService,*/
    TeamAndTeamSkillResolve,
    SkillResolve,
    AllSkillsResolve,
    AllCommentsResolve,
  ],
  exports: [SkillDetailsInfoComponent, SkillDetailsCommentsComponent],
  schemas: [CUSTOM_ELEMENTS_SCHEMA],
})
export class TeamsModule {}
