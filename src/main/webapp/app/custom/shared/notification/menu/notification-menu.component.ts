/*
 SPDX-FileCopyrightText: the TeamDojo authors
 SPDX-License-Identifier: Apache-2.0
 */
import { Component, OnInit } from '@angular/core';
import { HttpErrorResponse, HttpHeaders, HttpResponse } from '@angular/common/http';

import { ITeam } from 'app/entities/team/team.model';
import { IBadge } from 'app/entities/badge/badge.model';
import { TeamService } from 'app/entities/team/service/team.service';
import { ActivityService } from 'app/entities/activity/service/activity.service';
import { BadgeService } from 'app/entities/badge/service/badge.service';
import { INotification, Notification } from 'app/custom/shared/notification/model/notification.model';
import { ParseLinks } from 'app/core/util/parse-links.service';
import { IActivity } from 'app/entities/activity/activity.model';
import { TEAMS_PER_PAGE } from '../../../../config/pagination.constants';
import { NotificationItemComponent } from '../item/notification-item.component';
import SharedModule from 'app/shared/shared.module';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';
import { FormsModule } from '@angular/forms';
import { InfiniteScrollModule } from 'ngx-infinite-scroll';

@Component({
  standalone: true,
  selector: 'jhi-notification-menu',
  templateUrl: './notification-menu.component.html',
  styleUrls: ['./notification-menu.scss'],
  imports: [NotificationItemComponent, SharedModule, InfiniteScrollModule]
})
export class NotificationMenuComponent implements OnInit {
  notifications: INotification[] = [];
  teams: ITeam[] = [];
  badges: IBadge[] = [];
  itemsPerPage: number;
  links: any;
  page: any;
  predicate: any;
  reverse: any;
  totalItems = 0;

  constructor(
    private activityService: ActivityService,
    private teamService: TeamService,
    private badgeService: BadgeService,
    private parseLinks: ParseLinks
  ) {
    this.itemsPerPage = 8;
    this.page = 0;
    this.links = {
      last: 0,
    };
  }

  ngOnInit(): void {
    this.notifications = [];
    this.teams = [];
    this.teamService
      .query({
        page: 0,
        size: TEAMS_PER_PAGE,
      })
      .subscribe(response => {
        if (response.body) {
          this.teams = response.body;
        }
      });
    this.badges = [];
    this.badgeService.query().subscribe(response => {
      if (response.body) {
        this.badges = response.body;
      }
    });
  }

  public loadNotifications(): void {
    this.page = 0;
    this.notifications = [];
    this.getNext();
  }

  getNext(): void {
    this.activityService
      .query({
        page: this.page,
        size: this.itemsPerPage,
        sort: ['createdAt,desc'],
      })
      .subscribe((res: HttpResponse<IActivity[]>) => this.paginateActivities(res.body ? res.body : [], res.headers));
  }

  loadPage(page: any): void {
    this.page = page;
    this.getNext();
  }

  private paginateActivities(data: IActivity[], headers: HttpHeaders): void {
    const link = headers.get('link');
    const xTotalCount = headers.get('X-Total-Count');

    if (link) {
      this.links = this.parseLinks.parse(link);
    }

    if (xTotalCount) {
      this.totalItems = parseInt(xTotalCount, 10);
    }

    for (let i = 0; i < data.length; i++) {
      this.notifications.push(new Notification(data[i], true));
    }
  }
}
