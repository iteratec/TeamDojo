import { Team } from '../../entities/team/team.model';
import dayjs from 'dayjs/esm';
import { TeamValidation } from './team-validation';

describe('TeamValidation', () => {
  // We use a fixed time as current time to decouple from time based side-effects.
  const currentTime = dayjs('2022-03-15T16:00:00.000Z');
  let sut: TeamValidation;

  beforeEach(() => {
    sut = new TeamValidation();
    sut.currentTime = {
      now(): dayjs.Dayjs {
        return currentTime;
      },
    };
  });

  it.each([
    // daysFromNow, expected
    [-100, true],
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
    [92, false],
    [93, false],
    [94, false],
    [95, false],
    [100, false],
  ])('for %i days from now isValidTeam should return %s', (daysFromNow, expected) => {
    const team = new Team();
    team.expirationDate = currentTime.add(daysFromNow, 'day');

    expect(sut.isValidTeam(team)).toBe(expected);
  });

  it('isExpirationDateVisible should return false if given team is undefined', () => {
    expect(sut.isExpirationDateVisible(undefined)).toBe(false);
  });

  it("isExpirationDateVisible should return false if given team's expirationDate is null", () => {
    expect(sut.isExpirationDateVisible(new Team())).toBe(false);
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
  ])('for %i days from now isExpirationDateVisible should return %s', (daysFromNow, expected) => {
    const team = new Team();
    team.expirationDate = currentTime.add(daysFromNow, 'day');

    expect(sut.isExpirationDateVisible(team)).toBe(expected);
  });
});
