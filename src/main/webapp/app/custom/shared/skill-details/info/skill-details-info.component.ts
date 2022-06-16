import { Component, EventEmitter, Input, OnChanges, OnInit, Output, SimpleChanges, ViewChild } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import moment from 'moment';
import { HttpResponse } from '@angular/common/http';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';

import { ITeam } from 'app/entities/team/team.model';
import { ISkill } from 'app/entities/skill/skill.model';
import { ILevel } from 'app/entities/level/level.model';
import { IBadge } from 'app/entities/badge/badge.model';
import { IAchievableSkill } from 'app/custom/entities/achievable-skill/achievable-skill.model';
import { IComment } from 'app/entities/comment/comment.model';
import { ISkillRate } from 'app/custom/entities/skill-rate/skill-rate.model';
import { TeamsSelectionService } from 'app/custom/teams-selection/teams-selection.service';
import { ITraining } from 'app/entities/training/training.model';
import { ILevelSkill } from 'app/entities/level-skill/level-skill.model';
import { IBadgeSkill } from 'app/entities/badge-skill/badge-skill.model';
import { ITeamSkill } from 'app/entities/team-skill/team-skill.model';
import { TeamSkillService } from 'app/entities/team-skill/service/team-skill.service';
import { TrainingsAddComponent } from 'app/custom/shared/trainings/trainings-add.component';
import { SkillDetailsRatingComponent } from 'app/custom/teams/skill-details/skill-details-rating/skill-details-rating.component';
import { ServerInfoService } from 'app/custom/server-info/server-info.service';
import { SkillStatusUtils } from 'app/custom/entities/skill-status';
import { IServerInfo } from 'app/custom/entities/server-info/server-info.model';
import { SkillStatus } from 'app/entities/enumerations/skill-status.model';
import { ISkillObjects } from 'app/custom/entities/skill-objects/skill-objects.model';
import { TEAM_SKILLS_PER_PAGE } from '../../../../config/pagination.constants';

@Component({
  selector: 'jhi-skill-details-info',
  templateUrl: './skill-details-info.component.html',
  styleUrls: ['./skill-details-info.scss'],
})
export class SkillDetailsInfoComponent implements OnInit, OnChanges {
  @Input() team?: ITeam;

  @Input() skill?: ISkill;

  @Input() achievableSkill?: IAchievableSkill;

  @Output() skillChanged = new EventEmitter<IAchievableSkill>();
  // @Fixme Issue 37
  @Output() voteSubmitted = new EventEmitter<ISkillRate>();

  @Output() commentSubmitted = new EventEmitter<IComment>();

  @ViewChild(SkillDetailsRatingComponent) skillRating!: SkillDetailsRatingComponent;

  achievedByTeams: ITeam[] = [];

  neededForLevels: ILevel[] = [];

  neededForBadges: IBadge[] = [];

  trainings: ITraining[] = [];

  isTrainingPopupOpen = false;

  private _levels: ILevel[] = [];
  private _badges: IBadge[] = [];
  private _teams: ITeam[] = [];
  private _levelSkills: ILevelSkill[] = [];
  private _badgeSkills: IBadgeSkill[] = [];
  private _teamSkills: ITeamSkill[] = [];
  private _allTrainings: ITraining[] = [];

  constructor(
    private route: ActivatedRoute,
    private teamSkillsService: TeamSkillService,
    private teamsSelectionService: TeamsSelectionService,
    private modalService: NgbModal,
    private serverInfoService: ServerInfoService
  ) {}

  ngOnInit(): void {
    this.route.data.subscribe(({ dojoModel: { teams, teamSkills, levels, badges, levelSkills, badgeSkills }, trainings }) => {
      this._levels = (levels?.body ? levels.body : levels) || [];
      this._badges = (badges?.body ? badges.body : badges) || [];
      this._teams = (teams?.body ? teams.body : teams) || [];
      this._levelSkills = (levelSkills?.body ? levelSkills.body : levelSkills) || [];
      this._badgeSkills = (badgeSkills?.body ? badgeSkills.body : badgeSkills) || [];
      this._teamSkills = (teamSkills?.body ? teamSkills.body : teamSkills) || [];
      this._allTrainings = (trainings?.body ? trainings.body : trainings) || [];
      this.loadData();
    });
  }

  ngOnChanges(changes: SimpleChanges): void {
    // if (!changes.skill) {
    this.loadData();
    // }
  }

  loadData(): void {
    this.achievedByTeams = this._teams.filter((team: ITeam) =>
      this._teamSkills.some(
        (teamSkill: ITeamSkill) =>
          team.id === teamSkill.team?.id &&
          teamSkill.skill?.id === this.skill?.id &&
          teamSkill.skillStatus &&
          SkillStatusUtils.isValid(teamSkill.skillStatus)
      )
    );
    this.neededForLevels = this._levels.filter((level: ILevel) =>
      this._levelSkills.some((levelSkill: ILevelSkill) => level.id === levelSkill.level?.id && levelSkill.skill?.id === this.skill?.id)
    );
    this.neededForBadges = this._badges.filter((badge: IBadge) =>
      this._badgeSkills.some((badgeSkill: IBadgeSkill) => badge.id === badgeSkill.badge?.id && badgeSkill.skill?.id === this.skill?.id)
    );
    this.serverInfoService.query().subscribe((serverInfo: IServerInfo | null) => {
      this.trainings = this._allTrainings.filter(
        training =>
          (training.skills ?? []).find(skill => skill.id === this.skill?.id) &&
          (!training.validUntil || moment(serverInfo?.time).isBefore(training.validUntil.toDate()))
      );
    });
  }

  onVoteSubmittedFromChild(vote: ISkillRate): void {
    this.voteSubmitted.emit(vote);
    this.skillChanged.emit(this.achievableSkill);
  }

  onCommentSubmittedFromChild(comment: IComment): void {
    this.commentSubmitted.emit(comment);
  }

  onSkillInListChanged(skillObjs: ISkillObjects): void {
    this.achievableSkill = skillObjs.achievableSkill;
    this.skill = skillObjs.skill;
    this.skillRating.onSkillChanged(skillObjs.skill);
    this.teamSkillsService
      .query({
        page: 0,
        size: TEAM_SKILLS_PER_PAGE,
      })
      .subscribe((res: HttpResponse<ITeamSkill[]>) => {
        this._teamSkills = res.body ?? [];
        this.loadData();
      });
  }

  onSkillInListClicked(skillObjs: ISkillObjects): void {
    this.achievableSkill = skillObjs.achievableSkill;
    this.skill = skillObjs.skill;
    this.loadData();
    this.skillRating.onSkillChanged(skillObjs.skill);
  }

  onToggleSkill(): void {
    if (this.achievableSkill) {
      let isActivating = false;

      if (this.achievableSkill.skillStatus) {
        isActivating = !SkillStatusUtils.isValid(this.achievableSkill.skillStatus);
      }

      this.achievableSkill.achievedAt = isActivating ? moment() : undefined;

      this.skillChanged.emit(this.achievableSkill);
    }
  }

  onToggleIrrelevance(): void {
    if (this.achievableSkill) {
      const isIrrelevant = this.achievableSkill.skillStatus !== SkillStatus.IRRELEVANT;

      if (isIrrelevant) {
        this.achievableSkill.achievedAt = undefined;
      }
      this.achievableSkill.irrelevant = isIrrelevant;
      this.skillChanged.emit(this.achievableSkill);
    }
  }

  getStatusClass(skill: IAchievableSkill | undefined): string {
    if (skill?.skillStatus) {
      return SkillStatusUtils.getLowerCaseValue(skill.skillStatus);
    }

    return '';
  }

  getSkillStatusTranslationKey(skill: IAchievableSkill | undefined): string {
    if (skill?.skillStatus) {
      return SkillStatusUtils.getLowerCaseValue(skill.skillStatus);
    }

    return '';
  }

  updateSkillRating(skill: ISkill): void {
    this.skillRating.onSkillChanged(skill);
  }

  get isSkillAchieved(): boolean {
    if (this.achievableSkill) {
      return this.achievableSkill.achievedAt !== undefined;
    }

    return false;
  }

  isSameTeamSelected(): boolean {
    const selectedTeam = this.teamsSelectionService.selectedTeam;
    if (selectedTeam?.id && this.team?.id) {
      return selectedTeam.id === this.team.id;
    }

    return false;
  }

  addTraining(): NgbModalRef | undefined {
    if (this.isTrainingPopupOpen) {
      return;
    }
    this.isTrainingPopupOpen = true;
    const modalRef = this.modalService.open(TrainingsAddComponent, { size: 'lg' });
    modalRef.componentInstance.skills = [this.skill];
    modalRef.result.then(
      training => {
        this.isTrainingPopupOpen = false;
        this._allTrainings = this._allTrainings.concat(training);
        this.trainings = this.trainings.concat(training);
      },
      reason => {
        this.isTrainingPopupOpen = false;
      }
    );
    return modalRef;
  }
}
