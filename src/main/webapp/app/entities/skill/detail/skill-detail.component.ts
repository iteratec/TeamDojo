import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ISkill } from '../skill.model';

@Component({
  selector: 'jhi-skill-detail',
  templateUrl: './skill-detail.component.html',
})
export class SkillDetailComponent implements OnInit {
  skill: ISkill | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ skill }) => {
      this.skill = skill;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
