import { Component, Vue, Inject } from 'vue-property-decorator';

import { ITraining } from '@/shared/model/training.model';
import TrainingService from './training.service';

@Component
export default class TrainingDetails extends Vue {
  @Inject('trainingService') private trainingService: () => TrainingService;
  public training: ITraining = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.trainingId) {
        vm.retrieveTraining(to.params.trainingId);
      }
    });
  }

  public retrieveTraining(trainingId) {
    this.trainingService()
      .find(trainingId)
      .then(res => {
        this.training = res;
      });
  }

  public previousState() {
    this.$router.go(-1);
  }
}
