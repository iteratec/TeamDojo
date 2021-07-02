import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IBadge } from '../badge.model';

@Component({
  selector: 'jhi-badge-detail',
  templateUrl: './badge-detail.component.html',
})
export class BadgeDetailComponent implements OnInit {
  badge: IBadge | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ badge }) => {
      this.badge = badge;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
