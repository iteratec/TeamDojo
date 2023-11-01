import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import SharedModule from 'app/shared/shared.module';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';
import { ILevel } from '../level.model';
import { LevelService } from '../service/level.service';

@Component({
  standalone: true,
  templateUrl: './level-delete-dialog.component.html',
  imports: [SharedModule, FormsModule],
})
export class LevelDeleteDialogComponent {
  level?: ILevel;

  constructor(
    protected levelService: LevelService,
    protected activeModal: NgbActiveModal,
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.levelService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
