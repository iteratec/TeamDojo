import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { DimensionService } from '../service/dimension.service';
import { IDimension, Dimension } from '../dimension.model';

import { DimensionUpdateComponent } from './dimension-update.component';

describe('Dimension Management Update Component', () => {
  let comp: DimensionUpdateComponent;
  let fixture: ComponentFixture<DimensionUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let dimensionService: DimensionService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [DimensionUpdateComponent],
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
      .overrideTemplate(DimensionUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(DimensionUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    dimensionService = TestBed.inject(DimensionService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const dimension: IDimension = { id: 456 };

      activatedRoute.data = of({ dimension });
      comp.ngOnInit();

      expect(comp.editForm.value).toEqual(expect.objectContaining(dimension));
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<Dimension>>();
      const dimension = { id: 123 };
      jest.spyOn(dimensionService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ dimension });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: dimension }));
      saveSubject.complete();

      // THEN
      expect(comp.previousState).toHaveBeenCalled();
      expect(dimensionService.update).toHaveBeenCalledWith(dimension);
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<Dimension>>();
      const dimension = new Dimension();
      jest.spyOn(dimensionService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ dimension });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: dimension }));
      saveSubject.complete();

      // THEN
      expect(dimensionService.create).toHaveBeenCalledWith(dimension);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<Dimension>>();
      const dimension = { id: 123 };
      jest.spyOn(dimensionService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ dimension });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(dimensionService.update).toHaveBeenCalledWith(dimension);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
