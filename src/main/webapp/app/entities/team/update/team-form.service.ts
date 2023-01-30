import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import dayjs from 'dayjs/esm';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';
import { ITeam, NewTeam } from '../team.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts ITeam for edit and NewTeamFormGroupInput for create.
 */
type TeamFormGroupInput = ITeam | PartialWithRequiredKeyOf<NewTeam>;

/**
 * Type that converts some properties for forms.
 */
type FormValueOf<T extends ITeam | NewTeam> = Omit<T, 'expirationDate' | 'createdAt' | 'updatedAt'> & {
  expirationDate?: string | null;
  createdAt?: string | null;
  updatedAt?: string | null;
};

type TeamFormRawValue = FormValueOf<ITeam>;

type NewTeamFormRawValue = FormValueOf<NewTeam>;

type TeamFormDefaults = Pick<NewTeam, 'id' | 'expirationDate' | 'official' | 'createdAt' | 'updatedAt' | 'participations'>;

type TeamFormGroupContent = {
  id: FormControl<TeamFormRawValue['id'] | NewTeam['id']>;
  title: FormControl<TeamFormRawValue['title']>;
  shortTitle: FormControl<TeamFormRawValue['shortTitle']>;
  slogan: FormControl<TeamFormRawValue['slogan']>;
  contact: FormControl<TeamFormRawValue['contact']>;
  expirationDate: FormControl<TeamFormRawValue['expirationDate']>;
  official: FormControl<TeamFormRawValue['official']>;
  createdAt: FormControl<TeamFormRawValue['createdAt']>;
  updatedAt: FormControl<TeamFormRawValue['updatedAt']>;
  image: FormControl<TeamFormRawValue['image']>;
  participations: FormControl<TeamFormRawValue['participations']>;
  group: FormControl<TeamFormRawValue['group']>;
};

export type TeamFormGroup = FormGroup<TeamFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class TeamFormService {
  createTeamFormGroup(team: TeamFormGroupInput = { id: null }): TeamFormGroup {
    const teamRawValue = this.convertTeamToTeamRawValue({
      ...this.getFormDefaults(),
      ...team,
    });
    return new FormGroup<TeamFormGroupContent>({
      id: new FormControl(
        { value: teamRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      title: new FormControl(teamRawValue.title, {
        validators: [Validators.required, Validators.minLength(2), Validators.maxLength(50)],
      }),
      shortTitle: new FormControl(teamRawValue.shortTitle, {
        validators: [Validators.required, Validators.minLength(2), Validators.maxLength(20), Validators.pattern('^[a-zA-Z0-9_-]*$')],
      }),
      slogan: new FormControl(teamRawValue.slogan, {
        validators: [Validators.maxLength(255)],
      }),
      contact: new FormControl(teamRawValue.contact, {
        validators: [Validators.maxLength(255)],
      }),
      expirationDate: new FormControl(teamRawValue.expirationDate),
      official: new FormControl(teamRawValue.official, {
        validators: [Validators.required],
      }),
      createdAt: new FormControl(teamRawValue.createdAt, {
        validators: [Validators.required],
      }),
      updatedAt: new FormControl(teamRawValue.updatedAt, {
        validators: [Validators.required],
      }),
      image: new FormControl(teamRawValue.image),
      participations: new FormControl(teamRawValue.participations ?? []),
      group: new FormControl(teamRawValue.group, {
        validators: [Validators.required],
      }),
    });
  }

  getTeam(form: TeamFormGroup): ITeam | NewTeam {
    return this.convertTeamRawValueToTeam(form.getRawValue() as TeamFormRawValue | NewTeamFormRawValue);
  }

  resetForm(form: TeamFormGroup, team: TeamFormGroupInput): void {
    const teamRawValue = this.convertTeamToTeamRawValue({ ...this.getFormDefaults(), ...team });
    form.reset(
      {
        ...teamRawValue,
        id: { value: teamRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): TeamFormDefaults {
    const currentTime = dayjs();

    return {
      id: null,
      expirationDate: currentTime,
      official: false,
      createdAt: currentTime,
      updatedAt: currentTime,
      participations: [],
    };
  }

  private convertTeamRawValueToTeam(rawTeam: TeamFormRawValue | NewTeamFormRawValue): ITeam | NewTeam {
    return {
      ...rawTeam,
      expirationDate: dayjs(rawTeam.expirationDate, DATE_TIME_FORMAT),
      createdAt: dayjs(rawTeam.createdAt, DATE_TIME_FORMAT),
      updatedAt: dayjs(rawTeam.updatedAt, DATE_TIME_FORMAT),
    };
  }

  private convertTeamToTeamRawValue(
    team: ITeam | (Partial<NewTeam> & TeamFormDefaults)
  ): TeamFormRawValue | PartialWithRequiredKeyOf<NewTeamFormRawValue> {
    return {
      ...team,
      expirationDate: team.expirationDate ? team.expirationDate.format(DATE_TIME_FORMAT) : undefined,
      createdAt: team.createdAt ? team.createdAt.format(DATE_TIME_FORMAT) : undefined,
      updatedAt: team.updatedAt ? team.updatedAt.format(DATE_TIME_FORMAT) : undefined,
      participations: team.participations ?? [],
    };
  }
}
