import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { TeamFormService, TeamFormGroup } from './team-form.service';
import { ITeam } from '../team.model';
import { TeamService } from '../service/team.service';
import { IImage } from 'app/entities/image/image.model';
import { ImageService } from 'app/entities/image/service/image.service';
import { IDimension } from 'app/entities/dimension/dimension.model';
import { DimensionService } from 'app/entities/dimension/service/dimension.service';
import { ITeamGroup } from 'app/entities/team-group/team-group.model';
import { TeamGroupService } from 'app/entities/team-group/service/team-group.service';
// ### Modification-Start ###
import { DIMENSIONS_PER_PAGE, IMAGES_PER_PAGE, TEAM_GROUPS_PER_PAGE } from '../../../config/pagination.constants';
// ### Modification-End ###

@Component({
  selector: 'jhi-team-update',
  templateUrl: './team-update.component.html',
})
export class TeamUpdateComponent implements OnInit {
  isSaving = false;
  team: ITeam | null = null;

  imagesSharedCollection: IImage[] = [];
  dimensionsSharedCollection: IDimension[] = [];
  teamGroupsSharedCollection: ITeamGroup[] = [];

  editForm: TeamFormGroup = this.teamFormService.createTeamFormGroup();

  constructor(
    protected teamService: TeamService,
    protected teamFormService: TeamFormService,
    protected imageService: ImageService,
    protected dimensionService: DimensionService,
    protected teamGroupService: TeamGroupService,
    protected activatedRoute: ActivatedRoute
  ) {}

  compareImage = (o1: IImage | null, o2: IImage | null): boolean => this.imageService.compareImage(o1, o2);

  compareDimension = (o1: IDimension | null, o2: IDimension | null): boolean => this.dimensionService.compareDimension(o1, o2);

  compareTeamGroup = (o1: ITeamGroup | null, o2: ITeamGroup | null): boolean => this.teamGroupService.compareTeamGroup(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ team }) => {
      this.team = team;
      if (team) {
        this.updateForm(team);
      }

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const team = this.teamFormService.getTeam(this.editForm);
    if (team.id !== null) {
      this.subscribeToSaveResponse(this.teamService.update(team));
    } else {
      this.subscribeToSaveResponse(this.teamService.create(team));
    }
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
    this.team = team;
    this.teamFormService.resetForm(this.editForm, team);

    this.imagesSharedCollection = this.imageService.addImageToCollectionIfMissing<IImage>(this.imagesSharedCollection, team.image);
    this.dimensionsSharedCollection = this.dimensionService.addDimensionToCollectionIfMissing<IDimension>(
      this.dimensionsSharedCollection,
      ...(team.participations ?? [])
    );
    this.teamGroupsSharedCollection = this.teamGroupService.addTeamGroupToCollectionIfMissing<ITeamGroup>(
      this.teamGroupsSharedCollection,
      team.group
    );
  }

  protected loadRelationshipsOptions(): void {
    this.imageService
      // ### Modification-Start ###
      .query({ page: 0, size: IMAGES_PER_PAGE })
      // ### Modification-End###
      .pipe(map((res: HttpResponse<IImage[]>) => res.body ?? []))
      .pipe(map((images: IImage[]) => this.imageService.addImageToCollectionIfMissing<IImage>(images, this.team?.image)))
      .subscribe((images: IImage[]) => (this.imagesSharedCollection = images));

    this.dimensionService
      // ### Modification-Start ###
      .query({ page: 0, size: DIMENSIONS_PER_PAGE })
      // ### Modification-End ###
      .pipe(map((res: HttpResponse<IDimension[]>) => res.body ?? []))
      .pipe(
        map((dimensions: IDimension[]) =>
          this.dimensionService.addDimensionToCollectionIfMissing<IDimension>(dimensions, ...(this.team?.participations ?? []))
        )
      )
      .subscribe((dimensions: IDimension[]) => (this.dimensionsSharedCollection = dimensions));

    this.teamGroupService
      // ### Modification-Start ###
      .query({ page: 0, size: TEAM_GROUPS_PER_PAGE })
      // ### Modification-End ###
      .pipe(map((res: HttpResponse<ITeamGroup[]>) => res.body ?? []))
      .pipe(
        map((teamGroups: ITeamGroup[]) => this.teamGroupService.addTeamGroupToCollectionIfMissing<ITeamGroup>(teamGroups, this.team?.group))
      )
      .subscribe((teamGroups: ITeamGroup[]) => (this.teamGroupsSharedCollection = teamGroups));
  }
}
