import { Component, EventEmitter, Input, Output } from '@angular/core';
import { AchievableSkill, IAchievableSkill } from 'app/custom/entities/achievable-skill/achievable-skill.model';
import { SkillStatusUtils } from 'app/custom/entities/skill-status';
import { ITeam } from 'app/entities/team/team.model';
import { IBadge } from 'app/entities/badge/badge.model';
import { IDimension } from 'app/entities/dimension/dimension.model';
import { ILevel } from 'app/entities/level/level.model';
import { TeamsSelectionService } from 'app/custom/teams-selection/teams-selection.service';
import { SkillStatus } from 'app/entities/enumerations/skill-status.model';
import moment from 'moment';

@Component({
  selector: 'jhi-skill-card',
  templateUrl: './skill-card.component.html',
  styleUrls: ['./skill-card.component.scss'],
})
export class SkillCardComponent {
  @Input() skill!: IAchievableSkill;
  @Input() team?: ITeam;
  @Input() levelId: number | null = null;
  @Input() badgeId: number | null = null;
  @Input() dimensionId: number | null = null;
  @Input() activeBadge: IBadge | null = null;
  @Input() activeLevel: ILevel | null = null;
  @Input() activeDimension: IDimension | null = null;
  @Input() hasAuthority = false;

  @Output() skillClickedEvent = new EventEmitter<IAchievableSkill>();
  @Output() updateSkillEvent = new EventEmitter<IAchievableSkill>();

  eSkillStatus = SkillStatus;

  constructor(private teamsSelectionService: TeamsSelectionService) {}

  onSkillClicked(skill: IAchievableSkill): void {
    this.skillClickedEvent.emit(skill);
  }

  getStatusClass(skill: IAchievableSkill): string {
    if (skill.skillStatus) {
      return SkillStatusUtils.getLowerCaseValue(skill.skillStatus);
    }

    return '';
  }

  safeRateCount(rateCount?: number): number {
    return rateCount ? rateCount : 0;
  }

  getSkillStatusTranslationKey(skill: AchievableSkill): string {
    if (skill.skillStatus) {
      return SkillStatusUtils.getLowerCaseValue(skill.skillStatus);
    }

    return '';
  }

  isSameTeamSelected(): boolean {
    const selectedTeam = this.teamsSelectionService.selectedTeam;
    return selectedTeam?.id === this.team?.id;
  }

  clickSkillStatus(skill: IAchievableSkill): void {
    if (skill.skillStatus) {
      if (SkillStatusUtils.isValid(skill.skillStatus)) {
        this.setIncomplete(skill);
      } else if (SkillStatusUtils.isInvalid(skill.skillStatus)) {
        this.setComplete(skill);
      }
    }
  }

  setComplete(skill: IAchievableSkill): void {
    if (!skill.irrelevant) {
      skill.achievedAt = moment();
      skill.skillStatus = SkillStatus.ACHIEVED;
      this.updateSkillEvent.emit(skill);
    }
  }

  setIncomplete(skill: IAchievableSkill): void {
    if (!skill.irrelevant) {
      skill.achievedAt = undefined;
      skill.skillStatus = SkillStatus.OPEN;
      this.updateSkillEvent.emit(skill);
    }
  }

  setIrrelevant(skill: IAchievableSkill): void {
    skill.irrelevant = true;
    skill.achievedAt = undefined;
    this.updateSkillEvent.emit(skill);
  }

  setRelevant(skill: IAchievableSkill): void {
    skill.irrelevant = false;
    this.updateSkillEvent.emit(skill);
  }

  toggleRelevance(skill: IAchievableSkill): void {
    if (skill.irrelevant) {
      this.setRelevant(skill);
    } else {
      this.setIrrelevant(skill);
    }
  }

  upVote(s: IAchievableSkill): void {
    if (s.vote) {
      s.vote = s.vote + 1;
    }

    const array = s.voters ? s.voters.split('||') : [];
    if (this.teamsSelectionService.selectedTeam?.id) {
      array.push(this.teamsSelectionService.selectedTeam.id.toString());
    }

    s.voters = array.join('||');
    this.updateSkillEvent.emit(s);
  }

  downVote(s: IAchievableSkill): void {
    if (s.vote) {
      s.vote = s.vote - 1;
    }

    const array = s.voters ? s.voters.split('||') : [];
    if (this.teamsSelectionService.selectedTeam?.id) {
      array.push(this.teamsSelectionService.selectedTeam.id.toString());
    }

    s.voters = array.join('||');
    this.updateSkillEvent.emit(s);
  }

  isTeamVoteAble(s: IAchievableSkill): boolean {
    const selectedTeam = this.teamsSelectionService.selectedTeam;
    return !!(selectedTeam?.id && (!s.voters || (s.voters && !s.voters.split('||').includes(selectedTeam.id.toString()))));
  }

  isVoteAble(s: IAchievableSkill): boolean {
    return !!s.achievedAt && !s.verifiedAt && !!s.vote && s.vote > -5 && this.isTeamVoteAble(s);
  }

  incrementVote(skill: IAchievableSkill): void {
    if (skill.vote != null) {
      skill.vote = skill.vote + 1;
    }
  }

  decrementVote(skill: IAchievableSkill): void {
    if (skill.vote != null) {
      skill.vote = skill.vote - 1;
    }
  }
}
