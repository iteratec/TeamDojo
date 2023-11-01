import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import SharedModule from 'app/shared/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { ISkill } from 'app/entities/skill/skill.model';
import { SkillService } from 'app/entities/skill/service/skill.service';
import { ITeam } from 'app/entities/team/team.model';
import { TeamService } from 'app/entities/team/service/team.service';
import { SkillStatus } from 'app/entities/enumerations/skill-status.model';
import { TeamSkillService } from '../service/team-skill.service';
import { ITeamSkill } from '../team-skill.model';
import { TeamSkillFormService, TeamSkillFormGroup } from './team-skill-form.service';
// ### Modification-Start ###
import { SKILLS_PER_PAGE, TEAMS_PER_PAGE } from '../../../config/pagination.constants';
// ### Modification-End ###


@Component({
  standalone: true,
  selector: 'jhi-team-skill-update',
  templateUrl: './team-skill-update.component.html',
  imports: [SharedModule, FormsModule, ReactiveFormsModule],
})
export class TeamSkillUpdateComponent implements OnInit {
  isSaving = false;
  teamSkill: ITeamSkill | null = null;
  skillStatusValues = Object.keys(SkillStatus);

  skillsSharedCollection: ISkill[] = [];
  teamsSharedCollection: ITeam[] = [];

  editForm: TeamSkillFormGroup = this.teamSkillFormService.createTeamSkillFormGroup();

  constructor(
    protected teamSkillService: TeamSkillService,
    protected teamSkillFormService: TeamSkillFormService,
    protected skillService: SkillService,
    protected teamService: TeamService,
    protected activatedRoute: ActivatedRoute,
  ) {}

  compareSkill = (o1: ISkill | null, o2: ISkill | null): boolean => this.skillService.compareSkill(o1, o2);

  compareTeam = (o1: ITeam | null, o2: ITeam | null): boolean => this.teamService.compareTeam(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ teamSkill }) => {
      this.teamSkill = teamSkill;
      if (teamSkill) {
        this.updateForm(teamSkill);
      }

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const teamSkill = this.teamSkillFormService.getTeamSkill(this.editForm);
    if (teamSkill.id !== null) {
      this.subscribeToSaveResponse(this.teamSkillService.update(teamSkill));
    } else {
      this.subscribeToSaveResponse(this.teamSkillService.create(teamSkill));
    }
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
    this.teamSkill = teamSkill;
    this.teamSkillFormService.resetForm(this.editForm, teamSkill);

    this.skillsSharedCollection = this.skillService.addSkillToCollectionIfMissing<ISkill>(this.skillsSharedCollection, teamSkill.skill);
    this.teamsSharedCollection = this.teamService.addTeamToCollectionIfMissing<ITeam>(this.teamsSharedCollection, teamSkill.team);
  }

  protected loadRelationshipsOptions(): void {
    this.skillService
      // ### Modification-Start ###
      .query({ page: 0, size: SKILLS_PER_PAGE })
      // ### Modification-End ###
      .pipe(map((res: HttpResponse<ISkill[]>) => res.body ?? []))
      .pipe(map((skills: ISkill[]) => this.skillService.addSkillToCollectionIfMissing<ISkill>(skills, this.teamSkill?.skill)))
      .subscribe((skills: ISkill[]) => (this.skillsSharedCollection = skills));

    this.teamService
      // ### Modification-Start ###
      .query({ page: 0, size: TEAMS_PER_PAGE })
      // ### Modification-End ###
      .pipe(map((res: HttpResponse<ITeam[]>) => res.body ?? []))
      .pipe(map((teams: ITeam[]) => this.teamService.addTeamToCollectionIfMissing<ITeam>(teams, this.teamSkill?.team)))
      .subscribe((teams: ITeam[]) => (this.teamsSharedCollection = teams));
  }
}
