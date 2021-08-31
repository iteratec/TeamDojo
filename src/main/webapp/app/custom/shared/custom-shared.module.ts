import { NgModule } from '@angular/core';
import { BackgroundComponent } from 'app/custom/shared/background/background.component';
import { TeamsStatusComponent } from 'app/custom/teams/teams-status/teams-status.component';
import { SharedLibsModule } from 'app/shared/shared-libs.module';

@NgModule({
  imports: [SharedLibsModule],
  declarations: [BackgroundComponent, TeamsStatusComponent],
  exports: [BackgroundComponent, TeamsStatusComponent],
})
export class CustomSharedModule {}
