import { Injectable } from '@angular/core';

import { ITeam } from '../../entities/team/team.model';
import { IBadge } from '../../entities/badge/badge.model';
import { TranslateModelService } from '../shared/translate-model/translate-model.service';
import { Activity } from '../../entities/activity/activity.model';
import { ActivityType } from '../../entities/enumerations/activity-type.model';
import { ActivityService } from '../../entities/activity/service/activity.service';
import { ITeamSkill } from '../../entities/team-skill/team-skill.model';
import { RelevanceCheck } from '../helper/relevance-check';
import { CompletionCheck } from '../helper/completion-check';
import { ISkill } from '../../entities/skill/skill.model';

@Injectable()
export class BadgeNotificationService {
  constructor(private translateModelService: TranslateModelService, private activityService: ActivityService) {}

  createNotificationForNewlyCompletedBadge(team: ITeam, allSkills: ISkill[], updatedTeamSkills: ITeamSkill[], badges: IBadge[]): void {
    const oldCompletedBadgeList = this.getCompletedBadges(badges, team, allSkills);
    team.skills = updatedTeamSkills;
    const newCompletedBadgeList = this.getCompletedBadges(badges, team, allSkills);

    const newlyCompletedBadges = this.getNewlyCompletedBadges(oldCompletedBadgeList, newCompletedBadgeList);

    newlyCompletedBadges.forEach(badge => {
      this.createNotification(badge, team);
    });
  }

  private getNewlyCompletedBadges(oldCompletedBadgeList: IBadge[], newCompletedBadgeList: IBadge[]): IBadge[] {
    return newCompletedBadgeList.filter(x => oldCompletedBadgeList.indexOf(x) === -1);
  }

  private createNotification(badge: IBadge, team: ITeam): void {
    const activity = new Activity();

    const badgeTitle = this.translateModelService.translateProperty(badge, 'title');
    const obj = { teamId: team.id, teamName: team.title, badgeId: badge.id, badgeTitle };

    activity.type = ActivityType.BADGE_COMPLETED;
    activity.data = JSON.stringify(obj);

    this.activityService.create(activity).subscribe();
  }

  private getCompletedBadges(badges: IBadge[], team: ITeam, skills: ITeamSkill[]): IBadge[] {
    const res = badges.filter((badge: IBadge) => {
      const relevant = new RelevanceCheck(team).isRelevantBadge(badge);
      const completed = new CompletionCheck(team, badge, skills).isCompleted();
      return relevant && completed;
    });

    return res;
  }
}
