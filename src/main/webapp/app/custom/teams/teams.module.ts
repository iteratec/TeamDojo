import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
//import { TeamsSkillsComponent } from './teams-skills/teams-skills.component';
import { TeamsAchievementsComponent } from './teams-achievements/teams-achievements.component';
//import { TeamsStatusComponent } from './teams-status/teams-status.component';
import { TeamsComponent } from './teams.component';
import { SharedModule } from 'app/shared/shared.module';

@NgModule({
  declarations: [
    //TeamsSkillsComponent,
    TeamsComponent,
    TeamsAchievementsComponent,
    //TeamsStatusComponent
  ],
  imports: [CommonModule, SharedModule],
})
export class TeamsModule {}
