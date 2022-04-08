import { ActivatedRouteSnapshot, Resolve, Router, RouterStateSnapshot } from '@angular/router';
import { Injectable } from '@angular/core';
import { combineLatest, Observable } from 'rxjs';
import { filter, map, take } from 'rxjs/operators';
import { HttpResponse } from '@angular/common/http';

import { sortLevels } from 'app/custom/helper/level-util';
import { BadgeService } from 'app/entities/badge/service/badge.service';
import { TeamSkillService } from 'app/entities/team-skill/service/team-skill.service';
import { SkillService } from 'app/entities/skill/service/skill.service';
import { LevelService } from 'app/entities/level/service/level.service';
import { LevelSkillService } from 'app/entities/level-skill/service/level-skill.service';
import { BadgeSkillService } from 'app/entities/badge-skill/service/badge-skill.service';
import { CommentService } from 'app/entities/comment/service/comment.service';
import { TeamService } from 'app/entities/team/service/team.service';
import { TrainingService } from 'app/entities/training/service/training.service';
import { DimensionService } from 'app/entities/dimension/service/dimension.service';
import { ISkill, Skill } from 'app/entities/skill/skill.model';
import { IDimension } from 'app/entities/dimension/dimension.model';
import { ITeam } from 'app/entities/team/team.model';
import { ILevel } from 'app/entities/level/level.model';
import { IBadge } from 'app/entities/badge/badge.model';
import { ITeamSkill } from 'app/entities/team-skill/team-skill.model';
import { ILevelSkill } from 'app/entities/level-skill/level-skill.model';
import { IBadgeSkill } from 'app/entities/badge-skill/badge-skill.model';
import { IComment } from 'app/entities/comment/comment.model';
import { ITraining } from 'app/entities/training/training.model';

@Injectable()
export class AllTeamsResolve implements Resolve<any> {
  constructor(private teamService: TeamService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<HttpResponse<ITeam[]>> {
    return this.teamService.query();
  }
}

@Injectable()
export class DojoModelResolve implements Resolve<any> {
  constructor(
    private teamService: TeamService,
    private teamSkillService: TeamSkillService,
    private levelService: LevelService,
    private levelSkillService: LevelSkillService,
    private badgeSkillService: BadgeSkillService,
    private badgeService: BadgeService
  ) {}

  resolve(
    route: ActivatedRouteSnapshot,
    state: RouterStateSnapshot
  ): Observable<{
    teams: ITeam[];
    teamSkills: ITeamSkill[];
    levels: ILevel[];
    levelSkills: ILevelSkill[];
    badges: IBadge[];
    badgeSkills: IBadgeSkill[];
  }> {
    return combineLatest(
      this.teamService.query(),
      this.teamSkillService.query(),
      this.levelService.query(),
      this.levelSkillService.query(),
      this.badgeService.query(),
      this.badgeSkillService.query()
    ).pipe(
      map(([teamsRes, teamSkillsRes, levelsRes, levelSkillsRes, badgesRes, badgeSkillsRes]) => {
        const teams = teamsRes.body ?? [];
        const teamSkills = teamSkillsRes.body ?? [];
        const levels = levelsRes.body ?? [];
        const levelSkills = levelSkillsRes.body ?? [];
        const badges = badgesRes.body ?? [];
        const badgeSkills = badgeSkillsRes.body ?? [];

        const groupedTeamSkills: { [index: number]: any } = {};
        teamSkills.forEach(teamSkill => {
          const teamID = teamSkill.team?.id;
          if (teamID) {
            groupedTeamSkills[teamID] = groupedTeamSkills[teamID] || [];
            groupedTeamSkills[teamID].push(Object.assign(teamSkill));
          }
        });

        const groupedLevelSkills: { [index: number]: any } = {};
        levelSkills.forEach(levelSkill => {
          const levelId = levelSkill.level?.id;
          if (levelId) {
            groupedLevelSkills[levelId] = groupedLevelSkills[levelId] || [];
            groupedLevelSkills[levelId].push(Object.assign(levelSkill));
          }
        });

        const groupedLevels: { [index: number]: any } = {};
        levels.forEach(level => {
          const dimensionId = level.dimension?.id;
          if (dimensionId) {
            groupedLevels[dimensionId] = groupedLevels[dimensionId] || [];
            if (level.id) {
              groupedLevels[dimensionId].push(Object.assign(level, { skills: groupedLevelSkills[level.id] }));
            }
          }
        });
        for (const dimensionId in groupedLevels) {
          if (Object.prototype.hasOwnProperty.call(groupedLevels, dimensionId)) {
            groupedLevels[dimensionId] = sortLevels(groupedLevels[dimensionId]).reverse();
          }
        }

        const groupedBadgeSkills: { [index: number]: any } = {};
        badgeSkills.forEach(badgeSkill => {
          const badgeId = badgeSkill.badge?.id;
          if (badgeId) {
            groupedBadgeSkills[badgeId] = groupedBadgeSkills[badgeId] || [];
            groupedBadgeSkills[badgeId].push(Object.assign(badgeSkill));
          }
        });

        badges.forEach(badge => {
          if (badge.id) {
            badge.skills = groupedBadgeSkills[badge.id] || [];
          }
        });

        const groupedBadges: { [index: number]: any } = {};
        badges.forEach(badge => {
          (badge.dimensions ?? []).forEach(dimension => {
            const dimensionId = dimension.id;
            if (dimensionId) {
              groupedBadges[dimensionId] = groupedBadges[dimensionId] || [];
              if (badge.id) {
                groupedBadges[dimensionId].push(Object.assign(badge, { skills: groupedBadgeSkills[badge.id] }));
              }
            }
          });
        });

        teams.forEach(team => {
          if (team.id) {
            team.skills = groupedTeamSkills[team.id] || [];
          }
          team.participations?.forEach(dimension => {
            if (dimension.id) {
              dimension.levels = groupedLevels[dimension.id] || [];
              dimension.badges = groupedBadges[dimension.id] || [];
            }
          });
        });
        return { teams, teamSkills, levels, levelSkills, badges, badgeSkills };
      })
    );
  }
}

@Injectable()
export class AllDimensionsResolve implements Resolve<any> {
  constructor(private dimensionService: DimensionService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<HttpResponse<IDimension[]>> {
    return this.dimensionService.query();
  }
}

@Injectable()
export class AllLevelsResolve implements Resolve<any> {
  constructor(private levelService: LevelService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<HttpResponse<ILevel[]>> {
    return this.levelService.query();
  }
}

@Injectable()
export class AllBadgesResolve implements Resolve<any> {
  constructor(private badgeService: BadgeService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<HttpResponse<IBadge[]>> {
    return this.badgeService.query();
  }
}

@Injectable()
export class AllTeamSkillsResolve implements Resolve<any> {
  constructor(private teamSkillService: TeamSkillService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<HttpResponse<ITeamSkill[]>> {
    return this.teamSkillService.query();
  }
}

@Injectable()
export class AllLevelSkillsResolve implements Resolve<any> {
  constructor(private levelSkillService: LevelSkillService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<HttpResponse<ILevelSkill[]>> {
    return this.levelSkillService.query();
  }
}

@Injectable()
export class AllBadgeSkillsResolve implements Resolve<any> {
  constructor(private badgeSkillService: BadgeSkillService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<HttpResponse<IBadgeSkill[]>> {
    return this.badgeSkillService.query();
  }
}

@Injectable()
export class AllSkillsResolve implements Resolve<any> {
  constructor(private skillService: SkillService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<HttpResponse<ISkill[]>> {
    return this.skillService.query();
  }
}

@Injectable()
export class AllCommentsResolve implements Resolve<any> {
  constructor(private commentService: CommentService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<HttpResponse<IComment[]>> {
    return this.commentService.query();
  }
}

@Injectable()
export class SkillResolve implements Resolve<any> {
  constructor(private skillService: SkillService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<ISkill> | ISkill {
    const skillId = route.params['skillId'] ? route.params['skillId'] : null;
    if (skillId) {
      return this.skillService.query({ 'id.equals': skillId }).pipe(
        map(res => {
          if (res.body !== null && res.body.length !== 0) {
            return res.body[0];
          }
          // treat the case of body === null the same as missing skillID
          this.router.navigate(['/error']);
          return new Skill();
        })
      );
    }
    return new Skill();
  }
}

@Injectable()
export class AllTrainingsResolve implements Resolve<any> {
  constructor(private trainingService: TrainingService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<HttpResponse<ITraining[]>> {
    return this.trainingService.query();
  }
}
