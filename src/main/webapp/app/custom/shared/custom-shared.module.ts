import { NgModule } from '@angular/core';
import { BackgroundComponent } from 'app/custom/shared/background/background.component';
import { TeamsStatusComponent } from 'app/custom/teams/teams-status/teams-status.component';
import { SharedLibsModule } from 'app/shared/shared-libs.module';
import { TeamsSelectionService } from 'app/custom/teams-selection/teams-selection.service';
import { TeamsService } from 'app/custom/teams/teams.service';
import { TranslateModule } from '@ngx-translate/core';
import { TeamdojoSharedCommonModule } from 'app/custom/shared/shared-common.module';

@NgModule({
  imports: [SharedLibsModule, TranslateModule, TeamdojoSharedCommonModule],
  declarations: [BackgroundComponent, TeamsStatusComponent],
  exports: [BackgroundComponent, TeamsStatusComponent, TranslateModule, TeamdojoSharedCommonModule],
  providers: [TeamsSelectionService, TeamsService],
})
export class CustomSharedModule {}
