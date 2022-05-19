import { Pipe, PipeTransform } from '@angular/core';
import { TeamScore } from '../../entities/team-score/team-score.model';
import { ITeam } from '../../../entities/team/team.model';
import { ITeamGroup } from '../../../entities/team-group/team-group.model';

@Pipe({ name: 'teamGroupFilter' })
export class TeamGroupFilterPipe implements PipeTransform {
  /**
   * Removes every team, not contained inside the currently selected
   * Teamgroup from the Array. (Operates destructively on the input array)
   * @param teamScores Array containing all of the teams
   * @param selectedTeamGroup name of the selected team group
   */
  transform(teamScores: TeamScore[], selectedTeamGroup: string): TeamScore[] {
    if (selectedTeamGroup === '') {
      return teamScores;
    }

    return teamScores.filter(teamScore => this.isContainedInTeamGroup(selectedTeamGroup, teamScore.team));
  }

  private isContainedInTeamGroup(selectedTeamGroup: string, team?: ITeam): boolean {
    if (selectedTeamGroup === '' || !team) {
      return true;
    }

    if (!team.group) {
      return false;
    }

    return team.group.title === selectedTeamGroup || this.isParent(selectedTeamGroup, team.group.parent);
  }

  private isParent(selectedTeamGroup: string, teamGroup?: ITeamGroup | null): boolean {
    let currTeamGroup: ITeamGroup | null | undefined = teamGroup;
    while (currTeamGroup) {
      if (currTeamGroup.title === selectedTeamGroup) {
        return true;
      }
      currTeamGroup = currTeamGroup.parent;
    }

    return false;
  }
}
