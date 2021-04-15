/* tslint:disable max-line-length */
import axios from 'axios';
import sinon from 'sinon';
import dayjs from 'dayjs';

import { DATE_TIME_FORMAT } from '@/shared/date/filters';
import TeamSkillService from '@/entities/team-skill/team-skill.service';
import { TeamSkill } from '@/shared/model/team-skill.model';

const error = {
  response: {
    status: null,
    data: {
      type: null,
    },
  },
};

const axiosStub = {
  get: sinon.stub(axios, 'get'),
  post: sinon.stub(axios, 'post'),
  put: sinon.stub(axios, 'put'),
  patch: sinon.stub(axios, 'patch'),
  delete: sinon.stub(axios, 'delete'),
};

describe('Service Tests', () => {
  describe('TeamSkill Service', () => {
    let service: TeamSkillService;
    let elemDefault;
    let currentDate: Date;

    beforeEach(() => {
      service = new TeamSkillService();
      currentDate = new Date();
      elemDefault = new TeamSkill(0, currentDate, currentDate, false, 'AAAAAAA', 0, 'AAAAAAA', currentDate, currentDate);
    });

    describe('Service methods', () => {
      it('should find an element', async () => {
        const returnedFromService = Object.assign(
          {
            completedAt: dayjs(currentDate).format(DATE_TIME_FORMAT),
            verifiedAt: dayjs(currentDate).format(DATE_TIME_FORMAT),
            createdAt: dayjs(currentDate).format(DATE_TIME_FORMAT),
            updatedAt: dayjs(currentDate).format(DATE_TIME_FORMAT),
          },
          elemDefault
        );
        axiosStub.get.resolves({ data: returnedFromService });

        return service.find(123).then(res => {
          expect(res).toMatchObject(elemDefault);
        });
      });

      it('should not find an element', async () => {
        axiosStub.get.rejects(error);
        return service
          .find(123)
          .then()
          .catch(err => {
            expect(err).toMatchObject(error);
          });
      });

      it('should create a TeamSkill', async () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
            completedAt: dayjs(currentDate).format(DATE_TIME_FORMAT),
            verifiedAt: dayjs(currentDate).format(DATE_TIME_FORMAT),
            createdAt: dayjs(currentDate).format(DATE_TIME_FORMAT),
            updatedAt: dayjs(currentDate).format(DATE_TIME_FORMAT),
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

        axiosStub.post.resolves({ data: returnedFromService });
        return service.create({}).then(res => {
          expect(res).toMatchObject(expected);
        });
      });

      it('should not create a TeamSkill', async () => {
        axiosStub.post.rejects(error);

        return service
          .create({})
          .then()
          .catch(err => {
            expect(err).toMatchObject(error);
          });
      });

      it('should update a TeamSkill', async () => {
        const returnedFromService = Object.assign(
          {
            completedAt: dayjs(currentDate).format(DATE_TIME_FORMAT),
            verifiedAt: dayjs(currentDate).format(DATE_TIME_FORMAT),
            irrelevant: true,
            note: 'BBBBBB',
            vote: 1,
            voters: 'BBBBBB',
            createdAt: dayjs(currentDate).format(DATE_TIME_FORMAT),
            updatedAt: dayjs(currentDate).format(DATE_TIME_FORMAT),
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
        axiosStub.put.resolves({ data: returnedFromService });

        return service.update(expected).then(res => {
          expect(res).toMatchObject(expected);
        });
      });

      it('should not update a TeamSkill', async () => {
        axiosStub.put.rejects(error);

        return service
          .update({})
          .then()
          .catch(err => {
            expect(err).toMatchObject(error);
          });
      });

      it('should partial update a TeamSkill', async () => {
        const patchObject = Object.assign(
          {
            completedAt: dayjs(currentDate).format(DATE_TIME_FORMAT),
            verifiedAt: dayjs(currentDate).format(DATE_TIME_FORMAT),
            vote: 1,
            createdAt: dayjs(currentDate).format(DATE_TIME_FORMAT),
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
        axiosStub.patch.resolves({ data: returnedFromService });

        return service.partialUpdate(patchObject).then(res => {
          expect(res).toMatchObject(expected);
        });
      });

      it('should not partial update a TeamSkill', async () => {
        axiosStub.patch.rejects(error);

        return service
          .partialUpdate({})
          .then()
          .catch(err => {
            expect(err).toMatchObject(error);
          });
      });

      it('should return a list of TeamSkill', async () => {
        const returnedFromService = Object.assign(
          {
            completedAt: dayjs(currentDate).format(DATE_TIME_FORMAT),
            verifiedAt: dayjs(currentDate).format(DATE_TIME_FORMAT),
            irrelevant: true,
            note: 'BBBBBB',
            vote: 1,
            voters: 'BBBBBB',
            createdAt: dayjs(currentDate).format(DATE_TIME_FORMAT),
            updatedAt: dayjs(currentDate).format(DATE_TIME_FORMAT),
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
        axiosStub.get.resolves([returnedFromService]);
        return service.retrieve({ sort: {}, page: 0, size: 10 }).then(res => {
          expect(res).toContainEqual(expected);
        });
      });

      it('should not return a list of TeamSkill', async () => {
        axiosStub.get.rejects(error);

        return service
          .retrieve()
          .then()
          .catch(err => {
            expect(err).toMatchObject(error);
          });
      });

      it('should delete a TeamSkill', async () => {
        axiosStub.delete.resolves({ ok: true });
        return service.delete(123).then(res => {
          expect(res.ok).toBeTruthy();
        });
      });

      it('should not delete a TeamSkill', async () => {
        axiosStub.delete.rejects(error);

        return service
          .delete(123)
          .then()
          .catch(err => {
            expect(err).toMatchObject(error);
          });
      });
    });
  });
});
