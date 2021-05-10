import { Component, Vue, Inject } from 'vue-property-decorator';

import { IDimension } from '@/shared/model/dimension.model';
import DimensionService from './dimension.service';

@Component
export default class DimensionDetails extends Vue {
  @Inject('dimensionService') private dimensionService: () => DimensionService;
  public dimension: IDimension = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.dimensionId) {
        vm.retrieveDimension(to.params.dimensionId);
      }
    });
  }

  public retrieveDimension(dimensionId) {
    this.dimensionService()
      .find(dimensionId)
      .then(res => {
        this.dimension = res;
      });
  }

  public previousState() {
    this.$router.go(-1);
  }
}
