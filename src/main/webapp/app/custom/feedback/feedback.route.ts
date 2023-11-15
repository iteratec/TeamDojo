/*
 SPDX-FileCopyrightText: the TeamDojo authors
 SPDX-License-Identifier: Apache-2.0
 */
import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';

import { FeedbackComponent } from './feedback.component';
import { IReport } from 'app/entities/report/report.model';


export const feedbackRoute: Routes = [
  {
    path: 'feedback',
    component: FeedbackComponent,
    data: {
      authorities: [],
      pageTitle: 'teamDojoApp.feedback.home.title',
    },
  },
];
