import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import dayjs from 'dayjs/esm';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';

import { ISkill, Skill } from '../skill.model';
import { SkillService } from '../service/skill.service';

@Component({
  selector: 'jhi-skill-update',
  templateUrl: './skill-update.component.html',
})
export class SkillUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    title: [null, [Validators.required, Validators.minLength(5), Validators.maxLength(80)]],
    description: [null, [Validators.maxLength(4096)]],
    implementation: [null, [Validators.maxLength(4096)]],
    validation: [null, [Validators.maxLength(4096)]],
    expiryPeriod: [null, [Validators.min(1)]],
    contact: [null, [Validators.maxLength(255)]],
    score: [null, [Validators.required, Validators.min(0)]],
    rateScore: [null, [Validators.min(0), Validators.max(5)]],
    rateCount: [null, [Validators.required, Validators.min(0)]],
    createdAt: [null, [Validators.required]],
    updatedAt: [null, [Validators.required]],
  });

  constructor(protected skillService: SkillService, protected activatedRoute: ActivatedRoute, protected fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ skill }) => {
      if (skill.id === undefined) {
        const today = dayjs().startOf('day');
        skill.createdAt = today;
        skill.updatedAt = today;
      }

      this.updateForm(skill);
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const skill = this.createFromForm();
    if (skill.id !== undefined) {
      this.subscribeToSaveResponse(this.skillService.update(skill));
    } else {
      this.subscribeToSaveResponse(this.skillService.create(skill));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ISkill>>): void {
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

  protected updateForm(skill: ISkill): void {
    this.editForm.patchValue({
      id: skill.id,
      title: skill.title,
      description: skill.description,
      implementation: skill.implementation,
      validation: skill.validation,
      expiryPeriod: skill.expiryPeriod,
      contact: skill.contact,
      score: skill.score,
      rateScore: skill.rateScore,
      rateCount: skill.rateCount,
      createdAt: skill.createdAt ? skill.createdAt.format(DATE_TIME_FORMAT) : null,
      updatedAt: skill.updatedAt ? skill.updatedAt.format(DATE_TIME_FORMAT) : null,
    });
  }

  protected createFromForm(): ISkill {
    return {
      ...new Skill(),
      id: this.editForm.get(['id'])!.value,
      title: this.editForm.get(['title'])!.value,
      description: this.editForm.get(['description'])!.value,
      implementation: this.editForm.get(['implementation'])!.value,
      validation: this.editForm.get(['validation'])!.value,
      expiryPeriod: this.editForm.get(['expiryPeriod'])!.value,
      contact: this.editForm.get(['contact'])!.value,
      score: this.editForm.get(['score'])!.value,
      rateScore: this.editForm.get(['rateScore'])!.value,
      rateCount: this.editForm.get(['rateCount'])!.value,
      createdAt: this.editForm.get(['createdAt'])!.value ? dayjs(this.editForm.get(['createdAt'])!.value, DATE_TIME_FORMAT) : undefined,
      updatedAt: this.editForm.get(['updatedAt'])!.value ? dayjs(this.editForm.get(['updatedAt'])!.value, DATE_TIME_FORMAT) : undefined,
    };
  }
}
