import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IReport } from '../report.model';
import { ReportService } from '../service/report.service';

@Component({
  templateUrl: './report-delete-dialog.component.html',
})
export class ReportDeleteDialogComponent {
  report?: IReport;

  constructor(protected reportService: ReportService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.reportService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
