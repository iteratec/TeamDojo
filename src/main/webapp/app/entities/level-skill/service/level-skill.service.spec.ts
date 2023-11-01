import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { ILevelSkill } from '../level-skill.model';
import { sampleWithRequiredData, sampleWithNewData, sampleWithPartialData, sampleWithFullData } from '../level-skill.test-samples';

import { LevelSkillService } from './level-skill.service';

const requireRestSample: ILevelSkill = {
  ...sampleWithRequiredData,
};

describe('LevelSkill Service', () => {
  let service: LevelSkillService;
  let httpMock: HttpTestingController;
  let expectedResult: ILevelSkill | ILevelSkill[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(LevelSkillService);
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

    it('should create a LevelSkill', () => {
      const levelSkill = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(levelSkill).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a LevelSkill', () => {
      const levelSkill = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(levelSkill).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a LevelSkill', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of LevelSkill', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a LevelSkill', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addLevelSkillToCollectionIfMissing', () => {
      it('should add a LevelSkill to an empty array', () => {
        const levelSkill: ILevelSkill = sampleWithRequiredData;
        expectedResult = service.addLevelSkillToCollectionIfMissing([], levelSkill);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(levelSkill);
      });

      it('should not add a LevelSkill to an array that contains it', () => {
        const levelSkill: ILevelSkill = sampleWithRequiredData;
        const levelSkillCollection: ILevelSkill[] = [
          {
            ...levelSkill,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addLevelSkillToCollectionIfMissing(levelSkillCollection, levelSkill);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a LevelSkill to an array that doesn't contain it", () => {
        const levelSkill: ILevelSkill = sampleWithRequiredData;
        const levelSkillCollection: ILevelSkill[] = [sampleWithPartialData];
        expectedResult = service.addLevelSkillToCollectionIfMissing(levelSkillCollection, levelSkill);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(levelSkill);
      });

      it('should add only unique LevelSkill to an array', () => {
        const levelSkillArray: ILevelSkill[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const levelSkillCollection: ILevelSkill[] = [sampleWithRequiredData];
        expectedResult = service.addLevelSkillToCollectionIfMissing(levelSkillCollection, ...levelSkillArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const levelSkill: ILevelSkill = sampleWithRequiredData;
        const levelSkill2: ILevelSkill = sampleWithPartialData;
        expectedResult = service.addLevelSkillToCollectionIfMissing([], levelSkill, levelSkill2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(levelSkill);
        expect(expectedResult).toContain(levelSkill2);
      });

      it('should accept null and undefined values', () => {
        const levelSkill: ILevelSkill = sampleWithRequiredData;
        expectedResult = service.addLevelSkillToCollectionIfMissing([], null, levelSkill, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(levelSkill);
      });

      it('should return initial array if no LevelSkill is added', () => {
        const levelSkillCollection: ILevelSkill[] = [sampleWithRequiredData];
        expectedResult = service.addLevelSkillToCollectionIfMissing(levelSkillCollection, undefined, null);
        expect(expectedResult).toEqual(levelSkillCollection);
      });
    });

    describe('compareLevelSkill', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareLevelSkill(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareLevelSkill(entity1, entity2);
        const compareResult2 = service.compareLevelSkill(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareLevelSkill(entity1, entity2);
        const compareResult2 = service.compareLevelSkill(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareLevelSkill(entity1, entity2);
        const compareResult2 = service.compareLevelSkill(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
