import { ComponentFixture, TestBed } from '@angular/core/testing';
import { APP_BASE_HREF, Location, LocationStrategy, PathLocationStrategy } from '@angular/common';
import { of } from 'rxjs';
import { RouterTestingModule } from '@angular/router/testing';

import { SessionStorageService } from 'ngx-webstorage';
import { HttpResponse } from '@angular/common/http';
import moment from 'moment';
import { TeamsSkillsComponent } from 'app/custom/teams/skills/teams-skills.component';
import { Team } from 'app/entities/team/team.model';
import { SkillService } from 'app/entities/skill/service/skill.service';
import { TeamService } from 'app/entities/team/service/team.service';
import { TeamSkillService } from 'app/entities/team-skill/service/team-skill.service';
import { TeamsSelectionService } from 'app/custom/teams-selection/teams-selection.service';
import { BreadcrumbService } from 'app/custom/layouts/navbar/breadcrumb.service';
import { LevelService } from 'app/entities/level/service/level.service';
import { BadgeService } from 'app/entities/badge/service/badge.service';
import { DimensionService } from 'app/entities/dimension/service/dimension.service';
import { TeamsSkillsService } from 'app/custom/teams/teams-skills.service';
import { AchievableSkill, IAchievableSkill } from 'app/custom/entities/achievable-skill/achievable-skill.model';
import { SkillStatus } from 'app/entities/enumerations/skill-status.model';
import { SharedModule } from 'app/shared/shared.module';
import { TeamdojoSharedCommonModule } from 'app/custom/shared/shared-common.module';
import { TranslateService } from '@ngx-translate/core';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { LocalStorageService } from 'ngx-webstorage';

class StorageMock {
  private storage: Map<string, string[]>;

  constructor() {
    this.storage = new Map<string, any>();
  }

  public store(key: string, value: any): any {
    this.storage.set(key, value);
  }

  public retrieve(key: string): string[] | undefined {
    return this.storage.get(key);
  }
}

class TranslateServiceMock {}

describe('Component Tests', () => {
  describe('Team Skills Component', () => {
    let comp: TeamsSkillsComponent;
    let fixture: ComponentFixture<TeamsSkillsComponent>;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [RouterTestingModule, HttpClientTestingModule],
        declarations: [],
        providers: [
          TeamsSkillsComponent,
          SkillService,
          TeamService,
          TeamSkillService,
          TeamsSelectionService,
          Location,
          BreadcrumbService,
          LevelService,
          BadgeService,
          DimensionService,
          TeamsSkillsService,
          { provide: LocalStorageService, useClass: StorageMock },
          { provide: TranslateService, useClass: TranslateServiceMock },
          { provide: SessionStorageService, useClass: StorageMock },
          { provide: LocationStrategy, useClass: PathLocationStrategy },
          { provide: APP_BASE_HREF, useValue: '/' },
        ],
      });

      //fixture = TestBed.createComponent(TeamsSkillsComponent);
      //comp = fixture.componentInstance;

      comp = TestBed.inject(TeamsSkillsComponent);
    });

    it('Should call loadAll on init', () => {
      comp.team = new Team(125);
      comp.ngOnInit();
    });

    it('Should emit an event when toggling skill relevance', done => {
      let clickedOnce = false;
      const teamsSkillsService = TestBed.get(TeamsSkillsService);
      expect(teamsSkillsService).toBeTruthy();
      jest.spyOn(teamsSkillsService, 'updateAchievableSkill').mockImplementation((teamId, aSkill) => {
        expect(teamId).toEqual(125);
        expect(aSkill).toBeTruthy();
        expect((aSkill as IAchievableSkill).skillId).toEqual(1100);
        expect((aSkill as IAchievableSkill).title).toEqual('Input Validation');
        expect((aSkill as IAchievableSkill).skillStatus).toEqual(SkillStatus.OPEN);
        expect((aSkill as IAchievableSkill).irrelevant).toEqual(!clickedOnce);
        const achievableSkill = {
          teamSkillId: 1553,
          skillId: (aSkill as IAchievableSkill).skillId,
          title: (aSkill as IAchievableSkill).title,
          irrelevant: (aSkill as IAchievableSkill).irrelevant,
          skillStatus: clickedOnce ? SkillStatus.OPEN : SkillStatus.IRRELEVANT,
        };

        return of(new HttpResponse({ body: achievableSkill }));
      });
      const skillService = TestBed.get(SkillService);
      expect(skillService).toBeTruthy();
      jest.spyOn(skillService, 'find').mockImplementation(skillId => {
        expect(skillId).toEqual(1100);
        return of(new HttpResponse({ body: { id: skillId, title: 'Input Validation' } }));
      });
      comp.skillChanged.subscribe(ev => {
        expect(ev).toBeDefined();
        expect(ev.skill).toBeDefined();
        expect(ev.skill.id).toEqual(1100);
        expect(ev.skill.titleEN).toEqual('Input Validation');
        expect(ev.achievableSkill).toBeDefined();
        expect(ev.achievableSkill?.teamSkillId).toEqual(1553);
        expect(ev.achievableSkill?.skillId).toEqual(1100);
        expect(ev.achievableSkill?.title).toEqual('Input Validation');
        expect(ev.achievableSkill?.irrelevant).toEqual(!clickedOnce);
        expect(ev.achievableSkill?.skillStatus).toEqual(!clickedOnce ? SkillStatus.IRRELEVANT : SkillStatus.OPEN);
        if (clickedOnce) {
          done();
        }
      });
      comp.team = new Team(125);
      comp.ngOnInit();
      const skill = { skillId: 1100, title: 'Input Validation', skillStatus: SkillStatus.OPEN, irrelevant: false };
      expect(skill['irrelevant']).toEqual(false);
      comp.toggleRelevance(skill);
      expect(skill['irrelevant']).toBeDefined();
      expect(skill['irrelevant']).toEqual(true);
      clickedOnce = true;
      comp.toggleRelevance(skill);
      expect(skill['irrelevant']).toBeDefined();
      expect(skill['irrelevant']).toEqual(false);
    });

    it('Should emit an event when clicking the team skill status', done => {
      let clickedOnce = false;
      const teamsSkillsService = TestBed.get(TeamsSkillsService);
      expect(teamsSkillsService).toBeTruthy();
      jest.spyOn(teamsSkillsService, 'updateAchievableSkill').mockImplementation((teamId, aSkill) => {
        expect(teamId).toEqual(160);
        expect(aSkill).toBeTruthy();
        expect((aSkill as IAchievableSkill).skillId).toEqual(1500);
        expect((aSkill as IAchievableSkill).title).toEqual('Strong passwords');
        expect((aSkill as IAchievableSkill).skillStatus).toEqual(clickedOnce ? SkillStatus.ACHIEVED : SkillStatus.OPEN);
        expect((aSkill as IAchievableSkill).irrelevant).toEqual(false);
        const achievableSkill = {
          teamSkillId: 1556,
          skillId: (aSkill as IAchievableSkill).skillId,
          title: (aSkill as IAchievableSkill).title,
          irrelevant: (aSkill as IAchievableSkill).irrelevant,
          skillStatus: clickedOnce ? SkillStatus.OPEN : SkillStatus.ACHIEVED,
          achievedAt: !clickedOnce ? moment() : undefined,
        };
        return of(new HttpResponse({ body: achievableSkill }));
      });
      const skillService = TestBed.get(SkillService);
      expect(skillService).toBeTruthy();
      jest.spyOn(skillService, 'find').mockImplementation(skillId => {
        expect(skillId).toEqual(1500);
        return of(new HttpResponse({ body: { id: skillId, title: 'Strong passwords' } }));
      });
      comp.skillChanged.subscribe(ev => {
        expect(ev).toBeDefined();
        expect(ev.skill).toBeDefined();
        expect(ev.skill.id).toEqual(1500);
        expect(ev.skill.titleEN).toEqual('Strong passwords');
        expect(ev.achievableSkill).toBeDefined();
        expect(ev.achievableSkill?.teamSkillId).toEqual(1556);
        expect(ev.achievableSkill?.skillId).toEqual(1500);
        expect(ev.achievableSkill?.title).toEqual('Strong passwords');
        expect(ev.achievableSkill?.irrelevant).toEqual(false);
        expect(ev.achievableSkill?.skillStatus).toEqual(!clickedOnce ? SkillStatus.ACHIEVED : SkillStatus.OPEN);
        if (ev.achievableSkill != null) {
          skill = ev.achievableSkill;
        }
        if (clickedOnce) {
          done();
        }
      });
      comp.team = new Team(160);
      comp.ngOnInit();
      let skill = new AchievableSkill(123, 1500, 'Strong passwords');
      skill.skillStatus = SkillStatus.OPEN;
      skill.irrelevant = false;
      skill.achievedAt = undefined;
      expect(skill['achievedAt']).toBeUndefined();
      expect(skill['skillStatus']).toEqual(SkillStatus.OPEN);
      comp.clickSkillStatus(skill);
      expect(skill['achievedAt']).toBeDefined();
      expect(skill['skillStatus']).toEqual(SkillStatus.ACHIEVED);
      clickedOnce = true;
      comp.clickSkillStatus(skill);
      expect(skill['achievedAt']).toBeUndefined();
      expect(skill['skillStatus']).toEqual(SkillStatus.OPEN);
    });
  });
});
