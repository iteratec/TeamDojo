import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { ISkill } from 'app/entities/skill/skill.model';
import { SkillService } from 'app/entities/skill/service/skill.service';
import { ILevel } from 'app/entities/level/level.model';
import { LevelService } from 'app/entities/level/service/level.service';
import { ILevelSkill } from '../level-skill.model';
import { LevelSkillService } from '../service/level-skill.service';
import { LevelSkillFormService } from './level-skill-form.service';

import { LevelSkillUpdateComponent } from './level-skill-update.component';

describe('LevelSkill Management Update Component', () => {
  let comp: LevelSkillUpdateComponent;
  let fixture: ComponentFixture<LevelSkillUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let levelSkillFormService: LevelSkillFormService;
  let levelSkillService: LevelSkillService;
  let skillService: SkillService;
  let levelService: LevelService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([]), LevelSkillUpdateComponent],
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
      .overrideTemplate(LevelSkillUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(LevelSkillUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    levelSkillFormService = TestBed.inject(LevelSkillFormService);
    levelSkillService = TestBed.inject(LevelSkillService);
    skillService = TestBed.inject(SkillService);
    levelService = TestBed.inject(LevelService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call Skill query and add missing value', () => {
      const levelSkill: ILevelSkill = { id: 456 };
      const skill: ISkill = { id: 24051 };
      levelSkill.skill = skill;

      const skillCollection: ISkill[] = [{ id: 16685 }];
      jest.spyOn(skillService, 'query').mockReturnValue(of(new HttpResponse({ body: skillCollection })));
      const additionalSkills = [skill];
      const expectedCollection: ISkill[] = [...additionalSkills, ...skillCollection];
      jest.spyOn(skillService, 'addSkillToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ levelSkill });
      comp.ngOnInit();

      expect(skillService.query).toHaveBeenCalled();
      expect(skillService.addSkillToCollectionIfMissing).toHaveBeenCalledWith(
        skillCollection,
        ...additionalSkills.map(expect.objectContaining),
      );
      expect(comp.skillsSharedCollection).toEqual(expectedCollection);
    });

    it('Should call Level query and add missing value', () => {
      const levelSkill: ILevelSkill = { id: 456 };
      const level: ILevel = { id: 24251 };
      levelSkill.level = level;

      const levelCollection: ILevel[] = [{ id: 18449 }];
      jest.spyOn(levelService, 'query').mockReturnValue(of(new HttpResponse({ body: levelCollection })));
      const additionalLevels = [level];
      const expectedCollection: ILevel[] = [...additionalLevels, ...levelCollection];
      jest.spyOn(levelService, 'addLevelToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ levelSkill });
      comp.ngOnInit();

      expect(levelService.query).toHaveBeenCalled();
      expect(levelService.addLevelToCollectionIfMissing).toHaveBeenCalledWith(
        levelCollection,
        ...additionalLevels.map(expect.objectContaining),
      );
      expect(comp.levelsSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const levelSkill: ILevelSkill = { id: 456 };
      const skill: ISkill = { id: 13646 };
      levelSkill.skill = skill;
      const level: ILevel = { id: 14441 };
      levelSkill.level = level;

      activatedRoute.data = of({ levelSkill });
      comp.ngOnInit();

      expect(comp.skillsSharedCollection).toContain(skill);
      expect(comp.levelsSharedCollection).toContain(level);
      expect(comp.levelSkill).toEqual(levelSkill);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ILevelSkill>>();
      const levelSkill = { id: 123 };
      jest.spyOn(levelSkillFormService, 'getLevelSkill').mockReturnValue(levelSkill);
      jest.spyOn(levelSkillService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ levelSkill });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: levelSkill }));
      saveSubject.complete();

      // THEN
      expect(levelSkillFormService.getLevelSkill).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(levelSkillService.update).toHaveBeenCalledWith(expect.objectContaining(levelSkill));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ILevelSkill>>();
      const levelSkill = { id: 123 };
      jest.spyOn(levelSkillFormService, 'getLevelSkill').mockReturnValue({ id: null });
      jest.spyOn(levelSkillService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ levelSkill: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: levelSkill }));
      saveSubject.complete();

      // THEN
      expect(levelSkillFormService.getLevelSkill).toHaveBeenCalled();
      expect(levelSkillService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ILevelSkill>>();
      const levelSkill = { id: 123 };
      jest.spyOn(levelSkillService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ levelSkill });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(levelSkillService.update).toHaveBeenCalled();
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

    describe('compareLevel', () => {
      it('Should forward to levelService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(levelService, 'compareLevel');
        comp.compareLevel(entity, entity2);
        expect(levelService.compareLevel).toHaveBeenCalledWith(entity, entity2);
      });
    });
  });
});
