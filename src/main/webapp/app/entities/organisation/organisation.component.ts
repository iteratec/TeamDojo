import { mixins } from 'vue-class-component';

import { Component, Vue, Inject } from 'vue-property-decorator';
import Vue2Filters from 'vue2-filters';
import { IOrganisation } from '@/shared/model/organisation.model';

import OrganisationService from './organisation.service';

@Component({
  mixins: [Vue2Filters.mixin],
})
export default class Organisation extends Vue {
  @Inject('organisationService') private organisationService: () => OrganisationService;
  private removeId: number = null;

  public organisations: IOrganisation[] = [];

  public isFetching = false;

  public mounted(): void {
    this.retrieveAllOrganisations();
  }

  public clear(): void {
    this.retrieveAllOrganisations();
  }

  public retrieveAllOrganisations(): void {
    this.isFetching = true;

    this.organisationService()
      .retrieve()
      .then(
        res => {
          this.organisations = res.data;
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

  public prepareRemove(instance: IOrganisation): void {
    this.removeId = instance.id;
    if (<any>this.$refs.removeEntity) {
      (<any>this.$refs.removeEntity).show();
    }
  }

  public removeOrganisation(): void {
    this.organisationService()
      .delete(this.removeId)
      .then(() => {
        const message = this.$t('teamDojoApp.organisation.deleted', { param: this.removeId });
        this.$bvToast.toast(message.toString(), {
          toaster: 'b-toaster-top-center',
          title: 'Info',
          variant: 'danger',
          solid: true,
          autoHideDelay: 5000,
        });
        this.removeId = null;
        this.retrieveAllOrganisations();
        this.closeDialog();
      });
  }

  public closeDialog(): void {
    (<any>this.$refs.removeEntity).hide();
  }
}
