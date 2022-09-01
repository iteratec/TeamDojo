import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { ILevel } from '../level.model';
import { sampleWithRequiredData, sampleWithNewData, sampleWithPartialData, sampleWithFullData } from '../level.test-samples';

import { LevelService, RestLevel } from './level.service';

const requireRestSample: RestLevel = {
  ...sampleWithRequiredData,
  createdAt: sampleWithRequiredData.createdAt?.toJSON(),
  updatedAt: sampleWithRequiredData.updatedAt?.toJSON(),
};

describe('Level Service', () => {
  let service: LevelService;
  let httpMock: HttpTestingController;
  let expectedResult: ILevel | ILevel[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(LevelService);
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

    it('should create a Level', () => {
      // eslint-disable-next-line @typescript-eslint/no-unused-vars
      const level = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(level).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a Level', () => {
      const level = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(level).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a Level', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of Level', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a Level', () => {
      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult);
    });

    describe('addLevelToCollectionIfMissing', () => {
      it('should add a Level to an empty array', () => {
        const level: ILevel = sampleWithRequiredData;
        expectedResult = service.addLevelToCollectionIfMissing([], level);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(level);
      });

      it('should not add a Level to an array that contains it', () => {
        const level: ILevel = sampleWithRequiredData;
        const levelCollection: ILevel[] = [
          {
            ...level,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addLevelToCollectionIfMissing(levelCollection, level);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a Level to an array that doesn't contain it", () => {
        const level: ILevel = sampleWithRequiredData;
        const levelCollection: ILevel[] = [sampleWithPartialData];
        expectedResult = service.addLevelToCollectionIfMissing(levelCollection, level);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(level);
      });

      it('should add only unique Level to an array', () => {
        const levelArray: ILevel[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const levelCollection: ILevel[] = [sampleWithRequiredData];
        expectedResult = service.addLevelToCollectionIfMissing(levelCollection, ...levelArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const level: ILevel = sampleWithRequiredData;
        const level2: ILevel = sampleWithPartialData;
        expectedResult = service.addLevelToCollectionIfMissing([], level, level2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(level);
        expect(expectedResult).toContain(level2);
      });

      it('should accept null and undefined values', () => {
        const level: ILevel = sampleWithRequiredData;
        expectedResult = service.addLevelToCollectionIfMissing([], null, level, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(level);
      });

      it('should return initial array if no Level is added', () => {
        const levelCollection: ILevel[] = [sampleWithRequiredData];
        expectedResult = service.addLevelToCollectionIfMissing(levelCollection, undefined, null);
        expect(expectedResult).toEqual(levelCollection);
      });
    });

    describe('compareLevel', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareLevel(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareLevel(entity1, entity2);
        const compareResult2 = service.compareLevel(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareLevel(entity1, entity2);
        const compareResult2 = service.compareLevel(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareLevel(entity1, entity2);
        const compareResult2 = service.compareLevel(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
