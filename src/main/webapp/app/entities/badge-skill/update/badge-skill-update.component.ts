import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { IBadgeSkill, BadgeSkill } from '../badge-skill.model';
import { BadgeSkillService } from '../service/badge-skill.service';
import { IBadge } from 'app/entities/badge/badge.model';
import { BadgeService } from 'app/entities/badge/service/badge.service';
import { ISkill } from 'app/entities/skill/skill.model';
import { SkillService } from 'app/entities/skill/service/skill.service';

@Component({
  selector: 'jhi-badge-skill-update',
  templateUrl: './badge-skill-update.component.html',
})
export class BadgeSkillUpdateComponent implements OnInit {
  isSaving = false;

  badgesSharedCollection: IBadge[] = [];
  skillsSharedCollection: ISkill[] = [];

  editForm = this.fb.group({
    id: [],
    badge: [null, Validators.required],
    skill: [null, Validators.required],
  });

  constructor(
    protected badgeSkillService: BadgeSkillService,
    protected badgeService: BadgeService,
    protected skillService: SkillService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ badgeSkill }) => {
      this.updateForm(badgeSkill);

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const badgeSkill = this.createFromForm();
    if (badgeSkill.id !== undefined) {
      this.subscribeToSaveResponse(this.badgeSkillService.update(badgeSkill));
    } else {
      this.subscribeToSaveResponse(this.badgeSkillService.create(badgeSkill));
    }
  }

  trackBadgeById(index: number, item: IBadge): number {
    return item.id!;
  }

  trackSkillById(index: number, item: ISkill): number {
    return item.id!;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IBadgeSkill>>): void {
    result.pipe(finalize(() => this.onSaveFinalize())).subscribe(
      () => this.onSaveSuccess(),
      () => this.onSaveError()
    );
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

  protected updateForm(badgeSkill: IBadgeSkill): void {
    this.editForm.patchValue({
      id: badgeSkill.id,
      badge: badgeSkill.badge,
      skill: badgeSkill.skill,
    });

    this.badgesSharedCollection = this.badgeService.addBadgeToCollectionIfMissing(this.badgesSharedCollection, badgeSkill.badge);
    this.skillsSharedCollection = this.skillService.addSkillToCollectionIfMissing(this.skillsSharedCollection, badgeSkill.skill);
  }

  protected loadRelationshipsOptions(): void {
    this.badgeService
      .query()
      .pipe(map((res: HttpResponse<IBadge[]>) => res.body ?? []))
      .pipe(map((badges: IBadge[]) => this.badgeService.addBadgeToCollectionIfMissing(badges, this.editForm.get('badge')!.value)))
      .subscribe((badges: IBadge[]) => (this.badgesSharedCollection = badges));

    this.skillService
      .query()
      .pipe(map((res: HttpResponse<ISkill[]>) => res.body ?? []))
      .pipe(map((skills: ISkill[]) => this.skillService.addSkillToCollectionIfMissing(skills, this.editForm.get('skill')!.value)))
      .subscribe((skills: ISkill[]) => (this.skillsSharedCollection = skills));
  }

  protected createFromForm(): IBadgeSkill {
    return {
      ...new BadgeSkill(),
      id: this.editForm.get(['id'])!.value,
      badge: this.editForm.get(['badge'])!.value,
      skill: this.editForm.get(['skill'])!.value,
    };
  }
}
