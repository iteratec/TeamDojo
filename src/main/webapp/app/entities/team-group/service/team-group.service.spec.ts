import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import dayjs from 'dayjs/esm';

import { DATE_TIME_FORMAT } from 'app/config/input.constants';
import { ITeamGroup, TeamGroup } from '../team-group.model';

import { TeamGroupService } from './team-group.service';

describe('TeamGroup Service', () => {
  let service: TeamGroupService;
  let httpMock: HttpTestingController;
  let elemDefault: ITeamGroup;
  let expectedResult: ITeamGroup | ITeamGroup[] | boolean | null;
  let currentDate: dayjs.Dayjs;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(TeamGroupService);
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

    it('should create a TeamGroup', () => {
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

      service.create(new TeamGroup()).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a TeamGroup', () => {
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

    it('should partial update a TeamGroup', () => {
      const patchObject = Object.assign(
        {
          description: 'BBBBBB',
        },
        new TeamGroup()
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

    it('should return a list of TeamGroup', () => {
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

    it('should delete a TeamGroup', () => {
      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult);
    });

    describe('addTeamGroupToCollectionIfMissing', () => {
      it('should add a TeamGroup to an empty array', () => {
        const teamGroup: ITeamGroup = { id: 123 };
        expectedResult = service.addTeamGroupToCollectionIfMissing([], teamGroup);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(teamGroup);
      });

      it('should not add a TeamGroup to an array that contains it', () => {
        const teamGroup: ITeamGroup = { id: 123 };
        const teamGroupCollection: ITeamGroup[] = [
          {
            ...teamGroup,
          },
          { id: 456 },
        ];
        expectedResult = service.addTeamGroupToCollectionIfMissing(teamGroupCollection, teamGroup);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a TeamGroup to an array that doesn't contain it", () => {
        const teamGroup: ITeamGroup = { id: 123 };
        const teamGroupCollection: ITeamGroup[] = [{ id: 456 }];
        expectedResult = service.addTeamGroupToCollectionIfMissing(teamGroupCollection, teamGroup);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(teamGroup);
      });

      it('should add only unique TeamGroup to an array', () => {
        const teamGroupArray: ITeamGroup[] = [{ id: 123 }, { id: 456 }, { id: 83533 }];
        const teamGroupCollection: ITeamGroup[] = [{ id: 123 }];
        expectedResult = service.addTeamGroupToCollectionIfMissing(teamGroupCollection, ...teamGroupArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const teamGroup: ITeamGroup = { id: 123 };
        const teamGroup2: ITeamGroup = { id: 456 };
        expectedResult = service.addTeamGroupToCollectionIfMissing([], teamGroup, teamGroup2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(teamGroup);
        expect(expectedResult).toContain(teamGroup2);
      });

      it('should accept null and undefined values', () => {
        const teamGroup: ITeamGroup = { id: 123 };
        expectedResult = service.addTeamGroupToCollectionIfMissing([], null, teamGroup, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(teamGroup);
      });

      it('should return initial array if no TeamGroup is added', () => {
        const teamGroupCollection: ITeamGroup[] = [{ id: 123 }];
        expectedResult = service.addTeamGroupToCollectionIfMissing(teamGroupCollection, undefined, null);
        expect(expectedResult).toEqual(teamGroupCollection);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
