import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { TeamGroupComponent } from './list/team-group.component';
import { TeamGroupDetailComponent } from './detail/team-group-detail.component';
import { TeamGroupUpdateComponent } from './update/team-group-update.component';
import { TeamGroupDeleteDialogComponent } from './delete/team-group-delete-dialog.component';
import { TeamGroupRoutingModule } from './route/team-group-routing.module';

@NgModule({
  imports: [SharedModule, TeamGroupRoutingModule],
  declarations: [TeamGroupComponent, TeamGroupDetailComponent, TeamGroupUpdateComponent, TeamGroupDeleteDialogComponent],
})
export class TeamGroupModule {}
