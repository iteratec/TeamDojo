import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { TeamGroupFormService } from './team-group-form.service';
import { TeamGroupService } from '../service/team-group.service';
import { ITeamGroup } from '../team-group.model';

import { TeamGroupUpdateComponent } from './team-group-update.component';

describe('TeamGroup Management Update Component', () => {
  let comp: TeamGroupUpdateComponent;
  let fixture: ComponentFixture<TeamGroupUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let teamGroupFormService: TeamGroupFormService;
  let teamGroupService: TeamGroupService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [TeamGroupUpdateComponent],
      providers: [
        FormBuilder,
        {
          provide: ActivatedRoute,
          useValue: {
            params: from([{}]),
          },
        },
      ],
    })
      .overrideTemplate(TeamGroupUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(TeamGroupUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    teamGroupFormService = TestBed.inject(TeamGroupFormService);
    teamGroupService = TestBed.inject(TeamGroupService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call TeamGroup query and add missing value', () => {
      const teamGroup: ITeamGroup = { id: 456 };
      const parent: ITeamGroup = { id: 56995 };
      teamGroup.parent = parent;

      const teamGroupCollection: ITeamGroup[] = [{ id: 38959 }];
      jest.spyOn(teamGroupService, 'query').mockReturnValue(of(new HttpResponse({ body: teamGroupCollection })));
      const additionalTeamGroups = [parent];
      const expectedCollection: ITeamGroup[] = [...additionalTeamGroups, ...teamGroupCollection];
      jest.spyOn(teamGroupService, 'addTeamGroupToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ teamGroup });
      comp.ngOnInit();

      expect(teamGroupService.query).toHaveBeenCalled();
      expect(teamGroupService.addTeamGroupToCollectionIfMissing).toHaveBeenCalledWith(
        teamGroupCollection,
        ...additionalTeamGroups.map(expect.objectContaining)
      );
      expect(comp.teamGroupsSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const teamGroup: ITeamGroup = { id: 456 };
      const parent: ITeamGroup = { id: 20159 };
      teamGroup.parent = parent;

      activatedRoute.data = of({ teamGroup });
      comp.ngOnInit();

      expect(comp.teamGroupsSharedCollection).toContain(parent);
      expect(comp.teamGroup).toEqual(teamGroup);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ITeamGroup>>();
      const teamGroup = { id: 123 };
      jest.spyOn(teamGroupFormService, 'getTeamGroup').mockReturnValue(teamGroup);
      jest.spyOn(teamGroupService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ teamGroup });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: teamGroup }));
      saveSubject.complete();

      // THEN
      expect(teamGroupFormService.getTeamGroup).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(teamGroupService.update).toHaveBeenCalledWith(expect.objectContaining(teamGroup));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ITeamGroup>>();
      const teamGroup = { id: 123 };
      jest.spyOn(teamGroupFormService, 'getTeamGroup').mockReturnValue({ id: null });
      jest.spyOn(teamGroupService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ teamGroup: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: teamGroup }));
      saveSubject.complete();

      // THEN
      expect(teamGroupFormService.getTeamGroup).toHaveBeenCalled();
      expect(teamGroupService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ITeamGroup>>();
      const teamGroup = { id: 123 };
      jest.spyOn(teamGroupService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ teamGroup });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(teamGroupService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Compare relationships', () => {
    describe('compareTeamGroup', () => {
      it('Should forward to teamGroupService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(teamGroupService, 'compareTeamGroup');
        comp.compareTeamGroup(entity, entity2);
        expect(teamGroupService.compareTeamGroup).toHaveBeenCalledWith(entity, entity2);
      });
    });
  });
});
