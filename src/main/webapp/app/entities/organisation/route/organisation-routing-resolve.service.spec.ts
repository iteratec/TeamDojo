jest.mock('@angular/router');

import { TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of } from 'rxjs';

import { IOrganisation, Organisation } from '../organisation.model';
import { OrganisationService } from '../service/organisation.service';

import { OrganisationRoutingResolveService } from './organisation-routing-resolve.service';

describe('Service Tests', () => {
  describe('Organisation routing resolve service', () => {
    let mockRouter: Router;
    let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
    let routingResolveService: OrganisationRoutingResolveService;
    let service: OrganisationService;
    let resultOrganisation: IOrganisation | undefined;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        providers: [Router, ActivatedRouteSnapshot],
      });
      mockRouter = TestBed.inject(Router);
      mockActivatedRouteSnapshot = TestBed.inject(ActivatedRouteSnapshot);
      routingResolveService = TestBed.inject(OrganisationRoutingResolveService);
      service = TestBed.inject(OrganisationService);
      resultOrganisation = undefined;
    });

    describe('resolve', () => {
      it('should return IOrganisation returned by find', () => {
        // GIVEN
        service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
        mockActivatedRouteSnapshot.params = { id: 123 };

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultOrganisation = result;
        });

        // THEN
        expect(service.find).toBeCalledWith(123);
        expect(resultOrganisation).toEqual({ id: 123 });
      });

      it('should return new IOrganisation if id is not provided', () => {
        // GIVEN
        service.find = jest.fn();
        mockActivatedRouteSnapshot.params = {};

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultOrganisation = result;
        });

        // THEN
        expect(service.find).not.toBeCalled();
        expect(resultOrganisation).toEqual(new Organisation());
      });

      it('should route to 404 page if data not found in server', () => {
        // GIVEN
        spyOn(service, 'find').and.returnValue(of(new HttpResponse({ body: null })));
        mockActivatedRouteSnapshot.params = { id: 123 };

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultOrganisation = result;
        });

        // THEN
        expect(service.find).toBeCalledWith(123);
        expect(resultOrganisation).toEqual(undefined);
        expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
      });
    });
  });
});
