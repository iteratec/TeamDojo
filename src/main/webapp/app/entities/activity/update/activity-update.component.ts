import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import { ActivityFormService, ActivityFormGroup } from './activity-form.service';
import { IActivity } from '../activity.model';
import { ActivityService } from '../service/activity.service';
import { ActivityType } from 'app/entities/enumerations/activity-type.model';

@Component({
  selector: 'jhi-activity-update',
  templateUrl: './activity-update.component.html',
})
export class ActivityUpdateComponent implements OnInit {
  isSaving = false;
  activity: IActivity | null = null;
  activityTypeValues = Object.keys(ActivityType);

  editForm: ActivityFormGroup = this.activityFormService.createActivityFormGroup();

  constructor(
    protected activityService: ActivityService,
    protected activityFormService: ActivityFormService,
    protected activatedRoute: ActivatedRoute
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ activity }) => {
      this.activity = activity;
      if (activity) {
        this.updateForm(activity);
      }
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const activity = this.activityFormService.getActivity(this.editForm);
    if (activity.id !== null) {
      this.subscribeToSaveResponse(this.activityService.update(activity));
    } else {
      this.subscribeToSaveResponse(this.activityService.create(activity));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IActivity>>): void {
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

  protected updateForm(activity: IActivity): void {
    this.activity = activity;
    this.activityFormService.resetForm(this.editForm, activity);
  }
}
