/*
 SPDX-FileCopyrightText: the TeamDojo authors
 SPDX-License-Identifier: Apache-2.0
 */
import { Component, Input, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import dayjs from 'dayjs/esm';

import { ISkill } from 'app/entities/skill/skill.model';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';
import { TrainingService } from 'app/entities/training/service/training.service';
import { ITraining, Training } from 'app/entities/training/training.model';
import { AlertService } from 'app/core/util/alert.service';

@Component({
  selector: 'jhi-trainings-quickedit',
  templateUrl: './trainings-add.component.html',
})
export class TrainingsAddComponent implements OnInit {
  @Input() skills?: ISkill[];
  training: ITraining = new Training();
  isSaving = false;
  validUntil = '';

  constructor(private activeModal: NgbActiveModal, private alertService: AlertService, private trainingService: TrainingService) {}

  ngOnInit(): void {
    this.isSaving = false;
    this.training = new Training();
    this.training.isOfficial = false;
    this.training.skills = this.skills;
  }

  cancel(): void {
    this.activeModal.dismiss('Adding a training has been cancelled');
  }

  save(): void {
    this.isSaving = true;

    this.training.validUntil = dayjs(this.validUntil, DATE_TIME_FORMAT);
    this.trainingService.create(this.training).subscribe(
      (res: HttpResponse<ITraining>) => {
        this.isSaving = false;
        this.activeModal.close(res.body);
      },
      (res: HttpErrorResponse) => {
        this.isSaving = false;
        this.onError(res.message);
      }
    );
  }

  private onError(errorMessage: string): void {
    this.alertService.addAlert({ type: 'danger', message: errorMessage });
  }
}
