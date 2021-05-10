import { Component, Vue, Inject } from 'vue-property-decorator';

import { ISkill } from '@/shared/model/skill.model';
import SkillService from './skill.service';

@Component
export default class SkillDetails extends Vue {
  @Inject('skillService') private skillService: () => SkillService;
  public skill: ISkill = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.skillId) {
        vm.retrieveSkill(to.params.skillId);
      }
    });
  }

  public retrieveSkill(skillId) {
    this.skillService()
      .find(skillId)
      .then(res => {
        this.skill = res;
      });
  }

  public previousState() {
    this.$router.go(-1);
  }
}
