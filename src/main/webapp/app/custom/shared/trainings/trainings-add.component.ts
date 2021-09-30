import { Component, Input, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { JhiAlertService } from 'ng-jhipster';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { ITraining, Training } from 'app/shared/model/training.model';
import { TrainingService } from 'app/entities/training';
import * as moment from 'moment';
import { ISkill } from 'app/shared/model/skill.model';
import { DATE_TIME_FORMAT } from 'app/shared';

@Component({
  selector: 'jhi-trainings-quickedit',
  templateUrl: './trainings-add.component.html',
})
export class TrainingsAddComponent implements OnInit {
  @Input() skills: ISkill[];
  training: ITraining;
  isSaving: boolean;
  validUntil: string;

  constructor(private activeModal: NgbActiveModal, private jhiAlertService: JhiAlertService, private trainingService: TrainingService) {}

  ngOnInit() {
    this.isSaving = false;
    this.training = new Training();
    this.training.isOfficial = false;
    this.training.skills = this.skills;
  }

  cancel() {
    this.activeModal.dismiss('Adding a training has been cancelled');
  }

  save() {
    this.isSaving = true;

    this.training.validUntil = moment(this.validUntil, DATE_TIME_FORMAT);
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

  private onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }
}
