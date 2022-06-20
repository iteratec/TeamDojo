import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import dayjs from 'dayjs/esm';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';

import { IBadge, Badge } from '../badge.model';
import { BadgeService } from '../service/badge.service';
import { IImage } from 'app/entities/image/image.model';
import { ImageService } from 'app/entities/image/service/image.service';
import { IDimension } from 'app/entities/dimension/dimension.model';
import { DimensionService } from 'app/entities/dimension/service/dimension.service';
// ### Modification-Start ###
import { DIMENSIONS_PER_PAGE, IMAGES_PER_PAGE } from '../../../config/pagination.constants';
// ### Modification-End###

@Component({
  selector: 'jhi-badge-update',
  templateUrl: './badge-update.component.html',
})
export class BadgeUpdateComponent implements OnInit {
  isSaving = false;

  imagesSharedCollection: IImage[] = [];
  dimensionsSharedCollection: IDimension[] = [];

  editForm = this.fb.group({
    id: [],
    titleEN: [null, [Validators.required, Validators.minLength(2), Validators.maxLength(50)]],
    titleDE: [null, [Validators.minLength(2), Validators.maxLength(50)]],
    descriptionEN: [null, [Validators.maxLength(4096)]],
    descriptionDE: [null, [Validators.maxLength(4096)]],
    availableUntil: [],
    availableAmount: [null, [Validators.min(1)]],
    requiredScore: [null, [Validators.required, Validators.min(0), Validators.max(1)]],
    instantMultiplier: [null, [Validators.required, Validators.min(0)]],
    completionBonus: [null, [Validators.min(0)]],
    createdAt: [null, [Validators.required]],
    updatedAt: [null, [Validators.required]],
    image: [],
    dimensions: [],
  });

  constructor(
    protected badgeService: BadgeService,
    protected imageService: ImageService,
    protected dimensionService: DimensionService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ badge }) => {
      if (badge.id === undefined) {
        const today = dayjs().startOf('day');
        badge.availableUntil = today;
        badge.createdAt = today;
        badge.updatedAt = today;
      }

      this.updateForm(badge);

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const badge = this.createFromForm();
    if (badge.id !== undefined) {
      this.subscribeToSaveResponse(this.badgeService.update(badge));
    } else {
      this.subscribeToSaveResponse(this.badgeService.create(badge));
    }
  }

  trackImageById(index: number, item: IImage): number {
    return item.id!;
  }

  trackDimensionById(index: number, item: IDimension): number {
    return item.id!;
  }

  getSelectedDimension(option: IDimension, selectedVals?: IDimension[]): IDimension {
    if (selectedVals) {
      for (const selectedVal of selectedVals) {
        if (option.id === selectedVal.id) {
          return selectedVal;
        }
      }
    }
    return option;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IBadge>>): void {
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

  protected updateForm(badge: IBadge): void {
    this.editForm.patchValue({
      id: badge.id,
      titleEN: badge.titleEN,
      titleDE: badge.titleDE,
      descriptionEN: badge.descriptionEN,
      descriptionDE: badge.descriptionDE,
      availableUntil: badge.availableUntil ? badge.availableUntil.format(DATE_TIME_FORMAT) : null,
      availableAmount: badge.availableAmount,
      requiredScore: badge.requiredScore,
      instantMultiplier: badge.instantMultiplier,
      completionBonus: badge.completionBonus,
      createdAt: badge.createdAt ? badge.createdAt.format(DATE_TIME_FORMAT) : null,
      updatedAt: badge.updatedAt ? badge.updatedAt.format(DATE_TIME_FORMAT) : null,
      image: badge.image,
      dimensions: badge.dimensions,
    });

    this.imagesSharedCollection = this.imageService.addImageToCollectionIfMissing(this.imagesSharedCollection, badge.image);
    this.dimensionsSharedCollection = this.dimensionService.addDimensionToCollectionIfMissing(
      this.dimensionsSharedCollection,
      ...(badge.dimensions ?? [])
    );
  }

  protected loadRelationshipsOptions(): void {
    this.imageService
      // ### Modification-Start ###
      .query({ page: 0, size: IMAGES_PER_PAGE })
      // ### Modification-End  ###
      .pipe(map((res: HttpResponse<IImage[]>) => res.body ?? []))
      .pipe(map((images: IImage[]) => this.imageService.addImageToCollectionIfMissing(images, this.editForm.get('image')!.value)))
      .subscribe((images: IImage[]) => (this.imagesSharedCollection = images));

    this.dimensionService
      // ### Modification-Start ###
      .query({ page: 0, size: DIMENSIONS_PER_PAGE })
      // ### Modification-End ###
      .pipe(map((res: HttpResponse<IDimension[]>) => res.body ?? []))
      .pipe(
        map((dimensions: IDimension[]) =>
          this.dimensionService.addDimensionToCollectionIfMissing(dimensions, ...(this.editForm.get('dimensions')!.value ?? []))
        )
      )
      .subscribe((dimensions: IDimension[]) => (this.dimensionsSharedCollection = dimensions));
  }

  protected createFromForm(): IBadge {
    return {
      ...new Badge(),
      id: this.editForm.get(['id'])!.value,
      titleEN: this.editForm.get(['titleEN'])!.value,
      titleDE: this.editForm.get(['titleDE'])!.value,
      descriptionEN: this.editForm.get(['descriptionEN'])!.value,
      descriptionDE: this.editForm.get(['descriptionDE'])!.value,
      availableUntil: this.editForm.get(['availableUntil'])!.value
        ? dayjs(this.editForm.get(['availableUntil'])!.value, DATE_TIME_FORMAT)
        : undefined,
      availableAmount: this.editForm.get(['availableAmount'])!.value,
      requiredScore: this.editForm.get(['requiredScore'])!.value,
      instantMultiplier: this.editForm.get(['instantMultiplier'])!.value,
      completionBonus: this.editForm.get(['completionBonus'])!.value,
      createdAt: this.editForm.get(['createdAt'])!.value ? dayjs(this.editForm.get(['createdAt'])!.value, DATE_TIME_FORMAT) : undefined,
      updatedAt: this.editForm.get(['updatedAt'])!.value ? dayjs(this.editForm.get(['updatedAt'])!.value, DATE_TIME_FORMAT) : undefined,
      image: this.editForm.get(['image'])!.value,
      dimensions: this.editForm.get(['dimensions'])!.value,
    };
  }
}
