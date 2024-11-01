import { TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRouteSnapshot, ActivatedRoute, Router, convertToParamMap } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';

import { ITeamGroup, TeamGroup } from '../team-group.model';
import { TeamGroupService } from '../service/team-group.service';

import { TeamGroupRoutingResolveService } from './team-group-routing-resolve.service';

describe('TeamGroup routing resolve service', () => {
  let mockRouter: Router;
  let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
  let routingResolveService: TeamGroupRoutingResolveService;
  let service: TeamGroupService;
  let resultTeamGroup: ITeamGroup | undefined;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: {
            snapshot: {
              paramMap: convertToParamMap({}),
            },
          },
        },
      ],
    });
    mockRouter = TestBed.inject(Router);
    jest.spyOn(mockRouter, 'navigate').mockImplementation(() => Promise.resolve(true));
    mockActivatedRouteSnapshot = TestBed.inject(ActivatedRoute).snapshot;
    routingResolveService = TestBed.inject(TeamGroupRoutingResolveService);
    service = TestBed.inject(TeamGroupService);
    resultTeamGroup = undefined;
  });

  describe('resolve', () => {
    it('should return ITeamGroup returned by find', () => {
      // GIVEN
      service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultTeamGroup = result;
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultTeamGroup).toEqual({ id: 123 });
    });

    it('should return new ITeamGroup if id is not provided', () => {
      // GIVEN
      service.find = jest.fn();
      mockActivatedRouteSnapshot.params = {};

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultTeamGroup = result;
      });

      // THEN
      expect(service.find).not.toBeCalled();
      expect(resultTeamGroup).toEqual(new TeamGroup());
    });

    it('should route to 404 page if data not found in server', () => {
      // GIVEN
      jest.spyOn(service, 'find').mockReturnValue(of(new HttpResponse({ body: null as unknown as TeamGroup })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultTeamGroup = result;
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultTeamGroup).toEqual(undefined);
      expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
    });
  });
});
