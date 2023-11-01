import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IDimension } from '../dimension.model';
import { sampleWithRequiredData, sampleWithNewData, sampleWithPartialData, sampleWithFullData } from '../dimension.test-samples';

import { DimensionService, RestDimension } from './dimension.service';

const requireRestSample: RestDimension = {
  ...sampleWithRequiredData,
  createdAt: sampleWithRequiredData.createdAt?.toJSON(),
  updatedAt: sampleWithRequiredData.updatedAt?.toJSON(),
};

describe('Dimension Service', () => {
  let service: DimensionService;
  let httpMock: HttpTestingController;
  let expectedResult: IDimension | IDimension[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(DimensionService);
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

    it('should create a Dimension', () => {
      const dimension = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(dimension).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a Dimension', () => {
      const dimension = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(dimension).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a Dimension', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of Dimension', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a Dimension', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addDimensionToCollectionIfMissing', () => {
      it('should add a Dimension to an empty array', () => {
        const dimension: IDimension = sampleWithRequiredData;
        expectedResult = service.addDimensionToCollectionIfMissing([], dimension);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(dimension);
      });

      it('should not add a Dimension to an array that contains it', () => {
        const dimension: IDimension = sampleWithRequiredData;
        const dimensionCollection: IDimension[] = [
          {
            ...dimension,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addDimensionToCollectionIfMissing(dimensionCollection, dimension);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a Dimension to an array that doesn't contain it", () => {
        const dimension: IDimension = sampleWithRequiredData;
        const dimensionCollection: IDimension[] = [sampleWithPartialData];
        expectedResult = service.addDimensionToCollectionIfMissing(dimensionCollection, dimension);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(dimension);
      });

      it('should add only unique Dimension to an array', () => {
        const dimensionArray: IDimension[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const dimensionCollection: IDimension[] = [sampleWithRequiredData];
        expectedResult = service.addDimensionToCollectionIfMissing(dimensionCollection, ...dimensionArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const dimension: IDimension = sampleWithRequiredData;
        const dimension2: IDimension = sampleWithPartialData;
        expectedResult = service.addDimensionToCollectionIfMissing([], dimension, dimension2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(dimension);
        expect(expectedResult).toContain(dimension2);
      });

      it('should accept null and undefined values', () => {
        const dimension: IDimension = sampleWithRequiredData;
        expectedResult = service.addDimensionToCollectionIfMissing([], null, dimension, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(dimension);
      });

      it('should return initial array if no Dimension is added', () => {
        const dimensionCollection: IDimension[] = [sampleWithRequiredData];
        expectedResult = service.addDimensionToCollectionIfMissing(dimensionCollection, undefined, null);
        expect(expectedResult).toEqual(dimensionCollection);
      });
    });

    describe('compareDimension', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareDimension(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareDimension(entity1, entity2);
        const compareResult2 = service.compareDimension(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareDimension(entity1, entity2);
        const compareResult2 = service.compareDimension(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareDimension(entity1, entity2);
        const compareResult2 = service.compareDimension(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
