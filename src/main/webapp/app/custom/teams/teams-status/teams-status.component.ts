import { Component, Input, OnChanges, OnInit, SimpleChanges } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { take } from 'rxjs/operators';

import { ITeam } from 'app/entities/team/team.model';
import { IBadge } from 'app/entities/badge/badge.model';
import { CompletionCheck } from 'app/custom/helper/completion-check';
import { RelevanceCheck } from 'app/custom/helper/relevance-check';
import { IDimension } from 'app/entities/dimension/dimension.model';
import { ISkill } from 'app/entities/skill/skill.model';
import { ITeamSkill } from 'app/entities/team-skill/team-skill.model';
import { BreadcrumbService } from 'app/custom/layouts/navbar/breadcrumb.service';
import { TeamsSelectionService } from 'app/custom/teams-selection/teams-selection.service';
import { OrganisationService } from 'app/entities/organisation/service/organisation.service';
import { HighestLevel, IHighestLevel } from 'app/custom/entities/highest-level/highest-level.model';
import { ILevel } from 'app/entities/level/level.model';
import { TeamScoreCalculation } from 'app/custom/helper/team-score-calculation';
import { TeamsEditComponent } from 'app/custom/teams/teams-edit/teams-edit.component';

@Component({
  selector: 'jhi-teams-status',
  templateUrl: './teams-status.component.html',
  styleUrls: ['./teams-status.scss'],
})
export class TeamsStatusComponent implements OnInit, OnChanges {
  @Input() team?: ITeam;
  @Input() teamSkills?: ITeamSkill[] | null;
  @Input() badges?: IBadge[];
  @Input() skills?: ISkill[];
  completedBadges: IBadge[];
  highestAchievedLevels: IHighestLevel[];
  teamScore: number;
  levelUpScore: number;
  isTeamEditOpen: boolean;

  constructor(
    private organisationService: OrganisationService,
    private router: Router,
    private breadcrumbService: BreadcrumbService,
    private teamSelectionService: TeamsSelectionService,
    private modalService: NgbModal
  ) {
    this.highestAchievedLevels = [];
    this.completedBadges = [];
    this.teamScore = 0;
    this.levelUpScore = 0;
    this.isTeamEditOpen = false;
  }

  ngOnInit(): void {
    if (this.team) {
      this.team.skills = this.teamSkills;
    }
    this.organisationService
      .query()
      .pipe(take(1))
      .subscribe(res => {
        if (res.body) {
          this.levelUpScore = res.body[0].levelUpScore ? res.body[0].levelUpScore : 0;
        }
      });
    this.calculateStatus();
  }

  ngOnChanges(changes: SimpleChanges): void {
    if (this.team) {
      this.team.skills = this.teamSkills;
      this.calculateStatus();
    }
  }

  editTeam(): NgbModalRef | void {
    if (this.isTeamEditOpen) {
      return;
    }
    this.isTeamEditOpen = true;
    const modalRef = this.modalService.open(TeamsEditComponent, { size: 'lg' });
    (<TeamsEditComponent>modalRef.componentInstance).editMode = true;
    (<TeamsEditComponent>modalRef.componentInstance).team = Object.assign({}, this.team);
    modalRef.result.then(
      team => {
        this.team = team;
        this.isTeamEditOpen = false;
        this.teamSelectionService.query().subscribe();
        this.router.navigate(['teams', (<ITeam>team).shortTitle], { queryParamsHandling: 'preserve' });
      },
      reason => {
        this.isTeamEditOpen = false;
      }
    );
    return modalRef;
  }

  isSameTeamSelected(): boolean {
    const selectedTeam = this.teamSelectionService.selectedTeam;
    return selectedTeam?.id === this.team?.id;
  }

  selectItem(itemType: string, id: number | undefined): void {
    if (id) {
      this.router.navigate(['teams', this.team?.shortTitle], {
        queryParams: { [itemType]: id },
      });
    }
  }

  get hasLeveledUp(): boolean {
    return this.levelUpScore > 0 && this.teamScore >= this.levelUpScore;
  }

  private calculateStatus(): void {
    if (this.team && this.skills && this.badges) {
      this.teamScore = TeamScoreCalculation.calcTeamScore(this.team, this.skills, this.badges);
      this.completedBadges = this.getCompletedBadges();
      this.highestAchievedLevels = this.getHighestAchievedLevels();
    }
  }

  private getCompletedBadges(): IBadge[] {
    const res = this.badges?.filter((badge: IBadge) => {
      new RelevanceCheck(this.team).isRelevantBadge(badge) && new CompletionCheck(this.team, badge, this.skills).isCompleted();
    });

    return res ? res : [];
  }

  private isLevelCompleted(level: ILevel): boolean {
    if (this.team === undefined || this.skills === undefined) {
      return false;
    }
    return new CompletionCheck(this.team, level, this.skills).isCompleted();
  }

  private getHighestAchievedLevels(): IHighestLevel[] {
    const highestAchievedLevels: IHighestLevel[] = [];
    this.team?.participations?.forEach((dimension: IDimension) => {
      let ordinal = 0;
      let achievedLevel;
      for (const level of dimension.levels ?? []) {
        if (!this.isLevelCompleted(level)) {
          break;
        }
        achievedLevel = level;
        ordinal++;
      }
      if (achievedLevel) {
        highestAchievedLevels.push(new HighestLevel(dimension, achievedLevel, ordinal));
      }
    });
    return highestAchievedLevels;
  }
}
