import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IBadgeSkill, BadgeSkill } from '../badge-skill.model';

import { BadgeSkillService } from './badge-skill.service';

describe('BadgeSkill Service', () => {
  let service: BadgeSkillService;
  let httpMock: HttpTestingController;
  let elemDefault: IBadgeSkill;
  let expectedResult: IBadgeSkill | IBadgeSkill[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(BadgeSkillService);
    httpMock = TestBed.inject(HttpTestingController);

    elemDefault = {
      id: 0,
    };
  });

  describe('Service methods', () => {
    it('should find an element', () => {
      const returnedFromService = Object.assign({}, elemDefault);

      service.find(123).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(elemDefault);
    });

    it('should create a BadgeSkill', () => {
      const returnedFromService = Object.assign(
        {
          id: 0,
        },
        elemDefault
      );

      const expected = Object.assign({}, returnedFromService);

      service.create(new BadgeSkill()).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a BadgeSkill', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
        },
        elemDefault
      );

      const expected = Object.assign({}, returnedFromService);

      service.update(expected).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a BadgeSkill', () => {
      const patchObject = Object.assign({}, new BadgeSkill());

      const returnedFromService = Object.assign(patchObject, elemDefault);

      const expected = Object.assign({}, returnedFromService);

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of BadgeSkill', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
        },
        elemDefault
      );

      const expected = Object.assign({}, returnedFromService);

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toContainEqual(expected);
    });

    it('should delete a BadgeSkill', () => {
      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult);
    });

    describe('addBadgeSkillToCollectionIfMissing', () => {
      it('should add a BadgeSkill to an empty array', () => {
        const badgeSkill: IBadgeSkill = { id: 123 };
        expectedResult = service.addBadgeSkillToCollectionIfMissing([], badgeSkill);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(badgeSkill);
      });

      it('should not add a BadgeSkill to an array that contains it', () => {
        const badgeSkill: IBadgeSkill = { id: 123 };
        const badgeSkillCollection: IBadgeSkill[] = [
          {
            ...badgeSkill,
          },
          { id: 456 },
        ];
        expectedResult = service.addBadgeSkillToCollectionIfMissing(badgeSkillCollection, badgeSkill);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a BadgeSkill to an array that doesn't contain it", () => {
        const badgeSkill: IBadgeSkill = { id: 123 };
        const badgeSkillCollection: IBadgeSkill[] = [{ id: 456 }];
        expectedResult = service.addBadgeSkillToCollectionIfMissing(badgeSkillCollection, badgeSkill);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(badgeSkill);
      });

      it('should add only unique BadgeSkill to an array', () => {
        const badgeSkillArray: IBadgeSkill[] = [{ id: 123 }, { id: 456 }, { id: 19752 }];
        const badgeSkillCollection: IBadgeSkill[] = [{ id: 123 }];
        expectedResult = service.addBadgeSkillToCollectionIfMissing(badgeSkillCollection, ...badgeSkillArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const badgeSkill: IBadgeSkill = { id: 123 };
        const badgeSkill2: IBadgeSkill = { id: 456 };
        expectedResult = service.addBadgeSkillToCollectionIfMissing([], badgeSkill, badgeSkill2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(badgeSkill);
        expect(expectedResult).toContain(badgeSkill2);
      });

      it('should accept null and undefined values', () => {
        const badgeSkill: IBadgeSkill = { id: 123 };
        expectedResult = service.addBadgeSkillToCollectionIfMissing([], null, badgeSkill, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(badgeSkill);
      });

      it('should return initial array if no BadgeSkill is added', () => {
        const badgeSkillCollection: IBadgeSkill[] = [{ id: 123 }];
        expectedResult = service.addBadgeSkillToCollectionIfMissing(badgeSkillCollection, undefined, null);
        expect(expectedResult).toEqual(badgeSkillCollection);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
