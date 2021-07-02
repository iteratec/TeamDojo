jest.mock('@angular/router');

import { TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of } from 'rxjs';

import { ILevel, Level } from '../level.model';
import { LevelService } from '../service/level.service';

import { LevelRoutingResolveService } from './level-routing-resolve.service';

describe('Service Tests', () => {
  describe('Level routing resolve service', () => {
    let mockRouter: Router;
    let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
    let routingResolveService: LevelRoutingResolveService;
    let service: LevelService;
    let resultLevel: ILevel | undefined;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        providers: [Router, ActivatedRouteSnapshot],
      });
      mockRouter = TestBed.inject(Router);
      mockActivatedRouteSnapshot = TestBed.inject(ActivatedRouteSnapshot);
      routingResolveService = TestBed.inject(LevelRoutingResolveService);
      service = TestBed.inject(LevelService);
      resultLevel = undefined;
    });

    describe('resolve', () => {
      it('should return ILevel returned by find', () => {
        // GIVEN
        service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
        mockActivatedRouteSnapshot.params = { id: 123 };

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultLevel = result;
        });

        // THEN
        expect(service.find).toBeCalledWith(123);
        expect(resultLevel).toEqual({ id: 123 });
      });

      it('should return new ILevel if id is not provided', () => {
        // GIVEN
        service.find = jest.fn();
        mockActivatedRouteSnapshot.params = {};

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultLevel = result;
        });

        // THEN
        expect(service.find).not.toBeCalled();
        expect(resultLevel).toEqual(new Level());
      });

      it('should route to 404 page if data not found in server', () => {
        // GIVEN
        spyOn(service, 'find').and.returnValue(of(new HttpResponse({ body: null })));
        mockActivatedRouteSnapshot.params = { id: 123 };

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultLevel = result;
        });

        // THEN
        expect(service.find).toBeCalledWith(123);
        expect(resultLevel).toEqual(undefined);
        expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
      });
    });
  });
});
