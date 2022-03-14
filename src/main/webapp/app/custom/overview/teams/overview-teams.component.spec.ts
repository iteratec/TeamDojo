import { of } from 'rxjs';
import { ActivatedRoute, convertToParamMap } from '@angular/router';
import { TestBed } from '@angular/core/testing';
import { OverviewTeamsComponent } from 'app/custom/overview/teams/overview-teams.component';
import { TeamScore } from 'app/custom/entities/team-score/team-score.model';
import { Team } from '../../../entities/team/team.model';
import dayjs from 'dayjs/esm';

export class ActivatedRouteMock {
  public paramMap = of(
    convertToParamMap({
      badge: '10',
      level: '10',
      dimension: '10',
    })
  );
}

describe('OverviewTeamsComponent', () => {
  let sut: OverviewTeamsComponent;
  let activatedRoute: ActivatedRoute;

  beforeEach(() => {
    TestBed.configureTestingModule({
      // provide the component under test and dependency
      providers: [OverviewTeamsComponent, { provide: ActivatedRoute, useClass: ActivatedRouteMock }],
    });

    // inject both the component and the dependency
    sut = TestBed.inject(OverviewTeamsComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
  });

  it('compareTeamScores should return 0 if left.score and right.score are undefined', () => {
    const left = new TeamScore();
    const right = new TeamScore();

    expect(sut.compareTeamScores(left, right)).toBe(0);
  });

  it('compareTeamScores should return 0 if left.score is 0 and right.score is undefined', () => {
    const score = 0;
    const left = new TeamScore(undefined, score);
    const right = new TeamScore();

    expect(sut.compareTeamScores(left, right)).toBe(0);
  });

  it('compareTeamScores should return 0 if left.score is undefined and right.score is 0', () => {
    const score = 0;
    const left = new TeamScore();
    const right = new TeamScore(undefined, score);

    expect(sut.compareTeamScores(left, right)).toBe(0);
  });

  it('compareTeamScores should return 0 if left.score and right.score are equal', () => {
    const score = 10;
    const left = new TeamScore(undefined, score);
    const right = new TeamScore(undefined, score);

    expect(sut.compareTeamScores(left, right)).toBe(0);
  });

  it('compareTeamScores should return 1 if left.score is defined and greater than 0 and right.score is undefined', () => {
    const left = new TeamScore(undefined, 1);
    const right = new TeamScore();

    expect(sut.compareTeamScores(left, right)).toBe(1);
  });

  it('compareTeamScores should return 1 if left.score and right.score are defined and left.score is greater than right.score', () => {
    const left = new TeamScore(undefined, 3);
    const right = new TeamScore(undefined, 2);

    expect(sut.compareTeamScores(left, right)).toBe(1);
  });

  it('compareTeamScores should return -1 if left.score is undefined and right.score is defined and greater than 0', () => {
    const left = new TeamScore();
    const right = new TeamScore(undefined, 1);

    expect(sut.compareTeamScores(left, right)).toBe(-1);
  });

  it('compareTeamScores should return -1 if left.score and right.score are defined and left.score is lower than right.score', () => {
    const left = new TeamScore(undefined, 1);
    const right = new TeamScore(undefined, 2);

    expect(sut.compareTeamScores(left, right)).toBe(-1);
  });

  it('showExpirationDate should return false if given team is undefined', () => {
    expect(sut.showExpirationDate(undefined)).toBe(false);
  });

  it("showExpirationDate should return false if given team's expirationDate is null", () => {
    expect(sut.showExpirationDate(new Team())).toBe(false);
  });

  it.each([
    // daysFromNow, expected
    [-1, false],
    [0, false],
    [1, false],
    [2, false],
    [3, false],
    [4, false],
    [5, false],
    [6, false],
    [7, false],
    [8, false],
    [9, false],
    [10, false],
    [11, false],
    [12, false],
    [13, false],
    [14, false],
    [15, false],
    [16, false],
    [17, false],
    [18, false],
    [19, false],
    [20, false],
    [21, false],
    [22, false],
    [23, false],
    [24, false],
    [25, false],
    [26, false],
    [27, false],
    [28, false],
    [29, false],
    [30, false],
    [31, true],
    [32, true],
    [33, true],
    [34, true],
    [35, true],
    [36, true],
    [37, true],
    [38, true],
    [39, true],
    [40, true],
    [41, true],
    [42, true],
  ])('for %i days from now showExpirationDate should return %s', (daysFromNow, expected) => {
    const team = new Team();
    const now = dayjs();
    team.expirationDate = now.add(daysFromNow, 'day');

    expect(sut.showExpirationDate(team)).toBe(expected);
  });

  it('isNotPureTrainingTeam should be true by default', () => {
    const team = new Team();
    expect(sut.isNotPureTrainingTeam(team)).toBe(true);
  });

  it('isNotPureTrainingTeam should be false is pureTrainingTeam is true', () => {
    const team = new Team();
    team.pureTrainingTeam = true;
    expect(sut.isNotPureTrainingTeam(team)).toBe(false);
  });

  it.each([
    // daysFromNow, expected
    [-90, true],
    [-60, true],
    [-30, true],
    [-1, true],
    [0, true],
    [1, true],
    [30, true],
    [60, true],
    [89, true],
    [90, false],
    [91, false],
  ])('for %i days from now isValidTeam should return %s', (daysFromNow, expected) => {
    const team = new Team();
    const now = dayjs();
    team.expirationDate = now.add(daysFromNow, 'day');

    expect(sut.isValidTeam(team)).toBe(expected);
  });
});
