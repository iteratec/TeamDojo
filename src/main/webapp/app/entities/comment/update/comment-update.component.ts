import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import SharedModule from 'app/shared/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { ITeam } from 'app/entities/team/team.model';
import { TeamService } from 'app/entities/team/service/team.service';
import { ISkill } from 'app/entities/skill/skill.model';
import { SkillService } from 'app/entities/skill/service/skill.service';
import { CommentService } from '../service/comment.service';
import { IComment } from '../comment.model';
import { CommentFormService, CommentFormGroup } from './comment-form.service';
// ### Modification-Start ###
import { SKILLS_PER_PAGE, TEAMS_PER_PAGE } from '../../../config/pagination.constants';
// ### Modification-End###

@Component({
  standalone: true,
  selector: 'jhi-comment-update',
  templateUrl: './comment-update.component.html',
  imports: [SharedModule, FormsModule, ReactiveFormsModule],
})
export class CommentUpdateComponent implements OnInit {
  isSaving = false;
  comment: IComment | null = null;

  teamsSharedCollection: ITeam[] = [];
  skillsSharedCollection: ISkill[] = [];

  editForm: CommentFormGroup = this.commentFormService.createCommentFormGroup();

  constructor(
    protected commentService: CommentService,
    protected commentFormService: CommentFormService,
    protected teamService: TeamService,
    protected skillService: SkillService,
    protected activatedRoute: ActivatedRoute,
  ) {}

  compareTeam = (o1: ITeam | null, o2: ITeam | null): boolean => this.teamService.compareTeam(o1, o2);

  compareSkill = (o1: ISkill | null, o2: ISkill | null): boolean => this.skillService.compareSkill(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ comment }) => {
      this.comment = comment;
      if (comment) {
        this.updateForm(comment);
      }

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const comment = this.commentFormService.getComment(this.editForm);
    if (comment.id !== null) {
      this.subscribeToSaveResponse(this.commentService.update(comment));
    } else {
      this.subscribeToSaveResponse(this.commentService.create(comment));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IComment>>): void {
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

  protected updateForm(comment: IComment): void {
    this.comment = comment;
    this.commentFormService.resetForm(this.editForm, comment);

    this.teamsSharedCollection = this.teamService.addTeamToCollectionIfMissing<ITeam>(this.teamsSharedCollection, comment.team);
    this.skillsSharedCollection = this.skillService.addSkillToCollectionIfMissing<ISkill>(this.skillsSharedCollection, comment.skill);
  }

  protected loadRelationshipsOptions(): void {
    this.teamService
      // ### Modification-Start ###
      .query({ page: 0, size: TEAMS_PER_PAGE })
      // ### Modification-End###
      .pipe(map((res: HttpResponse<ITeam[]>) => res.body ?? []))
      .pipe(map((teams: ITeam[]) => this.teamService.addTeamToCollectionIfMissing<ITeam>(teams, this.comment?.team)))
      .subscribe((teams: ITeam[]) => (this.teamsSharedCollection = teams));

    this.skillService
      // ### Modification-Start ###
      .query({ page: 0, size: SKILLS_PER_PAGE })
      // ### Modification-End###
      .pipe(map((res: HttpResponse<ISkill[]>) => res.body ?? []))
      .pipe(map((skills: ISkill[]) => this.skillService.addSkillToCollectionIfMissing<ISkill>(skills, this.comment?.skill)))
      .subscribe((skills: ISkill[]) => (this.skillsSharedCollection = skills));
  }
}
