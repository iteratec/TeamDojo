import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import dayjs from 'dayjs/esm';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';
import { ITeamSkill, NewTeamSkill } from '../team-skill.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts ITeamSkill for edit and NewTeamSkillFormGroupInput for create.
 */
type TeamSkillFormGroupInput = ITeamSkill | PartialWithRequiredKeyOf<NewTeamSkill>;

/**
 * Type that converts some properties for forms.
 */
type FormValueOf<T extends ITeamSkill | NewTeamSkill> = Omit<T, 'completedAt' | 'verifiedAt' | 'createdAt' | 'updatedAt'> & {
  completedAt?: string | null;
  verifiedAt?: string | null;
  createdAt?: string | null;
  updatedAt?: string | null;
};

type TeamSkillFormRawValue = FormValueOf<ITeamSkill>;

type NewTeamSkillFormRawValue = FormValueOf<NewTeamSkill>;

type TeamSkillFormDefaults = Pick<NewTeamSkill, 'id' | 'completedAt' | 'verifiedAt' | 'irrelevant' | 'createdAt' | 'updatedAt'>;

type TeamSkillFormGroupContent = {
  id: FormControl<TeamSkillFormRawValue['id'] | NewTeamSkill['id']>;
  completedAt: FormControl<TeamSkillFormRawValue['completedAt']>;
  verifiedAt: FormControl<TeamSkillFormRawValue['verifiedAt']>;
  irrelevant: FormControl<TeamSkillFormRawValue['irrelevant']>;
  skillStatus: FormControl<TeamSkillFormRawValue['skillStatus']>;
  note: FormControl<TeamSkillFormRawValue['note']>;
  vote: FormControl<TeamSkillFormRawValue['vote']>;
  voters: FormControl<TeamSkillFormRawValue['voters']>;
  createdAt: FormControl<TeamSkillFormRawValue['createdAt']>;
  updatedAt: FormControl<TeamSkillFormRawValue['updatedAt']>;
  skill: FormControl<TeamSkillFormRawValue['skill']>;
  team: FormControl<TeamSkillFormRawValue['team']>;
};

export type TeamSkillFormGroup = FormGroup<TeamSkillFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class TeamSkillFormService {
  createTeamSkillFormGroup(teamSkill: TeamSkillFormGroupInput = { id: null }): TeamSkillFormGroup {
    const teamSkillRawValue = this.convertTeamSkillToTeamSkillRawValue({
      ...this.getFormDefaults(),
      ...teamSkill,
    });
    return new FormGroup<TeamSkillFormGroupContent>({
      id: new FormControl(
        { value: teamSkillRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        },
      ),
      completedAt: new FormControl(teamSkillRawValue.completedAt),
      verifiedAt: new FormControl(teamSkillRawValue.verifiedAt),
      irrelevant: new FormControl(teamSkillRawValue.irrelevant),
      skillStatus: new FormControl(teamSkillRawValue.skillStatus, {
        validators: [Validators.required],
      }),
      note: new FormControl(teamSkillRawValue.note, {
        validators: [Validators.maxLength(4096)],
      }),
      vote: new FormControl(teamSkillRawValue.vote, {
        validators: [Validators.required],
      }),
      voters: new FormControl(teamSkillRawValue.voters, {
        validators: [Validators.maxLength(255)],
      }),
      createdAt: new FormControl(teamSkillRawValue.createdAt, {
        validators: [Validators.required],
      }),
      updatedAt: new FormControl(teamSkillRawValue.updatedAt, {
        validators: [Validators.required],
      }),
      skill: new FormControl(teamSkillRawValue.skill, {
        validators: [Validators.required],
      }),
      team: new FormControl(teamSkillRawValue.team, {
        validators: [Validators.required],
      }),
    });
  }

  getTeamSkill(form: TeamSkillFormGroup): ITeamSkill | NewTeamSkill {
    return this.convertTeamSkillRawValueToTeamSkill(form.getRawValue() as TeamSkillFormRawValue | NewTeamSkillFormRawValue);
  }

  resetForm(form: TeamSkillFormGroup, teamSkill: TeamSkillFormGroupInput): void {
    const teamSkillRawValue = this.convertTeamSkillToTeamSkillRawValue({ ...this.getFormDefaults(), ...teamSkill });
    form.reset(
      {
        ...teamSkillRawValue,
        id: { value: teamSkillRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */,
    );
  }

  private getFormDefaults(): TeamSkillFormDefaults {
    const currentTime = dayjs();

    return {
      id: null,
      completedAt: currentTime,
      verifiedAt: currentTime,
      irrelevant: false,
      createdAt: currentTime,
      updatedAt: currentTime,
    };
  }

  private convertTeamSkillRawValueToTeamSkill(rawTeamSkill: TeamSkillFormRawValue | NewTeamSkillFormRawValue): ITeamSkill | NewTeamSkill {
    return {
      ...rawTeamSkill,
      completedAt: dayjs(rawTeamSkill.completedAt, DATE_TIME_FORMAT),
      verifiedAt: dayjs(rawTeamSkill.verifiedAt, DATE_TIME_FORMAT),
      createdAt: dayjs(rawTeamSkill.createdAt, DATE_TIME_FORMAT),
      updatedAt: dayjs(rawTeamSkill.updatedAt, DATE_TIME_FORMAT),
    };
  }

  private convertTeamSkillToTeamSkillRawValue(
    teamSkill: ITeamSkill | (Partial<NewTeamSkill> & TeamSkillFormDefaults),
  ): TeamSkillFormRawValue | PartialWithRequiredKeyOf<NewTeamSkillFormRawValue> {
    return {
      ...teamSkill,
      completedAt: teamSkill.completedAt ? teamSkill.completedAt.format(DATE_TIME_FORMAT) : undefined,
      verifiedAt: teamSkill.verifiedAt ? teamSkill.verifiedAt.format(DATE_TIME_FORMAT) : undefined,
      createdAt: teamSkill.createdAt ? teamSkill.createdAt.format(DATE_TIME_FORMAT) : undefined,
      updatedAt: teamSkill.updatedAt ? teamSkill.updatedAt.format(DATE_TIME_FORMAT) : undefined,
    };
  }
}
