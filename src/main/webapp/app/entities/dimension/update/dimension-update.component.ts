import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import SharedModule from 'app/shared/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { IDimension } from '../dimension.model';
import { DimensionService } from '../service/dimension.service';
import { DimensionFormService, DimensionFormGroup } from './dimension-form.service';

@Component({
  standalone: true,
  selector: 'jhi-dimension-update',
  templateUrl: './dimension-update.component.html',
  imports: [SharedModule, FormsModule, ReactiveFormsModule],
})
export class DimensionUpdateComponent implements OnInit {
  isSaving = false;
  dimension: IDimension | null = null;

  editForm: DimensionFormGroup = this.dimensionFormService.createDimensionFormGroup();

  constructor(
    protected dimensionService: DimensionService,
    protected dimensionFormService: DimensionFormService,
    protected activatedRoute: ActivatedRoute,
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ dimension }) => {
      this.dimension = dimension;
      if (dimension) {
        this.updateForm(dimension);
      }
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const dimension = this.dimensionFormService.getDimension(this.editForm);
    if (dimension.id !== null) {
      this.subscribeToSaveResponse(this.dimensionService.update(dimension));
    } else {
      this.subscribeToSaveResponse(this.dimensionService.create(dimension));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IDimension>>): void {
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

  protected updateForm(dimension: IDimension): void {
    this.dimension = dimension;
    this.dimensionFormService.resetForm(this.editForm, dimension);
  }
}
