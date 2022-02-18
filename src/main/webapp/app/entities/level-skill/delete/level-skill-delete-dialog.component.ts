import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { ILevelSkill } from '../level-skill.model';
import { LevelSkillService } from '../service/level-skill.service';

@Component({
  templateUrl: './level-skill-delete-dialog.component.html',
})
export class LevelSkillDeleteDialogComponent {
  levelSkill?: ILevelSkill;

  constructor(protected levelSkillService: LevelSkillService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.levelSkillService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
