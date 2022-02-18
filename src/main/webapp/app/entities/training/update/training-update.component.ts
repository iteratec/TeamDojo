import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import dayjs from 'dayjs/esm';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';

import { ITraining, Training } from '../training.model';
import { TrainingService } from '../service/training.service';
import { ISkill } from 'app/entities/skill/skill.model';
import { SkillService } from 'app/entities/skill/service/skill.service';

@Component({
  selector: 'jhi-training-update',
  templateUrl: './training-update.component.html',
})
export class TrainingUpdateComponent implements OnInit {
  isSaving = false;

  skillsSharedCollection: ISkill[] = [];

  editForm = this.fb.group({
    id: [],
    title: [null, [Validators.required, Validators.maxLength(80)]],
    description: [null, [Validators.maxLength(4096)]],
    contact: [null, [Validators.maxLength(255)]],
    link: [null, [Validators.maxLength(255)]],
    validUntil: [],
    isOfficial: [null, [Validators.required]],
    suggestedBy: [null, [Validators.maxLength(255)]],
    createdAt: [null, [Validators.required]],
    updatedAt: [null, [Validators.required]],
    skills: [],
  });

  constructor(
    protected trainingService: TrainingService,
    protected skillService: SkillService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ training }) => {
      if (training.id === undefined) {
        const today = dayjs().startOf('day');
        training.validUntil = today;
        training.createdAt = today;
        training.updatedAt = today;
      }

      this.updateForm(training);

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const training = this.createFromForm();
    if (training.id !== undefined) {
      this.subscribeToSaveResponse(this.trainingService.update(training));
    } else {
      this.subscribeToSaveResponse(this.trainingService.create(training));
    }
  }

  trackSkillById(index: number, item: ISkill): number {
    return item.id!;
  }

  getSelectedSkill(option: ISkill, selectedVals?: ISkill[]): ISkill {
    if (selectedVals) {
      for (const selectedVal of selectedVals) {
        if (option.id === selectedVal.id) {
          return selectedVal;
        }
      }
    }
    return option;
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
    this.editForm.patchValue({
      id: training.id,
      title: training.title,
      description: training.description,
      contact: training.contact,
      link: training.link,
      validUntil: training.validUntil ? training.validUntil.format(DATE_TIME_FORMAT) : null,
      isOfficial: training.isOfficial,
      suggestedBy: training.suggestedBy,
      createdAt: training.createdAt ? training.createdAt.format(DATE_TIME_FORMAT) : null,
      updatedAt: training.updatedAt ? training.updatedAt.format(DATE_TIME_FORMAT) : null,
      skills: training.skills,
    });

    this.skillsSharedCollection = this.skillService.addSkillToCollectionIfMissing(this.skillsSharedCollection, ...(training.skills ?? []));
  }

  protected loadRelationshipsOptions(): void {
    this.skillService
      .query()
      .pipe(map((res: HttpResponse<ISkill[]>) => res.body ?? []))
      .pipe(
        map((skills: ISkill[]) => this.skillService.addSkillToCollectionIfMissing(skills, ...(this.editForm.get('skills')!.value ?? [])))
      )
      .subscribe((skills: ISkill[]) => (this.skillsSharedCollection = skills));
  }

  protected createFromForm(): ITraining {
    return {
      ...new Training(),
      id: this.editForm.get(['id'])!.value,
      title: this.editForm.get(['title'])!.value,
      description: this.editForm.get(['description'])!.value,
      contact: this.editForm.get(['contact'])!.value,
      link: this.editForm.get(['link'])!.value,
      validUntil: this.editForm.get(['validUntil'])!.value ? dayjs(this.editForm.get(['validUntil'])!.value, DATE_TIME_FORMAT) : undefined,
      isOfficial: this.editForm.get(['isOfficial'])!.value,
      suggestedBy: this.editForm.get(['suggestedBy'])!.value,
      createdAt: this.editForm.get(['createdAt'])!.value ? dayjs(this.editForm.get(['createdAt'])!.value, DATE_TIME_FORMAT) : undefined,
      updatedAt: this.editForm.get(['updatedAt'])!.value ? dayjs(this.editForm.get(['updatedAt'])!.value, DATE_TIME_FORMAT) : undefined,
      skills: this.editForm.get(['skills'])!.value,
    };
  }
}
