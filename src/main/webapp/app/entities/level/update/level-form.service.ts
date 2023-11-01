import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import dayjs from 'dayjs/esm';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';
import { ILevel, NewLevel } from '../level.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts ILevel for edit and NewLevelFormGroupInput for create.
 */
type LevelFormGroupInput = ILevel | PartialWithRequiredKeyOf<NewLevel>;

/**
 * Type that converts some properties for forms.
 */
type FormValueOf<T extends ILevel | NewLevel> = Omit<T, 'createdAt' | 'updatedAt'> & {
  createdAt?: string | null;
  updatedAt?: string | null;
};

type LevelFormRawValue = FormValueOf<ILevel>;

type NewLevelFormRawValue = FormValueOf<NewLevel>;

type LevelFormDefaults = Pick<NewLevel, 'id' | 'createdAt' | 'updatedAt'>;

type LevelFormGroupContent = {
  id: FormControl<LevelFormRawValue['id'] | NewLevel['id']>;
  titleEN: FormControl<LevelFormRawValue['titleEN']>;
  titleDE: FormControl<LevelFormRawValue['titleDE']>;
  descriptionEN: FormControl<LevelFormRawValue['descriptionEN']>;
  descriptionDE: FormControl<LevelFormRawValue['descriptionDE']>;
  requiredScore: FormControl<LevelFormRawValue['requiredScore']>;
  instantMultiplier: FormControl<LevelFormRawValue['instantMultiplier']>;
  completionBonus: FormControl<LevelFormRawValue['completionBonus']>;
  createdAt: FormControl<LevelFormRawValue['createdAt']>;
  updatedAt: FormControl<LevelFormRawValue['updatedAt']>;
  dependsOn: FormControl<LevelFormRawValue['dependsOn']>;
  image: FormControl<LevelFormRawValue['image']>;
  dimension: FormControl<LevelFormRawValue['dimension']>;
};

export type LevelFormGroup = FormGroup<LevelFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class LevelFormService {
  createLevelFormGroup(level: LevelFormGroupInput = { id: null }): LevelFormGroup {
    const levelRawValue = this.convertLevelToLevelRawValue({
      ...this.getFormDefaults(),
      ...level,
    });
    return new FormGroup<LevelFormGroupContent>({
      id: new FormControl(
        { value: levelRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        },
      ),
      titleEN: new FormControl(levelRawValue.titleEN, {
        validators: [Validators.required, Validators.minLength(3), Validators.maxLength(50)],
      }),
      titleDE: new FormControl(levelRawValue.titleDE, {
        validators: [Validators.minLength(3), Validators.maxLength(50)],
      }),
      descriptionEN: new FormControl(levelRawValue.descriptionEN, {
        validators: [Validators.maxLength(4096)],
      }),
      descriptionDE: new FormControl(levelRawValue.descriptionDE, {
        validators: [Validators.maxLength(4096)],
      }),
      requiredScore: new FormControl(levelRawValue.requiredScore, {
        validators: [Validators.required, Validators.min(0), Validators.max(1)],
      }),
      instantMultiplier: new FormControl(levelRawValue.instantMultiplier, {
        validators: [Validators.required, Validators.min(0)],
      }),
      completionBonus: new FormControl(levelRawValue.completionBonus, {
        validators: [Validators.min(0)],
      }),
      createdAt: new FormControl(levelRawValue.createdAt, {
        validators: [Validators.required],
      }),
      updatedAt: new FormControl(levelRawValue.updatedAt, {
        validators: [Validators.required],
      }),
      dependsOn: new FormControl(levelRawValue.dependsOn),
      image: new FormControl(levelRawValue.image),
      dimension: new FormControl(levelRawValue.dimension, {
        validators: [Validators.required],
      }),
    });
  }

  getLevel(form: LevelFormGroup): ILevel | NewLevel {
    return this.convertLevelRawValueToLevel(form.getRawValue() as LevelFormRawValue | NewLevelFormRawValue);
  }

  resetForm(form: LevelFormGroup, level: LevelFormGroupInput): void {
    const levelRawValue = this.convertLevelToLevelRawValue({ ...this.getFormDefaults(), ...level });
    form.reset(
      {
        ...levelRawValue,
        id: { value: levelRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */,
    );
  }

  private getFormDefaults(): LevelFormDefaults {
    const currentTime = dayjs();

    return {
      id: null,
      createdAt: currentTime,
      updatedAt: currentTime,
    };
  }

  private convertLevelRawValueToLevel(rawLevel: LevelFormRawValue | NewLevelFormRawValue): ILevel | NewLevel {
    return {
      ...rawLevel,
      createdAt: dayjs(rawLevel.createdAt, DATE_TIME_FORMAT),
      updatedAt: dayjs(rawLevel.updatedAt, DATE_TIME_FORMAT),
    };
  }

  private convertLevelToLevelRawValue(
    level: ILevel | (Partial<NewLevel> & LevelFormDefaults),
  ): LevelFormRawValue | PartialWithRequiredKeyOf<NewLevelFormRawValue> {
    return {
      ...level,
      createdAt: level.createdAt ? level.createdAt.format(DATE_TIME_FORMAT) : undefined,
      updatedAt: level.updatedAt ? level.updatedAt.format(DATE_TIME_FORMAT) : undefined,
    };
  }
}
