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
});
