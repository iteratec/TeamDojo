import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../dimension.test-samples';

import { DimensionFormService } from './dimension-form.service';

describe('Dimension Form Service', () => {
  let service: DimensionFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(DimensionFormService);
  });

  describe('Service methods', () => {
    describe('createDimensionFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createDimensionFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            titleEN: expect.any(Object),
            titleDE: expect.any(Object),
            descriptionEN: expect.any(Object),
            descriptionDE: expect.any(Object),
            createdAt: expect.any(Object),
            updatedAt: expect.any(Object),
            badges: expect.any(Object),
            participants: expect.any(Object),
          }),
        );
      });

      it('passing IDimension should create a new form with FormGroup', () => {
        const formGroup = service.createDimensionFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            titleEN: expect.any(Object),
            titleDE: expect.any(Object),
            descriptionEN: expect.any(Object),
            descriptionDE: expect.any(Object),
            createdAt: expect.any(Object),
            updatedAt: expect.any(Object),
            badges: expect.any(Object),
            participants: expect.any(Object),
          }),
        );
      });
    });

    describe('getDimension', () => {
      it('should return NewDimension for default Dimension initial value', () => {
        const formGroup = service.createDimensionFormGroup(sampleWithNewData);

        const dimension = service.getDimension(formGroup) as any;

        expect(dimension).toMatchObject(sampleWithNewData);
      });

      it('should return NewDimension for empty Dimension initial value', () => {
        const formGroup = service.createDimensionFormGroup();

        const dimension = service.getDimension(formGroup) as any;

        expect(dimension).toMatchObject({});
      });

      it('should return IDimension', () => {
        const formGroup = service.createDimensionFormGroup(sampleWithRequiredData);

        const dimension = service.getDimension(formGroup) as any;

        expect(dimension).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IDimension should not enable id FormControl', () => {
        const formGroup = service.createDimensionFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewDimension should disable id FormControl', () => {
        const formGroup = service.createDimensionFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
