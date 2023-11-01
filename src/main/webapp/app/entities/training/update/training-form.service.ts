import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import dayjs from 'dayjs/esm';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';
import { ITraining, NewTraining } from '../training.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts ITraining for edit and NewTrainingFormGroupInput for create.
 */
type TrainingFormGroupInput = ITraining | PartialWithRequiredKeyOf<NewTraining>;

/**
 * Type that converts some properties for forms.
 */
type FormValueOf<T extends ITraining | NewTraining> = Omit<T, 'validUntil' | 'createdAt' | 'updatedAt'> & {
  validUntil?: string | null;
  createdAt?: string | null;
  updatedAt?: string | null;
};

type TrainingFormRawValue = FormValueOf<ITraining>;

type NewTrainingFormRawValue = FormValueOf<NewTraining>;

type TrainingFormDefaults = Pick<NewTraining, 'id' | 'validUntil' | 'isOfficial' | 'createdAt' | 'updatedAt' | 'skills'>;

type TrainingFormGroupContent = {
  id: FormControl<TrainingFormRawValue['id'] | NewTraining['id']>;
  titleEN: FormControl<TrainingFormRawValue['titleEN']>;
  titleDE: FormControl<TrainingFormRawValue['titleDE']>;
  descriptionEN: FormControl<TrainingFormRawValue['descriptionEN']>;
  descriptionDE: FormControl<TrainingFormRawValue['descriptionDE']>;
  contact: FormControl<TrainingFormRawValue['contact']>;
  link: FormControl<TrainingFormRawValue['link']>;
  validUntil: FormControl<TrainingFormRawValue['validUntil']>;
  isOfficial: FormControl<TrainingFormRawValue['isOfficial']>;
  suggestedBy: FormControl<TrainingFormRawValue['suggestedBy']>;
  createdAt: FormControl<TrainingFormRawValue['createdAt']>;
  updatedAt: FormControl<TrainingFormRawValue['updatedAt']>;
  skills: FormControl<TrainingFormRawValue['skills']>;
};

export type TrainingFormGroup = FormGroup<TrainingFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class TrainingFormService {
  createTrainingFormGroup(training: TrainingFormGroupInput = { id: null }): TrainingFormGroup {
    const trainingRawValue = this.convertTrainingToTrainingRawValue({
      ...this.getFormDefaults(),
      ...training,
    });
    return new FormGroup<TrainingFormGroupContent>({
      id: new FormControl(
        { value: trainingRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        },
      ),
      titleEN: new FormControl(trainingRawValue.titleEN, {
        validators: [Validators.required, Validators.maxLength(80)],
      }),
      titleDE: new FormControl(trainingRawValue.titleDE, {
        validators: [Validators.maxLength(80)],
      }),
      descriptionEN: new FormControl(trainingRawValue.descriptionEN, {
        validators: [Validators.maxLength(4096)],
      }),
      descriptionDE: new FormControl(trainingRawValue.descriptionDE, {
        validators: [Validators.maxLength(4096)],
      }),
      contact: new FormControl(trainingRawValue.contact, {
        validators: [Validators.maxLength(255)],
      }),
      link: new FormControl(trainingRawValue.link, {
        validators: [Validators.maxLength(255)],
      }),
      validUntil: new FormControl(trainingRawValue.validUntil),
      isOfficial: new FormControl(trainingRawValue.isOfficial, {
        validators: [Validators.required],
      }),
      suggestedBy: new FormControl(trainingRawValue.suggestedBy, {
        validators: [Validators.maxLength(255)],
      }),
      createdAt: new FormControl(trainingRawValue.createdAt, {
        validators: [Validators.required],
      }),
      updatedAt: new FormControl(trainingRawValue.updatedAt, {
        validators: [Validators.required],
      }),
      skills: new FormControl(trainingRawValue.skills ?? []),
    });
  }

  getTraining(form: TrainingFormGroup): ITraining | NewTraining {
    return this.convertTrainingRawValueToTraining(form.getRawValue() as TrainingFormRawValue | NewTrainingFormRawValue);
  }

  resetForm(form: TrainingFormGroup, training: TrainingFormGroupInput): void {
    const trainingRawValue = this.convertTrainingToTrainingRawValue({ ...this.getFormDefaults(), ...training });
    form.reset(
      {
        ...trainingRawValue,
        id: { value: trainingRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */,
    );
  }

  private getFormDefaults(): TrainingFormDefaults {
    const currentTime = dayjs();

    return {
      id: null,
      validUntil: currentTime,
      isOfficial: false,
      createdAt: currentTime,
      updatedAt: currentTime,
      skills: [],
    };
  }

  private convertTrainingRawValueToTraining(rawTraining: TrainingFormRawValue | NewTrainingFormRawValue): ITraining | NewTraining {
    return {
      ...rawTraining,
      validUntil: dayjs(rawTraining.validUntil, DATE_TIME_FORMAT),
      createdAt: dayjs(rawTraining.createdAt, DATE_TIME_FORMAT),
      updatedAt: dayjs(rawTraining.updatedAt, DATE_TIME_FORMAT),
    };
  }

  private convertTrainingToTrainingRawValue(
    training: ITraining | (Partial<NewTraining> & TrainingFormDefaults),
  ): TrainingFormRawValue | PartialWithRequiredKeyOf<NewTrainingFormRawValue> {
    return {
      ...training,
      validUntil: training.validUntil ? training.validUntil.format(DATE_TIME_FORMAT) : undefined,
      createdAt: training.createdAt ? training.createdAt.format(DATE_TIME_FORMAT) : undefined,
      updatedAt: training.updatedAt ? training.updatedAt.format(DATE_TIME_FORMAT) : undefined,
      skills: training.skills ?? [],
    };
  }
}
