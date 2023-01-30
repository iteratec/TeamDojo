import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { TeamGroupFormService, TeamGroupFormGroup } from './team-group-form.service';
import { ITeamGroup } from '../team-group.model';
import { TeamGroupService } from '../service/team-group.service';
// ### Modification-Start ###
import { TEAMS_PER_PAGE } from '../../../config/pagination.constants';
// ### Modification-End###

@Component({
  selector: 'jhi-team-group-update',
  templateUrl: './team-group-update.component.html',
})
export class TeamGroupUpdateComponent implements OnInit {
  isSaving = false;
  teamGroup: ITeamGroup | null = null;

  teamGroupsSharedCollection: ITeamGroup[] = [];

  editForm: TeamGroupFormGroup = this.teamGroupFormService.createTeamGroupFormGroup();

  constructor(
    protected teamGroupService: TeamGroupService,
    protected teamGroupFormService: TeamGroupFormService,
    protected activatedRoute: ActivatedRoute
  ) {}

  compareTeamGroup = (o1: ITeamGroup | null, o2: ITeamGroup | null): boolean => this.teamGroupService.compareTeamGroup(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ teamGroup }) => {
      this.teamGroup = teamGroup;
      if (teamGroup) {
        this.updateForm(teamGroup);
      }

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const teamGroup = this.teamGroupFormService.getTeamGroup(this.editForm);
    if (teamGroup.id !== null) {
      this.subscribeToSaveResponse(this.teamGroupService.update(teamGroup));
    } else {
      this.subscribeToSaveResponse(this.teamGroupService.create(teamGroup));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ITeamGroup>>): void {
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

  protected updateForm(teamGroup: ITeamGroup): void {
    this.teamGroup = teamGroup;
    this.teamGroupFormService.resetForm(this.editForm, teamGroup);

    this.teamGroupsSharedCollection = this.teamGroupService.addTeamGroupToCollectionIfMissing<ITeamGroup>(
      this.teamGroupsSharedCollection,
      teamGroup.parent
    );
  }

  protected loadRelationshipsOptions(): void {
    this.teamGroupService
      // ### Modification-Start ###
      .query({ page: 0, size: TEAMS_PER_PAGE })
      // ### Modification-End###
      .pipe(map((res: HttpResponse<ITeamGroup[]>) => res.body ?? []))
      .pipe(
        map((teamGroups: ITeamGroup[]) =>
          this.teamGroupService.addTeamGroupToCollectionIfMissing<ITeamGroup>(teamGroups, this.teamGroup?.parent)
        )
      )
      .subscribe((teamGroups: ITeamGroup[]) => (this.teamGroupsSharedCollection = teamGroups));
  }
}
