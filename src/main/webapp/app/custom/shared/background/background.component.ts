/*
 SPDX-FileCopyrightText: the TeamDojo authors
 SPDX-License-Identifier: Apache-2.0
 */
import { Component, Input } from '@angular/core';
import { TeamsSelectionService } from 'app/custom/teams-selection/teams-selection.service';
import { ITeam, Team } from 'app/entities/team/team.model';
import { ITeamSkill } from 'app/entities/team-skill/team-skill.model';
import { ISkill } from 'app/entities/skill/skill.model';
import { IBadge } from 'app/entities/badge/badge.model';

@Component({
  selector: 'jhi-background-component',
  templateUrl: './background.component.html',
  styleUrls: ['./background.scss'],
})
export class BackgroundComponent {
  @Input() team?: ITeam;
  @Input() teamSkills?: ITeamSkill[] | null = [];
  @Input() skills: ISkill[] = [];
  @Input() badges: IBadge[] = [];
  constructor(private teamsSelectionService: TeamsSelectionService) {}

  get currentTeam(): ITeam {
    const currTeam = this.team ?? this.teamsSelectionService.selectedTeam;
    return currTeam ? currTeam : new Team();
  }
}
