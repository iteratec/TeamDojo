import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../badge-skill.test-samples';

import { BadgeSkillFormService } from './badge-skill-form.service';

describe('BadgeSkill Form Service', () => {
  let service: BadgeSkillFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(BadgeSkillFormService);
  });

  describe('Service methods', () => {
    describe('createBadgeSkillFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createBadgeSkillFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            badge: expect.any(Object),
            skill: expect.any(Object),
          }),
        );
      });

      it('passing IBadgeSkill should create a new form with FormGroup', () => {
        const formGroup = service.createBadgeSkillFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            badge: expect.any(Object),
            skill: expect.any(Object),
          }),
        );
      });
    });

    describe('getBadgeSkill', () => {
      it('should return NewBadgeSkill for default BadgeSkill initial value', () => {
        const formGroup = service.createBadgeSkillFormGroup(sampleWithNewData);

        const badgeSkill = service.getBadgeSkill(formGroup) as any;

        expect(badgeSkill).toMatchObject(sampleWithNewData);
      });

      it('should return NewBadgeSkill for empty BadgeSkill initial value', () => {
        const formGroup = service.createBadgeSkillFormGroup();

        const badgeSkill = service.getBadgeSkill(formGroup) as any;

        expect(badgeSkill).toMatchObject({});
      });

      it('should return IBadgeSkill', () => {
        const formGroup = service.createBadgeSkillFormGroup(sampleWithRequiredData);

        const badgeSkill = service.getBadgeSkill(formGroup) as any;

        expect(badgeSkill).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IBadgeSkill should not enable id FormControl', () => {
        const formGroup = service.createBadgeSkillFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewBadgeSkill should disable id FormControl', () => {
        const formGroup = service.createBadgeSkillFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
