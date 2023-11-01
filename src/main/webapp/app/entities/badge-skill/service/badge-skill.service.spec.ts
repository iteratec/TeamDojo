import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IBadgeSkill } from '../badge-skill.model';
import { sampleWithRequiredData, sampleWithNewData, sampleWithPartialData, sampleWithFullData } from '../badge-skill.test-samples';

import { BadgeSkillService } from './badge-skill.service';

const requireRestSample: IBadgeSkill = {
  ...sampleWithRequiredData,
};

describe('BadgeSkill Service', () => {
  let service: BadgeSkillService;
  let httpMock: HttpTestingController;
  let expectedResult: IBadgeSkill | IBadgeSkill[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(BadgeSkillService);
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

    it('should create a BadgeSkill', () => {
      const badgeSkill = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(badgeSkill).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a BadgeSkill', () => {
      const badgeSkill = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(badgeSkill).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a BadgeSkill', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of BadgeSkill', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a BadgeSkill', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addBadgeSkillToCollectionIfMissing', () => {
      it('should add a BadgeSkill to an empty array', () => {
        const badgeSkill: IBadgeSkill = sampleWithRequiredData;
        expectedResult = service.addBadgeSkillToCollectionIfMissing([], badgeSkill);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(badgeSkill);
      });

      it('should not add a BadgeSkill to an array that contains it', () => {
        const badgeSkill: IBadgeSkill = sampleWithRequiredData;
        const badgeSkillCollection: IBadgeSkill[] = [
          {
            ...badgeSkill,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addBadgeSkillToCollectionIfMissing(badgeSkillCollection, badgeSkill);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a BadgeSkill to an array that doesn't contain it", () => {
        const badgeSkill: IBadgeSkill = sampleWithRequiredData;
        const badgeSkillCollection: IBadgeSkill[] = [sampleWithPartialData];
        expectedResult = service.addBadgeSkillToCollectionIfMissing(badgeSkillCollection, badgeSkill);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(badgeSkill);
      });

      it('should add only unique BadgeSkill to an array', () => {
        const badgeSkillArray: IBadgeSkill[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const badgeSkillCollection: IBadgeSkill[] = [sampleWithRequiredData];
        expectedResult = service.addBadgeSkillToCollectionIfMissing(badgeSkillCollection, ...badgeSkillArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const badgeSkill: IBadgeSkill = sampleWithRequiredData;
        const badgeSkill2: IBadgeSkill = sampleWithPartialData;
        expectedResult = service.addBadgeSkillToCollectionIfMissing([], badgeSkill, badgeSkill2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(badgeSkill);
        expect(expectedResult).toContain(badgeSkill2);
      });

      it('should accept null and undefined values', () => {
        const badgeSkill: IBadgeSkill = sampleWithRequiredData;
        expectedResult = service.addBadgeSkillToCollectionIfMissing([], null, badgeSkill, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(badgeSkill);
      });

      it('should return initial array if no BadgeSkill is added', () => {
        const badgeSkillCollection: IBadgeSkill[] = [sampleWithRequiredData];
        expectedResult = service.addBadgeSkillToCollectionIfMissing(badgeSkillCollection, undefined, null);
        expect(expectedResult).toEqual(badgeSkillCollection);
      });
    });

    describe('compareBadgeSkill', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareBadgeSkill(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareBadgeSkill(entity1, entity2);
        const compareResult2 = service.compareBadgeSkill(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareBadgeSkill(entity1, entity2);
        const compareResult2 = service.compareBadgeSkill(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareBadgeSkill(entity1, entity2);
        const compareResult2 = service.compareBadgeSkill(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
