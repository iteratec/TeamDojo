import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { ITeamSkill } from '../team-skill.model';
import { sampleWithRequiredData, sampleWithNewData, sampleWithPartialData, sampleWithFullData } from '../team-skill.test-samples';

import { TeamSkillService, RestTeamSkill } from './team-skill.service';

const requireRestSample: RestTeamSkill = {
  ...sampleWithRequiredData,
  completedAt: sampleWithRequiredData.completedAt?.toJSON(),
  verifiedAt: sampleWithRequiredData.verifiedAt?.toJSON(),
  createdAt: sampleWithRequiredData.createdAt?.toJSON(),
  updatedAt: sampleWithRequiredData.updatedAt?.toJSON(),
};

describe('TeamSkill Service', () => {
  let service: TeamSkillService;
  let httpMock: HttpTestingController;
  let expectedResult: ITeamSkill | ITeamSkill[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(TeamSkillService);
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

    it('should create a TeamSkill', () => {
      const teamSkill = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(teamSkill).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a TeamSkill', () => {
      const teamSkill = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(teamSkill).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a TeamSkill', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of TeamSkill', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a TeamSkill', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addTeamSkillToCollectionIfMissing', () => {
      it('should add a TeamSkill to an empty array', () => {
        const teamSkill: ITeamSkill = sampleWithRequiredData;
        expectedResult = service.addTeamSkillToCollectionIfMissing([], teamSkill);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(teamSkill);
      });

      it('should not add a TeamSkill to an array that contains it', () => {
        const teamSkill: ITeamSkill = sampleWithRequiredData;
        const teamSkillCollection: ITeamSkill[] = [
          {
            ...teamSkill,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addTeamSkillToCollectionIfMissing(teamSkillCollection, teamSkill);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a TeamSkill to an array that doesn't contain it", () => {
        const teamSkill: ITeamSkill = sampleWithRequiredData;
        const teamSkillCollection: ITeamSkill[] = [sampleWithPartialData];
        expectedResult = service.addTeamSkillToCollectionIfMissing(teamSkillCollection, teamSkill);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(teamSkill);
      });

      it('should add only unique TeamSkill to an array', () => {
        const teamSkillArray: ITeamSkill[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const teamSkillCollection: ITeamSkill[] = [sampleWithRequiredData];
        expectedResult = service.addTeamSkillToCollectionIfMissing(teamSkillCollection, ...teamSkillArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const teamSkill: ITeamSkill = sampleWithRequiredData;
        const teamSkill2: ITeamSkill = sampleWithPartialData;
        expectedResult = service.addTeamSkillToCollectionIfMissing([], teamSkill, teamSkill2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(teamSkill);
        expect(expectedResult).toContain(teamSkill2);
      });

      it('should accept null and undefined values', () => {
        const teamSkill: ITeamSkill = sampleWithRequiredData;
        expectedResult = service.addTeamSkillToCollectionIfMissing([], null, teamSkill, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(teamSkill);
      });

      it('should return initial array if no TeamSkill is added', () => {
        const teamSkillCollection: ITeamSkill[] = [sampleWithRequiredData];
        expectedResult = service.addTeamSkillToCollectionIfMissing(teamSkillCollection, undefined, null);
        expect(expectedResult).toEqual(teamSkillCollection);
      });
    });

    describe('compareTeamSkill', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareTeamSkill(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareTeamSkill(entity1, entity2);
        const compareResult2 = service.compareTeamSkill(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareTeamSkill(entity1, entity2);
        const compareResult2 = service.compareTeamSkill(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareTeamSkill(entity1, entity2);
        const compareResult2 = service.compareTeamSkill(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
