import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { TrainingService } from '../service/training.service';
import { ITraining, Training } from '../training.model';
import { ISkill } from 'app/entities/skill/skill.model';
import { SkillService } from 'app/entities/skill/service/skill.service';

import { TrainingUpdateComponent } from './training-update.component';

describe('Training Management Update Component', () => {
  let comp: TrainingUpdateComponent;
  let fixture: ComponentFixture<TrainingUpdateComponent>;
  let activatedRoute: ActivatedRoute;
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
      expect(skillService.addSkillToCollectionIfMissing).toHaveBeenCalledWith(skillCollection, ...additionalSkills);
      expect(comp.skillsSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const training: ITraining = { id: 456 };
      const skills: ISkill = { id: 49308 };
      training.skills = [skills];

      activatedRoute.data = of({ training });
      comp.ngOnInit();

      expect(comp.editForm.value).toEqual(expect.objectContaining(training));
      expect(comp.skillsSharedCollection).toContain(skills);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<Training>>();
      const training = { id: 123 };
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
      expect(comp.previousState).toHaveBeenCalled();
      expect(trainingService.update).toHaveBeenCalledWith(training);
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<Training>>();
      const training = new Training();
      jest.spyOn(trainingService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ training });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: training }));
      saveSubject.complete();

      // THEN
      expect(trainingService.create).toHaveBeenCalledWith(training);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<Training>>();
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
      expect(trainingService.update).toHaveBeenCalledWith(training);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Tracking relationships identifiers', () => {
    describe('trackSkillById', () => {
      it('Should return tracked Skill primary key', () => {
        const entity = { id: 123 };
        const trackResult = comp.trackSkillById(0, entity);
        expect(trackResult).toEqual(entity.id);
      });
    });
  });

  describe('Getting selected relationships', () => {
    describe('getSelectedSkill', () => {
      it('Should return option if no Skill is selected', () => {
        const option = { id: 123 };
        const result = comp.getSelectedSkill(option);
        expect(result === option).toEqual(true);
      });

      it('Should return selected Skill for according option', () => {
        const option = { id: 123 };
        const selected = { id: 123 };
        const selected2 = { id: 456 };
        const result = comp.getSelectedSkill(option, [selected2, selected]);
        expect(result === selected).toEqual(true);
        expect(result === selected2).toEqual(false);
        expect(result === option).toEqual(false);
      });

      it('Should return option if this Skill is not selected', () => {
        const option = { id: 123 };
        const selected = { id: 456 };
        const result = comp.getSelectedSkill(option, [selected]);
        expect(result === option).toEqual(true);
        expect(result === selected).toEqual(false);
      });
    });
  });
});
