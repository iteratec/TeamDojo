import { NgModule } from '@angular/core';
import { BackgroundComponent } from 'app/custom/shared/background/background.component';
import { TeamsStatusComponent } from 'app/custom/teams/teams-status/teams-status.component';
import { SharedLibsModule } from 'app/shared/shared-libs.module';
import { TeamsSelectionService } from 'app/custom/teams-selection/teams-selection.service';
import { TeamsService } from 'app/custom/teams/teams.service';

@NgModule({
  imports: [SharedLibsModule],
  declarations: [BackgroundComponent, TeamsStatusComponent],
  exports: [BackgroundComponent, TeamsStatusComponent],
  providers: [TeamsSelectionService, TeamsService],
})
export class CustomSharedModule {}
