import { Component, Input, OnChanges, OnInit, SimpleChanges } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { take } from 'rxjs/operators';

//import { TeamsEditComponent } from 'app/shared/teams/teams-edit.component';
//import {TeamScoreCalculation} from "app/custom/helper/team-score-calculation";

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

@Component({
  selector: 'jhi-teams-status',
  templateUrl: './teams-status.component.html',
  styleUrls: ['teams-status.scss'],
})
export class TeamsStatusComponent implements OnInit, OnChanges {
  @Input() team!: ITeam;
  @Input() teamSkills!: ITeamSkill[];
  @Input() badges!: IBadge[];
  @Input() skills!: ISkill[];
  completedBadges: IBadge[];
  highestAchievedLevels: IHighestLevel[];
  teamScore: number;
  levelUpScore: number;
  isTeamEditOpen: boolean;

  constructor(
    private organizationService: OrganisationService,
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
    this.team.skills = this.teamSkills;
    this.organizationService
      .query()
      .pipe(take(1))
      .subscribe(res => {
        this.levelUpScore = res && res.body[0] ? res.body[0].levelUpScore : 0;
      });
    this.calculateStatus();
  }

  ngOnChanges(changes: SimpleChanges): void {
    this.team.skills = this.teamSkills;
    this.calculateStatus();
  }

  /*editTeam(): NgbModalRef {
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
        this.router.navigate(['teams', (<ITeam>team).shortName], { preserveQueryParams: true });
      },
      reason => {
        this.isTeamEditOpen = false;
      }
    );
    return modalRef;
  }*/

  private hasTeamChanged(team: any) {
    return team && team.previousValue && team.previousValue.id !== team.currentValue.id;
  }

  private calculateStatus() {
    //this.teamScore = TeamScoreCalculation.calcTeamScore(this.team, this.skills, this.badges);
    this.completedBadges = this.getCompletedBadges();
    this.highestAchievedLevels = this.getHighestAchievedLevels();
  }

  selectItem(itemType: string, id: number) {
    this.router.navigate(['teams', this.team.shortTitle], {
      queryParams: { [itemType]: id },
    });
  }

  isSameTeamSelected(): boolean {
    const selectedTeam = this.teamSelectionService.selectedTeam;
    return selectedTeam?.id === this.team.id;
  }

  private getCompletedBadges(): IBadge[] {
    return this.badges.filter(
      (badge: IBadge) =>
        new RelevanceCheck(this.team).isRelevantBadge(badge) && new CompletionCheck(this.team, badge, this.skills).isCompleted()
    );
  }

  private isLevelCompleted(level: ILevel): boolean {
    return new CompletionCheck(this.team, level, this.skills).isCompleted();
  }

  private getHighestAchievedLevels(): IHighestLevel[] {
    const highestAchievedLevels: IHighestLevel[] = [];
    this.team.participations?.forEach((dimension: IDimension) => {
      let ordinal = 0;
      let achievedLevel;
      for (const level of dimension.levels || []) {
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

  get hasLeveledUp(): boolean {
    return this.levelUpScore > 0 && this.teamScore >= this.levelUpScore;
  }
}
