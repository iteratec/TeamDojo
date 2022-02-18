import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import dayjs from 'dayjs/esm';

import { DATE_TIME_FORMAT } from 'app/config/input.constants';
import { IDimension, Dimension } from '../dimension.model';

import { DimensionService } from './dimension.service';

describe('Dimension Service', () => {
  let service: DimensionService;
  let httpMock: HttpTestingController;
  let elemDefault: IDimension;
  let expectedResult: IDimension | IDimension[] | boolean | null;
  let currentDate: dayjs.Dayjs;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(DimensionService);
    httpMock = TestBed.inject(HttpTestingController);
    currentDate = dayjs();

    elemDefault = {
      id: 0,
      title: 'AAAAAAA',
      description: 'AAAAAAA',
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

    it('should create a Dimension', () => {
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

      service.create(new Dimension()).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a Dimension', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          title: 'BBBBBB',
          description: 'BBBBBB',
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

    it('should partial update a Dimension', () => {
      const patchObject = Object.assign(
        {
          description: 'BBBBBB',
          createdAt: currentDate.format(DATE_TIME_FORMAT),
          updatedAt: currentDate.format(DATE_TIME_FORMAT),
        },
        new Dimension()
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

    it('should return a list of Dimension', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          title: 'BBBBBB',
          description: 'BBBBBB',
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

    it('should delete a Dimension', () => {
      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult);
    });

    describe('addDimensionToCollectionIfMissing', () => {
      it('should add a Dimension to an empty array', () => {
        const dimension: IDimension = { id: 123 };
        expectedResult = service.addDimensionToCollectionIfMissing([], dimension);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(dimension);
      });

      it('should not add a Dimension to an array that contains it', () => {
        const dimension: IDimension = { id: 123 };
        const dimensionCollection: IDimension[] = [
          {
            ...dimension,
          },
          { id: 456 },
        ];
        expectedResult = service.addDimensionToCollectionIfMissing(dimensionCollection, dimension);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a Dimension to an array that doesn't contain it", () => {
        const dimension: IDimension = { id: 123 };
        const dimensionCollection: IDimension[] = [{ id: 456 }];
        expectedResult = service.addDimensionToCollectionIfMissing(dimensionCollection, dimension);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(dimension);
      });

      it('should add only unique Dimension to an array', () => {
        const dimensionArray: IDimension[] = [{ id: 123 }, { id: 456 }, { id: 59515 }];
        const dimensionCollection: IDimension[] = [{ id: 123 }];
        expectedResult = service.addDimensionToCollectionIfMissing(dimensionCollection, ...dimensionArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const dimension: IDimension = { id: 123 };
        const dimension2: IDimension = { id: 456 };
        expectedResult = service.addDimensionToCollectionIfMissing([], dimension, dimension2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(dimension);
        expect(expectedResult).toContain(dimension2);
      });

      it('should accept null and undefined values', () => {
        const dimension: IDimension = { id: 123 };
        expectedResult = service.addDimensionToCollectionIfMissing([], null, dimension, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(dimension);
      });

      it('should return initial array if no Dimension is added', () => {
        const dimensionCollection: IDimension[] = [{ id: 123 }];
        expectedResult = service.addDimensionToCollectionIfMissing(dimensionCollection, undefined, null);
        expect(expectedResult).toEqual(dimensionCollection);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
