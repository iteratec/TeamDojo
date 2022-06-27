/*
 SPDX-FileCopyrightText: the TeamDojo authors
 SPDX-License-Identifier: Apache-2.0
 */
import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { ITeamSkill } from 'app/entities/team-skill/team-skill.model';
import { TeamSkillService } from 'app/entities/team-skill/service/team-skill.service';

@Component({
  selector: 'jhi-team-skill-vote',
  templateUrl: './team-skill-vote.component.html',
})
export class TeamSkillVoteComponent implements OnInit {
  teamSkill?: ITeamSkill;

  constructor(private route: ActivatedRoute, private teamSkillService: TeamSkillService) {}

  ngOnInit(): void {
    this.route.data.subscribe(({ teamSkill }) => {
      this.teamSkill = teamSkill.body ? teamSkill.body : teamSkill;
    });
  }

  upVote(): void {
    if (this.teamSkill?.vote) {
      this.teamSkill.vote = this.teamSkill.vote + 1;
      this.teamSkillService.update(this.teamSkill).subscribe(response => (this.teamSkill = response.body ? response.body : undefined));
    }
  }

  downVote(): void {
    if (this.teamSkill?.vote) {
      this.teamSkill.vote = this.teamSkill.vote - 1;
      this.teamSkillService.update(this.teamSkill).subscribe(response => (this.teamSkill = response.body ? response.body : undefined));
    }
  }
}
