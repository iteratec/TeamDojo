import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { SkillComponent } from './list/skill.component';
import { SkillDetailComponent } from './detail/skill-detail.component';
import { SkillUpdateComponent } from './update/skill-update.component';
import { SkillDeleteDialogComponent } from './delete/skill-delete-dialog.component';
import { SkillRoutingModule } from './route/skill-routing.module';

@NgModule({
  imports: [SharedModule, SkillRoutingModule],
  declarations: [SkillComponent, SkillDetailComponent, SkillUpdateComponent, SkillDeleteDialogComponent],
  entryComponents: [SkillDeleteDialogComponent],
})
export class SkillModule {}
