import { Component, Vue, Inject } from 'vue-property-decorator';

import { IActivity } from '@/shared/model/activity.model';
import ActivityService from './activity.service';

@Component
export default class ActivityDetails extends Vue {
  @Inject('activityService') private activityService: () => ActivityService;
  public activity: IActivity = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.activityId) {
        vm.retrieveActivity(to.params.activityId);
      }
    });
  }

  public retrieveActivity(activityId) {
    this.activityService()
      .find(activityId)
      .then(res => {
        this.activity = res;
      });
  }

  public previousState() {
    this.$router.go(-1);
  }
}
