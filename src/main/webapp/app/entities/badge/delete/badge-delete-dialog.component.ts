import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IBadge } from '../badge.model';
import { BadgeService } from '../service/badge.service';

@Component({
  templateUrl: './badge-delete-dialog.component.html',
})
export class BadgeDeleteDialogComponent {
  badge?: IBadge;

  constructor(protected badgeService: BadgeService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.badgeService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
