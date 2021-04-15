import { mixins } from 'vue-class-component';

import { Component, Vue, Inject } from 'vue-property-decorator';
import Vue2Filters from 'vue2-filters';
import { IReport } from '@/shared/model/report.model';

import ReportService from './report.service';

@Component({
  mixins: [Vue2Filters.mixin],
})
export default class Report extends Vue {
  @Inject('reportService') private reportService: () => ReportService;
  private removeId: number = null;

  public reports: IReport[] = [];

  public isFetching = false;

  public mounted(): void {
    this.retrieveAllReports();
  }

  public clear(): void {
    this.retrieveAllReports();
  }

  public retrieveAllReports(): void {
    this.isFetching = true;

    this.reportService()
      .retrieve()
      .then(
        res => {
          this.reports = res.data;
          this.isFetching = false;
        },
        err => {
          this.isFetching = false;
        }
      );
  }

  public handleSyncList(): void {
    this.clear();
  }

  public prepareRemove(instance: IReport): void {
    this.removeId = instance.id;
    if (<any>this.$refs.removeEntity) {
      (<any>this.$refs.removeEntity).show();
    }
  }

  public removeReport(): void {
    this.reportService()
      .delete(this.removeId)
      .then(() => {
        const message = this.$t('teamDojoApp.report.deleted', { param: this.removeId });
        this.$bvToast.toast(message.toString(), {
          toaster: 'b-toaster-top-center',
          title: 'Info',
          variant: 'danger',
          solid: true,
          autoHideDelay: 5000,
        });
        this.removeId = null;
        this.retrieveAllReports();
        this.closeDialog();
      });
  }

  public closeDialog(): void {
    (<any>this.$refs.removeEntity).hide();
  }
}
