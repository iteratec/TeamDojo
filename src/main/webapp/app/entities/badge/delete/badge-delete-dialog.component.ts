import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import SharedModule from 'app/shared/shared.module';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';
import { IBadge } from '../badge.model';
import { BadgeService } from '../service/badge.service';

@Component({
  standalone: true,
  templateUrl: './badge-delete-dialog.component.html',
  imports: [SharedModule, FormsModule],
})
export class BadgeDeleteDialogComponent {
  badge?: IBadge;

  constructor(
    protected badgeService: BadgeService,
    protected activeModal: NgbActiveModal,
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.badgeService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
