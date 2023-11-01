import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IComment } from '../comment.model';
import { sampleWithRequiredData, sampleWithNewData, sampleWithPartialData, sampleWithFullData } from '../comment.test-samples';

import { CommentService, RestComment } from './comment.service';

const requireRestSample: RestComment = {
  ...sampleWithRequiredData,
  createdAt: sampleWithRequiredData.createdAt?.toJSON(),
  updatedAt: sampleWithRequiredData.updatedAt?.toJSON(),
};

describe('Comment Service', () => {
  let service: CommentService;
  let httpMock: HttpTestingController;
  let expectedResult: IComment | IComment[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(CommentService);
    httpMock = TestBed.inject(HttpTestingController);
  });

  describe('Service methods', () => {
    it('should find an element', () => {
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.find(123).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should create a Comment', () => {
      const comment = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(comment).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a Comment', () => {
      const comment = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(comment).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a Comment', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of Comment', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a Comment', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addCommentToCollectionIfMissing', () => {
      it('should add a Comment to an empty array', () => {
        const comment: IComment = sampleWithRequiredData;
        expectedResult = service.addCommentToCollectionIfMissing([], comment);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(comment);
      });

      it('should not add a Comment to an array that contains it', () => {
        const comment: IComment = sampleWithRequiredData;
        const commentCollection: IComment[] = [
          {
            ...comment,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addCommentToCollectionIfMissing(commentCollection, comment);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a Comment to an array that doesn't contain it", () => {
        const comment: IComment = sampleWithRequiredData;
        const commentCollection: IComment[] = [sampleWithPartialData];
        expectedResult = service.addCommentToCollectionIfMissing(commentCollection, comment);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(comment);
      });

      it('should add only unique Comment to an array', () => {
        const commentArray: IComment[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const commentCollection: IComment[] = [sampleWithRequiredData];
        expectedResult = service.addCommentToCollectionIfMissing(commentCollection, ...commentArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const comment: IComment = sampleWithRequiredData;
        const comment2: IComment = sampleWithPartialData;
        expectedResult = service.addCommentToCollectionIfMissing([], comment, comment2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(comment);
        expect(expectedResult).toContain(comment2);
      });

      it('should accept null and undefined values', () => {
        const comment: IComment = sampleWithRequiredData;
        expectedResult = service.addCommentToCollectionIfMissing([], null, comment, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(comment);
      });

      it('should return initial array if no Comment is added', () => {
        const commentCollection: IComment[] = [sampleWithRequiredData];
        expectedResult = service.addCommentToCollectionIfMissing(commentCollection, undefined, null);
        expect(expectedResult).toEqual(commentCollection);
      });
    });

    describe('compareComment', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareComment(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareComment(entity1, entity2);
        const compareResult2 = service.compareComment(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareComment(entity1, entity2);
        const compareResult2 = service.compareComment(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareComment(entity1, entity2);
        const compareResult2 = service.compareComment(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
