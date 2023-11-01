import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import dayjs from 'dayjs/esm';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';
import { IBadge, NewBadge } from '../badge.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IBadge for edit and NewBadgeFormGroupInput for create.
 */
type BadgeFormGroupInput = IBadge | PartialWithRequiredKeyOf<NewBadge>;

/**
 * Type that converts some properties for forms.
 */
type FormValueOf<T extends IBadge | NewBadge> = Omit<T, 'availableUntil' | 'createdAt' | 'updatedAt'> & {
  availableUntil?: string | null;
  createdAt?: string | null;
  updatedAt?: string | null;
};

type BadgeFormRawValue = FormValueOf<IBadge>;

type NewBadgeFormRawValue = FormValueOf<NewBadge>;

type BadgeFormDefaults = Pick<NewBadge, 'id' | 'availableUntil' | 'createdAt' | 'updatedAt' | 'dimensions'>;

type BadgeFormGroupContent = {
  id: FormControl<BadgeFormRawValue['id'] | NewBadge['id']>;
  titleEN: FormControl<BadgeFormRawValue['titleEN']>;
  titleDE: FormControl<BadgeFormRawValue['titleDE']>;
  descriptionEN: FormControl<BadgeFormRawValue['descriptionEN']>;
  descriptionDE: FormControl<BadgeFormRawValue['descriptionDE']>;
  availableUntil: FormControl<BadgeFormRawValue['availableUntil']>;
  availableAmount: FormControl<BadgeFormRawValue['availableAmount']>;
  requiredScore: FormControl<BadgeFormRawValue['requiredScore']>;
  instantMultiplier: FormControl<BadgeFormRawValue['instantMultiplier']>;
  completionBonus: FormControl<BadgeFormRawValue['completionBonus']>;
  createdAt: FormControl<BadgeFormRawValue['createdAt']>;
  updatedAt: FormControl<BadgeFormRawValue['updatedAt']>;
  image: FormControl<BadgeFormRawValue['image']>;
  dimensions: FormControl<BadgeFormRawValue['dimensions']>;
};

export type BadgeFormGroup = FormGroup<BadgeFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class BadgeFormService {
  createBadgeFormGroup(badge: BadgeFormGroupInput = { id: null }): BadgeFormGroup {
    const badgeRawValue = this.convertBadgeToBadgeRawValue({
      ...this.getFormDefaults(),
      ...badge,
    });
    return new FormGroup<BadgeFormGroupContent>({
      id: new FormControl(
        { value: badgeRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        },
      ),
      titleEN: new FormControl(badgeRawValue.titleEN, {
        validators: [Validators.required, Validators.minLength(2), Validators.maxLength(50)],
      }),
      titleDE: new FormControl(badgeRawValue.titleDE, {
        validators: [Validators.minLength(2), Validators.maxLength(50)],
      }),
      descriptionEN: new FormControl(badgeRawValue.descriptionEN, {
        validators: [Validators.maxLength(4096)],
      }),
      descriptionDE: new FormControl(badgeRawValue.descriptionDE, {
        validators: [Validators.maxLength(4096)],
      }),
      availableUntil: new FormControl(badgeRawValue.availableUntil),
      availableAmount: new FormControl(badgeRawValue.availableAmount, {
        validators: [Validators.min(1)],
      }),
      requiredScore: new FormControl(badgeRawValue.requiredScore, {
        validators: [Validators.required, Validators.min(0), Validators.max(1)],
      }),
      instantMultiplier: new FormControl(badgeRawValue.instantMultiplier, {
        validators: [Validators.required, Validators.min(0)],
      }),
      completionBonus: new FormControl(badgeRawValue.completionBonus, {
        validators: [Validators.min(0)],
      }),
      createdAt: new FormControl(badgeRawValue.createdAt, {
        validators: [Validators.required],
      }),
      updatedAt: new FormControl(badgeRawValue.updatedAt, {
        validators: [Validators.required],
      }),
      image: new FormControl(badgeRawValue.image),
      dimensions: new FormControl(badgeRawValue.dimensions ?? []),
    });
  }

  getBadge(form: BadgeFormGroup): IBadge | NewBadge {
    return this.convertBadgeRawValueToBadge(form.getRawValue() as BadgeFormRawValue | NewBadgeFormRawValue);
  }

  resetForm(form: BadgeFormGroup, badge: BadgeFormGroupInput): void {
    const badgeRawValue = this.convertBadgeToBadgeRawValue({ ...this.getFormDefaults(), ...badge });
    form.reset(
      {
        ...badgeRawValue,
        id: { value: badgeRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */,
    );
  }

  private getFormDefaults(): BadgeFormDefaults {
    const currentTime = dayjs();

    return {
      id: null,
      availableUntil: currentTime,
      createdAt: currentTime,
      updatedAt: currentTime,
      dimensions: [],
    };
  }

  private convertBadgeRawValueToBadge(rawBadge: BadgeFormRawValue | NewBadgeFormRawValue): IBadge | NewBadge {
    return {
      ...rawBadge,
      availableUntil: dayjs(rawBadge.availableUntil, DATE_TIME_FORMAT),
      createdAt: dayjs(rawBadge.createdAt, DATE_TIME_FORMAT),
      updatedAt: dayjs(rawBadge.updatedAt, DATE_TIME_FORMAT),
    };
  }

  private convertBadgeToBadgeRawValue(
    badge: IBadge | (Partial<NewBadge> & BadgeFormDefaults),
  ): BadgeFormRawValue | PartialWithRequiredKeyOf<NewBadgeFormRawValue> {
    return {
      ...badge,
      availableUntil: badge.availableUntil ? badge.availableUntil.format(DATE_TIME_FORMAT) : undefined,
      createdAt: badge.createdAt ? badge.createdAt.format(DATE_TIME_FORMAT) : undefined,
      updatedAt: badge.updatedAt ? badge.updatedAt.format(DATE_TIME_FORMAT) : undefined,
      dimensions: badge.dimensions ?? [],
    };
  }
}
