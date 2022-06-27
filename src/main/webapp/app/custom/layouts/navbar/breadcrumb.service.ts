import { EventEmitter, Injectable, Output } from '@angular/core';
import { ActivatedRoute, Router, Params } from '@angular/router';

import { ITeam } from 'app/entities/team/team.model';
import { IDimension } from 'app/entities/dimension/dimension.model';
import { IBadge } from 'app/entities/badge/badge.model';
import { ISkill } from 'app/entities/skill/skill.model';
import { ILevel } from 'app/entities/level/level.model';
import { Breadcrumb } from 'app/custom/entities/breadcrumb/breadcrumb.model';
import { TranslateModelService } from '../../shared/translate-model/translate-model.service';
import { ITeamGroup } from '../../../entities/team-group/team-group.model';

@Injectable()
export class BreadcrumbService {
  @Output() breadcrumbChanged = new EventEmitter<any>(true);

  private team!: ITeam | null;
  private dimension!: IDimension | null;
  private badge!: IBadge | null;
  private skill!: ISkill | null;
  private level!: ILevel | null;
  private params!: Params;

  constructor(private route: ActivatedRoute, private router: Router, private translation: TranslateModelService) {
    this.route.queryParams.subscribe(queryParams => {
      this.params = queryParams;
    });
  }

  setBreadcrumb(team: ITeam | null, dimension: IDimension | null, level: ILevel | null, badge: IBadge | null, skill: ISkill | null): void {
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

  getCurrentBreadcrumb(): Breadcrumb[] {
    const breadcrumbs = [];
    const path = [];

    if (this.team !== null && typeof this.team !== 'undefined') {
      path.push('teams', this.team.shortTitle);

      let url: string;
      let groupTitle = '';

      if (this.team.group) {
        let currGroup: ITeamGroup | null | undefined = this.team.group;
        while (currGroup) {
          groupTitle = currGroup.title ? currGroup.title : 'TeamDojo';
          url = this.router.createUrlTree(['overview', groupTitle]).toString();
          breadcrumbs.push(new Breadcrumb(groupTitle, url, false));
          currGroup = currGroup.parent;
        }

        breadcrumbs.reverse();
      }
      url = this.router.createUrlTree(path).toString();

      breadcrumbs.push(new Breadcrumb(this.team.shortTitle, url, false));
    } else {
      path.push('');
    }

    if (this.dimension !== null && typeof this.dimension !== 'undefined') {
      const url = this.router.createUrlTree(path).toString();
      const title = this.translation.translateProperty(this.dimension, 'title');
      breadcrumbs.push(new Breadcrumb(title, url, false, this.params));
    }

    if (this.level !== null && typeof this.level !== 'undefined') {
      const url = this.router.createUrlTree(path).toString();
      const title = this.translation.translateProperty(this.level, 'title');
      breadcrumbs.push(new Breadcrumb(title, url, false, { level: this.level.id }));
    }

    if (this.badge !== null && typeof this.badge !== 'undefined') {
      const url = this.router.createUrlTree(path).toString();
      const title = this.translation.translateProperty(this.badge, 'title');
      breadcrumbs.push(new Breadcrumb(title, url, false, { badge: this.badge.id }));
    }

    if (this.skill !== null && typeof this.skill !== 'undefined') {
      path.push('skills', this.skill.id);
      const url = this.router.createUrlTree(path).toString();
      const title = this.translation.translateProperty(this.skill, 'title');
      breadcrumbs.push(new Breadcrumb(title, url, false, this.params));
    }

    if (breadcrumbs.length > 0) {
      breadcrumbs[breadcrumbs.length - 1].active = true;
    }
    return breadcrumbs;
  }
}
