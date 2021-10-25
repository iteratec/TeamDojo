import { ActivatedRoute } from '@angular/router';

import { Component, OnInit } from '@angular/core';
import { SkillDetailsComponentParent } from 'app/custom/shared/skill-details/skill-details.component';
import { AchievableSkill, IAchievableSkill } from 'app/custom/entities/achievable-skill/achievable-skill.model';
import { TeamsSkillsService } from 'app/custom/teams/teams-skills.service';
import { TeamsSelectionService } from 'app/custom/teams-selection/teams-selection.service';

@Component({
  selector: 'jhi-skill-details',
  templateUrl: './skill-details.component.html',
  styleUrls: ['./skill-details.scss'],
})
export class SkillDetailsComponent extends SkillDetailsComponentParent implements OnInit {
  achievableSkill: IAchievableSkill;

  constructor(route: ActivatedRoute, teamsSkillsService: TeamsSkillsService, private teamsSelectionService: TeamsSelectionService) {
    super(route, teamsSkillsService);
  }

  ngOnInit(): void {
    this.route.data.subscribe(({ dojoModel: { teams, badges }, team, skill, skills, comments, selectedTeam }) => {
      this.team = team && team.body ? team.body : team;
      super.setResolvedData({ teams, skill, comments, selectedTeam, badges, skills });
    });
    this.loadData();
  }

  loadData() {
    this.achievableSkill = new AchievableSkill();
    this.achievableSkill.skillId = this.skill.id;
    this.teamsSkillsService.findAchievableSkill(this.team ? this.team.id : this.selectedTeam.id, this.skill.id).subscribe(skill => {
      this.achievableSkill = skill;
      this.skillComments = super._getSkillComments();
    });
  }

  onSkillInListClicked(skillObjs) {
    this.achievableSkill = skillObjs.aSkill;
    super.onSkillInListClicked(skillObjs);
  }

  onVoteSubmitted(voteObjs) {
    this.onCommentSubmitted(voteObjs.comment);
  }

  get isSameTeam(): boolean {
    const currentTeam = this.teamsSelectionService.selectedTeam;
    return currentTeam && this.team && currentTeam.id === this.team.id;
  }
}
