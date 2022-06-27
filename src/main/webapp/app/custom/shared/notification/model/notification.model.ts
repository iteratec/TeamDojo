/*
 SPDX-FileCopyrightText: the TeamDojo authors
 SPDX-License-Identifier: Apache-2.0
 */
import { IActivity } from 'app/entities/activity/activity.model';

export interface INotification {
  activity?: IActivity;
  data?: any;
  unread?: boolean;
}

export class Notification implements INotification {
  public data?: any;

  constructor(public activity?: IActivity, public unread?: boolean) {
    this.data = this.activity?.data ? JSON.parse(this.activity.data) : null;
  }
}
