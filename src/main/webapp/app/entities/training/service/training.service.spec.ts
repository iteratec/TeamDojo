import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { ITraining } from '../training.model';
import { sampleWithRequiredData, sampleWithNewData, sampleWithPartialData, sampleWithFullData } from '../training.test-samples';

import { TrainingService, RestTraining } from './training.service';

const requireRestSample: RestTraining = {
  ...sampleWithRequiredData,
  validUntil: sampleWithRequiredData.validUntil?.toJSON(),
  createdAt: sampleWithRequiredData.createdAt?.toJSON(),
  updatedAt: sampleWithRequiredData.updatedAt?.toJSON(),
};

describe('Training Service', () => {
  let service: TrainingService;
  let httpMock: HttpTestingController;
  let expectedResult: ITraining | ITraining[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(TrainingService);
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

    it('should create a Training', () => {
      // eslint-disable-next-line @typescript-eslint/no-unused-vars
      const training = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(training).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a Training', () => {
      const training = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(training).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a Training', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of Training', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a Training', () => {
      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult);
    });

    describe('addTrainingToCollectionIfMissing', () => {
      it('should add a Training to an empty array', () => {
        const training: ITraining = sampleWithRequiredData;
        expectedResult = service.addTrainingToCollectionIfMissing([], training);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(training);
      });

      it('should not add a Training to an array that contains it', () => {
        const training: ITraining = sampleWithRequiredData;
        const trainingCollection: ITraining[] = [
          {
            ...training,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addTrainingToCollectionIfMissing(trainingCollection, training);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a Training to an array that doesn't contain it", () => {
        const training: ITraining = sampleWithRequiredData;
        const trainingCollection: ITraining[] = [sampleWithPartialData];
        expectedResult = service.addTrainingToCollectionIfMissing(trainingCollection, training);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(training);
      });

      it('should add only unique Training to an array', () => {
        const trainingArray: ITraining[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const trainingCollection: ITraining[] = [sampleWithRequiredData];
        expectedResult = service.addTrainingToCollectionIfMissing(trainingCollection, ...trainingArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const training: ITraining = sampleWithRequiredData;
        const training2: ITraining = sampleWithPartialData;
        expectedResult = service.addTrainingToCollectionIfMissing([], training, training2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(training);
        expect(expectedResult).toContain(training2);
      });

      it('should accept null and undefined values', () => {
        const training: ITraining = sampleWithRequiredData;
        expectedResult = service.addTrainingToCollectionIfMissing([], null, training, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(training);
      });

      it('should return initial array if no Training is added', () => {
        const trainingCollection: ITraining[] = [sampleWithRequiredData];
        expectedResult = service.addTrainingToCollectionIfMissing(trainingCollection, undefined, null);
        expect(expectedResult).toEqual(trainingCollection);
      });
    });

    describe('compareTraining', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareTraining(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareTraining(entity1, entity2);
        const compareResult2 = service.compareTraining(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareTraining(entity1, entity2);
        const compareResult2 = service.compareTraining(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareTraining(entity1, entity2);
        const compareResult2 = service.compareTraining(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
