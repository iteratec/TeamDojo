import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import SharedModule from 'app/shared/shared.module';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';
import { ISkill } from '../skill.model';
import { SkillService } from '../service/skill.service';

@Component({
  standalone: true,
  templateUrl: './skill-delete-dialog.component.html',
  imports: [SharedModule, FormsModule],
})
export class SkillDeleteDialogComponent {
  skill?: ISkill;

  constructor(
    protected skillService: SkillService,
    protected activeModal: NgbActiveModal,
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.skillService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
