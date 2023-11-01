import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import SharedModule from 'app/shared/shared.module';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';
import { IActivity } from '../activity.model';
import { ActivityService } from '../service/activity.service';

@Component({
  standalone: true,
  templateUrl: './activity-delete-dialog.component.html',
  imports: [SharedModule, FormsModule],
})
export class ActivityDeleteDialogComponent {
  activity?: IActivity;

  constructor(
    protected activityService: ActivityService,
    protected activeModal: NgbActiveModal,
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.activityService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
