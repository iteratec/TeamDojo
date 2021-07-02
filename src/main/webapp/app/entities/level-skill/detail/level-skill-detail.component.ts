import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ILevelSkill } from '../level-skill.model';

@Component({
  selector: 'jhi-level-skill-detail',
  templateUrl: './level-skill-detail.component.html',
})
export class LevelSkillDetailComponent implements OnInit {
  levelSkill: ILevelSkill | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ levelSkill }) => {
      this.levelSkill = levelSkill;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
