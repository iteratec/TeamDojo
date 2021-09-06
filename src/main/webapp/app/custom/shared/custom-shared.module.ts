import { NgModule } from '@angular/core';
import { BackgroundComponent } from 'app/custom/shared/background/background.component';
import { TeamsStatusComponent } from 'app/custom/teams/teams-status/teams-status.component';
import { SharedLibsModule } from 'app/shared/shared-libs.module';
import { TeamsSelectionService } from 'app/custom/teams-selection/teams-selection.service';
import { TeamsService } from 'app/custom/teams/teams.service';
import { TranslateModule } from '@ngx-translate/core';

@NgModule({
  imports: [SharedLibsModule, TranslateModule],
  declarations: [BackgroundComponent, TeamsStatusComponent],
  exports: [BackgroundComponent, TeamsStatusComponent, TranslateModule],
  providers: [TeamsSelectionService, TeamsService],
})
export class CustomSharedModule {}
