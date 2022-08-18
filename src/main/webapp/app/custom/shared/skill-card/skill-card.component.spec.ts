import { HttpClientModule } from '@angular/common/http';
import { TestBed } from '@angular/core/testing';
import { AchievableSkill, IAchievableSkill } from 'app/custom/entities/achievable-skill/achievable-skill.model';
import { LocalStorageMock } from 'app/custom/overview/teams/overview-teams.component.spec';
import { TeamsSelectionService } from 'app/custom/teams-selection/teams-selection.service';
import { LocalStorageService } from 'ngx-webstorage';

import { SkillCardComponent } from './skill-card.component';

describe('SkillCardComponent', () => {
  let component: SkillCardComponent;
  let teamsSelectionService: TeamsSelectionService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientModule],
      declarations: [SkillCardComponent],
      providers: [TeamsSelectionService, { provide: LocalStorageService, useClass: LocalStorageMock }],
    }).compileComponents();
    teamsSelectionService = TestBed.inject(TeamsSelectionService);
    component = new SkillCardComponent(teamsSelectionService);
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  /*it.each([
    [undefined, 0],
    [NaN, 0],
    [Number.POSITIVE_INFINITY, Number.POSITIVE_INFINITY],
    [Number.NEGATIVE_INFINITY, Number.NEGATIVE_INFINITY],
    [-1, -1],
    [0, 0],
    [1, 1],
  ])('should return a null safe rateCount', (actual, expected) => {
    expect(component.safeRateCount(actual)).toBe(expected);
  });

  it('should increment vote', () => {
    const fixture: IAchievableSkill = new AchievableSkill();
    fixture.vote = 0;
    component.incrementVote(fixture);
    expect(fixture.vote).toBe(1);
  });

  it('should not increment vote if falsy', () => {
    const fixture: IAchievableSkill = new AchievableSkill();
    expect(fixture.vote).toBeFalsy();
    component.incrementVote(fixture);
    expect(fixture.vote).toBeFalsy();
  });

  it('should decrement vote', () => {
    const fixture: IAchievableSkill = new AchievableSkill();
    fixture.vote = 1;
    component.decrementVote(fixture);
    expect(fixture.vote).toBe(0);
  });

  it('should not decrement vote if falsy', () => {
    const fixture: IAchievableSkill = new AchievableSkill();
    expect(fixture.vote).toBeFalsy();
    component.decrementVote(fixture);
    expect(fixture.vote).toBeFalsy();
  }); */
});
