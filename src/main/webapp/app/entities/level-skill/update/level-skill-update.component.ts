import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { ILevelSkill, LevelSkill } from '../level-skill.model';
import { LevelSkillService } from '../service/level-skill.service';
import { ISkill } from 'app/entities/skill/skill.model';
import { SkillService } from 'app/entities/skill/service/skill.service';
import { ILevel } from 'app/entities/level/level.model';
import { LevelService } from 'app/entities/level/service/level.service';
// ### Modification-Start ###
import { LEVELS_PER_PAGE, SKILLS_PER_PAGE } from '../../../config/pagination.constants';
// ### Modification-End ###

@Component({
  selector: 'jhi-level-skill-update',
  templateUrl: './level-skill-update.component.html',
})
export class LevelSkillUpdateComponent implements OnInit {
  isSaving = false;

  skillsSharedCollection: ISkill[] = [];
  levelsSharedCollection: ILevel[] = [];

  editForm = this.fb.group({
    id: [],
    skill: [null, Validators.required],
    level: [null, Validators.required],
  });

  constructor(
    protected levelSkillService: LevelSkillService,
    protected skillService: SkillService,
    protected levelService: LevelService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ levelSkill }) => {
      this.updateForm(levelSkill);

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const levelSkill = this.createFromForm();
    if (levelSkill.id !== undefined) {
      this.subscribeToSaveResponse(this.levelSkillService.update(levelSkill));
    } else {
      this.subscribeToSaveResponse(this.levelSkillService.create(levelSkill));
    }
  }

  trackSkillById(index: number, item: ISkill): number {
    return item.id!;
  }

  trackLevelById(index: number, item: ILevel): number {
    return item.id!;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ILevelSkill>>): void {
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

  protected updateForm(levelSkill: ILevelSkill): void {
    this.editForm.patchValue({
      id: levelSkill.id,
      skill: levelSkill.skill,
      level: levelSkill.level,
    });

    this.skillsSharedCollection = this.skillService.addSkillToCollectionIfMissing(this.skillsSharedCollection, levelSkill.skill);
    this.levelsSharedCollection = this.levelService.addLevelToCollectionIfMissing(this.levelsSharedCollection, levelSkill.level);
  }

  protected loadRelationshipsOptions(): void {
    this.skillService
      // ### Modification-Start ###
      .query({ page: 0, size: SKILLS_PER_PAGE })
      // ### Modification-End ###
      .pipe(map((res: HttpResponse<ISkill[]>) => res.body ?? []))
      .pipe(map((skills: ISkill[]) => this.skillService.addSkillToCollectionIfMissing(skills, this.editForm.get('skill')!.value)))
      .subscribe((skills: ISkill[]) => (this.skillsSharedCollection = skills));

    this.levelService
      // ### Modification-Start ###
      .query({ page: 0, size: LEVELS_PER_PAGE })
      // ### Modification-End ###
      .pipe(map((res: HttpResponse<ILevel[]>) => res.body ?? []))
      .pipe(map((levels: ILevel[]) => this.levelService.addLevelToCollectionIfMissing(levels, this.editForm.get('level')!.value)))
      .subscribe((levels: ILevel[]) => (this.levelsSharedCollection = levels));
  }

  protected createFromForm(): ILevelSkill {
    return {
      ...new LevelSkill(),
      id: this.editForm.get(['id'])!.value,
      skill: this.editForm.get(['skill'])!.value,
      level: this.editForm.get(['level'])!.value,
    };
  }
}
