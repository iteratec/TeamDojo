import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { IBadge } from 'app/entities/badge/badge.model';
import { BadgeService } from 'app/entities/badge/service/badge.service';
import { ISkill } from 'app/entities/skill/skill.model';
import { SkillService } from 'app/entities/skill/service/skill.service';
import { IBadgeSkill } from '../badge-skill.model';
import { BadgeSkillService } from '../service/badge-skill.service';
import { BadgeSkillFormService } from './badge-skill-form.service';

import { BadgeSkillUpdateComponent } from './badge-skill-update.component';

describe('BadgeSkill Management Update Component', () => {
  let comp: BadgeSkillUpdateComponent;
  let fixture: ComponentFixture<BadgeSkillUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let badgeSkillFormService: BadgeSkillFormService;
  let badgeSkillService: BadgeSkillService;
  let badgeService: BadgeService;
  let skillService: SkillService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([]), BadgeSkillUpdateComponent],
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
      .overrideTemplate(BadgeSkillUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(BadgeSkillUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    badgeSkillFormService = TestBed.inject(BadgeSkillFormService);
    badgeSkillService = TestBed.inject(BadgeSkillService);
    badgeService = TestBed.inject(BadgeService);
    skillService = TestBed.inject(SkillService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call Badge query and add missing value', () => {
      const badgeSkill: IBadgeSkill = { id: 456 };
      const badge: IBadge = { id: 6819 };
      badgeSkill.badge = badge;

      const badgeCollection: IBadge[] = [{ id: 22202 }];
      jest.spyOn(badgeService, 'query').mockReturnValue(of(new HttpResponse({ body: badgeCollection })));
      const additionalBadges = [badge];
      const expectedCollection: IBadge[] = [...additionalBadges, ...badgeCollection];
      jest.spyOn(badgeService, 'addBadgeToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ badgeSkill });
      comp.ngOnInit();

      expect(badgeService.query).toHaveBeenCalled();
      expect(badgeService.addBadgeToCollectionIfMissing).toHaveBeenCalledWith(
        badgeCollection,
        ...additionalBadges.map(expect.objectContaining),
      );
      expect(comp.badgesSharedCollection).toEqual(expectedCollection);
    });

    it('Should call Skill query and add missing value', () => {
      const badgeSkill: IBadgeSkill = { id: 456 };
      const skill: ISkill = { id: 16931 };
      badgeSkill.skill = skill;

      const skillCollection: ISkill[] = [{ id: 9699 }];
      jest.spyOn(skillService, 'query').mockReturnValue(of(new HttpResponse({ body: skillCollection })));
      const additionalSkills = [skill];
      const expectedCollection: ISkill[] = [...additionalSkills, ...skillCollection];
      jest.spyOn(skillService, 'addSkillToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ badgeSkill });
      comp.ngOnInit();

      expect(skillService.query).toHaveBeenCalled();
      expect(skillService.addSkillToCollectionIfMissing).toHaveBeenCalledWith(
        skillCollection,
        ...additionalSkills.map(expect.objectContaining),
      );
      expect(comp.skillsSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const badgeSkill: IBadgeSkill = { id: 456 };
      const badge: IBadge = { id: 9467 };
      badgeSkill.badge = badge;
      const skill: ISkill = { id: 23096 };
      badgeSkill.skill = skill;

      activatedRoute.data = of({ badgeSkill });
      comp.ngOnInit();

      expect(comp.badgesSharedCollection).toContain(badge);
      expect(comp.skillsSharedCollection).toContain(skill);
      expect(comp.badgeSkill).toEqual(badgeSkill);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IBadgeSkill>>();
      const badgeSkill = { id: 123 };
      jest.spyOn(badgeSkillFormService, 'getBadgeSkill').mockReturnValue(badgeSkill);
      jest.spyOn(badgeSkillService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ badgeSkill });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: badgeSkill }));
      saveSubject.complete();

      // THEN
      expect(badgeSkillFormService.getBadgeSkill).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(badgeSkillService.update).toHaveBeenCalledWith(expect.objectContaining(badgeSkill));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IBadgeSkill>>();
      const badgeSkill = { id: 123 };
      jest.spyOn(badgeSkillFormService, 'getBadgeSkill').mockReturnValue({ id: null });
      jest.spyOn(badgeSkillService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ badgeSkill: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: badgeSkill }));
      saveSubject.complete();

      // THEN
      expect(badgeSkillFormService.getBadgeSkill).toHaveBeenCalled();
      expect(badgeSkillService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IBadgeSkill>>();
      const badgeSkill = { id: 123 };
      jest.spyOn(badgeSkillService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ badgeSkill });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(badgeSkillService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Compare relationships', () => {
    describe('compareBadge', () => {
      it('Should forward to badgeService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(badgeService, 'compareBadge');
        comp.compareBadge(entity, entity2);
        expect(badgeService.compareBadge).toHaveBeenCalledWith(entity, entity2);
      });
    });

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
