import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { IImage } from 'app/entities/image/image.model';
import { ImageService } from 'app/entities/image/service/image.service';
import { IDimension } from 'app/entities/dimension/dimension.model';
import { DimensionService } from 'app/entities/dimension/service/dimension.service';
import { ITeamGroup } from 'app/entities/team-group/team-group.model';
import { TeamGroupService } from 'app/entities/team-group/service/team-group.service';
import { ITeam } from '../team.model';
import { TeamService } from '../service/team.service';
import { TeamFormService } from './team-form.service';

import { TeamUpdateComponent } from './team-update.component';

describe('Team Management Update Component', () => {
  let comp: TeamUpdateComponent;
  let fixture: ComponentFixture<TeamUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let teamFormService: TeamFormService;
  let teamService: TeamService;
  let imageService: ImageService;
  let dimensionService: DimensionService;
  let teamGroupService: TeamGroupService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([]), TeamUpdateComponent],
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
      .overrideTemplate(TeamUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(TeamUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    teamFormService = TestBed.inject(TeamFormService);
    teamService = TestBed.inject(TeamService);
    imageService = TestBed.inject(ImageService);
    dimensionService = TestBed.inject(DimensionService);
    teamGroupService = TestBed.inject(TeamGroupService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call Image query and add missing value', () => {
      const team: ITeam = { id: 456 };
      const image: IImage = { id: 10472 };
      team.image = image;

      const imageCollection: IImage[] = [{ id: 2947 }];
      jest.spyOn(imageService, 'query').mockReturnValue(of(new HttpResponse({ body: imageCollection })));
      const additionalImages = [image];
      const expectedCollection: IImage[] = [...additionalImages, ...imageCollection];
      jest.spyOn(imageService, 'addImageToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ team });
      comp.ngOnInit();

      expect(imageService.query).toHaveBeenCalled();
      expect(imageService.addImageToCollectionIfMissing).toHaveBeenCalledWith(
        imageCollection,
        ...additionalImages.map(expect.objectContaining),
      );
      expect(comp.imagesSharedCollection).toEqual(expectedCollection);
    });

    it('Should call Dimension query and add missing value', () => {
      const team: ITeam = { id: 456 };
      const participations: IDimension[] = [{ id: 1866 }];
      team.participations = participations;

      const dimensionCollection: IDimension[] = [{ id: 20587 }];
      jest.spyOn(dimensionService, 'query').mockReturnValue(of(new HttpResponse({ body: dimensionCollection })));
      const additionalDimensions = [...participations];
      const expectedCollection: IDimension[] = [...additionalDimensions, ...dimensionCollection];
      jest.spyOn(dimensionService, 'addDimensionToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ team });
      comp.ngOnInit();

      expect(dimensionService.query).toHaveBeenCalled();
      expect(dimensionService.addDimensionToCollectionIfMissing).toHaveBeenCalledWith(
        dimensionCollection,
        ...additionalDimensions.map(expect.objectContaining),
      );
      expect(comp.dimensionsSharedCollection).toEqual(expectedCollection);
    });

    it('Should call TeamGroup query and add missing value', () => {
      const team: ITeam = { id: 456 };
      const group: ITeamGroup = { id: 9526 };
      team.group = group;

      const teamGroupCollection: ITeamGroup[] = [{ id: 20370 }];
      jest.spyOn(teamGroupService, 'query').mockReturnValue(of(new HttpResponse({ body: teamGroupCollection })));
      const additionalTeamGroups = [group];
      const expectedCollection: ITeamGroup[] = [...additionalTeamGroups, ...teamGroupCollection];
      jest.spyOn(teamGroupService, 'addTeamGroupToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ team });
      comp.ngOnInit();

      expect(teamGroupService.query).toHaveBeenCalled();
      expect(teamGroupService.addTeamGroupToCollectionIfMissing).toHaveBeenCalledWith(
        teamGroupCollection,
        ...additionalTeamGroups.map(expect.objectContaining),
      );
      expect(comp.teamGroupsSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const team: ITeam = { id: 456 };
      const image: IImage = { id: 8757 };
      team.image = image;
      const participations: IDimension = { id: 31929 };
      team.participations = [participations];
      const group: ITeamGroup = { id: 19697 };
      team.group = group;

      activatedRoute.data = of({ team });
      comp.ngOnInit();

      expect(comp.imagesSharedCollection).toContain(image);
      expect(comp.dimensionsSharedCollection).toContain(participations);
      expect(comp.teamGroupsSharedCollection).toContain(group);
      expect(comp.team).toEqual(team);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ITeam>>();
      const team = { id: 123 };
      jest.spyOn(teamFormService, 'getTeam').mockReturnValue(team);
      jest.spyOn(teamService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ team });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: team }));
      saveSubject.complete();

      // THEN
      expect(teamFormService.getTeam).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(teamService.update).toHaveBeenCalledWith(expect.objectContaining(team));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ITeam>>();
      const team = { id: 123 };
      jest.spyOn(teamFormService, 'getTeam').mockReturnValue({ id: null });
      jest.spyOn(teamService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ team: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: team }));
      saveSubject.complete();

      // THEN
      expect(teamFormService.getTeam).toHaveBeenCalled();
      expect(teamService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ITeam>>();
      const team = { id: 123 };
      jest.spyOn(teamService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ team });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(teamService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Compare relationships', () => {
    describe('compareImage', () => {
      it('Should forward to imageService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(imageService, 'compareImage');
        comp.compareImage(entity, entity2);
        expect(imageService.compareImage).toHaveBeenCalledWith(entity, entity2);
      });
    });

    describe('compareDimension', () => {
      it('Should forward to dimensionService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(dimensionService, 'compareDimension');
        comp.compareDimension(entity, entity2);
        expect(dimensionService.compareDimension).toHaveBeenCalledWith(entity, entity2);
      });
    });

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
