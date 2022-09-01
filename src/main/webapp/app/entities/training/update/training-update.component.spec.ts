import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { TrainingFormService } from './training-form.service';
import { TrainingService } from '../service/training.service';
import { ITraining } from '../training.model';
import { ISkill } from 'app/entities/skill/skill.model';
import { SkillService } from 'app/entities/skill/service/skill.service';

import { TrainingUpdateComponent } from './training-update.component';

describe('Training Management Update Component', () => {
  let comp: TrainingUpdateComponent;
  let fixture: ComponentFixture<TrainingUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let trainingFormService: TrainingFormService;
  let trainingService: TrainingService;
  let skillService: SkillService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [TrainingUpdateComponent],
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
      .overrideTemplate(TrainingUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(TrainingUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    trainingFormService = TestBed.inject(TrainingFormService);
    trainingService = TestBed.inject(TrainingService);
    skillService = TestBed.inject(SkillService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call Skill query and add missing value', () => {
      const training: ITraining = { id: 456 };
      const skills: ISkill[] = [{ id: 39278 }];
      training.skills = skills;

      const skillCollection: ISkill[] = [{ id: 14696 }];
      jest.spyOn(skillService, 'query').mockReturnValue(of(new HttpResponse({ body: skillCollection })));
      const additionalSkills = [...skills];
      const expectedCollection: ISkill[] = [...additionalSkills, ...skillCollection];
      jest.spyOn(skillService, 'addSkillToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ training });
      comp.ngOnInit();

      expect(skillService.query).toHaveBeenCalled();
      expect(skillService.addSkillToCollectionIfMissing).toHaveBeenCalledWith(
        skillCollection,
        ...additionalSkills.map(expect.objectContaining)
      );
      expect(comp.skillsSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const training: ITraining = { id: 456 };
      const skill: ISkill = { id: 49308 };
      training.skills = [skill];

      activatedRoute.data = of({ training });
      comp.ngOnInit();

      expect(comp.skillsSharedCollection).toContain(skill);
      expect(comp.training).toEqual(training);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ITraining>>();
      const training = { id: 123 };
      jest.spyOn(trainingFormService, 'getTraining').mockReturnValue(training);
      jest.spyOn(trainingService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ training });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: training }));
      saveSubject.complete();

      // THEN
      expect(trainingFormService.getTraining).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(trainingService.update).toHaveBeenCalledWith(expect.objectContaining(training));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ITraining>>();
      const training = { id: 123 };
      jest.spyOn(trainingFormService, 'getTraining').mockReturnValue({ id: null });
      jest.spyOn(trainingService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ training: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: training }));
      saveSubject.complete();

      // THEN
      expect(trainingFormService.getTraining).toHaveBeenCalled();
      expect(trainingService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ITraining>>();
      const training = { id: 123 };
      jest.spyOn(trainingService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ training });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(trainingService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Compare relationships', () => {
    describe('compareSkill', () => {
      it('Should forward to skillService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(skillService, 'compareSkill');
        comp.compareSkill(entity, entity2);
        expect(skillService.compareSkill).toHaveBeenCalledWith(entity, entity2);
      });
    });
  });
});
