import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { IBadgeSkill, NewBadgeSkill } from '../badge-skill.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IBadgeSkill for edit and NewBadgeSkillFormGroupInput for create.
 */
type BadgeSkillFormGroupInput = IBadgeSkill | PartialWithRequiredKeyOf<NewBadgeSkill>;

type BadgeSkillFormDefaults = Pick<NewBadgeSkill, 'id'>;

type BadgeSkillFormGroupContent = {
  id: FormControl<IBadgeSkill['id'] | NewBadgeSkill['id']>;
  badge: FormControl<IBadgeSkill['badge']>;
  skill: FormControl<IBadgeSkill['skill']>;
};

export type BadgeSkillFormGroup = FormGroup<BadgeSkillFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class BadgeSkillFormService {
  createBadgeSkillFormGroup(badgeSkill: BadgeSkillFormGroupInput = { id: null }): BadgeSkillFormGroup {
    const badgeSkillRawValue = {
      ...this.getFormDefaults(),
      ...badgeSkill,
    };
    return new FormGroup<BadgeSkillFormGroupContent>({
      id: new FormControl(
        { value: badgeSkillRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        },
      ),
      badge: new FormControl(badgeSkillRawValue.badge, {
        validators: [Validators.required],
      }),
      skill: new FormControl(badgeSkillRawValue.skill, {
        validators: [Validators.required],
      }),
    });
  }

  getBadgeSkill(form: BadgeSkillFormGroup): IBadgeSkill | NewBadgeSkill {
    return form.getRawValue() as IBadgeSkill | NewBadgeSkill;
  }

  resetForm(form: BadgeSkillFormGroup, badgeSkill: BadgeSkillFormGroupInput): void {
    const badgeSkillRawValue = { ...this.getFormDefaults(), ...badgeSkill };
    form.reset(
      {
        ...badgeSkillRawValue,
        id: { value: badgeSkillRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */,
    );
  }

  private getFormDefaults(): BadgeSkillFormDefaults {
    return {
      id: null,
    };
  }
}
