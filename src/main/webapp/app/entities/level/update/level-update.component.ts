import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { LevelFormService, LevelFormGroup } from './level-form.service';
import { ILevel } from '../level.model';
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
  level: ILevel | null = null;

  levelsSharedCollection: ILevel[] = [];
  imagesSharedCollection: IImage[] = [];
  dimensionsSharedCollection: IDimension[] = [];

  editForm: LevelFormGroup = this.levelFormService.createLevelFormGroup();

  constructor(
    protected levelService: LevelService,
    protected levelFormService: LevelFormService,
    protected imageService: ImageService,
    protected dimensionService: DimensionService,
    protected activatedRoute: ActivatedRoute
  ) {}

  compareLevel = (o1: ILevel | null, o2: ILevel | null): boolean => this.levelService.compareLevel(o1, o2);

  compareImage = (o1: IImage | null, o2: IImage | null): boolean => this.imageService.compareImage(o1, o2);

  compareDimension = (o1: IDimension | null, o2: IDimension | null): boolean => this.dimensionService.compareDimension(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ level }) => {
      this.level = level;
      if (level) {
        this.updateForm(level);
      }

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const level = this.levelFormService.getLevel(this.editForm);
    if (level.id !== null) {
      this.subscribeToSaveResponse(this.levelService.update(level));
    } else {
      this.subscribeToSaveResponse(this.levelService.create(level));
    }
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
    this.level = level;
    this.levelFormService.resetForm(this.editForm, level);

    this.levelsSharedCollection = this.levelService.addLevelToCollectionIfMissing<ILevel>(this.levelsSharedCollection, level.dependsOn);
    this.imagesSharedCollection = this.imageService.addImageToCollectionIfMissing<IImage>(this.imagesSharedCollection, level.image);
    this.dimensionsSharedCollection = this.dimensionService.addDimensionToCollectionIfMissing<IDimension>(
      this.dimensionsSharedCollection,
      level.dimension
    );
  }

  protected loadRelationshipsOptions(): void {
    this.levelService
      .query()
      .pipe(map((res: HttpResponse<ILevel[]>) => res.body ?? []))
      .pipe(map((levels: ILevel[]) => this.levelService.addLevelToCollectionIfMissing<ILevel>(levels, this.level?.dependsOn)))
      .subscribe((levels: ILevel[]) => (this.levelsSharedCollection = levels));

    this.imageService
      // ### Modification-Start ###
      .query({ page: 0, size: IMAGES_PER_PAGE })
      // ### Modification-End ###
      .pipe(map((res: HttpResponse<IImage[]>) => res.body ?? []))
      .pipe(map((images: IImage[]) => this.imageService.addImageToCollectionIfMissing<IImage>(images, this.level?.image)))
      .subscribe((images: IImage[]) => (this.imagesSharedCollection = images));

    this.dimensionService
      // ### Modification-Start ###
      .query({ page: 0, size: DIMENSIONS_PER_PAGE })
      // ### Modification-End ###
      .pipe(map((res: HttpResponse<IDimension[]>) => res.body ?? []))
      .pipe(
        map((dimensions: IDimension[]) =>
          this.dimensionService.addDimensionToCollectionIfMissing<IDimension>(dimensions, this.level?.dimension)
        )
      )
      .subscribe((dimensions: IDimension[]) => (this.dimensionsSharedCollection = dimensions));
  }
}
