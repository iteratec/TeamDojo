import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import { SkillFormService, SkillFormGroup } from './skill-form.service';
import { ISkill } from '../skill.model';
import { SkillService } from '../service/skill.service';

@Component({
  selector: 'jhi-skill-update',
  templateUrl: './skill-update.component.html',
})
export class SkillUpdateComponent implements OnInit {
  isSaving = false;
  skill: ISkill | null = null;

  editForm: SkillFormGroup = this.skillFormService.createSkillFormGroup();

  constructor(
    protected skillService: SkillService,
    protected skillFormService: SkillFormService,
    protected activatedRoute: ActivatedRoute
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ skill }) => {
      this.skill = skill;
      if (skill) {
        this.updateForm(skill);
      }
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const skill = this.skillFormService.getSkill(this.editForm);
    if (skill.id !== null) {
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
    this.skill = skill;
    this.skillFormService.resetForm(this.editForm, skill);
  }
}
