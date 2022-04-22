import { Component, OnInit } from '@angular/core';
import { TeamsSelectionService } from '../../../teams-selection/teams-selection.service';
import { ITeamGroup, TeamGroup } from '../../../../entities/team-group/team-group.model';

@Component({
  selector: 'jhi-breadcrumb-team-group',
  templateUrl: './breadcrumb-team-group.component.html',
  styleUrls: ['./breadcrumb-team-group.component.scss'],
})
export class BreadcrumbTeamGroupComponent implements OnInit {
  currentTeamGroup: ITeamGroup = BreadcrumbTeamGroupComponent.createDefaultTeamGroup();
  teamGroupChain: ITeamGroup[] = [];

  constructor(private teamsSelectionService: TeamsSelectionService) {
    this.teamGroupChain.push(this.currentTeamGroup);
  }

  private static createDefaultTeamGroup(): ITeamGroup {
    const defaultTeamGroup: ITeamGroup = new TeamGroup();
    defaultTeamGroup.title = 'n/a';
    return defaultTeamGroup;
  }

  ngOnInit(): void {
    this.currentTeamGroup = this.determineTeamGroup();
    this.teamsSelectionService.teamChanged.subscribe(() => {
      this.currentTeamGroup = this.determineTeamGroup();
      this.teamGroupChain = this.resolveTeamGroupChain(this.currentTeamGroup);
    });
  }

  resolveTeamGroupChain(current: ITeamGroup, accumulator: ITeamGroup[] = []): ITeamGroup[] {
    accumulator.unshift(current);

    if (current.parent) {
      return this.resolveTeamGroupChain(current.parent, accumulator);
    }

    return accumulator;
  }

  private determineTeamGroup(): ITeamGroup {
    const selectedTeam = this.teamsSelectionService.selectedTeam;

    if (!selectedTeam) {
      return BreadcrumbTeamGroupComponent.createDefaultTeamGroup();
    }

    if (!selectedTeam.group) {
      return BreadcrumbTeamGroupComponent.createDefaultTeamGroup();
    }

    return selectedTeam.group;
  }
}
