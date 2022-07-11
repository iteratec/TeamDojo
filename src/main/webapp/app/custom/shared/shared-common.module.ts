/*
 SPDX-FileCopyrightText: the TeamDojo authors
 SPDX-License-Identifier: Apache-2.0
 */
import { NgModule } from '@angular/core';
import { TeamdojoSharedLibsModule } from 'app/custom/shared/shared-libs.module';

import { TeamsSelectionService } from 'app/custom/teams-selection/teams-selection.service';
import { TeamsSelectionResolve } from 'app/custom/teams-selection/teams-selection.resolve';
import { TeamImageComponent } from 'app/custom/shared/team-image/team-image.component';
import { SharedModule } from 'app/shared/shared.module';
import { TruncateStringPipe } from 'app/custom/shared/pipe/truncate-string.pipe';
import { ImageUrlPipe } from 'app/custom/shared/pipe/image-url.pipe';
import { AchievementItemComponent } from 'app/custom/shared/achievement-item/achievement-item.component';
import { NumberInputComponent } from 'app/custom/shared/number-input/number-input.component';
import { SkillSortPipe } from 'app/custom/shared/pipe/skill-sort.pipe';
import { SkillFilterPipe } from 'app/custom/shared/pipe/skill-filter.pipe';
import { AchievableSkillFilterPipe } from 'app/custom/shared/pipe/achievable-skill-filter.pipe';
import { SkillScoreComponent } from 'app/custom/shared/skill-score/skill-score.component';
import { NotificationItemComponent } from 'app/custom/shared/notification/item/notification-item.component';
import { NotificationMenuComponent } from 'app/custom/shared/notification/menu/notification-menu.component';
import { TableFilterComponent } from 'app/custom/shared/table-filter/table-filter.component';
import { TeamGroupFilterPipe } from './pipe/team-group-filter.pipe';
import { TranslateModelPipe } from './translate-model/translate-model.pipe';
import { MarkdownRenderPipe } from './markdown-render/custom-markdown.pipe';

@NgModule({
  imports: [TeamdojoSharedLibsModule, SharedModule],
  declarations: [
    TeamImageComponent,
    TruncateStringPipe,
    ImageUrlPipe,
    AchievementItemComponent,
    NumberInputComponent,
    SkillSortPipe,
    SkillFilterPipe,
    AchievableSkillFilterPipe,
    SkillScoreComponent,
    NotificationItemComponent,
    NotificationMenuComponent,
    TableFilterComponent,
    TeamGroupFilterPipe,
    TranslateModelPipe,
    MarkdownRenderPipe,
  ],
  providers: [TeamsSelectionService, TeamsSelectionResolve],
  exports: [
    TeamdojoSharedLibsModule,
    TeamImageComponent,
    TruncateStringPipe,
    ImageUrlPipe,
    AchievementItemComponent,
    NumberInputComponent,
    SkillSortPipe,
    SkillFilterPipe,
    AchievableSkillFilterPipe,
    SharedModule,
    SkillScoreComponent,
    NotificationMenuComponent,
    NotificationItemComponent,
    TableFilterComponent,
    TeamGroupFilterPipe,
    TranslateModelPipe,
    MarkdownRenderPipe,
  ],
})
export class TeamdojoSharedCommonModule {}
