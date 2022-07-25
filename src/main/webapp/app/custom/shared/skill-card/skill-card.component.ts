import { Component, EventEmitter, Input, Output } from '@angular/core';
import { IAchievableSkill } from 'app/custom/entities/achievable-skill/achievable-skill.model';
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

  onSkillClicked(): void {
    this.skillClickedEvent.emit(this.skill);
  }

  updateSkill(): void {
    this.updateSkillEvent.emit(this.skill);
  }

  isSameTeamSelected(): boolean {
    const selectedTeam = this.teamsSelectionService.selectedTeam;
    return selectedTeam?.id === this.team?.id;
  }

  onSkillStatusClicked(): void {
    if (this.skill.skillStatus) {
      if (SkillStatusUtils.isValid(this.skill.skillStatus)) {
        this.setIncomplete();
      } else if (SkillStatusUtils.isInvalid(this.skill.skillStatus)) {
        this.setComplete();
      }
    }
  }

  setComplete(): void {
    if (!this.skill.irrelevant) {
      this.skill.achievedAt = moment();
      this.skill.skillStatus = SkillStatus.ACHIEVED;
      this.updateSkill();
    }
  }

  setIncomplete(): void {
    if (!this.skill.irrelevant) {
      this.skill.achievedAt = undefined;
      this.skill.skillStatus = SkillStatus.OPEN;
      this.updateSkill();
    }
  }

  setIrrelevant(): void {
    this.skill.irrelevant = true;
    this.skill.achievedAt = undefined;
    this.updateSkill();
  }

  setRelevant(): void {
    this.skill.irrelevant = false;
    this.updateSkill();
  }

  toggleRelevance(): void {
    if (this.skill.irrelevant) {
      this.setRelevant();
    } else {
      this.setIrrelevant();
    }
  }

  upVote(): void {
    this.incrementVote();
    this.addTeamToVoters();
    this.updateSkill();
  }

  downVote(): void {
    this.decrementVote();
    this.addTeamToVoters();
    this.updateSkill();
  }

  isVoteAble(): boolean {
    return !!this.skill.achievedAt && !this.skill.verifiedAt && !!this.skill.vote && this.skill.vote > -5 && this.isTeamVoteAble();
  }

  private isTeamVoteAble(): boolean {
    const selectedTeam = this.teamsSelectionService.selectedTeam;
    return !!(
      selectedTeam?.id &&
      (!this.skill.voters || (this.skill.voters && !this.skill.voters.split('||').includes(selectedTeam.id.toString())))
    );
  }

  private incrementVote(): void {
    if (this.skill.vote != null) {
      this.skill.vote = this.skill.vote + 1;
    }
  }

  private decrementVote(): void {
    if (this.skill.vote != null) {
      this.skill.vote = this.skill.vote - 1;
    }
  }

  private addTeamToVoters(): void {
    const array = this.skill.voters ? this.skill.voters.split('||') : [];

    if (this.teamsSelectionService.selectedTeam?.id) {
      array.push(this.teamsSelectionService.selectedTeam.id.toString());
    }

    this.skill.voters = array.join('||');
  }
}
