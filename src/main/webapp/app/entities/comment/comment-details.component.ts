import { Component, Vue, Inject } from 'vue-property-decorator';

import { IComment } from '@/shared/model/comment.model';
import CommentService from './comment.service';

@Component
export default class CommentDetails extends Vue {
  @Inject('commentService') private commentService: () => CommentService;
  public comment: IComment = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.commentId) {
        vm.retrieveComment(to.params.commentId);
      }
    });
  }

  public retrieveComment(commentId) {
    this.commentService()
      .find(commentId)
      .then(res => {
        this.comment = res;
      });
  }

  public previousState() {
    this.$router.go(-1);
  }
}
