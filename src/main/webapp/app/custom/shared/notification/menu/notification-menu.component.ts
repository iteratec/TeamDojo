import { Component, OnInit } from '@angular/core';
import { HttpErrorResponse, HttpHeaders, HttpResponse } from '@angular/common/http';

import { ITeam } from 'app/entities/team/team.model';
import { IBadge } from 'app/entities/badge/badge.model';
import { TeamService } from 'app/entities/team/service/team.service';
import { ActivityService } from 'app/entities/activity/service/activity.service';
import { BadgeService } from 'app/entities/badge/service/badge.service';
import { INotification } from 'app/custom/shared/notification/model/notification.model';
import { ParseLinks } from 'app/core/util/parse-links.service';
import { IActivity } from 'app/entities/activity/activity.model';

@Component({
  selector: 'jhi-notification-menu',
  templateUrl: './notification-menu.component.html',
  styleUrls: ['./notification-menu.scss'],
})
export class NotificationMenuComponent implements OnInit {
  notifications: INotification[];
  teams: ITeam[];
  badges: IBadge[];
  itemsPerPage: number;
  links: any;
  page: any;
  predicate: any;
  reverse: any;
  totalItems: number;

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
    this.teamService.query().subscribe(response => {
      this.teams = response.body;
    });
    this.badges = [];
    this.badgeService.query().subscribe(response => {
      this.badges = response.body;
    });
  }

  public loadNotifications(): void {
    this.page = 0;
    this.notifications = [];
    this.getNext();
  }

  getNext() {
    this.activityService
      .query({
        page: this.page,
        size: this.itemsPerPage,
        sort: ['createdAt,desc'],
      })
      .subscribe(
        (res: HttpResponse<IActivity[]>) => this.paginateActivities(res.body, res.headers),
        (res: HttpErrorResponse) => console.log('Error getting Activities')
      );
  }

  loadPage(page) {
    this.page = page;
    this.getNext();
  }

  private paginateActivities(data: IActivity[], headers: HttpHeaders) {
    this.links = this.parseLinks.parse(headers.get('link'));
    this.totalItems = parseInt(headers.get('X-Total-Count'), 10);
    for (let i = 0; i < data.length; i++) {
      this.notifications.push(new Notification(data[i], true));
    }
  }
}
