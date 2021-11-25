import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';

import { FeedbackComponent } from './feedback.component';
import { IReport, Report } from 'app/entities/report/report.model';

@Injectable()
export class FeedbackResolve implements Resolve<Report> {
  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): IReport {
    return new Report();
  }
}

export const feedbackRoute: Routes = [
  {
    path: 'feedback',
    component: FeedbackComponent,
    data: {
      authorities: [],
      pageTitle: 'teamDojoApp.feedback.home.title',
    },
    resolve: {
      report: FeedbackResolve,
    },
  },
];
