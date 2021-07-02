import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { ILevelSkill, LevelSkill } from '../level-skill.model';

import { LevelSkillService } from './level-skill.service';

describe('Service Tests', () => {
  describe('LevelSkill Service', () => {
    let service: LevelSkillService;
    let httpMock: HttpTestingController;
    let elemDefault: ILevelSkill;
    let expectedResult: ILevelSkill | ILevelSkill[] | boolean | null;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      service = TestBed.inject(LevelSkillService);
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

      it('should create a LevelSkill', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.create(new LevelSkill()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a LevelSkill', () => {
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

      it('should partial update a LevelSkill', () => {
        const patchObject = Object.assign({}, new LevelSkill());

        const returnedFromService = Object.assign(patchObject, elemDefault);

        const expected = Object.assign({}, returnedFromService);

        service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PATCH' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of LevelSkill', () => {
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

      it('should delete a LevelSkill', () => {
        service.delete(123).subscribe(resp => (expectedResult = resp.ok));

        const req = httpMock.expectOne({ method: 'DELETE' });
        req.flush({ status: 200 });
        expect(expectedResult);
      });

      describe('addLevelSkillToCollectionIfMissing', () => {
        it('should add a LevelSkill to an empty array', () => {
          const levelSkill: ILevelSkill = { id: 123 };
          expectedResult = service.addLevelSkillToCollectionIfMissing([], levelSkill);
          expect(expectedResult).toHaveLength(1);
          expect(expectedResult).toContain(levelSkill);
        });

        it('should not add a LevelSkill to an array that contains it', () => {
          const levelSkill: ILevelSkill = { id: 123 };
          const levelSkillCollection: ILevelSkill[] = [
            {
              ...levelSkill,
            },
            { id: 456 },
          ];
          expectedResult = service.addLevelSkillToCollectionIfMissing(levelSkillCollection, levelSkill);
          expect(expectedResult).toHaveLength(2);
        });

        it("should add a LevelSkill to an array that doesn't contain it", () => {
          const levelSkill: ILevelSkill = { id: 123 };
          const levelSkillCollection: ILevelSkill[] = [{ id: 456 }];
          expectedResult = service.addLevelSkillToCollectionIfMissing(levelSkillCollection, levelSkill);
          expect(expectedResult).toHaveLength(2);
          expect(expectedResult).toContain(levelSkill);
        });

        it('should add only unique LevelSkill to an array', () => {
          const levelSkillArray: ILevelSkill[] = [{ id: 123 }, { id: 456 }, { id: 48168 }];
          const levelSkillCollection: ILevelSkill[] = [{ id: 123 }];
          expectedResult = service.addLevelSkillToCollectionIfMissing(levelSkillCollection, ...levelSkillArray);
          expect(expectedResult).toHaveLength(3);
        });

        it('should accept varargs', () => {
          const levelSkill: ILevelSkill = { id: 123 };
          const levelSkill2: ILevelSkill = { id: 456 };
          expectedResult = service.addLevelSkillToCollectionIfMissing([], levelSkill, levelSkill2);
          expect(expectedResult).toHaveLength(2);
          expect(expectedResult).toContain(levelSkill);
          expect(expectedResult).toContain(levelSkill2);
        });

        it('should accept null and undefined values', () => {
          const levelSkill: ILevelSkill = { id: 123 };
          expectedResult = service.addLevelSkillToCollectionIfMissing([], null, levelSkill, undefined);
          expect(expectedResult).toHaveLength(1);
          expect(expectedResult).toContain(levelSkill);
        });
      });
    });

    afterEach(() => {
      httpMock.verify();
    });
  });
});
