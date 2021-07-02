jest.mock('@angular/router');

import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject } from 'rxjs';

import { TeamService } from '../service/team.service';
import { ITeam, Team } from '../team.model';
import { IImage } from 'app/entities/image/image.model';
import { ImageService } from 'app/entities/image/service/image.service';
import { IDimension } from 'app/entities/dimension/dimension.model';
import { DimensionService } from 'app/entities/dimension/service/dimension.service';

import { TeamUpdateComponent } from './team-update.component';

describe('Component Tests', () => {
  describe('Team Management Update Component', () => {
    let comp: TeamUpdateComponent;
    let fixture: ComponentFixture<TeamUpdateComponent>;
    let activatedRoute: ActivatedRoute;
    let teamService: TeamService;
    let imageService: ImageService;
    let dimensionService: DimensionService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        declarations: [TeamUpdateComponent],
        providers: [FormBuilder, ActivatedRoute],
      })
        .overrideTemplate(TeamUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(TeamUpdateComponent);
      activatedRoute = TestBed.inject(ActivatedRoute);
      teamService = TestBed.inject(TeamService);
      imageService = TestBed.inject(ImageService);
      dimensionService = TestBed.inject(DimensionService);

      comp = fixture.componentInstance;
    });

    describe('ngOnInit', () => {
      it('Should call Image query and add missing value', () => {
        const team: ITeam = { id: 456 };
        const image: IImage = { id: 46499 };
        team.image = image;

        const imageCollection: IImage[] = [{ id: 78307 }];
        spyOn(imageService, 'query').and.returnValue(of(new HttpResponse({ body: imageCollection })));
        const additionalImages = [image];
        const expectedCollection: IImage[] = [...additionalImages, ...imageCollection];
        spyOn(imageService, 'addImageToCollectionIfMissing').and.returnValue(expectedCollection);

        activatedRoute.data = of({ team });
        comp.ngOnInit();

        expect(imageService.query).toHaveBeenCalled();
        expect(imageService.addImageToCollectionIfMissing).toHaveBeenCalledWith(imageCollection, ...additionalImages);
        expect(comp.imagesSharedCollection).toEqual(expectedCollection);
      });

      it('Should call Dimension query and add missing value', () => {
        const team: ITeam = { id: 456 };
        const participations: IDimension[] = [{ id: 60511 }];
        team.participations = participations;

        const dimensionCollection: IDimension[] = [{ id: 69270 }];
        spyOn(dimensionService, 'query').and.returnValue(of(new HttpResponse({ body: dimensionCollection })));
        const additionalDimensions = [...participations];
        const expectedCollection: IDimension[] = [...additionalDimensions, ...dimensionCollection];
        spyOn(dimensionService, 'addDimensionToCollectionIfMissing').and.returnValue(expectedCollection);

        activatedRoute.data = of({ team });
        comp.ngOnInit();

        expect(dimensionService.query).toHaveBeenCalled();
        expect(dimensionService.addDimensionToCollectionIfMissing).toHaveBeenCalledWith(dimensionCollection, ...additionalDimensions);
        expect(comp.dimensionsSharedCollection).toEqual(expectedCollection);
      });

      it('Should update editForm', () => {
        const team: ITeam = { id: 456 };
        const image: IImage = { id: 15931 };
        team.image = image;
        const participations: IDimension = { id: 52377 };
        team.participations = [participations];

        activatedRoute.data = of({ team });
        comp.ngOnInit();

        expect(comp.editForm.value).toEqual(expect.objectContaining(team));
        expect(comp.imagesSharedCollection).toContain(image);
        expect(comp.dimensionsSharedCollection).toContain(participations);
      });
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', () => {
        // GIVEN
        const saveSubject = new Subject();
        const team = { id: 123 };
        spyOn(teamService, 'update').and.returnValue(saveSubject);
        spyOn(comp, 'previousState');
        activatedRoute.data = of({ team });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: team }));
        saveSubject.complete();

        // THEN
        expect(comp.previousState).toHaveBeenCalled();
        expect(teamService.update).toHaveBeenCalledWith(team);
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', () => {
        // GIVEN
        const saveSubject = new Subject();
        const team = new Team();
        spyOn(teamService, 'create').and.returnValue(saveSubject);
        spyOn(comp, 'previousState');
        activatedRoute.data = of({ team });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: team }));
        saveSubject.complete();

        // THEN
        expect(teamService.create).toHaveBeenCalledWith(team);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).toHaveBeenCalled();
      });

      it('Should set isSaving to false on error', () => {
        // GIVEN
        const saveSubject = new Subject();
        const team = { id: 123 };
        spyOn(teamService, 'update').and.returnValue(saveSubject);
        spyOn(comp, 'previousState');
        activatedRoute.data = of({ team });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.error('This is an error!');

        // THEN
        expect(teamService.update).toHaveBeenCalledWith(team);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).not.toHaveBeenCalled();
      });
    });

    describe('Tracking relationships identifiers', () => {
      describe('trackImageById', () => {
        it('Should return tracked Image primary key', () => {
          const entity = { id: 123 };
          const trackResult = comp.trackImageById(0, entity);
          expect(trackResult).toEqual(entity.id);
        });
      });

      describe('trackDimensionById', () => {
        it('Should return tracked Dimension primary key', () => {
          const entity = { id: 123 };
          const trackResult = comp.trackDimensionById(0, entity);
          expect(trackResult).toEqual(entity.id);
        });
      });
    });

    describe('Getting selected relationships', () => {
      describe('getSelectedDimension', () => {
        it('Should return option if no Dimension is selected', () => {
          const option = { id: 123 };
          const result = comp.getSelectedDimension(option);
          expect(result === option).toEqual(true);
        });

        it('Should return selected Dimension for according option', () => {
          const option = { id: 123 };
          const selected = { id: 123 };
          const selected2 = { id: 456 };
          const result = comp.getSelectedDimension(option, [selected2, selected]);
          expect(result === selected).toEqual(true);
          expect(result === selected2).toEqual(false);
          expect(result === option).toEqual(false);
        });

        it('Should return option if this Dimension is not selected', () => {
          const option = { id: 123 };
          const selected = { id: 456 };
          const result = comp.getSelectedDimension(option, [selected]);
          expect(result === option).toEqual(true);
          expect(result === selected).toEqual(false);
        });
      });
    });
  });
});
