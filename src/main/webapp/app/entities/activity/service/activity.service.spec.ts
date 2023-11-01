import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IActivity } from '../activity.model';
import { sampleWithRequiredData, sampleWithNewData, sampleWithPartialData, sampleWithFullData } from '../activity.test-samples';

import { ActivityService, RestActivity } from './activity.service';

const requireRestSample: RestActivity = {
  ...sampleWithRequiredData,
  createdAt: sampleWithRequiredData.createdAt?.toJSON(),
  updatedAt: sampleWithRequiredData.updatedAt?.toJSON(),
};

describe('Activity Service', () => {
  let service: ActivityService;
  let httpMock: HttpTestingController;
  let expectedResult: IActivity | IActivity[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(ActivityService);
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

    it('should create a Activity', () => {
      const activity = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(activity).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a Activity', () => {
      const activity = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(activity).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a Activity', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of Activity', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a Activity', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addActivityToCollectionIfMissing', () => {
      it('should add a Activity to an empty array', () => {
        const activity: IActivity = sampleWithRequiredData;
        expectedResult = service.addActivityToCollectionIfMissing([], activity);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(activity);
      });

      it('should not add a Activity to an array that contains it', () => {
        const activity: IActivity = sampleWithRequiredData;
        const activityCollection: IActivity[] = [
          {
            ...activity,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addActivityToCollectionIfMissing(activityCollection, activity);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a Activity to an array that doesn't contain it", () => {
        const activity: IActivity = sampleWithRequiredData;
        const activityCollection: IActivity[] = [sampleWithPartialData];
        expectedResult = service.addActivityToCollectionIfMissing(activityCollection, activity);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(activity);
      });

      it('should add only unique Activity to an array', () => {
        const activityArray: IActivity[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const activityCollection: IActivity[] = [sampleWithRequiredData];
        expectedResult = service.addActivityToCollectionIfMissing(activityCollection, ...activityArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const activity: IActivity = sampleWithRequiredData;
        const activity2: IActivity = sampleWithPartialData;
        expectedResult = service.addActivityToCollectionIfMissing([], activity, activity2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(activity);
        expect(expectedResult).toContain(activity2);
      });

      it('should accept null and undefined values', () => {
        const activity: IActivity = sampleWithRequiredData;
        expectedResult = service.addActivityToCollectionIfMissing([], null, activity, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(activity);
      });

      it('should return initial array if no Activity is added', () => {
        const activityCollection: IActivity[] = [sampleWithRequiredData];
        expectedResult = service.addActivityToCollectionIfMissing(activityCollection, undefined, null);
        expect(expectedResult).toEqual(activityCollection);
      });
    });

    describe('compareActivity', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareActivity(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareActivity(entity1, entity2);
        const compareResult2 = service.compareActivity(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareActivity(entity1, entity2);
        const compareResult2 = service.compareActivity(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareActivity(entity1, entity2);
        const compareResult2 = service.compareActivity(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
