import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import * as dayjs from 'dayjs';

import { DATE_TIME_FORMAT } from 'app/config/input.constants';
import { ApplicationMode } from 'app/entities/enumerations/application-mode.model';
import { IOrganisation, Organisation } from '../organisation.model';

import { OrganisationService } from './organisation.service';

describe('Service Tests', () => {
  describe('Organisation Service', () => {
    let service: OrganisationService;
    let httpMock: HttpTestingController;
    let elemDefault: IOrganisation;
    let expectedResult: IOrganisation | IOrganisation[] | boolean | null;
    let currentDate: dayjs.Dayjs;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      service = TestBed.inject(OrganisationService);
      httpMock = TestBed.inject(HttpTestingController);
      currentDate = dayjs();

      elemDefault = {
        id: 0,
        title: 'AAAAAAA',
        description: 'AAAAAAA',
        levelUpScore: 0,
        applicationMode: ApplicationMode.PERSON,
        countOfConfirmations: 0,
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

      it('should create a Organisation', () => {
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

        service.create(new Organisation()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a Organisation', () => {
        const returnedFromService = Object.assign(
          {
            id: 1,
            title: 'BBBBBB',
            description: 'BBBBBB',
            levelUpScore: 1,
            applicationMode: 'BBBBBB',
            countOfConfirmations: 1,
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

      it('should partial update a Organisation', () => {
        const patchObject = Object.assign(
          {
            title: 'BBBBBB',
            createdAt: currentDate.format(DATE_TIME_FORMAT),
          },
          new Organisation()
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

      it('should return a list of Organisation', () => {
        const returnedFromService = Object.assign(
          {
            id: 1,
            title: 'BBBBBB',
            description: 'BBBBBB',
            levelUpScore: 1,
            applicationMode: 'BBBBBB',
            countOfConfirmations: 1,
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

      it('should delete a Organisation', () => {
        service.delete(123).subscribe(resp => (expectedResult = resp.ok));

        const req = httpMock.expectOne({ method: 'DELETE' });
        req.flush({ status: 200 });
        expect(expectedResult);
      });

      describe('addOrganisationToCollectionIfMissing', () => {
        it('should add a Organisation to an empty array', () => {
          const organisation: IOrganisation = { id: 123 };
          expectedResult = service.addOrganisationToCollectionIfMissing([], organisation);
          expect(expectedResult).toHaveLength(1);
          expect(expectedResult).toContain(organisation);
        });

        it('should not add a Organisation to an array that contains it', () => {
          const organisation: IOrganisation = { id: 123 };
          const organisationCollection: IOrganisation[] = [
            {
              ...organisation,
            },
            { id: 456 },
          ];
          expectedResult = service.addOrganisationToCollectionIfMissing(organisationCollection, organisation);
          expect(expectedResult).toHaveLength(2);
        });

        it("should add a Organisation to an array that doesn't contain it", () => {
          const organisation: IOrganisation = { id: 123 };
          const organisationCollection: IOrganisation[] = [{ id: 456 }];
          expectedResult = service.addOrganisationToCollectionIfMissing(organisationCollection, organisation);
          expect(expectedResult).toHaveLength(2);
          expect(expectedResult).toContain(organisation);
        });

        it('should add only unique Organisation to an array', () => {
          const organisationArray: IOrganisation[] = [{ id: 123 }, { id: 456 }, { id: 6600 }];
          const organisationCollection: IOrganisation[] = [{ id: 123 }];
          expectedResult = service.addOrganisationToCollectionIfMissing(organisationCollection, ...organisationArray);
          expect(expectedResult).toHaveLength(3);
        });

        it('should accept varargs', () => {
          const organisation: IOrganisation = { id: 123 };
          const organisation2: IOrganisation = { id: 456 };
          expectedResult = service.addOrganisationToCollectionIfMissing([], organisation, organisation2);
          expect(expectedResult).toHaveLength(2);
          expect(expectedResult).toContain(organisation);
          expect(expectedResult).toContain(organisation2);
        });

        it('should accept null and undefined values', () => {
          const organisation: IOrganisation = { id: 123 };
          expectedResult = service.addOrganisationToCollectionIfMissing([], null, organisation, undefined);
          expect(expectedResult).toHaveLength(1);
          expect(expectedResult).toContain(organisation);
        });
      });
    });

    afterEach(() => {
      httpMock.verify();
    });
  });
});
