import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import SharedModule from 'app/shared/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { IBadge } from 'app/entities/badge/badge.model';
import { BadgeService } from 'app/entities/badge/service/badge.service';
import { ISkill } from 'app/entities/skill/skill.model';
import { SkillService } from 'app/entities/skill/service/skill.service';
import { BadgeSkillService } from '../service/badge-skill.service';
import { IBadgeSkill } from '../badge-skill.model';
import { BadgeSkillFormService, BadgeSkillFormGroup } from './badge-skill-form.service';
// ### Modification-Start ###
import { BADGES_PER_PAGE, SKILLS_PER_PAGE } from '../../../config/pagination.constants';
// ### Modification-End###

@Component({
  standalone: true,
  selector: 'jhi-badge-skill-update',
  templateUrl: './badge-skill-update.component.html',
  imports: [SharedModule, FormsModule, ReactiveFormsModule],
})
export class BadgeSkillUpdateComponent implements OnInit {
  isSaving = false;
  badgeSkill: IBadgeSkill | null = null;

  badgesSharedCollection: IBadge[] = [];
  skillsSharedCollection: ISkill[] = [];

  editForm: BadgeSkillFormGroup = this.badgeSkillFormService.createBadgeSkillFormGroup();

  constructor(
    protected badgeSkillService: BadgeSkillService,
    protected badgeSkillFormService: BadgeSkillFormService,
    protected badgeService: BadgeService,
    protected skillService: SkillService,
    protected activatedRoute: ActivatedRoute,
  ) {}

  compareBadge = (o1: IBadge | null, o2: IBadge | null): boolean => this.badgeService.compareBadge(o1, o2);

  compareSkill = (o1: ISkill | null, o2: ISkill | null): boolean => this.skillService.compareSkill(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ badgeSkill }) => {
      this.badgeSkill = badgeSkill;
      if (badgeSkill) {
        this.updateForm(badgeSkill);
      }

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const badgeSkill = this.badgeSkillFormService.getBadgeSkill(this.editForm);
    if (badgeSkill.id !== null) {
      this.subscribeToSaveResponse(this.badgeSkillService.update(badgeSkill));
    } else {
      this.subscribeToSaveResponse(this.badgeSkillService.create(badgeSkill));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IBadgeSkill>>): void {
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

  protected updateForm(badgeSkill: IBadgeSkill): void {
    this.badgeSkill = badgeSkill;
    this.badgeSkillFormService.resetForm(this.editForm, badgeSkill);

    this.badgesSharedCollection = this.badgeService.addBadgeToCollectionIfMissing<IBadge>(this.badgesSharedCollection, badgeSkill.badge);
    this.skillsSharedCollection = this.skillService.addSkillToCollectionIfMissing<ISkill>(this.skillsSharedCollection, badgeSkill.skill);
  }

  protected loadRelationshipsOptions(): void {
    this.badgeService
      // ### Modification-Start ###
      .query({ page: 0, size: BADGES_PER_PAGE })
      // ### Modification-End###
      .pipe(map((res: HttpResponse<IBadge[]>) => res.body ?? []))
      .pipe(map((badges: IBadge[]) => this.badgeService.addBadgeToCollectionIfMissing<IBadge>(badges, this.badgeSkill?.badge)))
      .subscribe((badges: IBadge[]) => (this.badgesSharedCollection = badges));

    this.skillService
      // ### Modification-Start ###
      .query({ page: 0, size: SKILLS_PER_PAGE })
      // ### Modification-End ###
      .pipe(map((res: HttpResponse<ISkill[]>) => res.body ?? []))
      .pipe(map((skills: ISkill[]) => this.skillService.addSkillToCollectionIfMissing<ISkill>(skills, this.badgeSkill?.skill)))
      .subscribe((skills: ISkill[]) => (this.skillsSharedCollection = skills));
  }
}
