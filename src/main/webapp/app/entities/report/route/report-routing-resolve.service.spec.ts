jest.mock('@angular/router');

import { TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of } from 'rxjs';

import { IReport, Report } from '../report.model';
import { ReportService } from '../service/report.service';

import { ReportRoutingResolveService } from './report-routing-resolve.service';

describe('Service Tests', () => {
  describe('Report routing resolve service', () => {
    let mockRouter: Router;
    let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
    let routingResolveService: ReportRoutingResolveService;
    let service: ReportService;
    let resultReport: IReport | undefined;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        providers: [Router, ActivatedRouteSnapshot],
      });
      mockRouter = TestBed.inject(Router);
      mockActivatedRouteSnapshot = TestBed.inject(ActivatedRouteSnapshot);
      routingResolveService = TestBed.inject(ReportRoutingResolveService);
      service = TestBed.inject(ReportService);
      resultReport = undefined;
    });

    describe('resolve', () => {
      it('should return IReport returned by find', () => {
        // GIVEN
        service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
        mockActivatedRouteSnapshot.params = { id: 123 };

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultReport = result;
        });

        // THEN
        expect(service.find).toBeCalledWith(123);
        expect(resultReport).toEqual({ id: 123 });
      });

      it('should return new IReport if id is not provided', () => {
        // GIVEN
        service.find = jest.fn();
        mockActivatedRouteSnapshot.params = {};

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultReport = result;
        });

        // THEN
        expect(service.find).not.toBeCalled();
        expect(resultReport).toEqual(new Report());
      });

      it('should route to 404 page if data not found in server', () => {
        // GIVEN
        spyOn(service, 'find').and.returnValue(of(new HttpResponse({ body: null })));
        mockActivatedRouteSnapshot.params = { id: 123 };

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultReport = result;
        });

        // THEN
        expect(service.find).toBeCalledWith(123);
        expect(resultReport).toEqual(undefined);
        expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
      });
    });
  });
});
