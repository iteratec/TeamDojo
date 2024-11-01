import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import dayjs from 'dayjs/esm';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';

import { ILevel, Level } from '../level.model';
import { LevelService } from '../service/level.service';
import { IImage } from 'app/entities/image/image.model';
import { ImageService } from 'app/entities/image/service/image.service';
import { IDimension } from 'app/entities/dimension/dimension.model';
import { DimensionService } from 'app/entities/dimension/service/dimension.service';
// ### Modification-Start ###
import { DIMENSIONS_PER_PAGE, IMAGES_PER_PAGE } from '../../../config/pagination.constants';
// ### Modification-End ###

@Component({
  selector: 'jhi-level-update',
  templateUrl: './level-update.component.html',
})
export class LevelUpdateComponent implements OnInit {
  isSaving = false;

  dependsOnsCollection: ILevel[] = [];
  imagesSharedCollection: IImage[] = [];
  dimensionsSharedCollection: IDimension[] = [];

  editForm = this.fb.group({
    id: [],
    titleEN: [null, [Validators.required, Validators.minLength(3), Validators.maxLength(50)]],
    titleDE: [null, [Validators.minLength(3), Validators.maxLength(50)]],
    descriptionEN: [null, [Validators.maxLength(4096)]],
    descriptionDE: [null, [Validators.maxLength(4096)]],
    requiredScore: [null, [Validators.required, Validators.min(0), Validators.max(1)]],
    instantMultiplier: [null, [Validators.required, Validators.min(0)]],
    completionBonus: [null, [Validators.min(0)]],
    createdAt: [null, [Validators.required]],
    updatedAt: [null, [Validators.required]],
    dependsOn: [],
    image: [],
    dimension: [null, Validators.required],
  });

  constructor(
    protected levelService: LevelService,
    protected imageService: ImageService,
    protected dimensionService: DimensionService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ level }) => {
      if (level.id === undefined) {
        const today = dayjs().startOf('day');
        level.createdAt = today;
        level.updatedAt = today;
      }

      this.updateForm(level);

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const level = this.createFromForm();
    if (level.id !== undefined) {
      this.subscribeToSaveResponse(this.levelService.update(level));
    } else {
      this.subscribeToSaveResponse(this.levelService.create(level));
    }
  }

  trackLevelById(_index: number, item: ILevel): number {
    return item.id!;
  }

  trackImageById(_index: number, item: IImage): number {
    return item.id!;
  }

  trackDimensionById(_index: number, item: IDimension): number {
    return item.id!;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ILevel>>): void {
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

  protected updateForm(level: ILevel): void {
    this.editForm.patchValue({
      id: level.id,
      titleEN: level.titleEN,
      titleDE: level.titleDE,
      descriptionEN: level.descriptionEN,
      descriptionDE: level.descriptionDE,
      requiredScore: level.requiredScore,
      instantMultiplier: level.instantMultiplier,
      completionBonus: level.completionBonus,
      createdAt: level.createdAt ? level.createdAt.format(DATE_TIME_FORMAT) : null,
      updatedAt: level.updatedAt ? level.updatedAt.format(DATE_TIME_FORMAT) : null,
      dependsOn: level.dependsOn,
      image: level.image,
      dimension: level.dimension,
    });

    this.dependsOnsCollection = this.levelService.addLevelToCollectionIfMissing(this.dependsOnsCollection, level.dependsOn);
    this.imagesSharedCollection = this.imageService.addImageToCollectionIfMissing(this.imagesSharedCollection, level.image);
    this.dimensionsSharedCollection = this.dimensionService.addDimensionToCollectionIfMissing(
      this.dimensionsSharedCollection,
      level.dimension
    );
  }

  protected loadRelationshipsOptions(): void {
    this.levelService
      .query({ 'levelId.specified': 'false' })
      .pipe(map((res: HttpResponse<ILevel[]>) => res.body ?? []))
      .pipe(map((levels: ILevel[]) => this.levelService.addLevelToCollectionIfMissing(levels, this.editForm.get('dependsOn')!.value)))
      .subscribe((levels: ILevel[]) => (this.dependsOnsCollection = levels));

    this.imageService
      // ### Modification-Start ###
      .query({ page: 0, size: IMAGES_PER_PAGE })
      // ### Modification-End ###
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
          this.dimensionService.addDimensionToCollectionIfMissing(dimensions, this.editForm.get('dimension')!.value)
        )
      )
      .subscribe((dimensions: IDimension[]) => (this.dimensionsSharedCollection = dimensions));
  }

  protected createFromForm(): ILevel {
    return {
      ...new Level(),
      id: this.editForm.get(['id'])!.value,
      titleEN: this.editForm.get(['titleEN'])!.value,
      titleDE: this.editForm.get(['titleDE'])!.value,
      descriptionEN: this.editForm.get(['descriptionEN'])!.value,
      descriptionDE: this.editForm.get(['descriptionDE'])!.value,
      requiredScore: this.editForm.get(['requiredScore'])!.value,
      instantMultiplier: this.editForm.get(['instantMultiplier'])!.value,
      completionBonus: this.editForm.get(['completionBonus'])!.value,
      createdAt: this.editForm.get(['createdAt'])!.value ? dayjs(this.editForm.get(['createdAt'])!.value, DATE_TIME_FORMAT) : undefined,
      updatedAt: this.editForm.get(['updatedAt'])!.value ? dayjs(this.editForm.get(['updatedAt'])!.value, DATE_TIME_FORMAT) : undefined,
      dependsOn: this.editForm.get(['dependsOn'])!.value,
      image: this.editForm.get(['image'])!.value,
      dimension: this.editForm.get(['dimension'])!.value,
    };
  }
}
