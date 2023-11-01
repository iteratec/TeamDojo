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
import { ILevel } from '../level.model';
import { LevelService } from '../service/level.service';
import { LevelFormService } from './level-form.service';

import { LevelUpdateComponent } from './level-update.component';

describe('Level Management Update Component', () => {
  let comp: LevelUpdateComponent;
  let fixture: ComponentFixture<LevelUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let levelFormService: LevelFormService;
  let levelService: LevelService;
  let imageService: ImageService;
  let dimensionService: DimensionService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([]), LevelUpdateComponent],
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
      .overrideTemplate(LevelUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(LevelUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    levelFormService = TestBed.inject(LevelFormService);
    levelService = TestBed.inject(LevelService);
    imageService = TestBed.inject(ImageService);
    dimensionService = TestBed.inject(DimensionService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call dependsOn query and add missing value', () => {
      const level: ILevel = { id: 456 };
      const dependsOn: ILevel = { id: 27683 };
      level.dependsOn = dependsOn;

      const dependsOnCollection: ILevel[] = [{ id: 29218 }];
      jest.spyOn(levelService, 'query').mockReturnValue(of(new HttpResponse({ body: dependsOnCollection })));
      const expectedCollection: ILevel[] = [dependsOn, ...dependsOnCollection];
      jest.spyOn(levelService, 'addLevelToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ level });
      comp.ngOnInit();

      expect(levelService.query).toHaveBeenCalled();
      expect(levelService.addLevelToCollectionIfMissing).toHaveBeenCalledWith(dependsOnCollection, dependsOn);
      expect(comp.dependsOnsCollection).toEqual(expectedCollection);
    });

    it('Should call Image query and add missing value', () => {
      const level: ILevel = { id: 456 };
      const image: IImage = { id: 19565 };
      level.image = image;

      const imageCollection: IImage[] = [{ id: 97 }];
      jest.spyOn(imageService, 'query').mockReturnValue(of(new HttpResponse({ body: imageCollection })));
      const additionalImages = [image];
      const expectedCollection: IImage[] = [...additionalImages, ...imageCollection];
      jest.spyOn(imageService, 'addImageToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ level });
      comp.ngOnInit();

      expect(imageService.query).toHaveBeenCalled();
      expect(imageService.addImageToCollectionIfMissing).toHaveBeenCalledWith(
        imageCollection,
        ...additionalImages.map(expect.objectContaining),
      );
      expect(comp.imagesSharedCollection).toEqual(expectedCollection);
    });

    it('Should call Dimension query and add missing value', () => {
      const level: ILevel = { id: 456 };
      const dimension: IDimension = { id: 17165 };
      level.dimension = dimension;

      const dimensionCollection: IDimension[] = [{ id: 1860 }];
      jest.spyOn(dimensionService, 'query').mockReturnValue(of(new HttpResponse({ body: dimensionCollection })));
      const additionalDimensions = [dimension];
      const expectedCollection: IDimension[] = [...additionalDimensions, ...dimensionCollection];
      jest.spyOn(dimensionService, 'addDimensionToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ level });
      comp.ngOnInit();

      expect(dimensionService.query).toHaveBeenCalled();
      expect(dimensionService.addDimensionToCollectionIfMissing).toHaveBeenCalledWith(
        dimensionCollection,
        ...additionalDimensions.map(expect.objectContaining),
      );
      expect(comp.dimensionsSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const level: ILevel = { id: 456 };
      const dependsOn: ILevel = { id: 21624 };
      level.dependsOn = dependsOn;
      const image: IImage = { id: 17088 };
      level.image = image;
      const dimension: IDimension = { id: 22674 };
      level.dimension = dimension;

      activatedRoute.data = of({ level });
      comp.ngOnInit();

      expect(comp.dependsOnsCollection).toContain(dependsOn);
      expect(comp.imagesSharedCollection).toContain(image);
      expect(comp.dimensionsSharedCollection).toContain(dimension);
      expect(comp.level).toEqual(level);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ILevel>>();
      const level = { id: 123 };
      jest.spyOn(levelFormService, 'getLevel').mockReturnValue(level);
      jest.spyOn(levelService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ level });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: level }));
      saveSubject.complete();

      // THEN
      expect(levelFormService.getLevel).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(levelService.update).toHaveBeenCalledWith(expect.objectContaining(level));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ILevel>>();
      const level = { id: 123 };
      jest.spyOn(levelFormService, 'getLevel').mockReturnValue({ id: null });
      jest.spyOn(levelService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ level: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: level }));
      saveSubject.complete();

      // THEN
      expect(levelFormService.getLevel).toHaveBeenCalled();
      expect(levelService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ILevel>>();
      const level = { id: 123 };
      jest.spyOn(levelService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ level });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(levelService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Compare relationships', () => {
    describe('compareLevel', () => {
      it('Should forward to levelService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(levelService, 'compareLevel');
        comp.compareLevel(entity, entity2);
        expect(levelService.compareLevel).toHaveBeenCalledWith(entity, entity2);
      });
    });

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
  });
});
