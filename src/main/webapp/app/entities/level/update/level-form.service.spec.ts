import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../level.test-samples';

import { LevelFormService } from './level-form.service';

describe('Level Form Service', () => {
  let service: LevelFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(LevelFormService);
  });

  describe('Service methods', () => {
    describe('createLevelFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createLevelFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            titleEN: expect.any(Object),
            titleDE: expect.any(Object),
            descriptionEN: expect.any(Object),
            descriptionDE: expect.any(Object),
            requiredScore: expect.any(Object),
            instantMultiplier: expect.any(Object),
            completionBonus: expect.any(Object),
            createdAt: expect.any(Object),
            updatedAt: expect.any(Object),
            dependsOn: expect.any(Object),
            image: expect.any(Object),
            dimension: expect.any(Object),
          })
        );
      });

      it('passing ILevel should create a new form with FormGroup', () => {
        const formGroup = service.createLevelFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            titleEN: expect.any(Object),
            titleDE: expect.any(Object),
            descriptionEN: expect.any(Object),
            descriptionDE: expect.any(Object),
            requiredScore: expect.any(Object),
            instantMultiplier: expect.any(Object),
            completionBonus: expect.any(Object),
            createdAt: expect.any(Object),
            updatedAt: expect.any(Object),
            dependsOn: expect.any(Object),
            image: expect.any(Object),
            dimension: expect.any(Object),
          })
        );
      });
    });

    describe('getLevel', () => {
      it('should return NewLevel for default Level initial value', () => {
        // eslint-disable-next-line @typescript-eslint/no-unused-vars
        const formGroup = service.createLevelFormGroup(sampleWithNewData);

        const level = service.getLevel(formGroup) as any;

        expect(level).toMatchObject(sampleWithNewData);
      });

      it('should return NewLevel for empty Level initial value', () => {
        const formGroup = service.createLevelFormGroup();

        const level = service.getLevel(formGroup) as any;

        expect(level).toMatchObject({});
      });

      it('should return ILevel', () => {
        const formGroup = service.createLevelFormGroup(sampleWithRequiredData);

        const level = service.getLevel(formGroup) as any;

        expect(level).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing ILevel should not enable id FormControl', () => {
        const formGroup = service.createLevelFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewLevel should disable id FormControl', () => {
        const formGroup = service.createLevelFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
