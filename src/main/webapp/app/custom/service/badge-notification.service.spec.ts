/*
 SPDX-FileCopyrightText: the TeamDojo authors
 SPDX-License-Identifier: Apache-2.0
 */
import { TestBed } from '@angular/core/testing';
import { BadgeNotificationService } from './badge-notification.service';
import { TranslateModelService } from '../shared/translate-model/translate-model.service';
import { LanguageService } from '../shared/translate-model/language.service';
import { TranslateModule, TranslateService } from '@ngx-translate/core';
import { ActivityService } from '../../entities/activity/service/activity.service';
import { ITeam, Team } from '../../entities/team/team.model';
import { ISkill, Skill } from '../../entities/skill/skill.model';
import { ITeamSkill, TeamSkill } from '../../entities/team-skill/team-skill.model';
import { Badge, IBadge } from '../../entities/badge/badge.model';
import { SkillStatus } from '../../entities/enumerations/skill-status.model';
import dayjs from 'dayjs/esm';
import { BadgeSkill } from '../../entities/badge-skill/badge-skill.model';

class ActivityServiceMock {
  private createdCalled = 0;

  create(): void {
    this.createdCalled++;
  }

  getCreatedCalled(): number {
    return this.createdCalled;
  }
}

describe('BadgeNotificationService', () => {
  const team1 = createTeam(1000, 'TestTeam');
  const skill1 = createSkill(1, 'TestSkill1');
  const teamSkillSkill1Achieved = createTeamSkill(100, skill1, SkillStatus.ACHIEVED, team1);
  const teamSkillSkill1Open = createTeamSkill(100, skill1, SkillStatus.OPEN, team1);

  const allSkills: ISkill[] = [skill1];

  const badge1 = createBadge(100, 1, [skill1], 40);
  const badges: IBadge[] = [badge1];

  let translateModelService: TranslateModelService;
  let sut: BadgeNotificationService;

  function createBadge(id: number, requiredScore: number, skills: ISkill[], badgeSkillId: number): IBadge {
    const badge = new Badge();
    badge.id = id;
    badge.requiredScore = requiredScore;
    badge.skills = skills.map(skill => {
      const badgeSkill = new BadgeSkill(badgeSkillId, new Badge(id), skill);
      badgeSkillId += 1;
      return badgeSkill;
    });

    return badge;
  }

  function createTeam(id: number, title: string, skills?: ISkill[]): ITeam {
    const team = new Team();
    team.id = id;
    team.title = title;
    team.shortTitle = title;
    team.skills = skills;

    return team;
  }

  function createSkill(id: number, title: string): ISkill {
    const skill = new Skill();
    skill.id = id;
    skill.titleDE = title;
    skill.titleEN = title;

    return skill;
  }

  function createTeamSkill(id: number, skill: ISkill, skillStatus: SkillStatus, team: ITeam): ITeamSkill {
    const teamSkill = new TeamSkill();
    teamSkill.id = id;
    teamSkill.skill = skill;
    teamSkill.skillStatus = skillStatus;
    teamSkill.team = team;
    teamSkill.completedAt = skillStatus === SkillStatus.ACHIEVED ? dayjs() : null;
    return teamSkill;
  }

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [TranslateModule.forRoot()],
      providers: [
        TranslateModelService,
        LanguageService,
        TranslateService,
        BadgeNotificationService,
        { provide: ActivityService, useClass: ActivityServiceMock },
      ],
    });
    translateModelService = TestBed.inject(TranslateModelService);
    sut = TestBed.inject(BadgeNotificationService);
  });

  it('should called create zero times', () => {
    const testTeam = createTeam(1000, 'TestTeam');
    const updatedTeamSkills: ITeamSkill[] = [];
    expect(sut.createNotificationForNewlyCompletedBadge(testTeam, allSkills, updatedTeamSkills, badges));
  });

  it('should call create once after completing "TestSkill1"', () => {
    const testTeam = createTeam(1000, 'TestTeam', [teamSkillSkill1Open]);
    const updatedTeamSkills = [teamSkillSkill1Achieved];
    expect(sut.createNotificationForNewlyCompletedBadge(testTeam, allSkills, updatedTeamSkills, badges));
  });
});
