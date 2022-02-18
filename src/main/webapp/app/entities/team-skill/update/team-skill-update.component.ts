import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import dayjs from 'dayjs/esm';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';

import { ITeamSkill, TeamSkill } from '../team-skill.model';
import { TeamSkillService } from '../service/team-skill.service';
import { ISkill } from 'app/entities/skill/skill.model';
import { SkillService } from 'app/entities/skill/service/skill.service';
import { ITeam } from 'app/entities/team/team.model';
import { TeamService } from 'app/entities/team/service/team.service';
import { SkillStatus } from 'app/entities/enumerations/skill-status.model';

@Component({
  selector: 'jhi-team-skill-update',
  templateUrl: './team-skill-update.component.html',
})
export class TeamSkillUpdateComponent implements OnInit {
  isSaving = false;
  skillStatusValues = Object.keys(SkillStatus);

  skillsSharedCollection: ISkill[] = [];
  teamsSharedCollection: ITeam[] = [];

  editForm = this.fb.group({
    id: [],
    completedAt: [],
    verifiedAt: [],
    irrelevant: [],
    skillStatus: [null, [Validators.required]],
    note: [null, [Validators.maxLength(4096)]],
    vote: [null, [Validators.required]],
    voters: [null, [Validators.maxLength(255)]],
    createdAt: [null, [Validators.required]],
    updatedAt: [null, [Validators.required]],
    skill: [null, Validators.required],
    team: [null, Validators.required],
  });

  constructor(
    protected teamSkillService: TeamSkillService,
    protected skillService: SkillService,
    protected teamService: TeamService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ teamSkill }) => {
      if (teamSkill.id === undefined) {
        const today = dayjs().startOf('day');
        teamSkill.completedAt = today;
        teamSkill.verifiedAt = today;
        teamSkill.createdAt = today;
        teamSkill.updatedAt = today;
      }

      this.updateForm(teamSkill);

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const teamSkill = this.createFromForm();
    if (teamSkill.id !== undefined) {
      this.subscribeToSaveResponse(this.teamSkillService.update(teamSkill));
    } else {
      this.subscribeToSaveResponse(this.teamSkillService.create(teamSkill));
    }
  }

  trackSkillById(index: number, item: ISkill): number {
    return item.id!;
  }

  trackTeamById(index: number, item: ITeam): number {
    return item.id!;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ITeamSkill>>): void {
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

  protected updateForm(teamSkill: ITeamSkill): void {
    this.editForm.patchValue({
      id: teamSkill.id,
      completedAt: teamSkill.completedAt ? teamSkill.completedAt.format(DATE_TIME_FORMAT) : null,
      verifiedAt: teamSkill.verifiedAt ? teamSkill.verifiedAt.format(DATE_TIME_FORMAT) : null,
      irrelevant: teamSkill.irrelevant,
      skillStatus: teamSkill.skillStatus,
      note: teamSkill.note,
      vote: teamSkill.vote,
      voters: teamSkill.voters,
      createdAt: teamSkill.createdAt ? teamSkill.createdAt.format(DATE_TIME_FORMAT) : null,
      updatedAt: teamSkill.updatedAt ? teamSkill.updatedAt.format(DATE_TIME_FORMAT) : null,
      skill: teamSkill.skill,
      team: teamSkill.team,
    });

    this.skillsSharedCollection = this.skillService.addSkillToCollectionIfMissing(this.skillsSharedCollection, teamSkill.skill);
    this.teamsSharedCollection = this.teamService.addTeamToCollectionIfMissing(this.teamsSharedCollection, teamSkill.team);
  }

  protected loadRelationshipsOptions(): void {
    this.skillService
      .query()
      .pipe(map((res: HttpResponse<ISkill[]>) => res.body ?? []))
      .pipe(map((skills: ISkill[]) => this.skillService.addSkillToCollectionIfMissing(skills, this.editForm.get('skill')!.value)))
      .subscribe((skills: ISkill[]) => (this.skillsSharedCollection = skills));

    this.teamService
      .query()
      .pipe(map((res: HttpResponse<ITeam[]>) => res.body ?? []))
      .pipe(map((teams: ITeam[]) => this.teamService.addTeamToCollectionIfMissing(teams, this.editForm.get('team')!.value)))
      .subscribe((teams: ITeam[]) => (this.teamsSharedCollection = teams));
  }

  protected createFromForm(): ITeamSkill {
    return {
      ...new TeamSkill(),
      id: this.editForm.get(['id'])!.value,
      completedAt: this.editForm.get(['completedAt'])!.value
        ? dayjs(this.editForm.get(['completedAt'])!.value, DATE_TIME_FORMAT)
        : undefined,
      verifiedAt: this.editForm.get(['verifiedAt'])!.value ? dayjs(this.editForm.get(['verifiedAt'])!.value, DATE_TIME_FORMAT) : undefined,
      irrelevant: this.editForm.get(['irrelevant'])!.value,
      skillStatus: this.editForm.get(['skillStatus'])!.value,
      note: this.editForm.get(['note'])!.value,
      vote: this.editForm.get(['vote'])!.value,
      voters: this.editForm.get(['voters'])!.value,
      createdAt: this.editForm.get(['createdAt'])!.value ? dayjs(this.editForm.get(['createdAt'])!.value, DATE_TIME_FORMAT) : undefined,
      updatedAt: this.editForm.get(['updatedAt'])!.value ? dayjs(this.editForm.get(['updatedAt'])!.value, DATE_TIME_FORMAT) : undefined,
      skill: this.editForm.get(['skill'])!.value,
      team: this.editForm.get(['team'])!.value,
    };
  }
}
