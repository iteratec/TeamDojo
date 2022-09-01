import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { TrainingFormService, TrainingFormGroup } from './training-form.service';
import { ITraining } from '../training.model';
import { TrainingService } from '../service/training.service';
import { ISkill } from 'app/entities/skill/skill.model';
import { SkillService } from 'app/entities/skill/service/skill.service';
// ### Modification-Start ###
import { SKILLS_PER_PAGE } from '../../../config/pagination.constants';
// ### Modification-End ###

@Component({
  selector: 'jhi-training-update',
  templateUrl: './training-update.component.html',
})
export class TrainingUpdateComponent implements OnInit {
  isSaving = false;
  training: ITraining | null = null;

  skillsSharedCollection: ISkill[] = [];

  editForm: TrainingFormGroup = this.trainingFormService.createTrainingFormGroup();

  constructor(
    protected trainingService: TrainingService,
    protected trainingFormService: TrainingFormService,
    protected skillService: SkillService,
    protected activatedRoute: ActivatedRoute
  ) {}

  compareSkill = (o1: ISkill | null, o2: ISkill | null): boolean => this.skillService.compareSkill(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ training }) => {
      this.training = training;
      if (training) {
        this.updateForm(training);
      }

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const training = this.trainingFormService.getTraining(this.editForm);
    if (training.id !== null) {
      this.subscribeToSaveResponse(this.trainingService.update(training));
    } else {
      this.subscribeToSaveResponse(this.trainingService.create(training));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ITraining>>): void {
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

  protected updateForm(training: ITraining): void {
    this.training = training;
    this.trainingFormService.resetForm(this.editForm, training);

    this.skillsSharedCollection = this.skillService.addSkillToCollectionIfMissing<ISkill>(
      this.skillsSharedCollection,
      ...(training.skills ?? [])
    );
  }

  protected loadRelationshipsOptions(): void {
    this.skillService
      // ### Modification-Start ###
      .query({ page: 0, size: SKILLS_PER_PAGE })
      // ### Modification-End ###
      .pipe(map((res: HttpResponse<ISkill[]>) => res.body ?? []))
      .pipe(map((skills: ISkill[]) => this.skillService.addSkillToCollectionIfMissing<ISkill>(skills, ...(this.training?.skills ?? []))))
      .subscribe((skills: ISkill[]) => (this.skillsSharedCollection = skills));
  }
}
