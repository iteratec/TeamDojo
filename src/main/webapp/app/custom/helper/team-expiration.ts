import { ITeam } from '../../entities/team/team.model';
import dayjs from 'dayjs/esm';

export interface CurrentTimeProvider {
  now(): dayjs.Dayjs;
}

/**
 * This type provides a helper method regarding the expiration date of a team
 */
export class TeamExpiration {
  /**
   * We consider a team as valid, if the expiration date is no more days in the past than defined here
   *
   * The value is negative because the uses Dayjs calculations return negative number for the diff(), if the date
   * compared to now is in the past.
   */
  static readonly TEAMS_VALID_PERIOD_IN_DAYS = -90;
  /**
   * This is the period before team expiration to show the expiration date to the user in the UI
   */
  static readonly EXPIRATION_GRACE_PERIOD_IN_DAYS = 30;

  #currentTime: CurrentTimeProvider;

  /**
   * Injection point for a current time provider to decouple from time based side effects
   *
   * @param value
   */
  set currentTime(value: CurrentTimeProvider) {
    this.#currentTime = value;
  }

  constructor() {
    this.#currentTime = {
      now(): dayjs.Dayjs {
        return dayjs();
      },
    };
  }

  /**
   * Predicate to filter out invalid teams
   *
   * A team is considered valid if the expiration date of that team is not too much in the past from now.
   *
   * @param team team to examine
   * @return true if team is valid, else false
   */
  isValidTeam(team: ITeam): boolean {
    // Dayjs returns a negative number if the argument of the diff method is after the
    // point in time of the method callee.

    // If team.expirationDate is null, the team has no expiration Date
    if (!team.expirationDate) {
      return true;
    }
    const daysUntilExpiration = this.#currentTime.now().diff(team.expirationDate, 'day');

    return daysUntilExpiration > TeamExpiration.TEAMS_VALID_PERIOD_IN_DAYS;
  }

  /**
   * Determines whether the team's expiration date should be shown in the UI
   *
   * @param team the team to decide on
   * @return true if expiration date should be shown, else false
   */
  isExpirationDateVisible(team?: ITeam): boolean {
    if (team?.expirationDate != null) {
      const gracePeriodStart = team.expirationDate.subtract(TeamExpiration.EXPIRATION_GRACE_PERIOD_IN_DAYS, 'day');

      return dayjs().isBefore(gracePeriodStart, 'day');
    }

    return false;
  }

  /**
   * Determines whether the team is expired or not
   *
   * @param team the team to decide on
   * @return true if team is expired, else false
   */
  isExpired(team?: ITeam): boolean {
    if (team?.expirationDate != null) {
      return team.expirationDate.isBefore(this.#currentTime.now());
    }

    return false;
  }
}
