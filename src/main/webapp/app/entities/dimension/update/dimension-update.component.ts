import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import * as dayjs from 'dayjs';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';

import { IDimension, Dimension } from '../dimension.model';
import { DimensionService } from '../service/dimension.service';

@Component({
  selector: 'jhi-dimension-update',
  templateUrl: './dimension-update.component.html',
})
export class DimensionUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    title: [null, [Validators.required, Validators.minLength(1), Validators.maxLength(50)]],
    description: [null, [Validators.maxLength(4096)]],
    createdAt: [null, [Validators.required]],
    updatedAt: [null, [Validators.required]],
  });

  constructor(protected dimensionService: DimensionService, protected activatedRoute: ActivatedRoute, protected fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ dimension }) => {
      if (dimension.id === undefined) {
        const today = dayjs().startOf('day');
        dimension.createdAt = today;
        dimension.updatedAt = today;
      }

      this.updateForm(dimension);
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const dimension = this.createFromForm();
    if (dimension.id !== undefined) {
      this.subscribeToSaveResponse(this.dimensionService.update(dimension));
    } else {
      this.subscribeToSaveResponse(this.dimensionService.create(dimension));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IDimension>>): void {
    result.pipe(finalize(() => this.onSaveFinalize())).subscribe(
      () => this.onSaveSuccess(),
      () => this.onSaveError()
    );
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

  protected updateForm(dimension: IDimension): void {
    this.editForm.patchValue({
      id: dimension.id,
      title: dimension.title,
      description: dimension.description,
      createdAt: dimension.createdAt ? dimension.createdAt.format(DATE_TIME_FORMAT) : null,
      updatedAt: dimension.updatedAt ? dimension.updatedAt.format(DATE_TIME_FORMAT) : null,
    });
  }

  protected createFromForm(): IDimension {
    return {
      ...new Dimension(),
      id: this.editForm.get(['id'])!.value,
      title: this.editForm.get(['title'])!.value,
      description: this.editForm.get(['description'])!.value,
      createdAt: this.editForm.get(['createdAt'])!.value ? dayjs(this.editForm.get(['createdAt'])!.value, DATE_TIME_FORMAT) : undefined,
      updatedAt: this.editForm.get(['updatedAt'])!.value ? dayjs(this.editForm.get(['updatedAt'])!.value, DATE_TIME_FORMAT) : undefined,
    };
  }
}
