import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { TeamSkillComponent } from './list/team-skill.component';
import { TeamSkillDetailComponent } from './detail/team-skill-detail.component';
import { TeamSkillUpdateComponent } from './update/team-skill-update.component';
import { TeamSkillDeleteDialogComponent } from './delete/team-skill-delete-dialog.component';
import { TeamSkillRoutingModule } from './route/team-skill-routing.module';
// ### Modification-Start ###
import { CustomTeamSkillModule } from 'app/custom/entities/team-skill/team-skill.module';
// ### MODIFICATION-END ###

@NgModule({
  imports: [
    SharedModule,
    TeamSkillRoutingModule,
    // ### MODIFICATION-START ###
    CustomTeamSkillModule,
    // ### MODIFICATION-END ###
  ],
  declarations: [TeamSkillComponent, TeamSkillDetailComponent, TeamSkillUpdateComponent, TeamSkillDeleteDialogComponent],
  entryComponents: [TeamSkillDeleteDialogComponent],
})
export class TeamSkillModule {}
