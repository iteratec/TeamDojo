import { NgModule } from '@angular/core';

import { SharedModule } from 'app/shared/shared.module';
import { TeamSkillComponent } from './list/team-skill.component';
import { TeamSkillDetailComponent } from './detail/team-skill-detail.component';
import { TeamSkillUpdateComponent } from './update/team-skill-update.component';
import { TeamSkillDeleteDialogComponent } from './delete/team-skill-delete-dialog.component';
import { TeamSkillRoutingModule } from './route/team-skill-routing.module';

@NgModule({
  imports: [SharedModule, TeamSkillRoutingModule],
  declarations: [TeamSkillComponent, TeamSkillDetailComponent, TeamSkillUpdateComponent, TeamSkillDeleteDialogComponent],
  entryComponents: [TeamSkillDeleteDialogComponent],
})
export class TeamSkillModule {}
