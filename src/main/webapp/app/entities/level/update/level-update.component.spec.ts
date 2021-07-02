jest.mock('@angular/router');

import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject } from 'rxjs';

import { LevelService } from '../service/level.service';
import { ILevel, Level } from '../level.model';
import { IImage } from 'app/entities/image/image.model';
import { ImageService } from 'app/entities/image/service/image.service';
import { IDimension } from 'app/entities/dimension/dimension.model';
import { DimensionService } from 'app/entities/dimension/service/dimension.service';

import { LevelUpdateComponent } from './level-update.component';

describe('Component Tests', () => {
  describe('Level Management Update Component', () => {
    let comp: LevelUpdateComponent;
    let fixture: ComponentFixture<LevelUpdateComponent>;
    let activatedRoute: ActivatedRoute;
    let levelService: LevelService;
    let imageService: ImageService;
    let dimensionService: DimensionService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        declarations: [LevelUpdateComponent],
        providers: [FormBuilder, ActivatedRoute],
      })
        .overrideTemplate(LevelUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(LevelUpdateComponent);
      activatedRoute = TestBed.inject(ActivatedRoute);
      levelService = TestBed.inject(LevelService);
      imageService = TestBed.inject(ImageService);
      dimensionService = TestBed.inject(DimensionService);

      comp = fixture.componentInstance;
    });

    describe('ngOnInit', () => {
      it('Should call dependsOn query and add missing value', () => {
        const level: ILevel = { id: 456 };
        const dependsOn: ILevel = { id: 96910 };
        level.dependsOn = dependsOn;

        const dependsOnCollection: ILevel[] = [{ id: 39385 }];
        spyOn(levelService, 'query').and.returnValue(of(new HttpResponse({ body: dependsOnCollection })));
        const expectedCollection: ILevel[] = [dependsOn, ...dependsOnCollection];
        spyOn(levelService, 'addLevelToCollectionIfMissing').and.returnValue(expectedCollection);

        activatedRoute.data = of({ level });
        comp.ngOnInit();

        expect(levelService.query).toHaveBeenCalled();
        expect(levelService.addLevelToCollectionIfMissing).toHaveBeenCalledWith(dependsOnCollection, dependsOn);
        expect(comp.dependsOnsCollection).toEqual(expectedCollection);
      });

      it('Should call Image query and add missing value', () => {
        const level: ILevel = { id: 456 };
        const image: IImage = { id: 68738 };
        level.image = image;

        const imageCollection: IImage[] = [{ id: 72129 }];
        spyOn(imageService, 'query').and.returnValue(of(new HttpResponse({ body: imageCollection })));
        const additionalImages = [image];
        const expectedCollection: IImage[] = [...additionalImages, ...imageCollection];
        spyOn(imageService, 'addImageToCollectionIfMissing').and.returnValue(expectedCollection);

        activatedRoute.data = of({ level });
        comp.ngOnInit();

        expect(imageService.query).toHaveBeenCalled();
        expect(imageService.addImageToCollectionIfMissing).toHaveBeenCalledWith(imageCollection, ...additionalImages);
        expect(comp.imagesSharedCollection).toEqual(expectedCollection);
      });

      it('Should call Dimension query and add missing value', () => {
        const level: ILevel = { id: 456 };
        const dimension: IDimension = { id: 54113 };
        level.dimension = dimension;

        const dimensionCollection: IDimension[] = [{ id: 84454 }];
        spyOn(dimensionService, 'query').and.returnValue(of(new HttpResponse({ body: dimensionCollection })));
        const additionalDimensions = [dimension];
        const expectedCollection: IDimension[] = [...additionalDimensions, ...dimensionCollection];
        spyOn(dimensionService, 'addDimensionToCollectionIfMissing').and.returnValue(expectedCollection);

        activatedRoute.data = of({ level });
        comp.ngOnInit();

        expect(dimensionService.query).toHaveBeenCalled();
        expect(dimensionService.addDimensionToCollectionIfMissing).toHaveBeenCalledWith(dimensionCollection, ...additionalDimensions);
        expect(comp.dimensionsSharedCollection).toEqual(expectedCollection);
      });

      it('Should update editForm', () => {
        const level: ILevel = { id: 456 };
        const dependsOn: ILevel = { id: 64635 };
        level.dependsOn = dependsOn;
        const image: IImage = { id: 82230 };
        level.image = image;
        const dimension: IDimension = { id: 60263 };
        level.dimension = dimension;

        activatedRoute.data = of({ level });
        comp.ngOnInit();

        expect(comp.editForm.value).toEqual(expect.objectContaining(level));
        expect(comp.dependsOnsCollection).toContain(dependsOn);
        expect(comp.imagesSharedCollection).toContain(image);
        expect(comp.dimensionsSharedCollection).toContain(dimension);
      });
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', () => {
        // GIVEN
        const saveSubject = new Subject();
        const level = { id: 123 };
        spyOn(levelService, 'update').and.returnValue(saveSubject);
        spyOn(comp, 'previousState');
        activatedRoute.data = of({ level });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: level }));
        saveSubject.complete();

        // THEN
        expect(comp.previousState).toHaveBeenCalled();
        expect(levelService.update).toHaveBeenCalledWith(level);
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', () => {
        // GIVEN
        const saveSubject = new Subject();
        const level = new Level();
        spyOn(levelService, 'create').and.returnValue(saveSubject);
        spyOn(comp, 'previousState');
        activatedRoute.data = of({ level });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: level }));
        saveSubject.complete();

        // THEN
        expect(levelService.create).toHaveBeenCalledWith(level);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).toHaveBeenCalled();
      });

      it('Should set isSaving to false on error', () => {
        // GIVEN
        const saveSubject = new Subject();
        const level = { id: 123 };
        spyOn(levelService, 'update').and.returnValue(saveSubject);
        spyOn(comp, 'previousState');
        activatedRoute.data = of({ level });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.error('This is an error!');

        // THEN
        expect(levelService.update).toHaveBeenCalledWith(level);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).not.toHaveBeenCalled();
      });
    });

    describe('Tracking relationships identifiers', () => {
      describe('trackLevelById', () => {
        it('Should return tracked Level primary key', () => {
          const entity = { id: 123 };
          const trackResult = comp.trackLevelById(0, entity);
          expect(trackResult).toEqual(entity.id);
        });
      });

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
  });
});
