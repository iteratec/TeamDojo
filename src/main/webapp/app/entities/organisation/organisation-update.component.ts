import { Component, Vue, Inject } from 'vue-property-decorator';

import { required, minLength, maxLength, numeric, minValue } from 'vuelidate/lib/validators';
import dayjs from 'dayjs';
import { DATE_TIME_LONG_FORMAT } from '@/shared/date/filters';

import { IOrganisation, Organisation } from '@/shared/model/organisation.model';
import OrganisationService from './organisation.service';

const validations: any = {
  organisation: {
    title: {
      required,
      minLength: minLength(1),
      maxLength: maxLength(50),
    },
    description: {},
    levelUpScore: {},
    applicationMode: {
      required,
    },
    countOfConfirmations: {
      numeric,
      min: minValue(0),
    },
    createdAt: {
      required,
    },
    updatedAt: {
      required,
    },
  },
};

@Component({
  validations,
})
export default class OrganisationUpdate extends Vue {
  @Inject('organisationService') private organisationService: () => OrganisationService;
  public organisation: IOrganisation = new Organisation();

  public organisations: IOrganisation[] = [];
  public isSaving = false;
  public currentLanguage = '';

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.organisationId) {
        vm.retrieveOrganisation(to.params.organisationId);
      }
      vm.initRelationships();
    });
  }

  created(): void {
    this.currentLanguage = this.$store.getters.currentLanguage;
    this.$store.watch(
      () => this.$store.getters.currentLanguage,
      () => {
        this.currentLanguage = this.$store.getters.currentLanguage;
      }
    );
  }

  public save(): void {
    this.isSaving = true;
    if (this.organisation.id) {
      this.organisationService()
        .update(this.organisation)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('teamDojoApp.organisation.updated', { param: param.id });
          return this.$root.$bvToast.toast(message.toString(), {
            toaster: 'b-toaster-top-center',
            title: 'Info',
            variant: 'info',
            solid: true,
            autoHideDelay: 5000,
          });
        });
    } else {
      this.organisationService()
        .create(this.organisation)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('teamDojoApp.organisation.created', { param: param.id });
          this.$root.$bvToast.toast(message.toString(), {
            toaster: 'b-toaster-top-center',
            title: 'Success',
            variant: 'success',
            solid: true,
            autoHideDelay: 5000,
          });
        });
    }
  }

  public convertDateTimeFromServer(date: Date): string {
    if (date && dayjs(date).isValid()) {
      return dayjs(date).format(DATE_TIME_LONG_FORMAT);
    }
    return null;
  }

  public updateInstantField(field, event) {
    if (event.target.value) {
      this.organisation[field] = dayjs(event.target.value, DATE_TIME_LONG_FORMAT);
    } else {
      this.organisation[field] = null;
    }
  }

  public updateZonedDateTimeField(field, event) {
    if (event.target.value) {
      this.organisation[field] = dayjs(event.target.value, DATE_TIME_LONG_FORMAT);
    } else {
      this.organisation[field] = null;
    }
  }

  public retrieveOrganisation(organisationId): void {
    this.organisationService()
      .find(organisationId)
      .then(res => {
        res.createdAt = new Date(res.createdAt);
        res.updatedAt = new Date(res.updatedAt);
        this.organisation = res;
      });
  }

  public previousState(): void {
    this.$router.go(-1);
  }

  public initRelationships(): void {
    this.organisationService()
      .retrieve()
      .then(res => {
        this.organisations = res.data;
      });
  }
}
