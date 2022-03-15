import { ITeam } from '../../entities/team/team.model';
import dayjs from 'dayjs/esm';

/**
 * This type provides a helper method determines whether a team is valid or not.
 */
export class TeamValidation {
  /**
   * We consider a team as valid, if the expiration date is no more days in the past than defined here
   *
   * The value is negative because the uses Dayjs calculations return negative number for the diff(), if the date
   * compared to now is in the past.
   */
  static readonly TEAMS_VALID_PERIOD_IN_DAYS = -90;

  /**
   * Predicate to filter out invalid teams
   *
   * A team is considered valid if the expiration date of that team is not too much in the past from now.
   *
   * @param team team to examine
   */
  isValidTeam(team: ITeam): boolean {
    const now = dayjs();
    // Dayjs returns a negative number if the argument of the diff method is after the
    // point in time of the method callee.
    const daysUntilExpiration = now.diff(team.expirationDate, 'day');

    return daysUntilExpiration > TeamValidation.TEAMS_VALID_PERIOD_IN_DAYS;
  }
}
