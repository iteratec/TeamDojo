import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { ILevelSkill, NewLevelSkill } from '../level-skill.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts ILevelSkill for edit and NewLevelSkillFormGroupInput for create.
 */
type LevelSkillFormGroupInput = ILevelSkill | PartialWithRequiredKeyOf<NewLevelSkill>;

type LevelSkillFormDefaults = Pick<NewLevelSkill, 'id'>;

type LevelSkillFormGroupContent = {
  id: FormControl<ILevelSkill['id'] | NewLevelSkill['id']>;
  skill: FormControl<ILevelSkill['skill']>;
  level: FormControl<ILevelSkill['level']>;
};

export type LevelSkillFormGroup = FormGroup<LevelSkillFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class LevelSkillFormService {
  createLevelSkillFormGroup(levelSkill: LevelSkillFormGroupInput = { id: null }): LevelSkillFormGroup {
    const levelSkillRawValue = {
      ...this.getFormDefaults(),
      ...levelSkill,
    };
    return new FormGroup<LevelSkillFormGroupContent>({
      id: new FormControl(
        { value: levelSkillRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        },
      ),
      skill: new FormControl(levelSkillRawValue.skill, {
        validators: [Validators.required],
      }),
      level: new FormControl(levelSkillRawValue.level, {
        validators: [Validators.required],
      }),
    });
  }

  getLevelSkill(form: LevelSkillFormGroup): ILevelSkill | NewLevelSkill {
    return form.getRawValue() as ILevelSkill | NewLevelSkill;
  }

  resetForm(form: LevelSkillFormGroup, levelSkill: LevelSkillFormGroupInput): void {
    const levelSkillRawValue = { ...this.getFormDefaults(), ...levelSkill };
    form.reset(
      {
        ...levelSkillRawValue,
        id: { value: levelSkillRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */,
    );
  }

  private getFormDefaults(): LevelSkillFormDefaults {
    return {
      id: null,
    };
  }
}
