import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../badge.test-samples';

import { BadgeFormService } from './badge-form.service';

describe('Badge Form Service', () => {
  let service: BadgeFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(BadgeFormService);
  });

  describe('Service methods', () => {
    describe('createBadgeFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createBadgeFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            titleEN: expect.any(Object),
            titleDE: expect.any(Object),
            descriptionEN: expect.any(Object),
            descriptionDE: expect.any(Object),
            availableUntil: expect.any(Object),
            availableAmount: expect.any(Object),
            requiredScore: expect.any(Object),
            instantMultiplier: expect.any(Object),
            completionBonus: expect.any(Object),
            createdAt: expect.any(Object),
            updatedAt: expect.any(Object),
            image: expect.any(Object),
            dimensions: expect.any(Object),
          })
        );
      });

      it('passing IBadge should create a new form with FormGroup', () => {
        const formGroup = service.createBadgeFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            titleEN: expect.any(Object),
            titleDE: expect.any(Object),
            descriptionEN: expect.any(Object),
            descriptionDE: expect.any(Object),
            availableUntil: expect.any(Object),
            availableAmount: expect.any(Object),
            requiredScore: expect.any(Object),
            instantMultiplier: expect.any(Object),
            completionBonus: expect.any(Object),
            createdAt: expect.any(Object),
            updatedAt: expect.any(Object),
            image: expect.any(Object),
            dimensions: expect.any(Object),
          })
        );
      });
    });

    describe('getBadge', () => {
      it('should return NewBadge for default Badge initial value', () => {
        // eslint-disable-next-line @typescript-eslint/no-unused-vars
        const formGroup = service.createBadgeFormGroup(sampleWithNewData);

        const badge = service.getBadge(formGroup) as any;

        expect(badge).toMatchObject(sampleWithNewData);
      });

      it('should return NewBadge for empty Badge initial value', () => {
        const formGroup = service.createBadgeFormGroup();

        const badge = service.getBadge(formGroup) as any;

        expect(badge).toMatchObject({});
      });

      it('should return IBadge', () => {
        const formGroup = service.createBadgeFormGroup(sampleWithRequiredData);

        const badge = service.getBadge(formGroup) as any;

        expect(badge).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IBadge should not enable id FormControl', () => {
        const formGroup = service.createBadgeFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewBadge should disable id FormControl', () => {
        const formGroup = service.createBadgeFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
