import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../comment.test-samples';

import { CommentFormService } from './comment-form.service';

describe('Comment Form Service', () => {
  let service: CommentFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(CommentFormService);
  });

  describe('Service methods', () => {
    describe('createCommentFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createCommentFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            text: expect.any(Object),
            createdAt: expect.any(Object),
            updatedAt: expect.any(Object),
            team: expect.any(Object),
            skill: expect.any(Object),
          })
        );
      });

      it('passing IComment should create a new form with FormGroup', () => {
        const formGroup = service.createCommentFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            text: expect.any(Object),
            createdAt: expect.any(Object),
            updatedAt: expect.any(Object),
            team: expect.any(Object),
            skill: expect.any(Object),
          })
        );
      });
    });

    describe('getComment', () => {
      it('should return NewComment for default Comment initial value', () => {
        // eslint-disable-next-line @typescript-eslint/no-unused-vars
        const formGroup = service.createCommentFormGroup(sampleWithNewData);

        const comment = service.getComment(formGroup) as any;

        expect(comment).toMatchObject(sampleWithNewData);
      });

      it('should return NewComment for empty Comment initial value', () => {
        const formGroup = service.createCommentFormGroup();

        const comment = service.getComment(formGroup) as any;

        expect(comment).toMatchObject({});
      });

      it('should return IComment', () => {
        const formGroup = service.createCommentFormGroup(sampleWithRequiredData);

        const comment = service.getComment(formGroup) as any;

        expect(comment).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IComment should not enable id FormControl', () => {
        const formGroup = service.createCommentFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewComment should disable id FormControl', () => {
        const formGroup = service.createCommentFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
