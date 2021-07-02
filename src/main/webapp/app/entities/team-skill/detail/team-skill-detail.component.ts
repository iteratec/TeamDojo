import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ITeamSkill } from '../team-skill.model';

@Component({
  selector: 'jhi-team-skill-detail',
  templateUrl: './team-skill-detail.component.html',
})
export class TeamSkillDetailComponent implements OnInit {
  teamSkill: ITeamSkill | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ teamSkill }) => {
      this.teamSkill = teamSkill;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
