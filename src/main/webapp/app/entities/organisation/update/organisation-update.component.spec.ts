jest.mock('@angular/router');

import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject } from 'rxjs';

import { OrganisationService } from '../service/organisation.service';
import { IOrganisation, Organisation } from '../organisation.model';

import { OrganisationUpdateComponent } from './organisation-update.component';

describe('Component Tests', () => {
  describe('Organisation Management Update Component', () => {
    let comp: OrganisationUpdateComponent;
    let fixture: ComponentFixture<OrganisationUpdateComponent>;
    let activatedRoute: ActivatedRoute;
    let organisationService: OrganisationService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        declarations: [OrganisationUpdateComponent],
        providers: [FormBuilder, ActivatedRoute],
      })
        .overrideTemplate(OrganisationUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(OrganisationUpdateComponent);
      activatedRoute = TestBed.inject(ActivatedRoute);
      organisationService = TestBed.inject(OrganisationService);

      comp = fixture.componentInstance;
    });

    describe('ngOnInit', () => {
      it('Should call Organisation query and add missing value', () => {
        const organisation: IOrganisation = { id: 456 };
        const parent: IOrganisation = { id: 62957 };
        organisation.parent = parent;

        const organisationCollection: IOrganisation[] = [{ id: 90733 }];
        spyOn(organisationService, 'query').and.returnValue(of(new HttpResponse({ body: organisationCollection })));
        const additionalOrganisations = [parent];
        const expectedCollection: IOrganisation[] = [...additionalOrganisations, ...organisationCollection];
        spyOn(organisationService, 'addOrganisationToCollectionIfMissing').and.returnValue(expectedCollection);

        activatedRoute.data = of({ organisation });
        comp.ngOnInit();

        expect(organisationService.query).toHaveBeenCalled();
        expect(organisationService.addOrganisationToCollectionIfMissing).toHaveBeenCalledWith(
          organisationCollection,
          ...additionalOrganisations
        );
        expect(comp.organisationsSharedCollection).toEqual(expectedCollection);
      });

      it('Should update editForm', () => {
        const organisation: IOrganisation = { id: 456 };
        const parent: IOrganisation = { id: 78970 };
        organisation.parent = parent;

        activatedRoute.data = of({ organisation });
        comp.ngOnInit();

        expect(comp.editForm.value).toEqual(expect.objectContaining(organisation));
        expect(comp.organisationsSharedCollection).toContain(parent);
      });
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', () => {
        // GIVEN
        const saveSubject = new Subject();
        const organisation = { id: 123 };
        spyOn(organisationService, 'update').and.returnValue(saveSubject);
        spyOn(comp, 'previousState');
        activatedRoute.data = of({ organisation });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: organisation }));
        saveSubject.complete();

        // THEN
        expect(comp.previousState).toHaveBeenCalled();
        expect(organisationService.update).toHaveBeenCalledWith(organisation);
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', () => {
        // GIVEN
        const saveSubject = new Subject();
        const organisation = new Organisation();
        spyOn(organisationService, 'create').and.returnValue(saveSubject);
        spyOn(comp, 'previousState');
        activatedRoute.data = of({ organisation });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: organisation }));
        saveSubject.complete();

        // THEN
        expect(organisationService.create).toHaveBeenCalledWith(organisation);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).toHaveBeenCalled();
      });

      it('Should set isSaving to false on error', () => {
        // GIVEN
        const saveSubject = new Subject();
        const organisation = { id: 123 };
        spyOn(organisationService, 'update').and.returnValue(saveSubject);
        spyOn(comp, 'previousState');
        activatedRoute.data = of({ organisation });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.error('This is an error!');

        // THEN
        expect(organisationService.update).toHaveBeenCalledWith(organisation);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).not.toHaveBeenCalled();
      });
    });

    describe('Tracking relationships identifiers', () => {
      describe('trackOrganisationById', () => {
        it('Should return tracked Organisation primary key', () => {
          const entity = { id: 123 };
          const trackResult = comp.trackOrganisationById(0, entity);
          expect(trackResult).toEqual(entity.id);
        });
      });
    });
  });
});
