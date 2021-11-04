import { NgModule } from '@angular/core';

import { SharedModule } from 'app/shared/shared.module';

import { CustomTeamSkillRoutingModule } from 'app/custom/entities/team-skill/route/team-skill-routing.module';
import { TeamSkillVoteComponent } from 'app/custom/entities/team-skill/team-skill-vote/team-skill-vote.component';

@NgModule({
  imports: [SharedModule, CustomTeamSkillRoutingModule],
  declarations: [TeamSkillVoteComponent],
  exports: [TeamSkillVoteComponent],
})
export class CustomTeamSkillModule {}
