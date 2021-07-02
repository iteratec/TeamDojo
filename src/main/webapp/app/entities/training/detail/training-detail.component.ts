import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ITraining } from '../training.model';

@Component({
  selector: 'jhi-training-detail',
  templateUrl: './training-detail.component.html',
})
export class TrainingDetailComponent implements OnInit {
  training: ITraining | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ training }) => {
      this.training = training;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
