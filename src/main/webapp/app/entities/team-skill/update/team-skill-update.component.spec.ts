import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { ISkill } from 'app/entities/skill/skill.model';
import { SkillService } from 'app/entities/skill/service/skill.service';
import { ITeam } from 'app/entities/team/team.model';
import { TeamService } from 'app/entities/team/service/team.service';
import { ITeamSkill } from '../team-skill.model';
import { TeamSkillService } from '../service/team-skill.service';
import { TeamSkillFormService } from './team-skill-form.service';

import { TeamSkillUpdateComponent } from './team-skill-update.component';

describe('TeamSkill Management Update Component', () => {
  let comp: TeamSkillUpdateComponent;
  let fixture: ComponentFixture<TeamSkillUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let teamSkillFormService: TeamSkillFormService;
  let teamSkillService: TeamSkillService;
  let skillService: SkillService;
  let teamService: TeamService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([]), TeamSkillUpdateComponent],
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
      .overrideTemplate(TeamSkillUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(TeamSkillUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    teamSkillFormService = TestBed.inject(TeamSkillFormService);
    teamSkillService = TestBed.inject(TeamSkillService);
    skillService = TestBed.inject(SkillService);
    teamService = TestBed.inject(TeamService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call Skill query and add missing value', () => {
      const teamSkill: ITeamSkill = { id: 456 };
      const skill: ISkill = { id: 12899 };
      teamSkill.skill = skill;

      const skillCollection: ISkill[] = [{ id: 26994 }];
      jest.spyOn(skillService, 'query').mockReturnValue(of(new HttpResponse({ body: skillCollection })));
      const additionalSkills = [skill];
      const expectedCollection: ISkill[] = [...additionalSkills, ...skillCollection];
      jest.spyOn(skillService, 'addSkillToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ teamSkill });
      comp.ngOnInit();

      expect(skillService.query).toHaveBeenCalled();
      expect(skillService.addSkillToCollectionIfMissing).toHaveBeenCalledWith(
        skillCollection,
        ...additionalSkills.map(expect.objectContaining),
      );
      expect(comp.skillsSharedCollection).toEqual(expectedCollection);
    });

    it('Should call Team query and add missing value', () => {
      const teamSkill: ITeamSkill = { id: 456 };
      const team: ITeam = { id: 6827 };
      teamSkill.team = team;

      const teamCollection: ITeam[] = [{ id: 10271 }];
      jest.spyOn(teamService, 'query').mockReturnValue(of(new HttpResponse({ body: teamCollection })));
      const additionalTeams = [team];
      const expectedCollection: ITeam[] = [...additionalTeams, ...teamCollection];
      jest.spyOn(teamService, 'addTeamToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ teamSkill });
      comp.ngOnInit();

      expect(teamService.query).toHaveBeenCalled();
      expect(teamService.addTeamToCollectionIfMissing).toHaveBeenCalledWith(
        teamCollection,
        ...additionalTeams.map(expect.objectContaining),
      );
      expect(comp.teamsSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const teamSkill: ITeamSkill = { id: 456 };
      const skill: ISkill = { id: 32507 };
      teamSkill.skill = skill;
      const team: ITeam = { id: 24057 };
      teamSkill.team = team;

      activatedRoute.data = of({ teamSkill });
      comp.ngOnInit();

      expect(comp.skillsSharedCollection).toContain(skill);
      expect(comp.teamsSharedCollection).toContain(team);
      expect(comp.teamSkill).toEqual(teamSkill);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ITeamSkill>>();
      const teamSkill = { id: 123 };
      jest.spyOn(teamSkillFormService, 'getTeamSkill').mockReturnValue(teamSkill);
      jest.spyOn(teamSkillService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ teamSkill });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: teamSkill }));
      saveSubject.complete();

      // THEN
      expect(teamSkillFormService.getTeamSkill).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(teamSkillService.update).toHaveBeenCalledWith(expect.objectContaining(teamSkill));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ITeamSkill>>();
      const teamSkill = { id: 123 };
      jest.spyOn(teamSkillFormService, 'getTeamSkill').mockReturnValue({ id: null });
      jest.spyOn(teamSkillService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ teamSkill: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: teamSkill }));
      saveSubject.complete();

      // THEN
      expect(teamSkillFormService.getTeamSkill).toHaveBeenCalled();
      expect(teamSkillService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ITeamSkill>>();
      const teamSkill = { id: 123 };
      jest.spyOn(teamSkillService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ teamSkill });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(teamSkillService.update).toHaveBeenCalled();
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

    describe('compareTeam', () => {
      it('Should forward to teamService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(teamService, 'compareTeam');
        comp.compareTeam(entity, entity2);
        expect(teamService.compareTeam).toHaveBeenCalledWith(entity, entity2);
      });
    });
  });
});
