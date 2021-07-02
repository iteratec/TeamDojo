import { NgModule } from '@angular/core';

import { SharedModule } from 'app/shared/shared.module';
import { LevelSkillComponent } from './list/level-skill.component';
import { LevelSkillDetailComponent } from './detail/level-skill-detail.component';
import { LevelSkillUpdateComponent } from './update/level-skill-update.component';
import { LevelSkillDeleteDialogComponent } from './delete/level-skill-delete-dialog.component';
import { LevelSkillRoutingModule } from './route/level-skill-routing.module';

@NgModule({
  imports: [SharedModule, LevelSkillRoutingModule],
  declarations: [LevelSkillComponent, LevelSkillDetailComponent, LevelSkillUpdateComponent, LevelSkillDeleteDialogComponent],
  entryComponents: [LevelSkillDeleteDialogComponent],
})
export class LevelSkillModule {}
