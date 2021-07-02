jest.mock('@angular/router');

import { TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of } from 'rxjs';

import { ITraining, Training } from '../training.model';
import { TrainingService } from '../service/training.service';

import { TrainingRoutingResolveService } from './training-routing-resolve.service';

describe('Service Tests', () => {
  describe('Training routing resolve service', () => {
    let mockRouter: Router;
    let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
    let routingResolveService: TrainingRoutingResolveService;
    let service: TrainingService;
    let resultTraining: ITraining | undefined;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        providers: [Router, ActivatedRouteSnapshot],
      });
      mockRouter = TestBed.inject(Router);
      mockActivatedRouteSnapshot = TestBed.inject(ActivatedRouteSnapshot);
      routingResolveService = TestBed.inject(TrainingRoutingResolveService);
      service = TestBed.inject(TrainingService);
      resultTraining = undefined;
    });

    describe('resolve', () => {
      it('should return ITraining returned by find', () => {
        // GIVEN
        service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
        mockActivatedRouteSnapshot.params = { id: 123 };

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultTraining = result;
        });

        // THEN
        expect(service.find).toBeCalledWith(123);
        expect(resultTraining).toEqual({ id: 123 });
      });

      it('should return new ITraining if id is not provided', () => {
        // GIVEN
        service.find = jest.fn();
        mockActivatedRouteSnapshot.params = {};

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultTraining = result;
        });

        // THEN
        expect(service.find).not.toBeCalled();
        expect(resultTraining).toEqual(new Training());
      });

      it('should route to 404 page if data not found in server', () => {
        // GIVEN
        spyOn(service, 'find').and.returnValue(of(new HttpResponse({ body: null })));
        mockActivatedRouteSnapshot.params = { id: 123 };

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultTraining = result;
        });

        // THEN
        expect(service.find).toBeCalledWith(123);
        expect(resultTraining).toEqual(undefined);
        expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
      });
    });
  });
});
