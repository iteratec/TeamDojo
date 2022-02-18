import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import dayjs from 'dayjs/esm';

import { DATE_TIME_FORMAT } from 'app/config/input.constants';
import { SkillStatus } from 'app/entities/enumerations/skill-status.model';
import { ITeamSkill, TeamSkill } from '../team-skill.model';

import { TeamSkillService } from './team-skill.service';

describe('TeamSkill Service', () => {
  let service: TeamSkillService;
  let httpMock: HttpTestingController;
  let elemDefault: ITeamSkill;
  let expectedResult: ITeamSkill | ITeamSkill[] | boolean | null;
  let currentDate: dayjs.Dayjs;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(TeamSkillService);
    httpMock = TestBed.inject(HttpTestingController);
    currentDate = dayjs();

    elemDefault = {
      id: 0,
      completedAt: currentDate,
      verifiedAt: currentDate,
      irrelevant: false,
      skillStatus: SkillStatus.OPEN,
      note: 'AAAAAAA',
      vote: 0,
      voters: 'AAAAAAA',
      createdAt: currentDate,
      updatedAt: currentDate,
    };
  });

  describe('Service methods', () => {
    it('should find an element', () => {
      const returnedFromService = Object.assign(
        {
          completedAt: currentDate.format(DATE_TIME_FORMAT),
          verifiedAt: currentDate.format(DATE_TIME_FORMAT),
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

    it('should create a TeamSkill', () => {
      const returnedFromService = Object.assign(
        {
          id: 0,
          completedAt: currentDate.format(DATE_TIME_FORMAT),
          verifiedAt: currentDate.format(DATE_TIME_FORMAT),
          createdAt: currentDate.format(DATE_TIME_FORMAT),
          updatedAt: currentDate.format(DATE_TIME_FORMAT),
        },
        elemDefault
      );

      const expected = Object.assign(
        {
          completedAt: currentDate,
          verifiedAt: currentDate,
          createdAt: currentDate,
          updatedAt: currentDate,
        },
        returnedFromService
      );

      service.create(new TeamSkill()).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a TeamSkill', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          completedAt: currentDate.format(DATE_TIME_FORMAT),
          verifiedAt: currentDate.format(DATE_TIME_FORMAT),
          irrelevant: true,
          skillStatus: 'BBBBBB',
          note: 'BBBBBB',
          vote: 1,
          voters: 'BBBBBB',
          createdAt: currentDate.format(DATE_TIME_FORMAT),
          updatedAt: currentDate.format(DATE_TIME_FORMAT),
        },
        elemDefault
      );

      const expected = Object.assign(
        {
          completedAt: currentDate,
          verifiedAt: currentDate,
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

    it('should partial update a TeamSkill', () => {
      const patchObject = Object.assign(
        {
          completedAt: currentDate.format(DATE_TIME_FORMAT),
          irrelevant: true,
          vote: 1,
        },
        new TeamSkill()
      );

      const returnedFromService = Object.assign(patchObject, elemDefault);

      const expected = Object.assign(
        {
          completedAt: currentDate,
          verifiedAt: currentDate,
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

    it('should return a list of TeamSkill', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          completedAt: currentDate.format(DATE_TIME_FORMAT),
          verifiedAt: currentDate.format(DATE_TIME_FORMAT),
          irrelevant: true,
          skillStatus: 'BBBBBB',
          note: 'BBBBBB',
          vote: 1,
          voters: 'BBBBBB',
          createdAt: currentDate.format(DATE_TIME_FORMAT),
          updatedAt: currentDate.format(DATE_TIME_FORMAT),
        },
        elemDefault
      );

      const expected = Object.assign(
        {
          completedAt: currentDate,
          verifiedAt: currentDate,
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

    it('should delete a TeamSkill', () => {
      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult);
    });

    describe('addTeamSkillToCollectionIfMissing', () => {
      it('should add a TeamSkill to an empty array', () => {
        const teamSkill: ITeamSkill = { id: 123 };
        expectedResult = service.addTeamSkillToCollectionIfMissing([], teamSkill);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(teamSkill);
      });

      it('should not add a TeamSkill to an array that contains it', () => {
        const teamSkill: ITeamSkill = { id: 123 };
        const teamSkillCollection: ITeamSkill[] = [
          {
            ...teamSkill,
          },
          { id: 456 },
        ];
        expectedResult = service.addTeamSkillToCollectionIfMissing(teamSkillCollection, teamSkill);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a TeamSkill to an array that doesn't contain it", () => {
        const teamSkill: ITeamSkill = { id: 123 };
        const teamSkillCollection: ITeamSkill[] = [{ id: 456 }];
        expectedResult = service.addTeamSkillToCollectionIfMissing(teamSkillCollection, teamSkill);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(teamSkill);
      });

      it('should add only unique TeamSkill to an array', () => {
        const teamSkillArray: ITeamSkill[] = [{ id: 123 }, { id: 456 }, { id: 51450 }];
        const teamSkillCollection: ITeamSkill[] = [{ id: 123 }];
        expectedResult = service.addTeamSkillToCollectionIfMissing(teamSkillCollection, ...teamSkillArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const teamSkill: ITeamSkill = { id: 123 };
        const teamSkill2: ITeamSkill = { id: 456 };
        expectedResult = service.addTeamSkillToCollectionIfMissing([], teamSkill, teamSkill2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(teamSkill);
        expect(expectedResult).toContain(teamSkill2);
      });

      it('should accept null and undefined values', () => {
        const teamSkill: ITeamSkill = { id: 123 };
        expectedResult = service.addTeamSkillToCollectionIfMissing([], null, teamSkill, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(teamSkill);
      });

      it('should return initial array if no TeamSkill is added', () => {
        const teamSkillCollection: ITeamSkill[] = [{ id: 123 }];
        expectedResult = service.addTeamSkillToCollectionIfMissing(teamSkillCollection, undefined, null);
        expect(expectedResult).toEqual(teamSkillCollection);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
