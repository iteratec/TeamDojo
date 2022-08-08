/*
 SPDX-FileCopyrightText: the TeamDojo authors
 SPDX-License-Identifier: Apache-2.0
 */
import { Component, Input, OnInit } from '@angular/core';
import { ITeam } from 'app/entities/team/team.model';
import { IBadge } from 'app/entities/badge/badge.model';
import { INotification } from 'app/custom/shared/notification/model/notification.model';

@Component({
  selector: 'jhi-notification-item',
  templateUrl: './notification-item.component.html',
  styleUrls: ['./notification-item.scss'],
})
export class NotificationItemComponent implements OnInit {
  @Input() notification?: INotification;
  @Input() teams?: ITeam[];
  @Input() badges?: IBadge[];
  picture = '';
  item?: IBadge | ITeam = {};

  ngOnInit(): void {
    this.item = {};
    const type = this.notification?.activity?.type;
    if (type?.toString() === 'BADGE_CREATED') {
      const badgeId = this.notification?.data.badgeId;
      this.item = this.badges?.find((b: IBadge) => b.id === badgeId);
    } else if (type?.toString() === 'SKILL_COMPLETED' || type?.toString() === 'BADGE_COMPLETED') {
      const teamId = this.notification?.data.teamId;
      this.item = this.teams?.find((t: ITeam) => t.id === teamId);
    }
  }
}
