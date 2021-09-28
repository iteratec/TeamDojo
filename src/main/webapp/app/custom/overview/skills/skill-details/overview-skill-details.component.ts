import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { ILevel } from 'app/entities/level/level.model';
import { ISkillRate } from 'app/custom/entities/skill-rate/skill-rate.model';
import { SkillDetailsComponentParent } from 'app/custom/shared/skill-details/skill-details.component';
import { TeamsSkillsService } from 'app/custom/teams/teams-skills.service';

@Component({
  selector: 'jhi-overview-skill-details',
  templateUrl: './overview-skill-details.component.html',
  styleUrls: ['./overview-skill-details.scss'],
})
export class OverviewSkillDetailsComponent extends SkillDetailsComponentParent implements OnInit {
  levels: ILevel[];

  constructor(route: ActivatedRoute, teamsSkillsService: TeamsSkillsService) {
    super(route, teamsSkillsService);
  }

  ngOnInit(): void {
    this.route.data.subscribe(({ dojoModel: { teams, badges, levels }, skill, skills, comments, selectedTeam }) => {
      this.levels = (levels && levels.body ? levels.body : levels) || [];
      super.setResolvedData({ teams, skill, comments, selectedTeam, badges, skills });
    });
  }

  onVoteSubmitted(skillRate: ISkillRate) {
    for (const skill of this.skills) {
      if (skillRate.skillId === skill.id) {
        skill.rateScore = skillRate.rateScore;
        skill.rateCount = skillRate.rateCount;
      }
    }
  }
}
