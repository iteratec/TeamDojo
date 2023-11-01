import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import SharedModule from 'app/shared/shared.module';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';
import { IBadgeSkill } from '../badge-skill.model';
import { BadgeSkillService } from '../service/badge-skill.service';

@Component({
  standalone: true,
  templateUrl: './badge-skill-delete-dialog.component.html',
  imports: [SharedModule, FormsModule],
})
export class BadgeSkillDeleteDialogComponent {
  badgeSkill?: IBadgeSkill;

  constructor(
    protected badgeSkillService: BadgeSkillService,
    protected activeModal: NgbActiveModal,
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.badgeSkillService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
