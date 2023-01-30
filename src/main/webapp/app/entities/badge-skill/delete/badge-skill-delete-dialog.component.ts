import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IBadgeSkill } from '../badge-skill.model';
import { BadgeSkillService } from '../service/badge-skill.service';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';

@Component({
  templateUrl: './badge-skill-delete-dialog.component.html',
})
export class BadgeSkillDeleteDialogComponent {
  badgeSkill?: IBadgeSkill;

  constructor(protected badgeSkillService: BadgeSkillService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.badgeSkillService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
