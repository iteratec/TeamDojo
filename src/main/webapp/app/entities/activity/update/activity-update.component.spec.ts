jest.mock('@angular/router');

import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject } from 'rxjs';

import { ActivityService } from '../service/activity.service';
import { IActivity, Activity } from '../activity.model';

import { ActivityUpdateComponent } from './activity-update.component';

describe('Component Tests', () => {
  describe('Activity Management Update Component', () => {
    let comp: ActivityUpdateComponent;
    let fixture: ComponentFixture<ActivityUpdateComponent>;
    let activatedRoute: ActivatedRoute;
    let activityService: ActivityService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        declarations: [ActivityUpdateComponent],
        providers: [FormBuilder, ActivatedRoute],
      })
        .overrideTemplate(ActivityUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ActivityUpdateComponent);
      activatedRoute = TestBed.inject(ActivatedRoute);
      activityService = TestBed.inject(ActivityService);

      comp = fixture.componentInstance;
    });

    describe('ngOnInit', () => {
      it('Should update editForm', () => {
        const activity: IActivity = { id: 456 };

        activatedRoute.data = of({ activity });
        comp.ngOnInit();

        expect(comp.editForm.value).toEqual(expect.objectContaining(activity));
      });
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', () => {
        // GIVEN
        const saveSubject = new Subject();
        const activity = { id: 123 };
        spyOn(activityService, 'update').and.returnValue(saveSubject);
        spyOn(comp, 'previousState');
        activatedRoute.data = of({ activity });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: activity }));
        saveSubject.complete();

        // THEN
        expect(comp.previousState).toHaveBeenCalled();
        expect(activityService.update).toHaveBeenCalledWith(activity);
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', () => {
        // GIVEN
        const saveSubject = new Subject();
        const activity = new Activity();
        spyOn(activityService, 'create').and.returnValue(saveSubject);
        spyOn(comp, 'previousState');
        activatedRoute.data = of({ activity });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: activity }));
        saveSubject.complete();

        // THEN
        expect(activityService.create).toHaveBeenCalledWith(activity);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).toHaveBeenCalled();
      });

      it('Should set isSaving to false on error', () => {
        // GIVEN
        const saveSubject = new Subject();
        const activity = { id: 123 };
        spyOn(activityService, 'update').and.returnValue(saveSubject);
        spyOn(comp, 'previousState');
        activatedRoute.data = of({ activity });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.error('This is an error!');

        // THEN
        expect(activityService.update).toHaveBeenCalledWith(activity);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).not.toHaveBeenCalled();
      });
    });
  });
});
