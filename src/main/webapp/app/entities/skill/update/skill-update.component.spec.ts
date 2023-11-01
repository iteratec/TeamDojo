import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { SkillService } from '../service/skill.service';
import { ISkill } from '../skill.model';
import { SkillFormService } from './skill-form.service';

import { SkillUpdateComponent } from './skill-update.component';

describe('Skill Management Update Component', () => {
  let comp: SkillUpdateComponent;
  let fixture: ComponentFixture<SkillUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let skillFormService: SkillFormService;
  let skillService: SkillService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([]), SkillUpdateComponent],
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
      .overrideTemplate(SkillUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(SkillUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    skillFormService = TestBed.inject(SkillFormService);
    skillService = TestBed.inject(SkillService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const skill: ISkill = { id: 456 };

      activatedRoute.data = of({ skill });
      comp.ngOnInit();

      expect(comp.skill).toEqual(skill);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ISkill>>();
      const skill = { id: 123 };
      jest.spyOn(skillFormService, 'getSkill').mockReturnValue(skill);
      jest.spyOn(skillService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ skill });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: skill }));
      saveSubject.complete();

      // THEN
      expect(skillFormService.getSkill).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(skillService.update).toHaveBeenCalledWith(expect.objectContaining(skill));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ISkill>>();
      const skill = { id: 123 };
      jest.spyOn(skillFormService, 'getSkill').mockReturnValue({ id: null });
      jest.spyOn(skillService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ skill: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: skill }));
      saveSubject.complete();

      // THEN
      expect(skillFormService.getSkill).toHaveBeenCalled();
      expect(skillService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ISkill>>();
      const skill = { id: 123 };
      jest.spyOn(skillService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ skill });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(skillService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
