/*
 SPDX-FileCopyrightText: the TeamDojo authors
 SPDX-License-Identifier: Apache-2.0
 */
import { ActivatedRoute } from '@angular/router';

import { Component, OnInit } from '@angular/core';
import { SkillDetailsBaseComponent } from 'app/custom/shared/skill-details/skill-details-base.component';
import { AchievableSkill, IAchievableSkill } from 'app/custom/entities/achievable-skill/achievable-skill.model';
import { TeamsSkillsService } from 'app/custom/teams/teams-skills.service';
import { TeamsSelectionService } from 'app/custom/teams-selection/teams-selection.service';
import { ISkillObjects } from 'app/custom/entities/skill-objects/skill-objects.model';
import { ISkillRate } from 'app/custom/entities/skill-rate/skill-rate.model';

@Component({
  selector: 'jhi-skill-details',
  templateUrl: './skill-details.component.html',
  styleUrls: ['./skill-details.scss'],
})
export class SkillDetailsComponent extends SkillDetailsBaseComponent implements OnInit {
  achievableSkill?: IAchievableSkill = new AchievableSkill();

  constructor(
    public route: ActivatedRoute,
    public teamsSkillsService: TeamsSkillsService,
    private teamsSelectionService: TeamsSelectionService
  ) {
    super(route, teamsSkillsService);
  }

  ngOnInit(): void {
    this.route.data.subscribe(({ dojoModel: { teams, badges }, team, skill, skills, comments, selectedTeam }: any) => {
      this.team = team?.body ? team.body : team;
      this.setResolvedData({ teams, skill, comments, selectedTeam, badges, skills });
    });
    this.loadData();
  }

  loadData(): void {
    this.achievableSkill = new AchievableSkill();
    this.achievableSkill.skillId = this.skill.id;
    const teamId = this.team.id ? this.team.id : this.selectedTeam.id;
    if (teamId && this.skill.id) {
      this.teamsSkillsService.findAchievableSkill(teamId, this.skill.id).subscribe(skill => {
        this.achievableSkill = skill;
        this.skillComments = this._getSkillComments();
      });
    }
  }

  onSkillInListClicked(skillObjs: ISkillObjects): void {
    this.achievableSkill = skillObjs.achievableSkill;
    super.onSkillInListClicked(skillObjs);
  }

  onVoteSubmitted(voteObjs: ISkillRate): void {
    if (voteObjs.comment) {
      this.onCommentSubmitted(voteObjs.comment);
    }
  }

  get isSameTeam(): boolean {
    const currentTeam = this.teamsSelectionService.selectedTeam;
    return currentTeam?.id === this.team.id;
  }
}
