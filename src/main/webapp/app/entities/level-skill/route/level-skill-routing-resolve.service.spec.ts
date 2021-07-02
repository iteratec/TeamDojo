jest.mock('@angular/router');

import { TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of } from 'rxjs';

import { ILevelSkill, LevelSkill } from '../level-skill.model';
import { LevelSkillService } from '../service/level-skill.service';

import { LevelSkillRoutingResolveService } from './level-skill-routing-resolve.service';

describe('Service Tests', () => {
  describe('LevelSkill routing resolve service', () => {
    let mockRouter: Router;
    let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
    let routingResolveService: LevelSkillRoutingResolveService;
    let service: LevelSkillService;
    let resultLevelSkill: ILevelSkill | undefined;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        providers: [Router, ActivatedRouteSnapshot],
      });
      mockRouter = TestBed.inject(Router);
      mockActivatedRouteSnapshot = TestBed.inject(ActivatedRouteSnapshot);
      routingResolveService = TestBed.inject(LevelSkillRoutingResolveService);
      service = TestBed.inject(LevelSkillService);
      resultLevelSkill = undefined;
    });

    describe('resolve', () => {
      it('should return ILevelSkill returned by find', () => {
        // GIVEN
        service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
        mockActivatedRouteSnapshot.params = { id: 123 };

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultLevelSkill = result;
        });

        // THEN
        expect(service.find).toBeCalledWith(123);
        expect(resultLevelSkill).toEqual({ id: 123 });
      });

      it('should return new ILevelSkill if id is not provided', () => {
        // GIVEN
        service.find = jest.fn();
        mockActivatedRouteSnapshot.params = {};

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultLevelSkill = result;
        });

        // THEN
        expect(service.find).not.toBeCalled();
        expect(resultLevelSkill).toEqual(new LevelSkill());
      });

      it('should route to 404 page if data not found in server', () => {
        // GIVEN
        spyOn(service, 'find').and.returnValue(of(new HttpResponse({ body: null })));
        mockActivatedRouteSnapshot.params = { id: 123 };

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultLevelSkill = result;
        });

        // THEN
        expect(service.find).toBeCalledWith(123);
        expect(resultLevelSkill).toEqual(undefined);
        expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
      });
    });
  });
});
