import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IBadgeSkill } from '../badge-skill.model';

@Component({
  selector: 'jhi-badge-skill-detail',
  templateUrl: './badge-skill-detail.component.html',
})
export class BadgeSkillDetailComponent implements OnInit {
  badgeSkill: IBadgeSkill | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ badgeSkill }) => {
      this.badgeSkill = badgeSkill;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
