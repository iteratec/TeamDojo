import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import dayjs from 'dayjs/esm';

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
  @Output() commentSubmitted = new EventEmitter<IComment>();
  newComment: IComment = new Comment();

  constructor(private commentService: CommentService) {}

  ngOnInit(): void {
    this.newComment = new Comment();
  }

  isActiveTeam(comment: IComment): boolean {
    return this.selectedTeam?.id === comment.team?.id;
  }

  onSubmit(): void {
    if (this.selectedTeam?.id) {
      this.newComment.skill = this.skill ? this.skill : undefined;
      this.newComment.team = this.selectedTeam;
      this.commentService.create(this.newComment).subscribe((res: HttpResponse<IComment>) => {
        if (res.body) {
          this.newComment = new Comment();
          this.commentSubmitted.emit(res.body);
        }
      });
    }
  }
}
