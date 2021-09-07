import { NgModule } from '@angular/core';
import { TeamdojoSharedLibsModule } from 'app/custom/shared/shared-libs.module';
import { AlertComponent } from 'app/shared/alert/alert.component';
import { AlertErrorComponent } from 'app/shared/alert/alert-error.component';
//import { DojoTranslateDirective } from 'app/custom/shared/language/dojoTranslate.directive';
import { TeamsSelectionService } from 'app/custom/teams-selection/teams-selection.service';
import { TeamsSelectionResolve } from 'app/custom/teams-selection/teams-selection.resolve';
import { FindLanguageFromKeyPipe } from 'app/shared/language/find-language-from-key.pipe';
import { TeamImageComponent } from 'app/custom/shared/team-image/team-image.component';

@NgModule({
  imports: [TeamdojoSharedLibsModule],
  declarations: [
    AlertErrorComponent,
    AlertComponent,
    FindLanguageFromKeyPipe,
    TeamImageComponent,
    /*SkillFilterPipe,
    DojoTranslateDirective,
    SkillSortPipe,
    ImageUrlPipe,
    TruncateStringPipe,
    AchievementItemComponent,
    NumberInputComponent,
    SkillScoreComponent,
    NotificationMenuComponent,
    NotificationItemComponent,*/
  ],
  providers: [TeamsSelectionService, TeamsSelectionResolve],
  exports: [
    TeamdojoSharedLibsModule,
    FindLanguageFromKeyPipe,
    AlertComponent,
    AlertErrorComponent,
    TeamImageComponent,
    /*SkillFilterPipe,
    DojoTranslateDirective,
    SkillSortPipe,
    ImageUrlPipe,
    TruncateStringPipe,
    AchievementItemComponent,
    NumberInputComponent,
    SkillScoreComponent,
    NotificationMenuComponent,
    NotificationItemComponent,*/
  ],
})
export class TeamdojoSharedCommonModule {}
