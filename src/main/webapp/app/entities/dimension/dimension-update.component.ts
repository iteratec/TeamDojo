import { Component, Vue, Inject } from 'vue-property-decorator';

import { required, minLength, maxLength } from 'vuelidate/lib/validators';
import dayjs from 'dayjs';
import { DATE_TIME_LONG_FORMAT } from '@/shared/date/filters';

import LevelService from '@/entities/level/level.service';
import { ILevel } from '@/shared/model/level.model';

import BadgeService from '@/entities/badge/badge.service';
import { IBadge } from '@/shared/model/badge.model';

import TeamService from '@/entities/team/team.service';
import { ITeam } from '@/shared/model/team.model';

import { IDimension, Dimension } from '@/shared/model/dimension.model';
import DimensionService from './dimension.service';

const validations: any = {
  dimension: {
    title: {
      required,
      minLength: minLength(1),
      maxLength: maxLength(50),
    },
    description: {
      maxLength: maxLength(255),
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
export default class DimensionUpdate extends Vue {
  @Inject('dimensionService') private dimensionService: () => DimensionService;
  public dimension: IDimension = new Dimension();

  @Inject('levelService') private levelService: () => LevelService;

  public levels: ILevel[] = [];

  @Inject('badgeService') private badgeService: () => BadgeService;

  public badges: IBadge[] = [];

  @Inject('teamService') private teamService: () => TeamService;

  public teams: ITeam[] = [];
  public isSaving = false;
  public currentLanguage = '';

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.dimensionId) {
        vm.retrieveDimension(to.params.dimensionId);
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
    if (this.dimension.id) {
      this.dimensionService()
        .update(this.dimension)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('teamDojoApp.dimension.updated', { param: param.id });
          return this.$root.$bvToast.toast(message.toString(), {
            toaster: 'b-toaster-top-center',
            title: 'Info',
            variant: 'info',
            solid: true,
            autoHideDelay: 5000,
          });
        });
    } else {
      this.dimensionService()
        .create(this.dimension)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('teamDojoApp.dimension.created', { param: param.id });
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
      this.dimension[field] = dayjs(event.target.value, DATE_TIME_LONG_FORMAT);
    } else {
      this.dimension[field] = null;
    }
  }

  public updateZonedDateTimeField(field, event) {
    if (event.target.value) {
      this.dimension[field] = dayjs(event.target.value, DATE_TIME_LONG_FORMAT);
    } else {
      this.dimension[field] = null;
    }
  }

  public retrieveDimension(dimensionId): void {
    this.dimensionService()
      .find(dimensionId)
      .then(res => {
        res.createdAt = new Date(res.createdAt);
        res.updatedAt = new Date(res.updatedAt);
        this.dimension = res;
      });
  }

  public previousState(): void {
    this.$router.go(-1);
  }

  public initRelationships(): void {
    this.levelService()
      .retrieve()
      .then(res => {
        this.levels = res.data;
      });
    this.badgeService()
      .retrieve()
      .then(res => {
        this.badges = res.data;
      });
    this.teamService()
      .retrieve()
      .then(res => {
        this.teams = res.data;
      });
  }
}
