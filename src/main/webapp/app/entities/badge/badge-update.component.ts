import { Component, Vue, Inject } from 'vue-property-decorator';

import { required, minLength, maxLength, numeric, minValue, decimal, maxValue } from 'vuelidate/lib/validators';
import dayjs from 'dayjs';
import { DATE_TIME_LONG_FORMAT } from '@/shared/date/filters';

import BadgeSkillService from '@/entities/badge-skill/badge-skill.service';
import { IBadgeSkill } from '@/shared/model/badge-skill.model';

import ImageService from '@/entities/image/image.service';
import { IImage } from '@/shared/model/image.model';

import DimensionService from '@/entities/dimension/dimension.service';
import { IDimension } from '@/shared/model/dimension.model';

import { IBadge, Badge } from '@/shared/model/badge.model';
import BadgeService from './badge.service';

const validations: any = {
  badge: {
    title: {
      required,
      minLength: minLength(2),
      maxLength: maxLength(20),
    },
    description: {},
    availableUntil: {},
    availableAmount: {
      numeric,
      min: minValue(1),
    },
    requiredScore: {
      required,
      decimal,
      min: minValue(0),
      max: maxValue(1),
    },
    instantMultiplier: {
      required,
      decimal,
      min: minValue(0),
    },
    completionBonus: {
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
export default class BadgeUpdate extends Vue {
  @Inject('badgeService') private badgeService: () => BadgeService;
  public badge: IBadge = new Badge();

  @Inject('badgeSkillService') private badgeSkillService: () => BadgeSkillService;

  public badgeSkills: IBadgeSkill[] = [];

  @Inject('imageService') private imageService: () => ImageService;

  public images: IImage[] = [];

  @Inject('dimensionService') private dimensionService: () => DimensionService;

  public dimensions: IDimension[] = [];
  public isSaving = false;
  public currentLanguage = '';

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.badgeId) {
        vm.retrieveBadge(to.params.badgeId);
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
    this.badge.dimensions = [];
  }

  public save(): void {
    this.isSaving = true;
    if (this.badge.id) {
      this.badgeService()
        .update(this.badge)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('teamDojoApp.badge.updated', { param: param.id });
          return this.$root.$bvToast.toast(message.toString(), {
            toaster: 'b-toaster-top-center',
            title: 'Info',
            variant: 'info',
            solid: true,
            autoHideDelay: 5000,
          });
        });
    } else {
      this.badgeService()
        .create(this.badge)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('teamDojoApp.badge.created', { param: param.id });
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
      this.badge[field] = dayjs(event.target.value, DATE_TIME_LONG_FORMAT);
    } else {
      this.badge[field] = null;
    }
  }

  public updateZonedDateTimeField(field, event) {
    if (event.target.value) {
      this.badge[field] = dayjs(event.target.value, DATE_TIME_LONG_FORMAT);
    } else {
      this.badge[field] = null;
    }
  }

  public retrieveBadge(badgeId): void {
    this.badgeService()
      .find(badgeId)
      .then(res => {
        res.availableUntil = new Date(res.availableUntil);
        res.createdAt = new Date(res.createdAt);
        res.updatedAt = new Date(res.updatedAt);
        this.badge = res;
      });
  }

  public previousState(): void {
    this.$router.go(-1);
  }

  public initRelationships(): void {
    this.badgeSkillService()
      .retrieve()
      .then(res => {
        this.badgeSkills = res.data;
      });
    this.imageService()
      .retrieve()
      .then(res => {
        this.images = res.data;
      });
    this.dimensionService()
      .retrieve()
      .then(res => {
        this.dimensions = res.data;
      });
  }

  public getSelected(selectedVals, option): any {
    if (selectedVals) {
      for (let i = 0; i < selectedVals.length; i++) {
        if (option.id === selectedVals[i].id) {
          return selectedVals[i];
        }
      }
    }
    return option;
  }
}
