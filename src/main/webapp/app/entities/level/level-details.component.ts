import { Component, Vue, Inject } from 'vue-property-decorator';

import { ILevel } from '@/shared/model/level.model';
import LevelService from './level.service';

@Component
export default class LevelDetails extends Vue {
  @Inject('levelService') private levelService: () => LevelService;
  public level: ILevel = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.levelId) {
        vm.retrieveLevel(to.params.levelId);
      }
    });
  }

  public retrieveLevel(levelId) {
    this.levelService()
      .find(levelId)
      .then(res => {
        this.level = res;
      });
  }

  public previousState() {
    this.$router.go(-1);
  }
}
