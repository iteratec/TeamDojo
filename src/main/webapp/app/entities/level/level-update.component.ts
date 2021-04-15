import { Component, Vue, Inject } from 'vue-property-decorator';

import { required, minLength, maxLength, decimal, minValue, maxValue, numeric } from 'vuelidate/lib/validators';
import dayjs from 'dayjs';
import { DATE_TIME_LONG_FORMAT } from '@/shared/date/filters';

import LevelSkillService from '@/entities/level-skill/level-skill.service';
import { ILevelSkill } from '@/shared/model/level-skill.model';

import ImageService from '@/entities/image/image.service';
import { IImage } from '@/shared/model/image.model';

import DimensionService from '@/entities/dimension/dimension.service';
import { IDimension } from '@/shared/model/dimension.model';

import { ILevel, Level } from '@/shared/model/level.model';
import LevelService from './level.service';

const validations: any = {
  level: {
    title: {
      required,
      minLength: minLength(3),
      maxLength: maxLength(50),
    },
    description: {},
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
    dimension: {
      required,
    },
  },
};

@Component({
  validations,
})
export default class LevelUpdate extends Vue {
  @Inject('levelService') private levelService: () => LevelService;
  public level: ILevel = new Level();

  public levels: ILevel[] = [];

  @Inject('levelSkillService') private levelSkillService: () => LevelSkillService;

  public levelSkills: ILevelSkill[] = [];

  @Inject('imageService') private imageService: () => ImageService;

  public images: IImage[] = [];

  @Inject('dimensionService') private dimensionService: () => DimensionService;

  public dimensions: IDimension[] = [];
  public isSaving = false;
  public currentLanguage = '';

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.levelId) {
        vm.retrieveLevel(to.params.levelId);
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
    if (this.level.id) {
      this.levelService()
        .update(this.level)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('teamDojoApp.level.updated', { param: param.id });
          return this.$root.$bvToast.toast(message.toString(), {
            toaster: 'b-toaster-top-center',
            title: 'Info',
            variant: 'info',
            solid: true,
            autoHideDelay: 5000,
          });
        });
    } else {
      this.levelService()
        .create(this.level)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('teamDojoApp.level.created', { param: param.id });
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
      this.level[field] = dayjs(event.target.value, DATE_TIME_LONG_FORMAT);
    } else {
      this.level[field] = null;
    }
  }

  public updateZonedDateTimeField(field, event) {
    if (event.target.value) {
      this.level[field] = dayjs(event.target.value, DATE_TIME_LONG_FORMAT);
    } else {
      this.level[field] = null;
    }
  }

  public retrieveLevel(levelId): void {
    this.levelService()
      .find(levelId)
      .then(res => {
        res.createdAt = new Date(res.createdAt);
        res.updatedAt = new Date(res.updatedAt);
        this.level = res;
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
    this.levelSkillService()
      .retrieve()
      .then(res => {
        this.levelSkills = res.data;
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
}
