import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IDimension } from '../dimension.model';

@Component({
  selector: 'jhi-dimension-detail',
  templateUrl: './dimension-detail.component.html',
})
export class DimensionDetailComponent implements OnInit {
  dimension: IDimension | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ dimension }) => {
      this.dimension = dimension;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
