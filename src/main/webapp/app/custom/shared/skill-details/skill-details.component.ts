import { EventEmitter, OnInit, Output, ViewChild } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { ITeam, Team } from 'app/entities/team/team.model';
import { ISkill, Skill } from 'app/entities/skill/skill.model';
import { IBadge } from 'app/entities/badge/badge.model';
import { IComment } from 'app/entities/comment/comment.model';
import { IAchievableSkill } from 'app/custom/entities/achievable-skill/achievable-skill.model';
import { TeamsSkillsService } from 'app/custom/teams/teams-skills.service';
import { TeamsSkillsComponent } from 'app/custom/teams/skills/teams-skills.component';
import { SkillDetailsInfoComponent } from 'app/custom/shared/skill-details/info/skill-details-info.component';

export class SkillDetailsComponentParent {
  team: ITeam = new Team();
  skill: ISkill = new Skill();
  selectedTeam: ITeam = new Team();
  teams: ITeam[] = [];
  badges: IBadge[] = [];
  skills: ISkill[] = [];
  comments: IComment[] = [];
  skillComments: IComment[] = [];

  @Output() skillChanged = new EventEmitter<IAchievableSkill>();

  @ViewChild(TeamsSkillsComponent) skillList?: TeamsSkillsComponent;
  @ViewChild(SkillDetailsInfoComponent) skillInfo?: SkillDetailsInfoComponent;

  constructor(public route: ActivatedRoute, public teamsSkillsService: TeamsSkillsService) {}

  // TODO make type more specific
  setResolvedData({ teams, skill, comments, selectedTeam, badges, skills }: any): void {
    this.teams = (teams?.body ? teams.body : teams) || [];
    this.skill = skill?.body ? skill.body : skill;
    this.selectedTeam = selectedTeam?.body ? selectedTeam.body : selectedTeam;
    this.badges = (badges?.body ? badges.body : badges) || [];
    this.skills = (skills?.body ? skills.body : skills) || [];
    this.comments = (comments?.body ? comments.body : comments) || [];
    this._mapCommentAuthors();
    this.skillComments = this._getSkillComments();
  }
  // TODO make type more specific
  onSkillInListChanged(skillObjs: any): void {
    this.skill = skillObjs.iSkill;
    this.skillInfo?.onSkillInListChanged(skillObjs);
    this.skillComments = this._getSkillComments();
  }
  // TODO make type more specific
  onSkillInListClicked(skillObjs: any): void {
    this.skill = skillObjs.iSkill;
    this.skillInfo?.onSkillInListClicked(skillObjs);
    this.skillComments = this._getSkillComments();
  }

  onCommentSubmitted(newComment: IComment): void {
    this.comments.push(newComment);
    this._mapCommentAuthors();
    this.skillComments = this._getSkillComments();
  }

  protected _mapCommentAuthors(): void {
    this.comments
      .filter((comment: IComment) => comment.team === undefined || Object.keys(comment.team).length === 0)
      .forEach((comment: IComment) => {
        comment.team = this.teams.find((t: ITeam) => t.id === comment.team?.id) ?? {};
      });
  }

  protected _getSkillComments(): IComment[] {
    return this.comments
      .filter((comment: IComment) => comment.skill?.id === this.skill.id)
      .sort((comment1: IComment, comment2: IComment) => this.compareCommentByCreationDate(comment1, comment2));
  }

  public compareCommentByCreationDate(left: IComment, right: IComment): number {
    if (left.createdAt && right.createdAt) {
      return this.truncateNumber(left.createdAt.diff(right.createdAt));
    }

    if (left.createdAt && right.createdAt === undefined) {
      return 1;
    }

    if (left.createdAt === undefined && right.createdAt) {
      return -1;
    }

    return 0;
  }

  private truncateNumber(num: number) {
    if (num < 0) {
      return -1;
    }

    if (num > 0) {
      return 1;
    }

    return num;
  }
}
