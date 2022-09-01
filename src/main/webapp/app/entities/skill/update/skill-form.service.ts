import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import dayjs from 'dayjs/esm';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';
import { ISkill, NewSkill } from '../skill.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts ISkill for edit and NewSkillFormGroupInput for create.
 */
type SkillFormGroupInput = ISkill | PartialWithRequiredKeyOf<NewSkill>;

/**
 * Type that converts some properties for forms.
 */
type FormValueOf<T extends ISkill | NewSkill> = Omit<T, 'createdAt' | 'updatedAt'> & {
  createdAt?: string | null;
  updatedAt?: string | null;
};

type SkillFormRawValue = FormValueOf<ISkill>;

type NewSkillFormRawValue = FormValueOf<NewSkill>;

type SkillFormDefaults = Pick<NewSkill, 'id' | 'createdAt' | 'updatedAt' | 'trainings'>;

type SkillFormGroupContent = {
  id: FormControl<SkillFormRawValue['id'] | NewSkill['id']>;
  titleEN: FormControl<SkillFormRawValue['titleEN']>;
  titleDE: FormControl<SkillFormRawValue['titleDE']>;
  descriptionEN: FormControl<SkillFormRawValue['descriptionEN']>;
  descriptionDE: FormControl<SkillFormRawValue['descriptionDE']>;
  implementationEN: FormControl<SkillFormRawValue['implementationEN']>;
  implementationDE: FormControl<SkillFormRawValue['implementationDE']>;
  validationEN: FormControl<SkillFormRawValue['validationEN']>;
  validationDE: FormControl<SkillFormRawValue['validationDE']>;
  expiryPeriod: FormControl<SkillFormRawValue['expiryPeriod']>;
  contact: FormControl<SkillFormRawValue['contact']>;
  score: FormControl<SkillFormRawValue['score']>;
  rateScore: FormControl<SkillFormRawValue['rateScore']>;
  rateCount: FormControl<SkillFormRawValue['rateCount']>;
  createdAt: FormControl<SkillFormRawValue['createdAt']>;
  updatedAt: FormControl<SkillFormRawValue['updatedAt']>;
  trainings: FormControl<SkillFormRawValue['trainings']>;
};

export type SkillFormGroup = FormGroup<SkillFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class SkillFormService {
  createSkillFormGroup(skill: SkillFormGroupInput = { id: null }): SkillFormGroup {
    const skillRawValue = this.convertSkillToSkillRawValue({
      ...this.getFormDefaults(),
      ...skill,
    });
    return new FormGroup<SkillFormGroupContent>({
      id: new FormControl(
        { value: skillRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      titleEN: new FormControl(skillRawValue.titleEN, {
        validators: [Validators.required, Validators.minLength(5), Validators.maxLength(80)],
      }),
      titleDE: new FormControl(skillRawValue.titleDE, {
        validators: [Validators.minLength(5), Validators.maxLength(80)],
      }),
      descriptionEN: new FormControl(skillRawValue.descriptionEN, {
        validators: [Validators.maxLength(4096)],
      }),
      descriptionDE: new FormControl(skillRawValue.descriptionDE, {
        validators: [Validators.maxLength(4096)],
      }),
      implementationEN: new FormControl(skillRawValue.implementationEN, {
        validators: [Validators.maxLength(4096)],
      }),
      implementationDE: new FormControl(skillRawValue.implementationDE, {
        validators: [Validators.maxLength(4096)],
      }),
      validationEN: new FormControl(skillRawValue.validationEN, {
        validators: [Validators.maxLength(4096)],
      }),
      validationDE: new FormControl(skillRawValue.validationDE, {
        validators: [Validators.maxLength(4096)],
      }),
      expiryPeriod: new FormControl(skillRawValue.expiryPeriod, {
        validators: [Validators.min(1)],
      }),
      contact: new FormControl(skillRawValue.contact, {
        validators: [Validators.maxLength(255)],
      }),
      score: new FormControl(skillRawValue.score, {
        validators: [Validators.required, Validators.min(0)],
      }),
      rateScore: new FormControl(skillRawValue.rateScore, {
        validators: [Validators.min(0), Validators.max(5)],
      }),
      rateCount: new FormControl(skillRawValue.rateCount, {
        validators: [Validators.required, Validators.min(0)],
      }),
      createdAt: new FormControl(skillRawValue.createdAt, {
        validators: [Validators.required],
      }),
      updatedAt: new FormControl(skillRawValue.updatedAt, {
        validators: [Validators.required],
      }),
      trainings: new FormControl(skillRawValue.trainings ?? []),
    });
  }

  getSkill(form: SkillFormGroup): ISkill | NewSkill {
    return this.convertSkillRawValueToSkill(form.getRawValue() as SkillFormRawValue | NewSkillFormRawValue);
  }

  resetForm(form: SkillFormGroup, skill: SkillFormGroupInput): void {
    const skillRawValue = this.convertSkillToSkillRawValue({ ...this.getFormDefaults(), ...skill });
    form.reset(
      {
        ...skillRawValue,
        id: { value: skillRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): SkillFormDefaults {
    const currentTime = dayjs();

    return {
      id: null,
      createdAt: currentTime,
      updatedAt: currentTime,
      trainings: [],
    };
  }

  private convertSkillRawValueToSkill(rawSkill: SkillFormRawValue | NewSkillFormRawValue): ISkill | NewSkill {
    return {
      ...rawSkill,
      createdAt: dayjs(rawSkill.createdAt, DATE_TIME_FORMAT),
      updatedAt: dayjs(rawSkill.updatedAt, DATE_TIME_FORMAT),
    };
  }

  private convertSkillToSkillRawValue(
    skill: ISkill | (Partial<NewSkill> & SkillFormDefaults)
  ): SkillFormRawValue | PartialWithRequiredKeyOf<NewSkillFormRawValue> {
    return {
      ...skill,
      createdAt: skill.createdAt ? skill.createdAt.format(DATE_TIME_FORMAT) : undefined,
      updatedAt: skill.updatedAt ? skill.updatedAt.format(DATE_TIME_FORMAT) : undefined,
      trainings: skill.trainings ?? [],
    };
  }
}
