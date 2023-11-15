/*
 SPDX-FileCopyrightText: the TeamDojo authors
 SPDX-License-Identifier: Apache-2.0
 */
import { Component, Input, OnInit } from '@angular/core';
import { ITeam } from 'app/entities/team/team.model';
import { IBadge } from 'app/entities/badge/badge.model';
import { INotification } from 'app/custom/shared/notification/model/notification.model';
import { DatePipe } from '@angular/common';
import SharedModule from 'app/shared/shared.module';
import { AchievementItemComponent } from '../../achievement-item/achievement-item.component';
import { TeamImageComponent } from '../../team-image/team-image.component';
import { Badge, Team } from 'app/custom/custom.types';

@Component({
  standalone: true,
  selector: 'jhi-notification-item',
  templateUrl: './notification-item.component.html',
  styleUrls: ['./notification-item.scss'],
  imports: [DatePipe, SharedModule, AchievementItemComponent, TeamImageComponent]
})
export class NotificationItemComponent implements OnInit {
  @Input() notification?: INotification;
  @Input() teams?: Team[];
  @Input() badges?: Badge[];
  picture = '';
  item?: Badge | Team = {};

  ngOnInit(): void {
    this.item = {};
    const type = this.notification?.activity?.type;
    if (type?.toString() === 'BADGE_CREATED') {
      const badgeId = this.notification?.data.badgeId;
      this.item = this.badges?.find((b: Badge) => b.id === badgeId);
    } else if (type?.toString() === 'SKILL_COMPLETED' || type?.toString() === 'BADGE_COMPLETED') {
      const teamId = this.notification?.data.teamId;
      this.item = this.teams?.find((t: Team) => t.id === teamId);
    }
  }
}
