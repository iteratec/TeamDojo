import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
//import { TeamsSkillsComponent } from './teams-skills/teams-skills.component';
import { TeamsAchievementsComponent } from './teams-achievements/teams-achievements.component';
import { TeamsComponent } from './teams.component';
import { SharedModule } from 'app/shared/shared.module';

@NgModule({
  declarations: [
    //TeamsSkillsComponent,
    TeamsComponent,
    TeamsAchievementsComponent,
  ],
  imports: [CommonModule, SharedModule],
})
export class TeamsModule {}
