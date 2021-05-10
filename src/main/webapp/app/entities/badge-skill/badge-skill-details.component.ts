import { Component, Vue, Inject } from 'vue-property-decorator';

import { IBadgeSkill } from '@/shared/model/badge-skill.model';
import BadgeSkillService from './badge-skill.service';

@Component
export default class BadgeSkillDetails extends Vue {
  @Inject('badgeSkillService') private badgeSkillService: () => BadgeSkillService;
  public badgeSkill: IBadgeSkill = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.badgeSkillId) {
        vm.retrieveBadgeSkill(to.params.badgeSkillId);
      }
    });
  }

  public retrieveBadgeSkill(badgeSkillId) {
    this.badgeSkillService()
      .find(badgeSkillId)
      .then(res => {
        this.badgeSkill = res;
      });
  }

  public previousState() {
    this.$router.go(-1);
  }
}
