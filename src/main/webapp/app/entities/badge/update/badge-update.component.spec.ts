import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { BadgeService } from '../service/badge.service';
import { IBadge, Badge } from '../badge.model';
import { IImage } from 'app/entities/image/image.model';
import { ImageService } from 'app/entities/image/service/image.service';
import { IDimension } from 'app/entities/dimension/dimension.model';
import { DimensionService } from 'app/entities/dimension/service/dimension.service';

import { BadgeUpdateComponent } from './badge-update.component';

describe('Badge Management Update Component', () => {
  let comp: BadgeUpdateComponent;
  let fixture: ComponentFixture<BadgeUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let badgeService: BadgeService;
  let imageService: ImageService;
  let dimensionService: DimensionService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [BadgeUpdateComponent],
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
      .overrideTemplate(BadgeUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(BadgeUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    badgeService = TestBed.inject(BadgeService);
    imageService = TestBed.inject(ImageService);
    dimensionService = TestBed.inject(DimensionService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call Image query and add missing value', () => {
      const badge: IBadge = { id: 456 };
      const image: IImage = { id: 9432 };
      badge.image = image;

      const imageCollection: IImage[] = [{ id: 19876 }];
      jest.spyOn(imageService, 'query').mockReturnValue(of(new HttpResponse({ body: imageCollection })));
      const additionalImages = [image];
      const expectedCollection: IImage[] = [...additionalImages, ...imageCollection];
      jest.spyOn(imageService, 'addImageToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ badge });
      comp.ngOnInit();

      expect(imageService.query).toHaveBeenCalled();
      expect(imageService.addImageToCollectionIfMissing).toHaveBeenCalledWith(imageCollection, ...additionalImages);
      expect(comp.imagesSharedCollection).toEqual(expectedCollection);
    });

    it('Should call Dimension query and add missing value', () => {
      const badge: IBadge = { id: 456 };
      const dimensions: IDimension[] = [{ id: 28962 }];
      badge.dimensions = dimensions;

      const dimensionCollection: IDimension[] = [{ id: 89454 }];
      jest.spyOn(dimensionService, 'query').mockReturnValue(of(new HttpResponse({ body: dimensionCollection })));
      const additionalDimensions = [...dimensions];
      const expectedCollection: IDimension[] = [...additionalDimensions, ...dimensionCollection];
      jest.spyOn(dimensionService, 'addDimensionToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ badge });
      comp.ngOnInit();

      expect(dimensionService.query).toHaveBeenCalled();
      expect(dimensionService.addDimensionToCollectionIfMissing).toHaveBeenCalledWith(dimensionCollection, ...additionalDimensions);
      expect(comp.dimensionsSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const badge: IBadge = { id: 456 };
      const image: IImage = { id: 71042 };
      badge.image = image;
      const dimensions: IDimension = { id: 69203 };
      badge.dimensions = [dimensions];

      activatedRoute.data = of({ badge });
      comp.ngOnInit();

      expect(comp.editForm.value).toEqual(expect.objectContaining(badge));
      expect(comp.imagesSharedCollection).toContain(image);
      expect(comp.dimensionsSharedCollection).toContain(dimensions);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<Badge>>();
      const badge = { id: 123 };
      jest.spyOn(badgeService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ badge });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: badge }));
      saveSubject.complete();

      // THEN
      expect(comp.previousState).toHaveBeenCalled();
      expect(badgeService.update).toHaveBeenCalledWith(badge);
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<Badge>>();
      const badge = new Badge();
      jest.spyOn(badgeService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ badge });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: badge }));
      saveSubject.complete();

      // THEN
      expect(badgeService.create).toHaveBeenCalledWith(badge);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<Badge>>();
      const badge = { id: 123 };
      jest.spyOn(badgeService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ badge });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(badgeService.update).toHaveBeenCalledWith(badge);
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
