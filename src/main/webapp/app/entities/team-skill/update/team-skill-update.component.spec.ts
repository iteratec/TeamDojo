jest.mock('@angular/router');

import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject } from 'rxjs';

import { TeamSkillService } from '../service/team-skill.service';
import { ITeamSkill, TeamSkill } from '../team-skill.model';
import { ISkill } from 'app/entities/skill/skill.model';
import { SkillService } from 'app/entities/skill/service/skill.service';
import { ITeam } from 'app/entities/team/team.model';
import { TeamService } from 'app/entities/team/service/team.service';

import { TeamSkillUpdateComponent } from './team-skill-update.component';

describe('Component Tests', () => {
  describe('TeamSkill Management Update Component', () => {
    let comp: TeamSkillUpdateComponent;
    let fixture: ComponentFixture<TeamSkillUpdateComponent>;
    let activatedRoute: ActivatedRoute;
    let teamSkillService: TeamSkillService;
    let skillService: SkillService;
    let teamService: TeamService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        declarations: [TeamSkillUpdateComponent],
        providers: [FormBuilder, ActivatedRoute],
      })
        .overrideTemplate(TeamSkillUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(TeamSkillUpdateComponent);
      activatedRoute = TestBed.inject(ActivatedRoute);
      teamSkillService = TestBed.inject(TeamSkillService);
      skillService = TestBed.inject(SkillService);
      teamService = TestBed.inject(TeamService);

      comp = fixture.componentInstance;
    });

    describe('ngOnInit', () => {
      it('Should call Skill query and add missing value', () => {
        const teamSkill: ITeamSkill = { id: 456 };
        const skill: ISkill = { id: 66239 };
        teamSkill.skill = skill;

        const skillCollection: ISkill[] = [{ id: 1499 }];
        spyOn(skillService, 'query').and.returnValue(of(new HttpResponse({ body: skillCollection })));
        const additionalSkills = [skill];
        const expectedCollection: ISkill[] = [...additionalSkills, ...skillCollection];
        spyOn(skillService, 'addSkillToCollectionIfMissing').and.returnValue(expectedCollection);

        activatedRoute.data = of({ teamSkill });
        comp.ngOnInit();

        expect(skillService.query).toHaveBeenCalled();
        expect(skillService.addSkillToCollectionIfMissing).toHaveBeenCalledWith(skillCollection, ...additionalSkills);
        expect(comp.skillsSharedCollection).toEqual(expectedCollection);
      });

      it('Should call Team query and add missing value', () => {
        const teamSkill: ITeamSkill = { id: 456 };
        const team: ITeam = { id: 97022 };
        teamSkill.team = team;

        const teamCollection: ITeam[] = [{ id: 62131 }];
        spyOn(teamService, 'query').and.returnValue(of(new HttpResponse({ body: teamCollection })));
        const additionalTeams = [team];
        const expectedCollection: ITeam[] = [...additionalTeams, ...teamCollection];
        spyOn(teamService, 'addTeamToCollectionIfMissing').and.returnValue(expectedCollection);

        activatedRoute.data = of({ teamSkill });
        comp.ngOnInit();

        expect(teamService.query).toHaveBeenCalled();
        expect(teamService.addTeamToCollectionIfMissing).toHaveBeenCalledWith(teamCollection, ...additionalTeams);
        expect(comp.teamsSharedCollection).toEqual(expectedCollection);
      });

      it('Should update editForm', () => {
        const teamSkill: ITeamSkill = { id: 456 };
        const skill: ISkill = { id: 83709 };
        teamSkill.skill = skill;
        const team: ITeam = { id: 89243 };
        teamSkill.team = team;

        activatedRoute.data = of({ teamSkill });
        comp.ngOnInit();

        expect(comp.editForm.value).toEqual(expect.objectContaining(teamSkill));
        expect(comp.skillsSharedCollection).toContain(skill);
        expect(comp.teamsSharedCollection).toContain(team);
      });
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', () => {
        // GIVEN
        const saveSubject = new Subject();
        const teamSkill = { id: 123 };
        spyOn(teamSkillService, 'update').and.returnValue(saveSubject);
        spyOn(comp, 'previousState');
        activatedRoute.data = of({ teamSkill });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: teamSkill }));
        saveSubject.complete();

        // THEN
        expect(comp.previousState).toHaveBeenCalled();
        expect(teamSkillService.update).toHaveBeenCalledWith(teamSkill);
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', () => {
        // GIVEN
        const saveSubject = new Subject();
        const teamSkill = new TeamSkill();
        spyOn(teamSkillService, 'create').and.returnValue(saveSubject);
        spyOn(comp, 'previousState');
        activatedRoute.data = of({ teamSkill });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: teamSkill }));
        saveSubject.complete();

        // THEN
        expect(teamSkillService.create).toHaveBeenCalledWith(teamSkill);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).toHaveBeenCalled();
      });

      it('Should set isSaving to false on error', () => {
        // GIVEN
        const saveSubject = new Subject();
        const teamSkill = { id: 123 };
        spyOn(teamSkillService, 'update').and.returnValue(saveSubject);
        spyOn(comp, 'previousState');
        activatedRoute.data = of({ teamSkill });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.error('This is an error!');

        // THEN
        expect(teamSkillService.update).toHaveBeenCalledWith(teamSkill);
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

      describe('trackTeamById', () => {
        it('Should return tracked Team primary key', () => {
          const entity = { id: 123 };
          const trackResult = comp.trackTeamById(0, entity);
          expect(trackResult).toEqual(entity.id);
        });
      });
    });
  });
});
