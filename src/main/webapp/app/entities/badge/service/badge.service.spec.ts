import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import dayjs from 'dayjs/esm';

import { DATE_TIME_FORMAT } from 'app/config/input.constants';
import { IBadge, Badge } from '../badge.model';

import { BadgeService } from './badge.service';

describe('Badge Service', () => {
  let service: BadgeService;
  let httpMock: HttpTestingController;
  let elemDefault: IBadge;
  let expectedResult: IBadge | IBadge[] | boolean | null;
  let currentDate: dayjs.Dayjs;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(BadgeService);
    httpMock = TestBed.inject(HttpTestingController);
    currentDate = dayjs();

    elemDefault = {
      id: 0,
      titleEN: 'AAAAAAA',
      titleDE: 'AAAAAAA',
      descriptionEN: 'AAAAAAA',
      descriptionDE: 'AAAAAAA',
      availableUntil: currentDate,
      availableAmount: 0,
      requiredScore: 0,
      instantMultiplier: 0,
      completionBonus: 0,
      createdAt: currentDate,
      updatedAt: currentDate,
    };
  });

  describe('Service methods', () => {
    it('should find an element', () => {
      const returnedFromService = Object.assign(
        {
          availableUntil: currentDate.format(DATE_TIME_FORMAT),
          createdAt: currentDate.format(DATE_TIME_FORMAT),
          updatedAt: currentDate.format(DATE_TIME_FORMAT),
        },
        elemDefault
      );

      service.find(123).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(elemDefault);
    });

    it('should create a Badge', () => {
      const returnedFromService = Object.assign(
        {
          id: 0,
          availableUntil: currentDate.format(DATE_TIME_FORMAT),
          createdAt: currentDate.format(DATE_TIME_FORMAT),
          updatedAt: currentDate.format(DATE_TIME_FORMAT),
        },
        elemDefault
      );

      const expected = Object.assign(
        {
          availableUntil: currentDate,
          createdAt: currentDate,
          updatedAt: currentDate,
        },
        returnedFromService
      );

      service.create(new Badge()).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a Badge', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          titleEN: 'BBBBBB',
          titleDE: 'BBBBBB',
          descriptionEN: 'BBBBBB',
          descriptionDE: 'BBBBBB',
          availableUntil: currentDate.format(DATE_TIME_FORMAT),
          availableAmount: 1,
          requiredScore: 1,
          instantMultiplier: 1,
          completionBonus: 1,
          createdAt: currentDate.format(DATE_TIME_FORMAT),
          updatedAt: currentDate.format(DATE_TIME_FORMAT),
        },
        elemDefault
      );

      const expected = Object.assign(
        {
          availableUntil: currentDate,
          createdAt: currentDate,
          updatedAt: currentDate,
        },
        returnedFromService
      );

      service.update(expected).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a Badge', () => {
      const patchObject = Object.assign(
        {
          titleDE: 'BBBBBB',
          requiredScore: 1,
        },
        new Badge()
      );

      const returnedFromService = Object.assign(patchObject, elemDefault);

      const expected = Object.assign(
        {
          availableUntil: currentDate,
          createdAt: currentDate,
          updatedAt: currentDate,
        },
        returnedFromService
      );

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of Badge', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          titleEN: 'BBBBBB',
          titleDE: 'BBBBBB',
          descriptionEN: 'BBBBBB',
          descriptionDE: 'BBBBBB',
          availableUntil: currentDate.format(DATE_TIME_FORMAT),
          availableAmount: 1,
          requiredScore: 1,
          instantMultiplier: 1,
          completionBonus: 1,
          createdAt: currentDate.format(DATE_TIME_FORMAT),
          updatedAt: currentDate.format(DATE_TIME_FORMAT),
        },
        elemDefault
      );

      const expected = Object.assign(
        {
          availableUntil: currentDate,
          createdAt: currentDate,
          updatedAt: currentDate,
        },
        returnedFromService
      );

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toContainEqual(expected);
    });

    it('should delete a Badge', () => {
      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult);
    });

    describe('addBadgeToCollectionIfMissing', () => {
      it('should add a Badge to an empty array', () => {
        const badge: IBadge = { id: 123 };
        expectedResult = service.addBadgeToCollectionIfMissing([], badge);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(badge);
      });

      it('should not add a Badge to an array that contains it', () => {
        const badge: IBadge = { id: 123 };
        const badgeCollection: IBadge[] = [
          {
            ...badge,
          },
          { id: 456 },
        ];
        expectedResult = service.addBadgeToCollectionIfMissing(badgeCollection, badge);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a Badge to an array that doesn't contain it", () => {
        const badge: IBadge = { id: 123 };
        const badgeCollection: IBadge[] = [{ id: 456 }];
        expectedResult = service.addBadgeToCollectionIfMissing(badgeCollection, badge);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(badge);
      });

      it('should add only unique Badge to an array', () => {
        const badgeArray: IBadge[] = [{ id: 123 }, { id: 456 }, { id: 5756 }];
        const badgeCollection: IBadge[] = [{ id: 123 }];
        expectedResult = service.addBadgeToCollectionIfMissing(badgeCollection, ...badgeArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const badge: IBadge = { id: 123 };
        const badge2: IBadge = { id: 456 };
        expectedResult = service.addBadgeToCollectionIfMissing([], badge, badge2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(badge);
        expect(expectedResult).toContain(badge2);
      });

      it('should accept null and undefined values', () => {
        const badge: IBadge = { id: 123 };
        expectedResult = service.addBadgeToCollectionIfMissing([], null, badge, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(badge);
      });

      it('should return initial array if no Badge is added', () => {
        const badgeCollection: IBadge[] = [{ id: 123 }];
        expectedResult = service.addBadgeToCollectionIfMissing(badgeCollection, undefined, null);
        expect(expectedResult).toEqual(badgeCollection);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
