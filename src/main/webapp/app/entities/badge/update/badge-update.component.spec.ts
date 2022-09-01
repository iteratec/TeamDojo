import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { BadgeFormService } from './badge-form.service';
import { BadgeService } from '../service/badge.service';
import { IBadge } from '../badge.model';
import { IImage } from 'app/entities/image/image.model';
import { ImageService } from 'app/entities/image/service/image.service';
import { IDimension } from 'app/entities/dimension/dimension.model';
import { DimensionService } from 'app/entities/dimension/service/dimension.service';

import { BadgeUpdateComponent } from './badge-update.component';

describe('Badge Management Update Component', () => {
  let comp: BadgeUpdateComponent;
  let fixture: ComponentFixture<BadgeUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let badgeFormService: BadgeFormService;
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
    badgeFormService = TestBed.inject(BadgeFormService);
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
      expect(imageService.addImageToCollectionIfMissing).toHaveBeenCalledWith(
        imageCollection,
        ...additionalImages.map(expect.objectContaining)
      );
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
      expect(dimensionService.addDimensionToCollectionIfMissing).toHaveBeenCalledWith(
        dimensionCollection,
        ...additionalDimensions.map(expect.objectContaining)
      );
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

      expect(comp.imagesSharedCollection).toContain(image);
      expect(comp.dimensionsSharedCollection).toContain(dimensions);
      expect(comp.badge).toEqual(badge);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IBadge>>();
      const badge = { id: 123 };
      jest.spyOn(badgeFormService, 'getBadge').mockReturnValue(badge);
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
      expect(badgeFormService.getBadge).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(badgeService.update).toHaveBeenCalledWith(expect.objectContaining(badge));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IBadge>>();
      const badge = { id: 123 };
      jest.spyOn(badgeFormService, 'getBadge').mockReturnValue({ id: null });
      jest.spyOn(badgeService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ badge: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: badge }));
      saveSubject.complete();

      // THEN
      expect(badgeFormService.getBadge).toHaveBeenCalled();
      expect(badgeService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IBadge>>();
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
      expect(badgeService.update).toHaveBeenCalled();
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
  });
});
