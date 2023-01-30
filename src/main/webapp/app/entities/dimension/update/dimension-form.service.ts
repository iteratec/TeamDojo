import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import dayjs from 'dayjs/esm';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';
import { IDimension, NewDimension } from '../dimension.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IDimension for edit and NewDimensionFormGroupInput for create.
 */
type DimensionFormGroupInput = IDimension | PartialWithRequiredKeyOf<NewDimension>;

/**
 * Type that converts some properties for forms.
 */
type FormValueOf<T extends IDimension | NewDimension> = Omit<T, 'createdAt' | 'updatedAt'> & {
  createdAt?: string | null;
  updatedAt?: string | null;
};

type DimensionFormRawValue = FormValueOf<IDimension>;

type NewDimensionFormRawValue = FormValueOf<NewDimension>;

type DimensionFormDefaults = Pick<NewDimension, 'id' | 'createdAt' | 'updatedAt' | 'badges' | 'participants'>;

type DimensionFormGroupContent = {
  id: FormControl<DimensionFormRawValue['id'] | NewDimension['id']>;
  titleEN: FormControl<DimensionFormRawValue['titleEN']>;
  titleDE: FormControl<DimensionFormRawValue['titleDE']>;
  descriptionEN: FormControl<DimensionFormRawValue['descriptionEN']>;
  descriptionDE: FormControl<DimensionFormRawValue['descriptionDE']>;
  createdAt: FormControl<DimensionFormRawValue['createdAt']>;
  updatedAt: FormControl<DimensionFormRawValue['updatedAt']>;
  badges: FormControl<DimensionFormRawValue['badges']>;
  participants: FormControl<DimensionFormRawValue['participants']>;
};

export type DimensionFormGroup = FormGroup<DimensionFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class DimensionFormService {
  createDimensionFormGroup(dimension: DimensionFormGroupInput = { id: null }): DimensionFormGroup {
    const dimensionRawValue = this.convertDimensionToDimensionRawValue({
      ...this.getFormDefaults(),
      ...dimension,
    });
    return new FormGroup<DimensionFormGroupContent>({
      id: new FormControl(
        { value: dimensionRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      titleEN: new FormControl(dimensionRawValue.titleEN, {
        validators: [Validators.required, Validators.minLength(1), Validators.maxLength(50)],
      }),
      titleDE: new FormControl(dimensionRawValue.titleDE, {
        validators: [Validators.minLength(1), Validators.maxLength(50)],
      }),
      descriptionEN: new FormControl(dimensionRawValue.descriptionEN, {
        validators: [Validators.maxLength(4096)],
      }),
      descriptionDE: new FormControl(dimensionRawValue.descriptionDE, {
        validators: [Validators.maxLength(4096)],
      }),
      createdAt: new FormControl(dimensionRawValue.createdAt, {
        validators: [Validators.required],
      }),
      updatedAt: new FormControl(dimensionRawValue.updatedAt, {
        validators: [Validators.required],
      }),
      badges: new FormControl(dimensionRawValue.badges ?? []),
      participants: new FormControl(dimensionRawValue.participants ?? []),
    });
  }

  getDimension(form: DimensionFormGroup): IDimension | NewDimension {
    return this.convertDimensionRawValueToDimension(form.getRawValue() as DimensionFormRawValue | NewDimensionFormRawValue);
  }

  resetForm(form: DimensionFormGroup, dimension: DimensionFormGroupInput): void {
    const dimensionRawValue = this.convertDimensionToDimensionRawValue({ ...this.getFormDefaults(), ...dimension });
    form.reset(
      {
        ...dimensionRawValue,
        id: { value: dimensionRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): DimensionFormDefaults {
    const currentTime = dayjs();

    return {
      id: null,
      createdAt: currentTime,
      updatedAt: currentTime,
      badges: [],
      participants: [],
    };
  }

  private convertDimensionRawValueToDimension(rawDimension: DimensionFormRawValue | NewDimensionFormRawValue): IDimension | NewDimension {
    return {
      ...rawDimension,
      createdAt: dayjs(rawDimension.createdAt, DATE_TIME_FORMAT),
      updatedAt: dayjs(rawDimension.updatedAt, DATE_TIME_FORMAT),
    };
  }

  private convertDimensionToDimensionRawValue(
    dimension: IDimension | (Partial<NewDimension> & DimensionFormDefaults)
  ): DimensionFormRawValue | PartialWithRequiredKeyOf<NewDimensionFormRawValue> {
    return {
      ...dimension,
      createdAt: dimension.createdAt ? dimension.createdAt.format(DATE_TIME_FORMAT) : undefined,
      updatedAt: dimension.updatedAt ? dimension.updatedAt.format(DATE_TIME_FORMAT) : undefined,
      badges: dimension.badges ?? [],
      participants: dimension.participants ?? [],
    };
  }
}
