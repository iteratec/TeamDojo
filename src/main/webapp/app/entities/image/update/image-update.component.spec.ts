jest.mock('@angular/router');

import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject } from 'rxjs';

import { ImageService } from '../service/image.service';
import { IImage, Image } from '../image.model';

import { ImageUpdateComponent } from './image-update.component';

describe('Component Tests', () => {
  describe('Image Management Update Component', () => {
    let comp: ImageUpdateComponent;
    let fixture: ComponentFixture<ImageUpdateComponent>;
    let activatedRoute: ActivatedRoute;
    let imageService: ImageService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        declarations: [ImageUpdateComponent],
        providers: [FormBuilder, ActivatedRoute],
      })
        .overrideTemplate(ImageUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ImageUpdateComponent);
      activatedRoute = TestBed.inject(ActivatedRoute);
      imageService = TestBed.inject(ImageService);

      comp = fixture.componentInstance;
    });

    describe('ngOnInit', () => {
      it('Should update editForm', () => {
        const image: IImage = { id: 456 };

        activatedRoute.data = of({ image });
        comp.ngOnInit();

        expect(comp.editForm.value).toEqual(expect.objectContaining(image));
      });
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', () => {
        // GIVEN
        const saveSubject = new Subject();
        const image = { id: 123 };
        spyOn(imageService, 'update').and.returnValue(saveSubject);
        spyOn(comp, 'previousState');
        activatedRoute.data = of({ image });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: image }));
        saveSubject.complete();

        // THEN
        expect(comp.previousState).toHaveBeenCalled();
        expect(imageService.update).toHaveBeenCalledWith(image);
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', () => {
        // GIVEN
        const saveSubject = new Subject();
        const image = new Image();
        spyOn(imageService, 'create').and.returnValue(saveSubject);
        spyOn(comp, 'previousState');
        activatedRoute.data = of({ image });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: image }));
        saveSubject.complete();

        // THEN
        expect(imageService.create).toHaveBeenCalledWith(image);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).toHaveBeenCalled();
      });

      it('Should set isSaving to false on error', () => {
        // GIVEN
        const saveSubject = new Subject();
        const image = { id: 123 };
        spyOn(imageService, 'update').and.returnValue(saveSubject);
        spyOn(comp, 'previousState');
        activatedRoute.data = of({ image });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.error('This is an error!');

        // THEN
        expect(imageService.update).toHaveBeenCalledWith(image);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).not.toHaveBeenCalled();
      });
    });
  });
});
