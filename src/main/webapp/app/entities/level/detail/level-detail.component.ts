import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ILevel } from '../level.model';

@Component({
  selector: 'jhi-level-detail',
  templateUrl: './level-detail.component.html',
})
export class LevelDetailComponent implements OnInit {
  level: ILevel | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ level }) => {
      this.level = level;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
