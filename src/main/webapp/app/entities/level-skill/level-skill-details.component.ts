import { Component, Vue, Inject } from 'vue-property-decorator';

import { ILevelSkill } from '@/shared/model/level-skill.model';
import LevelSkillService from './level-skill.service';

@Component
export default class LevelSkillDetails extends Vue {
  @Inject('levelSkillService') private levelSkillService: () => LevelSkillService;
  public levelSkill: ILevelSkill = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.levelSkillId) {
        vm.retrieveLevelSkill(to.params.levelSkillId);
      }
    });
  }

  public retrieveLevelSkill(levelSkillId) {
    this.levelSkillService()
      .find(levelSkillId)
      .then(res => {
        this.levelSkill = res;
      });
  }

  public previousState() {
    this.$router.go(-1);
  }
}
