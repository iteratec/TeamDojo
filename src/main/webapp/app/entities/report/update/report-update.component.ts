import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import SharedModule from 'app/shared/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { ReportType } from 'app/entities/enumerations/report-type.model';
import { IReport } from '../report.model';
import { ReportService } from '../service/report.service';
import { ReportFormService, ReportFormGroup } from './report-form.service';

@Component({
  standalone: true,
  selector: 'jhi-report-update',
  templateUrl: './report-update.component.html',
  imports: [SharedModule, FormsModule, ReactiveFormsModule],
})
export class ReportUpdateComponent implements OnInit {
  isSaving = false;
  report: IReport | null = null;
  reportTypeValues = Object.keys(ReportType);

  editForm: ReportFormGroup = this.reportFormService.createReportFormGroup();

  constructor(
    protected reportService: ReportService,
    protected reportFormService: ReportFormService,
    protected activatedRoute: ActivatedRoute,
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ report }) => {
      this.report = report;
      if (report) {
        this.updateForm(report);
      }
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const report = this.reportFormService.getReport(this.editForm);
    if (report.id !== null) {
      this.subscribeToSaveResponse(this.reportService.update(report));
    } else {
      this.subscribeToSaveResponse(this.reportService.create(report));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IReport>>): void {
    result.pipe(finalize(() => this.onSaveFinalize())).subscribe({
      next: () => this.onSaveSuccess(),
      error: () => this.onSaveError(),
    });
  }

  protected onSaveSuccess(): void {
    this.previousState();
  }

  protected onSaveError(): void {
    // Api for inheritance.
  }

  protected onSaveFinalize(): void {
    this.isSaving = false;
  }

  protected updateForm(report: IReport): void {
    this.report = report;
    this.reportFormService.resetForm(this.editForm, report);
  }
}
