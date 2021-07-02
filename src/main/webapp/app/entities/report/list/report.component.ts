import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IReport } from '../report.model';
import { ReportService } from '../service/report.service';
import { ReportDeleteDialogComponent } from '../delete/report-delete-dialog.component';

@Component({
  selector: 'jhi-report',
  templateUrl: './report.component.html',
})
export class ReportComponent implements OnInit {
  reports?: IReport[];
  isLoading = false;

  constructor(protected reportService: ReportService, protected modalService: NgbModal) {}

  loadAll(): void {
    this.isLoading = true;

    this.reportService.query().subscribe(
      (res: HttpResponse<IReport[]>) => {
        this.isLoading = false;
        this.reports = res.body ?? [];
      },
      () => {
        this.isLoading = false;
      }
    );
  }

  ngOnInit(): void {
    this.loadAll();
  }

  trackId(index: number, item: IReport): number {
    return item.id!;
  }

  delete(report: IReport): void {
    const modalRef = this.modalService.open(ReportDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.report = report;
    // unsubscribe not needed because closed completes on modal close
    modalRef.closed.subscribe(reason => {
      if (reason === 'deleted') {
        this.loadAll();
      }
    });
  }
}
