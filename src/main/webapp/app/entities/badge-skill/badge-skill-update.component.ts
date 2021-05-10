import { Component, Vue, Inject } from 'vue-property-decorator';

import { required } from 'vuelidate/lib/validators';

import BadgeService from '@/entities/badge/badge.service';
import { IBadge } from '@/shared/model/badge.model';

import SkillService from '@/entities/skill/skill.service';
import { ISkill } from '@/shared/model/skill.model';

import { IBadgeSkill, BadgeSkill } from '@/shared/model/badge-skill.model';
import BadgeSkillService from './badge-skill.service';

const validations: any = {
  badgeSkill: {
    badge: {
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
export default class BadgeSkillUpdate extends Vue {
  @Inject('badgeSkillService') private badgeSkillService: () => BadgeSkillService;
  public badgeSkill: IBadgeSkill = new BadgeSkill();

  @Inject('badgeService') private badgeService: () => BadgeService;

  public badges: IBadge[] = [];

  @Inject('skillService') private skillService: () => SkillService;

  public skills: ISkill[] = [];
  public isSaving = false;
  public currentLanguage = '';

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.badgeSkillId) {
        vm.retrieveBadgeSkill(to.params.badgeSkillId);
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
    if (this.badgeSkill.id) {
      this.badgeSkillService()
        .update(this.badgeSkill)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('teamDojoApp.badgeSkill.updated', { param: param.id });
          return this.$root.$bvToast.toast(message.toString(), {
            toaster: 'b-toaster-top-center',
            title: 'Info',
            variant: 'info',
            solid: true,
            autoHideDelay: 5000,
          });
        });
    } else {
      this.badgeSkillService()
        .create(this.badgeSkill)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('teamDojoApp.badgeSkill.created', { param: param.id });
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

  public retrieveBadgeSkill(badgeSkillId): void {
    this.badgeSkillService()
      .find(badgeSkillId)
      .then(res => {
        this.badgeSkill = res;
      });
  }

  public previousState(): void {
    this.$router.go(-1);
  }

  public initRelationships(): void {
    this.badgeService()
      .retrieve()
      .then(res => {
        this.badges = res.data;
      });
    this.skillService()
      .retrieve()
      .then(res => {
        this.skills = res.data;
      });
  }
}
