import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import SharedModule from 'app/shared/shared.module';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';
import { ILevelSkill } from '../level-skill.model';
import { LevelSkillService } from '../service/level-skill.service';

@Component({
  standalone: true,
  templateUrl: './level-skill-delete-dialog.component.html',
  imports: [SharedModule, FormsModule],
})
export class LevelSkillDeleteDialogComponent {
  levelSkill?: ILevelSkill;

  constructor(
    protected levelSkillService: LevelSkillService,
    protected activeModal: NgbActiveModal,
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.levelSkillService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
