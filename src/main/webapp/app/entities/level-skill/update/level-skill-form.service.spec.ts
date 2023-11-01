import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../level-skill.test-samples';

import { LevelSkillFormService } from './level-skill-form.service';

describe('LevelSkill Form Service', () => {
  let service: LevelSkillFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(LevelSkillFormService);
  });

  describe('Service methods', () => {
    describe('createLevelSkillFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createLevelSkillFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            skill: expect.any(Object),
            level: expect.any(Object),
          }),
        );
      });

      it('passing ILevelSkill should create a new form with FormGroup', () => {
        const formGroup = service.createLevelSkillFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            skill: expect.any(Object),
            level: expect.any(Object),
          }),
        );
      });
    });

    describe('getLevelSkill', () => {
      it('should return NewLevelSkill for default LevelSkill initial value', () => {
        const formGroup = service.createLevelSkillFormGroup(sampleWithNewData);

        const levelSkill = service.getLevelSkill(formGroup) as any;

        expect(levelSkill).toMatchObject(sampleWithNewData);
      });

      it('should return NewLevelSkill for empty LevelSkill initial value', () => {
        const formGroup = service.createLevelSkillFormGroup();

        const levelSkill = service.getLevelSkill(formGroup) as any;

        expect(levelSkill).toMatchObject({});
      });

      it('should return ILevelSkill', () => {
        const formGroup = service.createLevelSkillFormGroup(sampleWithRequiredData);

        const levelSkill = service.getLevelSkill(formGroup) as any;

        expect(levelSkill).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing ILevelSkill should not enable id FormControl', () => {
        const formGroup = service.createLevelSkillFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewLevelSkill should disable id FormControl', () => {
        const formGroup = service.createLevelSkillFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
