import { TestBed } from '@angular/core/testing';
import { TeamImageComponent } from './team-image.component';
import { Team } from '../../../entities/team/team.model';
import dayjs from 'dayjs/esm';

describe('TeamImageComponent', () => {
  let sut: TeamImageComponent;

  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [TeamImageComponent],
    });

    sut = TestBed.inject(TeamImageComponent);
  });

  it('isExpired should return true if expiration date is before now', () => {
    const team = new Team();
    team.expirationDate = dayjs().subtract(1, 'day');

    expect(sut.isExpired(team)).toBe(true);
  });

  it('isExpired should return false if expiration date is same day as now', () => {
    const team = new Team();
    team.expirationDate = dayjs();

    expect(sut.isExpired(team)).toBe(false);
  });

  it('isExpired should return false if expiration date is after now', () => {
    const team = new Team();
    team.expirationDate = dayjs().add(1, 'day');

    expect(sut.isExpired(team)).toBe(false);
  });

  it('isExpired should return false if given team is undefined', () => {
    expect(sut.isExpired(undefined)).toBe(false);
  });

  it("isExpired should return false if given team's expiration date is null", () => {
    const team = new Team();
    team.expirationDate = null;
    expect(sut.isExpired(team)).toBe(false);
  });

  it("isExpired should return false if given team's expiration date is undefined", () => {
    const team = new Team();
    team.expirationDate = undefined;
    expect(sut.isExpired(team)).toBe(false);
  });
});
