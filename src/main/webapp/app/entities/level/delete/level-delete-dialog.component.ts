import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { ILevel } from '../level.model';
import { LevelService } from '../service/level.service';

@Component({
  templateUrl: './level-delete-dialog.component.html',
})
export class LevelDeleteDialogComponent {
  level?: ILevel;

  constructor(protected levelService: LevelService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.levelService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
