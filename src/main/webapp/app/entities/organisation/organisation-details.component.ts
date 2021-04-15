import { Component, Vue, Inject } from 'vue-property-decorator';

import { IOrganisation } from '@/shared/model/organisation.model';
import OrganisationService from './organisation.service';

@Component
export default class OrganisationDetails extends Vue {
  @Inject('organisationService') private organisationService: () => OrganisationService;
  public organisation: IOrganisation = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.organisationId) {
        vm.retrieveOrganisation(to.params.organisationId);
      }
    });
  }

  public retrieveOrganisation(organisationId) {
    this.organisationService()
      .find(organisationId)
      .then(res => {
        this.organisation = res;
      });
  }

  public previousState() {
    this.$router.go(-1);
  }
}
