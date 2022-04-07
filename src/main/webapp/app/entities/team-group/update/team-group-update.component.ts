import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import dayjs from 'dayjs/esm';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';

import { ITeamGroup, TeamGroup } from '../team-group.model';
import { TeamGroupService } from '../service/team-group.service';

@Component({
  selector: 'jhi-team-group-update',
  templateUrl: './team-group-update.component.html',
})
export class TeamGroupUpdateComponent implements OnInit {
  isSaving = false;

  teamGroupsSharedCollection: ITeamGroup[] = [];

  editForm = this.fb.group({
    id: [],
    title: [null, [Validators.required, Validators.maxLength(80)]],
    description: [null, [Validators.maxLength(4096)]],
    createdAt: [null, [Validators.required]],
    updatedAt: [null, [Validators.required]],
    parent: [],
  });

  constructor(protected teamGroupService: TeamGroupService, protected activatedRoute: ActivatedRoute, protected fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ teamGroup }) => {
      if (teamGroup.id === undefined) {
        const today = dayjs().startOf('day');
        teamGroup.createdAt = today;
        teamGroup.updatedAt = today;
      }

      this.updateForm(teamGroup);

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const teamGroup = this.createFromForm();
    if (teamGroup.id !== undefined) {
      this.subscribeToSaveResponse(this.teamGroupService.update(teamGroup));
    } else {
      this.subscribeToSaveResponse(this.teamGroupService.create(teamGroup));
    }
  }

  trackTeamGroupById(index: number, item: ITeamGroup): number {
    return item.id!;
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
    this.editForm.patchValue({
      id: teamGroup.id,
      title: teamGroup.title,
      description: teamGroup.description,
      createdAt: teamGroup.createdAt ? teamGroup.createdAt.format(DATE_TIME_FORMAT) : null,
      updatedAt: teamGroup.updatedAt ? teamGroup.updatedAt.format(DATE_TIME_FORMAT) : null,
      parent: teamGroup.parent,
    });

    this.teamGroupsSharedCollection = this.teamGroupService.addTeamGroupToCollectionIfMissing(
      this.teamGroupsSharedCollection,
      teamGroup.parent
    );
  }

  protected loadRelationshipsOptions(): void {
    this.teamGroupService
      .query()
      .pipe(map((res: HttpResponse<ITeamGroup[]>) => res.body ?? []))
      .pipe(
        map((teamGroups: ITeamGroup[]) =>
          this.teamGroupService.addTeamGroupToCollectionIfMissing(teamGroups, this.editForm.get('parent')!.value)
        )
      )
      .subscribe((teamGroups: ITeamGroup[]) => (this.teamGroupsSharedCollection = teamGroups));
  }

  protected createFromForm(): ITeamGroup {
    return {
      ...new TeamGroup(),
      id: this.editForm.get(['id'])!.value,
      title: this.editForm.get(['title'])!.value,
      description: this.editForm.get(['description'])!.value,
      createdAt: this.editForm.get(['createdAt'])!.value ? dayjs(this.editForm.get(['createdAt'])!.value, DATE_TIME_FORMAT) : undefined,
      updatedAt: this.editForm.get(['updatedAt'])!.value ? dayjs(this.editForm.get(['updatedAt'])!.value, DATE_TIME_FORMAT) : undefined,
      parent: this.editForm.get(['parent'])!.value,
    };
  }
}
