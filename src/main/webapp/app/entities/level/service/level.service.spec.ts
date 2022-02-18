import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import dayjs from 'dayjs/esm';

import { DATE_TIME_FORMAT } from 'app/config/input.constants';
import { ILevel, Level } from '../level.model';

import { LevelService } from './level.service';

describe('Level Service', () => {
  let service: LevelService;
  let httpMock: HttpTestingController;
  let elemDefault: ILevel;
  let expectedResult: ILevel | ILevel[] | boolean | null;
  let currentDate: dayjs.Dayjs;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(LevelService);
    httpMock = TestBed.inject(HttpTestingController);
    currentDate = dayjs();

    elemDefault = {
      id: 0,
      title: 'AAAAAAA',
      description: 'AAAAAAA',
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

    it('should create a Level', () => {
      const returnedFromService = Object.assign(
        {
          id: 0,
          createdAt: currentDate.format(DATE_TIME_FORMAT),
          updatedAt: currentDate.format(DATE_TIME_FORMAT),
        },
        elemDefault
      );

      const expected = Object.assign(
        {
          createdAt: currentDate,
          updatedAt: currentDate,
        },
        returnedFromService
      );

      service.create(new Level()).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a Level', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          title: 'BBBBBB',
          description: 'BBBBBB',
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

    it('should partial update a Level', () => {
      const patchObject = Object.assign(
        {
          title: 'BBBBBB',
          requiredScore: 1,
          instantMultiplier: 1,
          createdAt: currentDate.format(DATE_TIME_FORMAT),
        },
        new Level()
      );

      const returnedFromService = Object.assign(patchObject, elemDefault);

      const expected = Object.assign(
        {
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

    it('should return a list of Level', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          title: 'BBBBBB',
          description: 'BBBBBB',
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

    it('should delete a Level', () => {
      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult);
    });

    describe('addLevelToCollectionIfMissing', () => {
      it('should add a Level to an empty array', () => {
        const level: ILevel = { id: 123 };
        expectedResult = service.addLevelToCollectionIfMissing([], level);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(level);
      });

      it('should not add a Level to an array that contains it', () => {
        const level: ILevel = { id: 123 };
        const levelCollection: ILevel[] = [
          {
            ...level,
          },
          { id: 456 },
        ];
        expectedResult = service.addLevelToCollectionIfMissing(levelCollection, level);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a Level to an array that doesn't contain it", () => {
        const level: ILevel = { id: 123 };
        const levelCollection: ILevel[] = [{ id: 456 }];
        expectedResult = service.addLevelToCollectionIfMissing(levelCollection, level);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(level);
      });

      it('should add only unique Level to an array', () => {
        const levelArray: ILevel[] = [{ id: 123 }, { id: 456 }, { id: 25698 }];
        const levelCollection: ILevel[] = [{ id: 123 }];
        expectedResult = service.addLevelToCollectionIfMissing(levelCollection, ...levelArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const level: ILevel = { id: 123 };
        const level2: ILevel = { id: 456 };
        expectedResult = service.addLevelToCollectionIfMissing([], level, level2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(level);
        expect(expectedResult).toContain(level2);
      });

      it('should accept null and undefined values', () => {
        const level: ILevel = { id: 123 };
        expectedResult = service.addLevelToCollectionIfMissing([], null, level, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(level);
      });

      it('should return initial array if no Level is added', () => {
        const levelCollection: ILevel[] = [{ id: 123 }];
        expectedResult = service.addLevelToCollectionIfMissing(levelCollection, undefined, null);
        expect(expectedResult).toEqual(levelCollection);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
