import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import SharedModule from 'app/shared/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { IImage } from 'app/entities/image/image.model';
import { ImageService } from 'app/entities/image/service/image.service';
import { IDimension } from 'app/entities/dimension/dimension.model';
import { DimensionService } from 'app/entities/dimension/service/dimension.service';
import { BadgeService } from '../service/badge.service';
import { IBadge } from '../badge.model';
import { BadgeFormService, BadgeFormGroup } from './badge-form.service';
// ### Modification-Start ###
import { DIMENSIONS_PER_PAGE, IMAGES_PER_PAGE } from '../../../config/pagination.constants';
// ### Modification-End###

@Component({
  standalone: true,
  selector: 'jhi-badge-update',
  templateUrl: './badge-update.component.html',
  imports: [SharedModule, FormsModule, ReactiveFormsModule],
})
export class BadgeUpdateComponent implements OnInit {
  isSaving = false;
  badge: IBadge | null = null;

  imagesSharedCollection: IImage[] = [];
  dimensionsSharedCollection: IDimension[] = [];

  editForm: BadgeFormGroup = this.badgeFormService.createBadgeFormGroup();

  constructor(
    protected badgeService: BadgeService,
    protected badgeFormService: BadgeFormService,
    protected imageService: ImageService,
    protected dimensionService: DimensionService,
    protected activatedRoute: ActivatedRoute,
  ) {}

  compareImage = (o1: IImage | null, o2: IImage | null): boolean => this.imageService.compareImage(o1, o2);

  compareDimension = (o1: IDimension | null, o2: IDimension | null): boolean => this.dimensionService.compareDimension(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ badge }) => {
      this.badge = badge;
      if (badge) {
        this.updateForm(badge);
      }

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const badge = this.badgeFormService.getBadge(this.editForm);
    if (badge.id !== null) {
      this.subscribeToSaveResponse(this.badgeService.update(badge));
    } else {
      this.subscribeToSaveResponse(this.badgeService.create(badge));
    }
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
    this.badge = badge;
    this.badgeFormService.resetForm(this.editForm, badge);

    this.imagesSharedCollection = this.imageService.addImageToCollectionIfMissing<IImage>(this.imagesSharedCollection, badge.image);
    this.dimensionsSharedCollection = this.dimensionService.addDimensionToCollectionIfMissing<IDimension>(
      this.dimensionsSharedCollection,
      ...(badge.dimensions ?? []),
    );
  }

  protected loadRelationshipsOptions(): void {
    this.imageService
      // ### Modification-Start ###
      .query({ page: 0, size: IMAGES_PER_PAGE })
      // ### Modification-End  ###
      .pipe(map((res: HttpResponse<IImage[]>) => res.body ?? []))
      .pipe(map((images: IImage[]) => this.imageService.addImageToCollectionIfMissing<IImage>(images, this.badge?.image)))
      .subscribe((images: IImage[]) => (this.imagesSharedCollection = images));

    this.dimensionService
      // ### Modification-Start ###
      .query({ page: 0, size: DIMENSIONS_PER_PAGE })
      // ### Modification-End ###
      .pipe(map((res: HttpResponse<IDimension[]>) => res.body ?? []))
      .pipe(
        map((dimensions: IDimension[]) =>
          this.dimensionService.addDimensionToCollectionIfMissing<IDimension>(dimensions, ...(this.badge?.dimensions ?? [])),
        ),
      )
      .subscribe((dimensions: IDimension[]) => (this.dimensionsSharedCollection = dimensions));
  }
}
