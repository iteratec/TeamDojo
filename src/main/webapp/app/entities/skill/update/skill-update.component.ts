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
    titleEN: [null, [Validators.required, Validators.minLength(5), Validators.maxLength(80)]],
    titleDE: [null, [Validators.minLength(5), Validators.maxLength(80)]],
    descriptionEN: [null, [Validators.maxLength(8192)]],
    descriptionDE: [null, [Validators.maxLength(8192)]],
    implementationEN: [null, [Validators.maxLength(4096)]],
    implementationDE: [null, [Validators.maxLength(4096)]],
    validationEN: [null, [Validators.maxLength(4096)]],
    validationDE: [null, [Validators.maxLength(4096)]],
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
      titleEN: skill.titleEN,
      titleDE: skill.titleDE,
      descriptionEN: skill.descriptionEN,
      descriptionDE: skill.descriptionDE,
      implementationEN: skill.implementationEN,
      implementationDE: skill.implementationDE,
      validationEN: skill.validationEN,
      validationDE: skill.validationDE,
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
      titleEN: this.editForm.get(['titleEN'])!.value,
      titleDE: this.editForm.get(['titleDE'])!.value,
      descriptionEN: this.editForm.get(['descriptionEN'])!.value,
      descriptionDE: this.editForm.get(['descriptionDE'])!.value,
      implementationEN: this.editForm.get(['implementationEN'])!.value,
      implementationDE: this.editForm.get(['implementationDE'])!.value,
      validationEN: this.editForm.get(['validationEN'])!.value,
      validationDE: this.editForm.get(['validationDE'])!.value,
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
