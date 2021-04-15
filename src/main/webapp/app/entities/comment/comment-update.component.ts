import { Component, Vue, Inject } from 'vue-property-decorator';

import { required } from 'vuelidate/lib/validators';
import dayjs from 'dayjs';
import { DATE_TIME_LONG_FORMAT } from '@/shared/date/filters';

import TeamService from '@/entities/team/team.service';
import { ITeam } from '@/shared/model/team.model';

import SkillService from '@/entities/skill/skill.service';
import { ISkill } from '@/shared/model/skill.model';

import { IComment, Comment } from '@/shared/model/comment.model';
import CommentService from './comment.service';

const validations: any = {
  comment: {
    text: {
      required,
    },
    createdAt: {
      required,
    },
    updatedAt: {
      required,
    },
    team: {
      required,
    },
    skill: {
      required,
    },
  },
};

@Component({
  validations,
})
export default class CommentUpdate extends Vue {
  @Inject('commentService') private commentService: () => CommentService;
  public comment: IComment = new Comment();

  @Inject('teamService') private teamService: () => TeamService;

  public teams: ITeam[] = [];

  @Inject('skillService') private skillService: () => SkillService;

  public skills: ISkill[] = [];
  public isSaving = false;
  public currentLanguage = '';

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.commentId) {
        vm.retrieveComment(to.params.commentId);
      }
      vm.initRelationships();
    });
  }

  created(): void {
    this.currentLanguage = this.$store.getters.currentLanguage;
    this.$store.watch(
      () => this.$store.getters.currentLanguage,
      () => {
        this.currentLanguage = this.$store.getters.currentLanguage;
      }
    );
  }

  public save(): void {
    this.isSaving = true;
    if (this.comment.id) {
      this.commentService()
        .update(this.comment)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('teamDojoApp.comment.updated', { param: param.id });
          return this.$root.$bvToast.toast(message.toString(), {
            toaster: 'b-toaster-top-center',
            title: 'Info',
            variant: 'info',
            solid: true,
            autoHideDelay: 5000,
          });
        });
    } else {
      this.commentService()
        .create(this.comment)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('teamDojoApp.comment.created', { param: param.id });
          this.$root.$bvToast.toast(message.toString(), {
            toaster: 'b-toaster-top-center',
            title: 'Success',
            variant: 'success',
            solid: true,
            autoHideDelay: 5000,
          });
        });
    }
  }

  public convertDateTimeFromServer(date: Date): string {
    if (date && dayjs(date).isValid()) {
      return dayjs(date).format(DATE_TIME_LONG_FORMAT);
    }
    return null;
  }

  public updateInstantField(field, event) {
    if (event.target.value) {
      this.comment[field] = dayjs(event.target.value, DATE_TIME_LONG_FORMAT);
    } else {
      this.comment[field] = null;
    }
  }

  public updateZonedDateTimeField(field, event) {
    if (event.target.value) {
      this.comment[field] = dayjs(event.target.value, DATE_TIME_LONG_FORMAT);
    } else {
      this.comment[field] = null;
    }
  }

  public retrieveComment(commentId): void {
    this.commentService()
      .find(commentId)
      .then(res => {
        res.createdAt = new Date(res.createdAt);
        res.updatedAt = new Date(res.updatedAt);
        this.comment = res;
      });
  }

  public previousState(): void {
    this.$router.go(-1);
  }

  public initRelationships(): void {
    this.teamService()
      .retrieve()
      .then(res => {
        this.teams = res.data;
      });
    this.skillService()
      .retrieve()
      .then(res => {
        this.skills = res.data;
      });
  }
}
