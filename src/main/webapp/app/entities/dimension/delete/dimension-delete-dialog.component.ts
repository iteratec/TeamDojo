import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import SharedModule from 'app/shared/shared.module';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';
import { IDimension } from '../dimension.model';
import { DimensionService } from '../service/dimension.service';

@Component({
  standalone: true,
  templateUrl: './dimension-delete-dialog.component.html',
  imports: [SharedModule, FormsModule],
})
export class DimensionDeleteDialogComponent {
  dimension?: IDimension;

  constructor(
    protected dimensionService: DimensionService,
    protected activeModal: NgbActiveModal,
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.dimensionService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
