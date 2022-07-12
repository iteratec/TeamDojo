/*
 SPDX-FileCopyrightText: the TeamDojo authors
 SPDX-License-Identifier: Apache-2.0
 */
import { NgModule } from '@angular/core';
import { BackgroundComponent } from 'app/custom/shared/background/background.component';
import { TeamsStatusComponent } from 'app/custom/teams/teams-status/teams-status.component';
import { SharedLibsModule } from 'app/shared/shared-libs.module';
import { TeamsSelectionService } from 'app/custom/teams-selection/teams-selection.service';
import { TeamService } from 'app/entities/team/service/team.service';
import { TranslateModule } from '@ngx-translate/core';
import { TeamdojoSharedCommonModule } from 'app/custom/shared/shared-common.module';
import { TrainingsAddComponent } from 'app/custom/shared/trainings/trainings-add.component';
import { MarkdownModule } from 'ngx-markdown';

@NgModule({
  imports: [SharedLibsModule, TranslateModule, TeamdojoSharedCommonModule, MarkdownModule.forChild()],
  declarations: [BackgroundComponent, TeamsStatusComponent, TrainingsAddComponent],
  exports: [BackgroundComponent, TeamsStatusComponent, TranslateModule, TeamdojoSharedCommonModule],
  providers: [TeamsSelectionService, TeamService],
  entryComponents: [TrainingsAddComponent],
})
export class CustomSharedModule {}
