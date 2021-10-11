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
import { AchievableSkillSortPipe } from 'app/custom/shared/pipe/achievable-skill-sort.pipe';

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
    AchievableSkillSortPipe,
    /*
    SkillScoreComponent,
    NotificationMenuComponent,
    NotificationItemComponent,*/
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
    AchievableSkillSortPipe,
    SharedModule,
    /*
    SkillScoreComponent,
    NotificationMenuComponent,
    NotificationItemComponent,*/
  ],
})
export class TeamdojoSharedCommonModule {}
