import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import SharedModule from 'app/shared/shared.module';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';
import { ITraining } from '../training.model';
import { TrainingService } from '../service/training.service';

@Component({
  standalone: true,
  templateUrl: './training-delete-dialog.component.html',
  imports: [SharedModule, FormsModule],
})
export class TrainingDeleteDialogComponent {
  training?: ITraining;

  constructor(
    protected trainingService: TrainingService,
    protected activeModal: NgbActiveModal,
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.trainingService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
