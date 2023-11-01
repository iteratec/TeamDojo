import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import SharedModule from 'app/shared/shared.module';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';
import { IReport } from '../report.model';
import { ReportService } from '../service/report.service';

@Component({
  standalone: true,
  templateUrl: './report-delete-dialog.component.html',
  imports: [SharedModule, FormsModule],
})
export class ReportDeleteDialogComponent {
  report?: IReport;

  constructor(
    protected reportService: ReportService,
    protected activeModal: NgbActiveModal,
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.reportService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
