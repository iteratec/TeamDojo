import { EventEmitter, Injectable, Output } from '@angular/core';
import { ActivatedRoute, Router, Params } from '@angular/router';

import { ITeam } from 'app/entities/team/team.model';
import { IDimension } from 'app/entities/dimension/dimension.model';
import { IBadge } from 'app/entities/badge/badge.model';
import { ISkill } from 'app/entities/skill/skill.model';
import { ILevel } from 'app/entities/level/level.model';
import { Breadcrumb } from 'app/custom/entities/breadcrumb/breadcrumb.model';

@Injectable()
export class BreadcrumbService {
  @Output() breadcrumbChanged = new EventEmitter<any>(true);

  private team!: ITeam | null;
  private dimension!: IDimension | null;
  private badge!: IBadge | null;
  private skill!: ISkill | null;
  private level!: ILevel | null;
  private params!: Params;

  constructor(private route: ActivatedRoute, private router: Router) {
    this.route.queryParams.subscribe(queryParams => {
      this.params = queryParams;
    });
  }

  setBreadcrumb(team: ITeam | null, dimension: IDimension | null, level: ILevel | null, badge: IBadge | null, skill: ISkill | null) {
    this.team = team;

    if (badge) {
      this.badge = badge;
      this.dimension = null;
      this.level = null;
    } else if (this.badge) {
      this.badge = null;
    }
    if (level || dimension) {
      this.dimension = dimension;
      this.level = level;
      this.badge = null;
    } else if (this.level || this.dimension) {
      this.dimension = null;
      this.level = null;
    }

    this.skill = skill;
    this.breadcrumbChanged.emit('Breadcrumb changed');
  }

  getCurrentBreadcrumb() {
    const breadcrumbs = [];

    const path = [];

    if (this.team !== null && typeof this.team !== 'undefined') {
      path.push('teams', this.team.shortTitle);
      const url = this.router.createUrlTree(path).toString();
      breadcrumbs.push(new Breadcrumb(this.team.shortTitle, url, false));
    } else {
      path.push('');
    }
    if (this.dimension !== null && typeof this.dimension !== 'undefined') {
      const url = this.router.createUrlTree(path, { queryParams: this.params }).toString();
      breadcrumbs.push(new Breadcrumb(this.dimension.title, url, false));
    }
    if (this.level !== null && typeof this.level !== 'undefined') {
      const url = this.router.createUrlTree(path, { queryParams: { level: this.level.id } }).toString();
      breadcrumbs.push(new Breadcrumb(this.level.title, url, false));
    }
    if (this.badge !== null && typeof this.badge !== 'undefined') {
      const url = this.router.createUrlTree(path, { queryParams: { badge: this.badge.id } }).toString();
      breadcrumbs.push(new Breadcrumb(this.badge.title, url, false));
    }
    if (this.skill !== null && typeof this.skill !== 'undefined') {
      path.push('skills', this.skill.id);
      const url = this.router.createUrlTree(path, { queryParams: this.params }).toString();
      breadcrumbs.push(new Breadcrumb(this.skill.title, url, false));
    }
    if (breadcrumbs.length > 0) {
      breadcrumbs[breadcrumbs.length - 1].active = true;
    }
    return breadcrumbs;
  }
}
