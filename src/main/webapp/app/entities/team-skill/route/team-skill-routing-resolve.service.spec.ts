jest.mock('@angular/router');

import { TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of } from 'rxjs';

import { ITeamSkill, TeamSkill } from '../team-skill.model';
import { TeamSkillService } from '../service/team-skill.service';

import { TeamSkillRoutingResolveService } from './team-skill-routing-resolve.service';

describe('Service Tests', () => {
  describe('TeamSkill routing resolve service', () => {
    let mockRouter: Router;
    let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
    let routingResolveService: TeamSkillRoutingResolveService;
    let service: TeamSkillService;
    let resultTeamSkill: ITeamSkill | undefined;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        providers: [Router, ActivatedRouteSnapshot],
      });
      mockRouter = TestBed.inject(Router);
      mockActivatedRouteSnapshot = TestBed.inject(ActivatedRouteSnapshot);
      routingResolveService = TestBed.inject(TeamSkillRoutingResolveService);
      service = TestBed.inject(TeamSkillService);
      resultTeamSkill = undefined;
    });

    describe('resolve', () => {
      it('should return ITeamSkill returned by find', () => {
        // GIVEN
        service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
        mockActivatedRouteSnapshot.params = { id: 123 };

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultTeamSkill = result;
        });

        // THEN
        expect(service.find).toBeCalledWith(123);
        expect(resultTeamSkill).toEqual({ id: 123 });
      });

      it('should return new ITeamSkill if id is not provided', () => {
        // GIVEN
        service.find = jest.fn();
        mockActivatedRouteSnapshot.params = {};

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultTeamSkill = result;
        });

        // THEN
        expect(service.find).not.toBeCalled();
        expect(resultTeamSkill).toEqual(new TeamSkill());
      });

      it('should route to 404 page if data not found in server', () => {
        // GIVEN
        spyOn(service, 'find').and.returnValue(of(new HttpResponse({ body: null })));
        mockActivatedRouteSnapshot.params = { id: 123 };

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultTeamSkill = result;
        });

        // THEN
        expect(service.find).toBeCalledWith(123);
        expect(resultTeamSkill).toEqual(undefined);
        expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
      });
    });
  });
});