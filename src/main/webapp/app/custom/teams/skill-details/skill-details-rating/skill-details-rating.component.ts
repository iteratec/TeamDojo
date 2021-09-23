import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { HttpResponse } from '@angular/common/http';

import * as dayjs from 'dayjs';

import { ISkill, Skill } from 'app/entities/skill/skill.model';
import { SkillService } from 'app/entities/skill/service/skill.service';
import { Comment, IComment } from 'app/entities/comment/comment.model';
import { CommentService } from 'app/entities/comment/service/comment.service';
import { TeamsSelectionService } from 'app/custom/teams-selection/teams-selection.service';
import { ISkillRate, SkillRate } from 'app/custom/entities/skill-rate/skill-rate.model';

@Component({
  selector: 'jhi-star-rating',
  templateUrl: './skill-details-rating.component.html',
  styleUrls: ['./skill-details-rating.scss'],
})
export class SkillDetailsRatingComponent implements OnInit {
  @Input() skill: ISkill = new Skill();
  @Output() onVoteSubmitted = new EventEmitter<ISkillRate>();
  @Output() onCommentSubmitted = new EventEmitter<IComment>();

  rateScore = 0;
  rateCount = 0;
  comment: string = '';
  private modalRef?: NgbModalRef;
  private newComment: IComment = new Comment();

  constructor(
    private skillService: SkillService,
    private modalService: NgbModal,
    private commentService: CommentService,
    private teamsSelectionService: TeamsSelectionService
  ) {}

  ngOnInit(): void {
    this.onSkillChanged(this.skill);
    this.newComment = new Comment();
  }

  onSkillChanged(skill: ISkill) {
    this.skill = skill;
    if (this.skill.rateScore) {
      this.rateScore = this.skill.rateScore;
    }

    this.rateCount = this.skill.rateCount !== null && typeof this.skill.rateCount !== 'undefined' ? this.skill.rateCount : 0;
  }

  isActiveTeam(): Boolean {
    return this.teamsSelectionService.selectedTeam !== null && typeof this.teamsSelectionService.selectedTeam !== 'undefined';
  }

  voteSkill() {
    const rate = new SkillRate(this.skill.id, this.rateScore);
    this.skillService.createVote(rate).subscribe((res: HttpResponse<ISkill>) => {
      if (res.body) {
        this.skill = res.body;
        if (this.skill.rateScore) {
          this.rateScore = this.skill.rateScore;
        }

        if (this.skill.rateCount) {
          this.rateCount = this.skill.rateCount;
        }

        this.onVoteSubmitted.emit({ skillId: this.skill.id, rateCount: this.rateCount, rateScore: this.rateScore });
      }
    });

    this.newComment.text = this.rateScore + ' ★ - ' + this.comment;
    this.submitComment();
    this.comment = '';
    this.modalRef?.close();
  }

  submitComment(): void {
    const team = this.teamsSelectionService.selectedTeam;
    this.newComment.createdAt = dayjs();
    if (this.newComment.skill) {
      this.newComment.skill.id = this.skill ? this.skill.id : undefined;
      this.newComment.skill.title = this.skill ? this.skill.title : undefined;
    }

    if (this.newComment.team) {
      this.newComment.team.id = team ? team.id : undefined;
      this.newComment.team.shortTitle = team ? team.shortTitle : undefined;
    }

    this.commentService.create(this.newComment).subscribe((res: HttpResponse<IComment>) => {
      if (res.body) {
        this.newComment = new Comment();
        this.onCommentSubmitted.emit(res.body);
      }
    });
  }

  open(content) {
    if (this.isActiveTeam()) {
      this.modalRef = this.modalService.open(content);
    }
  }
}
