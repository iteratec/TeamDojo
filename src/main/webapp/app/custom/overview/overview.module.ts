import { CUSTOM_ELEMENTS_SCHEMA, NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';
import { SharedModule } from 'app/shared/shared.module';
import { OVERVIEW_ROUTE } from 'app/custom/overview/overview.route';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { MarkdownModule } from 'ngx-markdown';

import { OverviewComponent } from 'app/custom/overview/overview.component';
import { OverviewTeamsComponent } from 'app/custom/overview/teams/overview-teams.component';
import { OverviewAchievementsComponent } from 'app/custom/overview/achievements/overview-achievements.component';
import { OverviewSkillsComponent } from 'app/custom/overview/skills/overview-skills.component';

import { TeamsModule } from 'app/custom/teams/teams.module';
import { BreadcrumbService } from 'app/custom/layouts/navbar/breadcrumb.service';
import {
  AllCommentsResolve,
  AllDimensionsResolve,
  AllSkillsResolve,
  AllTrainingsResolve,
  DojoModelResolve,
  SkillResolve,
  TeamGroupResolve,
} from 'app/custom/common.resolver';
import { ServerInfoService } from 'app/custom/server-info/server-info.service';
import { CustomSharedModule } from 'app/custom/shared/custom-shared.module';
import { TeamsSelectionResolve } from 'app/custom/teams-selection/teams-selection.resolve';
import { TeamdojoSharedCommonModule } from 'app/custom/shared/shared-common.module';
import { SharedLibsModule } from 'app/shared/shared-libs.module';
import { OverviewSkillDetailsComponent } from 'app/custom/overview/skills/skill-details/overview-skill-details.component';

@NgModule({
  imports: [
    SharedModule,
    RouterModule.forChild(OVERVIEW_ROUTE),
    NgbModule,
    TeamsModule,
    CustomSharedModule,
    TeamdojoSharedCommonModule,
    SharedLibsModule,
    MarkdownModule.forChild(),
  ],
  declarations: [
    OverviewComponent,
    OverviewTeamsComponent,
    OverviewAchievementsComponent,
    OverviewSkillsComponent,
    OverviewSkillDetailsComponent,
  ],
  entryComponents: [],
  providers: [
    DojoModelResolve,
    SkillResolve,
    AllSkillsResolve,
    AllCommentsResolve,
    AllTrainingsResolve,
    AllDimensionsResolve,
    BreadcrumbService,
    ServerInfoService,
    TeamGroupResolve,
    TeamsSelectionResolve,
  ],
  schemas: [CUSTOM_ELEMENTS_SCHEMA],
})
export class OverviewModule {}
