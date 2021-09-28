import { of } from 'rxjs';
import { convertToParamMap } from '@angular/router';
import { TestBed } from '@angular/core/testing';
import { OverviewTeamsComponent } from 'app/custom/overview/teams/overview-teams.component';
import { ActivatedRoute } from '@angular/router';
import { TeamScore } from 'app/custom/entities/team-score/team-score.model';

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
  let comp: OverviewTeamsComponent;
  let activatedRoute: ActivatedRoute;

  beforeEach(() => {
    TestBed.configureTestingModule({
      // provide the component under test and dependency
      providers: [OverviewTeamsComponent, { provide: ActivatedRoute, useClass: ActivatedRouteMock }],
    });

    // inject both the component and the dependency
    comp = TestBed.inject(OverviewTeamsComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
  });

  it('compareTeamScores should return 0 if left.score and right.score are undefined', () => {
    const left = new TeamScore();
    const right = new TeamScore();

    expect(comp.compareTeamScores(left, right)).toBe(0);
  });

  it('compareTeamScores should return 0 if left.score is 0 and right.score is undefined', () => {
    const score = 0;
    const left = new TeamScore(undefined, score);
    const right = new TeamScore();

    expect(comp.compareTeamScores(left, right)).toBe(0);
  });

  it('compareTeamScores should return 0 if left.score is undefined and right.score is 0', () => {
    const score = 0;
    const left = new TeamScore();
    const right = new TeamScore(undefined, score);

    expect(comp.compareTeamScores(left, right)).toBe(0);
  });

  it('compareTeamScores should return 0 if left.score and right.score are equal', () => {
    const score = 10;
    const left = new TeamScore(undefined, score);
    const right = new TeamScore(undefined, score);

    expect(comp.compareTeamScores(left, right)).toBe(0);
  });

  it('compareTeamScores should return 1 if left.score is defined and greater than 0 and right.score is undefined', () => {
    const left = new TeamScore(undefined, 1);
    const right = new TeamScore();

    expect(comp.compareTeamScores(left, right)).toBe(1);
  });

  it('compareTeamScores should return 1 if left.score and right.score are defined and left.score is greater than right.score', () => {
    const left = new TeamScore(undefined, 3);
    const right = new TeamScore(undefined, 2);

    expect(comp.compareTeamScores(left, right)).toBe(1);
  });

  it('compareTeamScores should return -1 if left.score is undefined and right.score is defined and greater than ß', () => {
    const left = new TeamScore();
    const right = new TeamScore(undefined, 1);

    expect(comp.compareTeamScores(left, right)).toBe(-1);
  });

  it('compareTeamScores should return -1 if left.score and right.score are defined and left.score is lower than right.score', () => {
    const left = new TeamScore(undefined, 1);
    const right = new TeamScore(undefined, 2);

    expect(comp.compareTeamScores(left, right)).toBe(-1);
  });
});
