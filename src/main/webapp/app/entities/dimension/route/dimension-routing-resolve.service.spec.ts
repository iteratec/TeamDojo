jest.mock('@angular/router');

import { TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of } from 'rxjs';

import { IDimension, Dimension } from '../dimension.model';
import { DimensionService } from '../service/dimension.service';

import { DimensionRoutingResolveService } from './dimension-routing-resolve.service';

describe('Service Tests', () => {
  describe('Dimension routing resolve service', () => {
    let mockRouter: Router;
    let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
    let routingResolveService: DimensionRoutingResolveService;
    let service: DimensionService;
    let resultDimension: IDimension | undefined;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        providers: [Router, ActivatedRouteSnapshot],
      });
      mockRouter = TestBed.inject(Router);
      mockActivatedRouteSnapshot = TestBed.inject(ActivatedRouteSnapshot);
      routingResolveService = TestBed.inject(DimensionRoutingResolveService);
      service = TestBed.inject(DimensionService);
      resultDimension = undefined;
    });

    describe('resolve', () => {
      it('should return IDimension returned by find', () => {
        // GIVEN
        service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
        mockActivatedRouteSnapshot.params = { id: 123 };

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultDimension = result;
        });

        // THEN
        expect(service.find).toBeCalledWith(123);
        expect(resultDimension).toEqual({ id: 123 });
      });

      it('should return new IDimension if id is not provided', () => {
        // GIVEN
        service.find = jest.fn();
        mockActivatedRouteSnapshot.params = {};

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultDimension = result;
        });

        // THEN
        expect(service.find).not.toBeCalled();
        expect(resultDimension).toEqual(new Dimension());
      });

      it('should route to 404 page if data not found in server', () => {
        // GIVEN
        spyOn(service, 'find').and.returnValue(of(new HttpResponse({ body: null })));
        mockActivatedRouteSnapshot.params = { id: 123 };

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultDimension = result;
        });

        // THEN
        expect(service.find).toBeCalledWith(123);
        expect(resultDimension).toEqual(undefined);
        expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
      });
    });
  });
});
