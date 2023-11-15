/*
 SPDX-FileCopyrightText: the TeamDojo authors
 SPDX-License-Identifier: Apache-2.0
 */
import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { FeedbackService } from './feedback.service';
import { IReport, NewReport } from 'app/entities/report/report.model';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';
import dayjs from 'dayjs/esm';

@Component({
  selector: 'jhi-feedback',
  templateUrl: './feedback.component.html',
})
export class FeedbackComponent implements OnInit {
  isSubmitting = false;
  private _report: IReport = { id: 0 };

  constructor(private feedbackService: FeedbackService, private route: ActivatedRoute) {}

  ngOnInit(): void {
    this.isSubmitting = false;
    this.route.data.subscribe(({ report }) => {
      this.report = report.body ? report.body : report;
    });
  }

  previousState(): void {
    window.history.back();
  }

  submit(): void {
    this.isSubmitting = true;
    this._report.createdAt = dayjs(DATE_TIME_FORMAT);
    this.subscribeToSubmitResponse(this.feedbackService.create(this.report));
  }

  private subscribeToSubmitResponse(result: Observable<HttpResponse<IReport>>): void {
    result.subscribe(
      (res: HttpResponse<IReport>) => this.onSubmitSuccess(res.body ? res.body : undefined),
      (res: HttpErrorResponse) => this.onSubmitError()
    );
  }

  private onSubmitSuccess(result?: IReport): void {
    this.isSubmitting = false;
    this.previousState();
  }

  private onSubmitError(): void {
    this.isSubmitting = false;
  }

  get report(): IReport {
    return this._report;
  }

  set report(report: IReport) {
    this._report = report;
  }
}
