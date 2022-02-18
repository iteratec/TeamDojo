import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { ISkill } from '../skill.model';
import { SkillService } from '../service/skill.service';

@Component({
  templateUrl: './skill-delete-dialog.component.html',
})
export class SkillDeleteDialogComponent {
  skill?: ISkill;

  constructor(protected skillService: SkillService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.skillService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
