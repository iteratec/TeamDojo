import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import * as moment from 'moment';

import 'simplebar';

import { CommentService } from 'app/entities/comment/service/comment.service';
import { ISkill } from 'app/entities/skill/skill.model';
import { ITeam } from 'app/entities/team/team.model';
import { IComment } from 'app/entities/comment/comment.model';

@Component({
  selector: 'jhi-skill-details-comments',
  templateUrl: './skill-details-comments.component.html',
  styleUrls: ['./skill-details-comments.scss'],
})
export class SkillDetailsCommentsComponent implements OnInit {
  @Input() selectedTeam: ITeam;
  @Input() skill: ISkill;
  @Input() teams: ITeam[] = [];
  @Input() comments: IComment[] = [];
  @Output() onCommentSubmitted = new EventEmitter<IComment>();
  newComment: IComment = new Comment();

  constructor(private commentService: CommentService) {}

  ngOnInit() {
    this.newComment = new Comment();
  }

  isActiveTeam(comment: IComment) {
    return this.selectedTeam && comment && this.selectedTeam.id === comment.teamId;
  }

  onSubmit() {
    this.newComment.creationDate = moment();
    this.newComment.skillId = this.skill ? this.skill.id : undefined;
    this.newComment.skillTitle = this.skill ? this.skill.title : undefined;
    this.newComment.teamId = this.selectedTeam ? this.selectedTeam.id : undefined;
    this.newComment.teamShortName = this.selectedTeam ? this.selectedTeam.shortName : undefined;
    this.commentService.create(this.newComment).subscribe((res: HttpResponse<IComment>) => {
      if (res.body) {
        this.newComment = new Comment();
        this.onCommentSubmitted.emit(res.body);
      }
    });
  }
}
