import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { LevelSkillFormService, LevelSkillFormGroup } from './level-skill-form.service';
import { ILevelSkill } from '../level-skill.model';
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
  levelSkill: ILevelSkill | null = null;

  skillsSharedCollection: ISkill[] = [];
  levelsSharedCollection: ILevel[] = [];

  editForm: LevelSkillFormGroup = this.levelSkillFormService.createLevelSkillFormGroup();

  constructor(
    protected levelSkillService: LevelSkillService,
    protected levelSkillFormService: LevelSkillFormService,
    protected skillService: SkillService,
    protected levelService: LevelService,
    protected activatedRoute: ActivatedRoute
  ) {}

  compareSkill = (o1: ISkill | null, o2: ISkill | null): boolean => this.skillService.compareSkill(o1, o2);

  compareLevel = (o1: ILevel | null, o2: ILevel | null): boolean => this.levelService.compareLevel(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ levelSkill }) => {
      this.levelSkill = levelSkill;
      if (levelSkill) {
        this.updateForm(levelSkill);
      }

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const levelSkill = this.levelSkillFormService.getLevelSkill(this.editForm);
    if (levelSkill.id !== null) {
      this.subscribeToSaveResponse(this.levelSkillService.update(levelSkill));
    } else {
      this.subscribeToSaveResponse(this.levelSkillService.create(levelSkill));
    }
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
    this.levelSkill = levelSkill;
    this.levelSkillFormService.resetForm(this.editForm, levelSkill);

    this.skillsSharedCollection = this.skillService.addSkillToCollectionIfMissing<ISkill>(this.skillsSharedCollection, levelSkill.skill);
    this.levelsSharedCollection = this.levelService.addLevelToCollectionIfMissing<ILevel>(this.levelsSharedCollection, levelSkill.level);
  }

  protected loadRelationshipsOptions(): void {
    this.skillService
      // ### Modification-Start ###
      .query({ page: 0, size: SKILLS_PER_PAGE })
      // ### Modification-End ###
      .pipe(map((res: HttpResponse<ISkill[]>) => res.body ?? []))
      .pipe(map((skills: ISkill[]) => this.skillService.addSkillToCollectionIfMissing<ISkill>(skills, this.levelSkill?.skill)))
      .subscribe((skills: ISkill[]) => (this.skillsSharedCollection = skills));

    this.levelService
      // ### Modification-Start ###
      .query({ page: 0, size: LEVELS_PER_PAGE })
      // ### Modification-End ###
      .pipe(map((res: HttpResponse<ILevel[]>) => res.body ?? []))
      .pipe(map((levels: ILevel[]) => this.levelService.addLevelToCollectionIfMissing<ILevel>(levels, this.levelSkill?.level)))
      .subscribe((levels: ILevel[]) => (this.levelsSharedCollection = levels));
  }
}
