import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import dayjs from 'dayjs/esm';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';

import { ITeam, Team } from '../team.model';
import { TeamService } from '../service/team.service';
import { IImage } from 'app/entities/image/image.model';
import { ImageService } from 'app/entities/image/service/image.service';
import { IDimension } from 'app/entities/dimension/dimension.model';
import { DimensionService } from 'app/entities/dimension/service/dimension.service';
import { ITeamGroup } from 'app/entities/team-group/team-group.model';
import { TeamGroupService } from 'app/entities/team-group/service/team-group.service';
// ### Modification-Start ###
import { DIMENSIONS_PER_PAGE, IMAGES_PER_PAGE } from '../../../config/pagination.constants';
// ### Modification-End ###

@Component({
  selector: 'jhi-team-update',
  templateUrl: './team-update.component.html',
})
export class TeamUpdateComponent implements OnInit {
  isSaving = false;

  imagesSharedCollection: IImage[] = [];
  dimensionsSharedCollection: IDimension[] = [];
  teamGroupsSharedCollection: ITeamGroup[] = [];

  editForm = this.fb.group({
    id: [],
    title: [null, [Validators.required, Validators.minLength(2), Validators.maxLength(50)]],
    shortTitle: [null, [Validators.required, Validators.minLength(2), Validators.maxLength(20), Validators.pattern('^[a-zA-Z0-9_-]*$')]],
    slogan: [null, [Validators.maxLength(255)]],
    contact: [null, [Validators.maxLength(255)]],
    expirationDate: [],
    official: [null, [Validators.required]],
    createdAt: [null, [Validators.required]],
    updatedAt: [null, [Validators.required]],
    image: [],
    participations: [],
    group: [null, Validators.required],
  });

  constructor(
    protected teamService: TeamService,
    protected imageService: ImageService,
    protected dimensionService: DimensionService,
    protected teamGroupService: TeamGroupService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ team }) => {
      if (team.id === undefined) {
        const today = dayjs().startOf('day');
        team.expirationDate = today;
        team.createdAt = today;
        team.updatedAt = today;
      }

      this.updateForm(team);

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const team = this.createFromForm();
    if (team.id !== undefined) {
      this.subscribeToSaveResponse(this.teamService.update(team));
    } else {
      this.subscribeToSaveResponse(this.teamService.create(team));
    }
  }

  trackImageById(_index: number, item: IImage): number {
    return item.id!;
  }

  trackDimensionById(_index: number, item: IDimension): number {
    return item.id!;
  }

  trackTeamGroupById(_index: number, item: ITeamGroup): number {
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

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ITeam>>): void {
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

  protected updateForm(team: ITeam): void {
    this.editForm.patchValue({
      id: team.id,
      title: team.title,
      shortTitle: team.shortTitle,
      slogan: team.slogan,
      contact: team.contact,
      expirationDate: team.expirationDate ? team.expirationDate.format(DATE_TIME_FORMAT) : null,
      official: team.official,
      createdAt: team.createdAt ? team.createdAt.format(DATE_TIME_FORMAT) : null,
      updatedAt: team.updatedAt ? team.updatedAt.format(DATE_TIME_FORMAT) : null,
      image: team.image,
      participations: team.participations,
      group: team.group,
    });

    this.imagesSharedCollection = this.imageService.addImageToCollectionIfMissing(this.imagesSharedCollection, team.image);
    this.dimensionsSharedCollection = this.dimensionService.addDimensionToCollectionIfMissing(
      this.dimensionsSharedCollection,
      ...(team.participations ?? [])
    );
    this.teamGroupsSharedCollection = this.teamGroupService.addTeamGroupToCollectionIfMissing(this.teamGroupsSharedCollection, team.group);
  }

  protected loadRelationshipsOptions(): void {
    this.imageService
      // ### Modification-Start ###
      .query({ page: 0, size: IMAGES_PER_PAGE })
      // ### Modification-End###
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
          this.dimensionService.addDimensionToCollectionIfMissing(dimensions, ...(this.editForm.get('participations')!.value ?? []))
        )
      )
      .subscribe((dimensions: IDimension[]) => (this.dimensionsSharedCollection = dimensions));

    this.teamGroupService
      .query()
      .pipe(map((res: HttpResponse<ITeamGroup[]>) => res.body ?? []))
      .pipe(
        map((teamGroups: ITeamGroup[]) =>
          this.teamGroupService.addTeamGroupToCollectionIfMissing(teamGroups, this.editForm.get('group')!.value)
        )
      )
      .subscribe((teamGroups: ITeamGroup[]) => (this.teamGroupsSharedCollection = teamGroups));
  }

  protected createFromForm(): ITeam {
    return {
      ...new Team(),
      id: this.editForm.get(['id'])!.value,
      title: this.editForm.get(['title'])!.value,
      shortTitle: this.editForm.get(['shortTitle'])!.value,
      slogan: this.editForm.get(['slogan'])!.value,
      contact: this.editForm.get(['contact'])!.value,
      expirationDate: this.editForm.get(['expirationDate'])!.value
        ? dayjs(this.editForm.get(['expirationDate'])!.value, DATE_TIME_FORMAT)
        : undefined,
      official: this.editForm.get(['official'])!.value,
      createdAt: this.editForm.get(['createdAt'])!.value ? dayjs(this.editForm.get(['createdAt'])!.value, DATE_TIME_FORMAT) : undefined,
      updatedAt: this.editForm.get(['updatedAt'])!.value ? dayjs(this.editForm.get(['updatedAt'])!.value, DATE_TIME_FORMAT) : undefined,
      image: this.editForm.get(['image'])!.value,
      participations: this.editForm.get(['participations'])!.value,
      group: this.editForm.get(['group'])!.value,
    };
  }
}
