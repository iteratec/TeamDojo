jest.mock('@angular/router');

import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject } from 'rxjs';

import { BadgeSkillService } from '../service/badge-skill.service';
import { IBadgeSkill, BadgeSkill } from '../badge-skill.model';
import { IBadge } from 'app/entities/badge/badge.model';
import { BadgeService } from 'app/entities/badge/service/badge.service';
import { ISkill } from 'app/entities/skill/skill.model';
import { SkillService } from 'app/entities/skill/service/skill.service';

import { BadgeSkillUpdateComponent } from './badge-skill-update.component';

describe('Component Tests', () => {
  describe('BadgeSkill Management Update Component', () => {
    let comp: BadgeSkillUpdateComponent;
    let fixture: ComponentFixture<BadgeSkillUpdateComponent>;
    let activatedRoute: ActivatedRoute;
    let badgeSkillService: BadgeSkillService;
    let badgeService: BadgeService;
    let skillService: SkillService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        declarations: [BadgeSkillUpdateComponent],
        providers: [FormBuilder, ActivatedRoute],
      })
        .overrideTemplate(BadgeSkillUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(BadgeSkillUpdateComponent);
      activatedRoute = TestBed.inject(ActivatedRoute);
      badgeSkillService = TestBed.inject(BadgeSkillService);
      badgeService = TestBed.inject(BadgeService);
      skillService = TestBed.inject(SkillService);

      comp = fixture.componentInstance;
    });

    describe('ngOnInit', () => {
      it('Should call Badge query and add missing value', () => {
        const badgeSkill: IBadgeSkill = { id: 456 };
        const badge: IBadge = { id: 3738 };
        badgeSkill.badge = badge;

        const badgeCollection: IBadge[] = [{ id: 81211 }];
        spyOn(badgeService, 'query').and.returnValue(of(new HttpResponse({ body: badgeCollection })));
        const additionalBadges = [badge];
        const expectedCollection: IBadge[] = [...additionalBadges, ...badgeCollection];
        spyOn(badgeService, 'addBadgeToCollectionIfMissing').and.returnValue(expectedCollection);

        activatedRoute.data = of({ badgeSkill });
        comp.ngOnInit();

        expect(badgeService.query).toHaveBeenCalled();
        expect(badgeService.addBadgeToCollectionIfMissing).toHaveBeenCalledWith(badgeCollection, ...additionalBadges);
        expect(comp.badgesSharedCollection).toEqual(expectedCollection);
      });

      it('Should call Skill query and add missing value', () => {
        const badgeSkill: IBadgeSkill = { id: 456 };
        const skill: ISkill = { id: 28978 };
        badgeSkill.skill = skill;

        const skillCollection: ISkill[] = [{ id: 23518 }];
        spyOn(skillService, 'query').and.returnValue(of(new HttpResponse({ body: skillCollection })));
        const additionalSkills = [skill];
        const expectedCollection: ISkill[] = [...additionalSkills, ...skillCollection];
        spyOn(skillService, 'addSkillToCollectionIfMissing').and.returnValue(expectedCollection);

        activatedRoute.data = of({ badgeSkill });
        comp.ngOnInit();

        expect(skillService.query).toHaveBeenCalled();
        expect(skillService.addSkillToCollectionIfMissing).toHaveBeenCalledWith(skillCollection, ...additionalSkills);
        expect(comp.skillsSharedCollection).toEqual(expectedCollection);
      });

      it('Should update editForm', () => {
        const badgeSkill: IBadgeSkill = { id: 456 };
        const badge: IBadge = { id: 1003 };
        badgeSkill.badge = badge;
        const skill: ISkill = { id: 77182 };
        badgeSkill.skill = skill;

        activatedRoute.data = of({ badgeSkill });
        comp.ngOnInit();

        expect(comp.editForm.value).toEqual(expect.objectContaining(badgeSkill));
        expect(comp.badgesSharedCollection).toContain(badge);
        expect(comp.skillsSharedCollection).toContain(skill);
      });
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', () => {
        // GIVEN
        const saveSubject = new Subject();
        const badgeSkill = { id: 123 };
        spyOn(badgeSkillService, 'update').and.returnValue(saveSubject);
        spyOn(comp, 'previousState');
        activatedRoute.data = of({ badgeSkill });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: badgeSkill }));
        saveSubject.complete();

        // THEN
        expect(comp.previousState).toHaveBeenCalled();
        expect(badgeSkillService.update).toHaveBeenCalledWith(badgeSkill);
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', () => {
        // GIVEN
        const saveSubject = new Subject();
        const badgeSkill = new BadgeSkill();
        spyOn(badgeSkillService, 'create').and.returnValue(saveSubject);
        spyOn(comp, 'previousState');
        activatedRoute.data = of({ badgeSkill });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: badgeSkill }));
        saveSubject.complete();

        // THEN
        expect(badgeSkillService.create).toHaveBeenCalledWith(badgeSkill);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).toHaveBeenCalled();
      });

      it('Should set isSaving to false on error', () => {
        // GIVEN
        const saveSubject = new Subject();
        const badgeSkill = { id: 123 };
        spyOn(badgeSkillService, 'update').and.returnValue(saveSubject);
        spyOn(comp, 'previousState');
        activatedRoute.data = of({ badgeSkill });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.error('This is an error!');

        // THEN
        expect(badgeSkillService.update).toHaveBeenCalledWith(badgeSkill);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).not.toHaveBeenCalled();
      });
    });

    describe('Tracking relationships identifiers', () => {
      describe('trackBadgeById', () => {
        it('Should return tracked Badge primary key', () => {
          const entity = { id: 123 };
          const trackResult = comp.trackBadgeById(0, entity);
          expect(trackResult).toEqual(entity.id);
        });
      });

      describe('trackSkillById', () => {
        it('Should return tracked Skill primary key', () => {
          const entity = { id: 123 };
          const trackResult = comp.trackSkillById(0, entity);
          expect(trackResult).toEqual(entity.id);
        });
      });
    });
  });
});
