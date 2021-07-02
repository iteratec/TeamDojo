import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IDimension } from '../dimension.model';
import { DimensionService } from '../service/dimension.service';

@Component({
  templateUrl: './dimension-delete-dialog.component.html',
})
export class DimensionDeleteDialogComponent {
  dimension?: IDimension;

  constructor(protected dimensionService: DimensionService, public activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.dimensionService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
