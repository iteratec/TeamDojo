import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { TeamGroupService } from '../service/team-group.service';
import { ITeamGroup, TeamGroup } from '../team-group.model';

import { TeamGroupUpdateComponent } from './team-group-update.component';

describe('TeamGroup Management Update Component', () => {
  let comp: TeamGroupUpdateComponent;
  let fixture: ComponentFixture<TeamGroupUpdateComponent>;
  let activatedRoute: ActivatedRoute;
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
      expect(teamGroupService.addTeamGroupToCollectionIfMissing).toHaveBeenCalledWith(teamGroupCollection, ...additionalTeamGroups);
      expect(comp.teamGroupsSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const teamGroup: ITeamGroup = { id: 456 };
      const parent: ITeamGroup = { id: 20159 };
      teamGroup.parent = parent;

      activatedRoute.data = of({ teamGroup });
      comp.ngOnInit();

      expect(comp.editForm.value).toEqual(expect.objectContaining(teamGroup));
      expect(comp.teamGroupsSharedCollection).toContain(parent);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<TeamGroup>>();
      const teamGroup = { id: 123 };
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
      expect(comp.previousState).toHaveBeenCalled();
      expect(teamGroupService.update).toHaveBeenCalledWith(teamGroup);
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<TeamGroup>>();
      const teamGroup = new TeamGroup();
      jest.spyOn(teamGroupService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ teamGroup });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: teamGroup }));
      saveSubject.complete();

      // THEN
      expect(teamGroupService.create).toHaveBeenCalledWith(teamGroup);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<TeamGroup>>();
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
      expect(teamGroupService.update).toHaveBeenCalledWith(teamGroup);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Tracking relationships identifiers', () => {
    describe('trackTeamGroupById', () => {
      it('Should return tracked TeamGroup primary key', () => {
        const entity = { id: 123 };
        const trackResult = comp.trackTeamGroupById(0, entity);
        expect(trackResult).toEqual(entity.id);
      });
    });
  });
});
