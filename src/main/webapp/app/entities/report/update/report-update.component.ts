import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import dayjs from 'dayjs/esm';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';

import { IReport, Report } from '../report.model';
import { ReportService } from '../service/report.service';
import { ReportType } from 'app/entities/enumerations/report-type.model';

@Component({
  selector: 'jhi-report-update',
  templateUrl: './report-update.component.html',
})
export class ReportUpdateComponent implements OnInit {
  isSaving = false;
  reportTypeValues = Object.keys(ReportType);

  editForm = this.fb.group({
    id: [],
    title: [null, [Validators.required, Validators.minLength(1), Validators.maxLength(50)]],
    description: [null, [Validators.required, Validators.minLength(1), Validators.maxLength(4096)]],
    type: [null, [Validators.required]],
    createdAt: [null, [Validators.required]],
    updatedAt: [null, [Validators.required]],
  });

  constructor(protected reportService: ReportService, protected activatedRoute: ActivatedRoute, protected fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ report }) => {
      if (report.id === undefined) {
        const today = dayjs().startOf('day');
        report.createdAt = today;
        report.updatedAt = today;
      }

      this.updateForm(report);
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const report = this.createFromForm();
    if (report.id !== undefined) {
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
    this.editForm.patchValue({
      id: report.id,
      title: report.title,
      description: report.description,
      type: report.type,
      createdAt: report.createdAt ? report.createdAt.format(DATE_TIME_FORMAT) : null,
      updatedAt: report.updatedAt ? report.updatedAt.format(DATE_TIME_FORMAT) : null,
    });
  }

  protected createFromForm(): IReport {
    return {
      ...new Report(),
      id: this.editForm.get(['id'])!.value,
      title: this.editForm.get(['title'])!.value,
      description: this.editForm.get(['description'])!.value,
      type: this.editForm.get(['type'])!.value,
      createdAt: this.editForm.get(['createdAt'])!.value ? dayjs(this.editForm.get(['createdAt'])!.value, DATE_TIME_FORMAT) : undefined,
      updatedAt: this.editForm.get(['updatedAt'])!.value ? dayjs(this.editForm.get(['updatedAt'])!.value, DATE_TIME_FORMAT) : undefined,
    };
  }
}
