3,15c3
< import { FindLanguageFromKeyPipe, JhiAlertComponent, JhiAlertErrorComponent, TeamdojoSharedLibsModule } from './';
< import { ImageUrlPipe } from './pipe/image-url.pipe';
< import { TruncateStringPipe } from './pipe/truncate-string.pipe';
< import { AchievementItemComponent } from 'app/shared/achievement';
< import { TeamImageComponent } from 'app/shared/team-image/team-image.component';
< import { NotificationItemComponent, NotificationMenuComponent } from 'app/shared/notification';
< import { SkillFilterPipe } from 'app/shared/pipe/skill-filter.pipe';
< import { SkillSortPipe } from 'app/shared/pipe/skill-sort.pipe';
< import { TeamsSelectionResolve } from 'app/shared/teams-selection/teams-selection.resolve';
< import { TeamsSelectionService } from 'app/shared/teams-selection/teams-selection.service';
< import { DojoTranslateDirective } from 'app/shared/language/dojoTranslate.directive';
< import { SkillScoreComponent } from 'app/shared/skill-score/skill-score.component';
< import { NumberInputComponent } from 'app/shared/number-input/number-input.component';
---
> import { TeamdojoSharedLibsModule, FindLanguageFromKeyPipe, JhiAlertComponent, JhiAlertErrorComponent } from './';
19,52c7,8
<     declarations: [
<         FindLanguageFromKeyPipe,
<         JhiAlertComponent,
<         JhiAlertErrorComponent,
<         SkillFilterPipe,
<         SkillSortPipe,
<         DojoTranslateDirective,
<         ImageUrlPipe,
<         TruncateStringPipe,
<         AchievementItemComponent,
<         NumberInputComponent,
<         TeamImageComponent,
<         SkillScoreComponent,
<         NotificationMenuComponent,
<         NotificationItemComponent
<     ],
<     providers: [TeamsSelectionService, TeamsSelectionResolve],
<     exports: [
<         TeamdojoSharedLibsModule,
<         FindLanguageFromKeyPipe,
<         JhiAlertComponent,
<         JhiAlertErrorComponent,
<         SkillFilterPipe,
<         DojoTranslateDirective,
<         SkillSortPipe,
<         ImageUrlPipe,
<         TruncateStringPipe,
<         AchievementItemComponent,
<         NumberInputComponent,
<         TeamImageComponent,
<         SkillScoreComponent,
<         NotificationMenuComponent,
<         NotificationItemComponent
<     ]
---
>     declarations: [FindLanguageFromKeyPipe, JhiAlertComponent, JhiAlertErrorComponent],
>     exports: [TeamdojoSharedLibsModule, FindLanguageFromKeyPipe, JhiAlertComponent, JhiAlertErrorComponent]
