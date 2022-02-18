import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { LevelSkillService } from '../service/level-skill.service';
import { ILevelSkill, LevelSkill } from '../level-skill.model';
import { ISkill } from 'app/entities/skill/skill.model';
import { SkillService } from 'app/entities/skill/service/skill.service';
import { ILevel } from 'app/entities/level/level.model';
import { LevelService } from 'app/entities/level/service/level.service';

import { LevelSkillUpdateComponent } from './level-skill-update.component';

describe('LevelSkill Management Update Component', () => {
  let comp: LevelSkillUpdateComponent;
  let fixture: ComponentFixture<LevelSkillUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let levelSkillService: LevelSkillService;
  let skillService: SkillService;
  let levelService: LevelService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [LevelSkillUpdateComponent],
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
    levelSkillService = TestBed.inject(LevelSkillService);
    skillService = TestBed.inject(SkillService);
    levelService = TestBed.inject(LevelService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call Skill query and add missing value', () => {
      const levelSkill: ILevelSkill = { id: 456 };
      const skill: ISkill = { id: 37505 };
      levelSkill.skill = skill;

      const skillCollection: ISkill[] = [{ id: 47120 }];
      jest.spyOn(skillService, 'query').mockReturnValue(of(new HttpResponse({ body: skillCollection })));
      const additionalSkills = [skill];
      const expectedCollection: ISkill[] = [...additionalSkills, ...skillCollection];
      jest.spyOn(skillService, 'addSkillToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ levelSkill });
      comp.ngOnInit();

      expect(skillService.query).toHaveBeenCalled();
      expect(skillService.addSkillToCollectionIfMissing).toHaveBeenCalledWith(skillCollection, ...additionalSkills);
      expect(comp.skillsSharedCollection).toEqual(expectedCollection);
    });

    it('Should call Level query and add missing value', () => {
      const levelSkill: ILevelSkill = { id: 456 };
      const level: ILevel = { id: 55830 };
      levelSkill.level = level;

      const levelCollection: ILevel[] = [{ id: 71169 }];
      jest.spyOn(levelService, 'query').mockReturnValue(of(new HttpResponse({ body: levelCollection })));
      const additionalLevels = [level];
      const expectedCollection: ILevel[] = [...additionalLevels, ...levelCollection];
      jest.spyOn(levelService, 'addLevelToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ levelSkill });
      comp.ngOnInit();

      expect(levelService.query).toHaveBeenCalled();
      expect(levelService.addLevelToCollectionIfMissing).toHaveBeenCalledWith(levelCollection, ...additionalLevels);
      expect(comp.levelsSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const levelSkill: ILevelSkill = { id: 456 };
      const skill: ISkill = { id: 24786 };
      levelSkill.skill = skill;
      const level: ILevel = { id: 6053 };
      levelSkill.level = level;

      activatedRoute.data = of({ levelSkill });
      comp.ngOnInit();

      expect(comp.editForm.value).toEqual(expect.objectContaining(levelSkill));
      expect(comp.skillsSharedCollection).toContain(skill);
      expect(comp.levelsSharedCollection).toContain(level);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<LevelSkill>>();
      const levelSkill = { id: 123 };
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
      expect(comp.previousState).toHaveBeenCalled();
      expect(levelSkillService.update).toHaveBeenCalledWith(levelSkill);
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<LevelSkill>>();
      const levelSkill = new LevelSkill();
      jest.spyOn(levelSkillService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ levelSkill });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: levelSkill }));
      saveSubject.complete();

      // THEN
      expect(levelSkillService.create).toHaveBeenCalledWith(levelSkill);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<LevelSkill>>();
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
      expect(levelSkillService.update).toHaveBeenCalledWith(levelSkill);
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

    describe('trackLevelById', () => {
      it('Should return tracked Level primary key', () => {
        const entity = { id: 123 };
        const trackResult = comp.trackLevelById(0, entity);
        expect(trackResult).toEqual(entity.id);
      });
    });
  });
});
