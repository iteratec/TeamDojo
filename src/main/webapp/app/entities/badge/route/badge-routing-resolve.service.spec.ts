jest.mock('@angular/router');

import { TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of } from 'rxjs';

import { IBadge, Badge } from '../badge.model';
import { BadgeService } from '../service/badge.service';

import { BadgeRoutingResolveService } from './badge-routing-resolve.service';

describe('Service Tests', () => {
  describe('Badge routing resolve service', () => {
    let mockRouter: Router;
    let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
    let routingResolveService: BadgeRoutingResolveService;
    let service: BadgeService;
    let resultBadge: IBadge | undefined;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        providers: [Router, ActivatedRouteSnapshot],
      });
      mockRouter = TestBed.inject(Router);
      mockActivatedRouteSnapshot = TestBed.inject(ActivatedRouteSnapshot);
      routingResolveService = TestBed.inject(BadgeRoutingResolveService);
      service = TestBed.inject(BadgeService);
      resultBadge = undefined;
    });

    describe('resolve', () => {
      it('should return IBadge returned by find', () => {
        // GIVEN
        service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
        mockActivatedRouteSnapshot.params = { id: 123 };

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultBadge = result;
        });

        // THEN
        expect(service.find).toBeCalledWith(123);
        expect(resultBadge).toEqual({ id: 123 });
      });

      it('should return new IBadge if id is not provided', () => {
        // GIVEN
        service.find = jest.fn();
        mockActivatedRouteSnapshot.params = {};

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultBadge = result;
        });

        // THEN
        expect(service.find).not.toBeCalled();
        expect(resultBadge).toEqual(new Badge());
      });

      it('should route to 404 page if data not found in server', () => {
        // GIVEN
        spyOn(service, 'find').and.returnValue(of(new HttpResponse({ body: null })));
        mockActivatedRouteSnapshot.params = { id: 123 };

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultBadge = result;
        });

        // THEN
        expect(service.find).toBeCalledWith(123);
        expect(resultBadge).toEqual(undefined);
        expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
      });
    });
  });
});
