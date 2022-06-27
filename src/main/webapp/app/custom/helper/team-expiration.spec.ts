/*
 SPDX-FileCopyrightText: the TeamDojo authors
 SPDX-License-Identifier: Apache-2.0
 */
import { Team } from '../../entities/team/team.model';
import dayjs from 'dayjs/esm';
import { TeamExpiration } from './team-expiration';

describe('TeamValidation', () => {
  // We use a fixed time as current time to decouple from time based side effects.
  const staticCurrentTime = dayjs('2022-03-15T16:00:00.000Z');
  let sut: TeamExpiration;

  beforeEach(() => {
    sut = new TeamExpiration();
    // Here we inject the static time fixture to mock out the side effect of the real current time.
    sut.currentTime = {
      now(): dayjs.Dayjs {
        return staticCurrentTime;
      },
    };
  });

  it.each([
    // The tested range is chosen to cover at least a range of three months into future and past.
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
    // Set the expiration date into the future by adding the days (negative days result in moving backwards in time).
    team.expirationDate = staticCurrentTime.add(daysFromNow, 'day');

    expect(sut.isValidTeam(team)).toBe(expected);
  });

  it('isExpirationDateVisible should return false if given team is undefined', () => {
    expect(sut.isExpirationDateVisible(undefined)).toBe(false);
  });

  it("isExpirationDateVisible should return false if given team's expirationDate is null", () => {
    expect(sut.isExpirationDateVisible(new Team())).toBe(false);
  });

  it('isValidTeam should return true if expirationDate is null', () => {
    const team = new Team();
    team.expirationDate = null;
    expect(sut.isValidTeam(team)).toBe(true);
  });

  it('isValidTeam should return true if expirationDate is undefined', () => {
    const team = new Team();
    team.expirationDate = undefined;
    expect(sut.isValidTeam(team)).toBe(true);
  });

  it.each([
    // The tested range is chosen from past (edge case) up to a random number beyond grace period.
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
    // Set the expiration date into the future by adding the days (negative days result in moving backwards in time).
    team.expirationDate = staticCurrentTime.add(daysFromNow, 'day');

    expect(sut.isExpirationDateVisible(team)).toBe(expected);
  });

  it('isExpired should return false if given team is undefined', () => {
    expect(sut.isExpired(undefined)).toBe(false);
  });

  it("isExpired should return false if given team's expirationDate is null", () => {
    expect(sut.isExpired(new Team())).toBe(false);
  });

  it.each([
    // The tested range is chosen fairly random before and after expiration.
    // [daysBefore, expected]
    [10, false],
    [9, false],
    [8, false],
    [7, false],
    [6, false],
    [5, false],
    [4, false],
    [3, false],
    [2, false],
    [1, false],
    [0, false],
    [-1, true],
    [-2, true],
    [-3, true],
    [-4, true],
    [-5, true],
    [-6, true],
    [-7, true],
    [-10, true],
  ])('for %i days before now isExpired should return %s', (daysBefore, expected) => {
    const team = new Team();
    // Set the expiration date in the future by adding the days (negative days result in moving backwards in time).
    team.expirationDate = staticCurrentTime.add(daysBefore, 'day');

    expect(sut.isExpired(team)).toBe(expected);
  });
});
