import { NgModule } from '@angular/core';
import { TeamdojoSharedLibsModule } from 'app/custom/shared/shared-libs.module';

//import { DojoTranslateDirective } from 'app/custom/shared/language/dojoTranslate.directive';
import { TeamsSelectionService } from 'app/custom/teams-selection/teams-selection.service';
import { TeamsSelectionResolve } from 'app/custom/teams-selection/teams-selection.resolve';
import { TeamImageComponent } from 'app/custom/shared/team-image/team-image.component';
import { SharedModule } from 'app/shared/shared.module';
import { TruncateStringPipe } from 'app/custom/shared/pipe/truncate-string.pipe';
import { ImageUrlPipe } from 'app/custom/shared/pipe/image-url.pipe';
import { AchievementItemComponent } from 'app/custom/shared/achievement-item/achievement-item.component';
import { NumberInputComponent } from 'app/custom/shared/number-input/number-input.component';

@NgModule({
  imports: [TeamdojoSharedLibsModule, SharedModule],
  declarations: [
    TeamImageComponent,
    TruncateStringPipe,
    ImageUrlPipe,
    AchievementItemComponent,
    NumberInputComponent,
    /*SkillFilterPipe,
    DojoTranslateDirective,
    SkillSortPipe,
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
    /*SkillFilterPipe,
    DojoTranslateDirective,
    SkillSortPipe,
    SkillScoreComponent,
    NotificationMenuComponent,
    NotificationItemComponent,*/
  ],
})
export class TeamdojoSharedCommonModule {}
