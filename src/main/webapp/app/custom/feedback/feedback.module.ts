/*
 SPDX-FileCopyrightText: the TeamDojo authors
 SPDX-License-Identifier: Apache-2.0
 */
import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { FeedbackService } from './feedback.service';
import { FeedbackComponent } from './feedback.component';
import { FeedbackResolve, feedbackRoute } from './feedback.route';
import { SharedModule } from 'app/shared/shared.module';

const ENTITY_STATES = [...feedbackRoute];

@NgModule({
  imports: [SharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [FeedbackComponent],
  entryComponents: [FeedbackComponent],
  providers: [FeedbackService, FeedbackResolve],
  schemas: [CUSTOM_ELEMENTS_SCHEMA],
})
export class FeedbackModule {}
