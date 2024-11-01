/*
 SPDX-FileCopyrightText: the TeamDojo authors
 SPDX-License-Identifier: Apache-2.0
 */
import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { HttpResponse } from '@angular/common/http';

import { ISkill } from 'app/entities/skill/skill.model';
import { Comment, IComment } from 'app/entities/comment/comment.model';
import { CommentService } from 'app/entities/comment/service/comment.service';
import { TeamsSelectionService } from 'app/custom/teams-selection/teams-selection.service';
import { ISkillRate, SkillRate } from 'app/custom/entities/skill-rate/skill-rate.model';
import { ExtendedSkillService } from '../../../../entities/skill/service/custom/extended-skill.service';

@Component({
  selector: 'jhi-star-rating',
  templateUrl: './skill-details-rating.component.html',
  styleUrls: ['./skill-details-rating.scss'],
})
export class SkillDetailsRatingComponent implements OnInit {
  @Input() skill?: ISkill;
  @Output() voteSubmitted = new EventEmitter<ISkillRate>();
  @Output() commentSubmitted = new EventEmitter<IComment>();

  rateScore = 0;
  rateCount = 0;
  comment = '';
  private modalRef?: NgbModalRef;
  private newComment: IComment = new Comment();

  constructor(
    private skillService: ExtendedSkillService,
    private modalService: NgbModal,
    private commentService: CommentService,
    private teamsSelectionService: TeamsSelectionService
  ) {}

  ngOnInit(): void {
    this.onSkillChanged(this.skill);
    this.newComment = new Comment();
  }

  onSkillChanged(skill: ISkill | undefined): void {
    this.skill = skill;
    if (this.skill?.rateScore) {
      this.rateScore = this.skill.rateScore;
    }

    this.rateCount = this.skill?.rateCount ? this.skill.rateCount : 0;
  }

  isActiveTeam(): boolean {
    return this.teamsSelectionService.selectedTeam != null && typeof this.teamsSelectionService.selectedTeam !== 'undefined';
  }

  voteSkill(): void {
    const rate = new SkillRate(this.skill?.id, this.rateScore);

    this.skillService.createVote(rate).subscribe((res: HttpResponse<ISkill>) => {
      if (res.body) {
        this.skill = res.body;
        if (this.skill.rateScore) {
          this.rateScore = this.skill.rateScore;
        }

        if (this.skill.rateCount) {
          this.rateCount = this.skill.rateCount;
        }

        this.voteSubmitted.emit({ skillId: this.skill.id, rateCount: this.rateCount, rateScore: this.rateScore });
      }
    });

    this.newComment.text = String(this.rateScore) + ' ★ - ' + this.comment;
    this.submitComment();
    this.comment = '';
    this.modalRef?.close();
  }

  submitComment(): void {
    const team = this.teamsSelectionService.selectedTeam;

    if (team != null && this.skill != null) {
      this.newComment.skill = this.skill;
      this.newComment.team = team;

      this.commentService.create(this.newComment).subscribe((res: HttpResponse<IComment>) => {
        if (res.body) {
          this.newComment = new Comment();
          this.commentSubmitted.emit(res.body);
        }
      });
    }
  }

  open(content: any): void {
    if (this.isActiveTeam()) {
      this.modalRef = this.modalService.open(content);
    }
  }
}
