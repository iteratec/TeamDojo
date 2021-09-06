import { CUSTOM_ELEMENTS_SCHEMA, NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { MarkdownModule } from 'ngx-markdown';

import { TeamAndTeamSkillResolve, TEAMS_ROUTES } from 'app/custom/teams/teams.route';
import { TeamsComponent } from 'app/custom/teams/teams.component';
import { TeamsAchievementsComponent } from 'app/custom/teams/teams-achievements/teams-achievements.component';
import { TeamsSkillsComponent } from 'app/custom/teams/teams-skills/teams-skills.component';
import { TeamsEditComponent } from 'app/custom/teams/teams-edit/teams-edit.component';
import { AllCommentsResolve, AllSkillsResolve, DojoModelResolve, SkillResolve } from 'app/custom/common.resolver';
import { TeamsService } from 'app/custom/teams/teams.service';
import { CustomSharedModule } from 'app/custom/shared/custom-shared.module';
import { FormsModule } from '@angular/forms';

@NgModule({
  imports: [FormsModule, CustomSharedModule, RouterModule.forChild(TEAMS_ROUTES), NgbModule, MarkdownModule.forChild()],
  declarations: [
    TeamsComponent,
    TeamsAchievementsComponent,
    TeamsSkillsComponent,
    TeamsEditComponent,
    /*TeamsSelectionComponent,
    SkillDetailsComponent,
    SkillDetailsInfoComponent,
    SkillDetailsCommentsComponent,
    SkillDetailsRatingComponent,*/
  ],
  // @Fixme add this back to entrycomponents: TeamsSelectionComponent
  entryComponents: [TeamsEditComponent],
  providers: [
    DojoModelResolve,
    TeamsService,
    /*TeamsSkillsService,
    TeamsAchievementsService,*/
    TeamAndTeamSkillResolve,
    SkillResolve,
    AllSkillsResolve,
    AllCommentsResolve,
  ],
  //exports: [SkillDetailsInfoComponent, SkillDetailsCommentsComponent],
  schemas: [CUSTOM_ELEMENTS_SCHEMA],
})
export class TeamsModule {}
