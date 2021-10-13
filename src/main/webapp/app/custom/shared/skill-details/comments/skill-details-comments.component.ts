import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import * as dayjs from 'dayjs';

import 'simplebar';

import { CommentService } from 'app/entities/comment/service/comment.service';
import { ISkill } from 'app/entities/skill/skill.model';
import { ITeam } from 'app/entities/team/team.model';
import { IComment, Comment } from 'app/entities/comment/comment.model';

@Component({
  selector: 'jhi-skill-details-comments',
  templateUrl: './skill-details-comments.component.html',
  styleUrls: ['./skill-details-comments.scss'],
})
export class SkillDetailsCommentsComponent implements OnInit {
  @Input() selectedTeam?: ITeam;
  @Input() skill?: ISkill;
  @Input() teams: ITeam[] = [];
  @Input() comments: IComment[] = [];
  @Output() onCommentSubmitted = new EventEmitter<IComment>();
  newComment: IComment = new Comment();

  constructor(private commentService: CommentService) {}

  ngOnInit() {
    this.newComment = new Comment();
  }

  isActiveTeam(comment: IComment) {
    return this.selectedTeam && comment && this.selectedTeam.id === comment.team?.id;
  }

  onSubmit() {
    this.newComment.createdAt = dayjs();
    this.newComment.skill = this.skill ? this.skill : undefined;
    this.newComment.team = this.selectedTeam ? this.selectedTeam : undefined;
    this.commentService.create(this.newComment).subscribe((res: HttpResponse<IComment>) => {
      if (res.body) {
        this.newComment = new Comment();
        this.onCommentSubmitted.emit(res.body);
      }
    });
  }
}
