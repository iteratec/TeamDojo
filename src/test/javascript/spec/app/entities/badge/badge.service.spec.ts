/* tslint:disable max-line-length */
import axios from 'axios';
import sinon from 'sinon';
import dayjs from 'dayjs';

import { DATE_TIME_FORMAT } from '@/shared/date/filters';
import BadgeService from '@/entities/badge/badge.service';
import { Badge } from '@/shared/model/badge.model';

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
  describe('Badge Service', () => {
    let service: BadgeService;
    let elemDefault;
    let currentDate: Date;

    beforeEach(() => {
      service = new BadgeService();
      currentDate = new Date();
      elemDefault = new Badge(0, 'AAAAAAA', 'AAAAAAA', currentDate, 0, 0, 0, 0, currentDate, currentDate);
    });

    describe('Service methods', () => {
      it('should find an element', async () => {
        const returnedFromService = Object.assign(
          {
            availableUntil: dayjs(currentDate).format(DATE_TIME_FORMAT),
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

      it('should create a Badge', async () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
            availableUntil: dayjs(currentDate).format(DATE_TIME_FORMAT),
            createdAt: dayjs(currentDate).format(DATE_TIME_FORMAT),
            updatedAt: dayjs(currentDate).format(DATE_TIME_FORMAT),
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

        axiosStub.post.resolves({ data: returnedFromService });
        return service.create({}).then(res => {
          expect(res).toMatchObject(expected);
        });
      });

      it('should not create a Badge', async () => {
        axiosStub.post.rejects(error);

        return service
          .create({})
          .then()
          .catch(err => {
            expect(err).toMatchObject(error);
          });
      });

      it('should update a Badge', async () => {
        const returnedFromService = Object.assign(
          {
            title: 'BBBBBB',
            description: 'BBBBBB',
            availableUntil: dayjs(currentDate).format(DATE_TIME_FORMAT),
            availableAmount: 1,
            requiredScore: 1,
            instantMultiplier: 1,
            completionBonus: 1,
            createdAt: dayjs(currentDate).format(DATE_TIME_FORMAT),
            updatedAt: dayjs(currentDate).format(DATE_TIME_FORMAT),
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
        axiosStub.put.resolves({ data: returnedFromService });

        return service.update(expected).then(res => {
          expect(res).toMatchObject(expected);
        });
      });

      it('should not update a Badge', async () => {
        axiosStub.put.rejects(error);

        return service
          .update({})
          .then()
          .catch(err => {
            expect(err).toMatchObject(error);
          });
      });

      it('should partial update a Badge', async () => {
        const patchObject = Object.assign(
          {
            description: 'BBBBBB',
            availableAmount: 1,
            requiredScore: 1,
            instantMultiplier: 1,
            completionBonus: 1,
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
        axiosStub.patch.resolves({ data: returnedFromService });

        return service.partialUpdate(patchObject).then(res => {
          expect(res).toMatchObject(expected);
        });
      });

      it('should not partial update a Badge', async () => {
        axiosStub.patch.rejects(error);

        return service
          .partialUpdate({})
          .then()
          .catch(err => {
            expect(err).toMatchObject(error);
          });
      });

      it('should return a list of Badge', async () => {
        const returnedFromService = Object.assign(
          {
            title: 'BBBBBB',
            description: 'BBBBBB',
            availableUntil: dayjs(currentDate).format(DATE_TIME_FORMAT),
            availableAmount: 1,
            requiredScore: 1,
            instantMultiplier: 1,
            completionBonus: 1,
            createdAt: dayjs(currentDate).format(DATE_TIME_FORMAT),
            updatedAt: dayjs(currentDate).format(DATE_TIME_FORMAT),
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
        axiosStub.get.resolves([returnedFromService]);
        return service.retrieve({ sort: {}, page: 0, size: 10 }).then(res => {
          expect(res).toContainEqual(expected);
        });
      });

      it('should not return a list of Badge', async () => {
        axiosStub.get.rejects(error);

        return service
          .retrieve()
          .then()
          .catch(err => {
            expect(err).toMatchObject(error);
          });
      });

      it('should delete a Badge', async () => {
        axiosStub.delete.resolves({ ok: true });
        return service.delete(123).then(res => {
          expect(res.ok).toBeTruthy();
        });
      });

      it('should not delete a Badge', async () => {
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
