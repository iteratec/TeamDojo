import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import dayjs from 'dayjs/esm';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';
import { ITeamGroup, NewTeamGroup } from '../team-group.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts ITeamGroup for edit and NewTeamGroupFormGroupInput for create.
 */
type TeamGroupFormGroupInput = ITeamGroup | PartialWithRequiredKeyOf<NewTeamGroup>;

/**
 * Type that converts some properties for forms.
 */
type FormValueOf<T extends ITeamGroup | NewTeamGroup> = Omit<T, 'createdAt' | 'updatedAt'> & {
  createdAt?: string | null;
  updatedAt?: string | null;
};

type TeamGroupFormRawValue = FormValueOf<ITeamGroup>;

type NewTeamGroupFormRawValue = FormValueOf<NewTeamGroup>;

type TeamGroupFormDefaults = Pick<NewTeamGroup, 'id' | 'createdAt' | 'updatedAt'>;

type TeamGroupFormGroupContent = {
  id: FormControl<TeamGroupFormRawValue['id'] | NewTeamGroup['id']>;
  title: FormControl<TeamGroupFormRawValue['title']>;
  description: FormControl<TeamGroupFormRawValue['description']>;
  createdAt: FormControl<TeamGroupFormRawValue['createdAt']>;
  updatedAt: FormControl<TeamGroupFormRawValue['updatedAt']>;
  parent: FormControl<TeamGroupFormRawValue['parent']>;
};

export type TeamGroupFormGroup = FormGroup<TeamGroupFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class TeamGroupFormService {
  createTeamGroupFormGroup(teamGroup: TeamGroupFormGroupInput = { id: null }): TeamGroupFormGroup {
    const teamGroupRawValue = this.convertTeamGroupToTeamGroupRawValue({
      ...this.getFormDefaults(),
      ...teamGroup,
    });
    return new FormGroup<TeamGroupFormGroupContent>({
      id: new FormControl(
        { value: teamGroupRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      title: new FormControl(teamGroupRawValue.title, {
        validators: [Validators.required, Validators.maxLength(80)],
      }),
      description: new FormControl(teamGroupRawValue.description, {
        validators: [Validators.maxLength(4096)],
      }),
      createdAt: new FormControl(teamGroupRawValue.createdAt, {
        validators: [Validators.required],
      }),
      updatedAt: new FormControl(teamGroupRawValue.updatedAt, {
        validators: [Validators.required],
      }),
      parent: new FormControl(teamGroupRawValue.parent),
    });
  }

  getTeamGroup(form: TeamGroupFormGroup): ITeamGroup | NewTeamGroup {
    return this.convertTeamGroupRawValueToTeamGroup(form.getRawValue() as TeamGroupFormRawValue | NewTeamGroupFormRawValue);
  }

  resetForm(form: TeamGroupFormGroup, teamGroup: TeamGroupFormGroupInput): void {
    const teamGroupRawValue = this.convertTeamGroupToTeamGroupRawValue({ ...this.getFormDefaults(), ...teamGroup });
    form.reset(
      {
        ...teamGroupRawValue,
        id: { value: teamGroupRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): TeamGroupFormDefaults {
    const currentTime = dayjs();

    return {
      id: null,
      createdAt: currentTime,
      updatedAt: currentTime,
    };
  }

  private convertTeamGroupRawValueToTeamGroup(rawTeamGroup: TeamGroupFormRawValue | NewTeamGroupFormRawValue): ITeamGroup | NewTeamGroup {
    return {
      ...rawTeamGroup,
      createdAt: dayjs(rawTeamGroup.createdAt, DATE_TIME_FORMAT),
      updatedAt: dayjs(rawTeamGroup.updatedAt, DATE_TIME_FORMAT),
    };
  }

  private convertTeamGroupToTeamGroupRawValue(
    teamGroup: ITeamGroup | (Partial<NewTeamGroup> & TeamGroupFormDefaults)
  ): TeamGroupFormRawValue | PartialWithRequiredKeyOf<NewTeamGroupFormRawValue> {
    return {
      ...teamGroup,
      createdAt: teamGroup.createdAt ? teamGroup.createdAt.format(DATE_TIME_FORMAT) : undefined,
      updatedAt: teamGroup.updatedAt ? teamGroup.updatedAt.format(DATE_TIME_FORMAT) : undefined,
    };
  }
}
