import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { BadgeSkillComponent } from './list/badge-skill.component';
import { BadgeSkillDetailComponent } from './detail/badge-skill-detail.component';
import { BadgeSkillUpdateComponent } from './update/badge-skill-update.component';
import { BadgeSkillDeleteDialogComponent } from './delete/badge-skill-delete-dialog.component';
import { BadgeSkillRoutingModule } from './route/badge-skill-routing.module';

@NgModule({
  imports: [SharedModule, BadgeSkillRoutingModule],
  declarations: [BadgeSkillComponent, BadgeSkillDetailComponent, BadgeSkillUpdateComponent, BadgeSkillDeleteDialogComponent],
  entryComponents: [BadgeSkillDeleteDialogComponent],
})
export class BadgeSkillModule {}
