import { Component, Vue, Inject } from 'vue-property-decorator';

import { required, maxLength } from 'vuelidate/lib/validators';
import dayjs from 'dayjs';
import { DATE_TIME_LONG_FORMAT } from '@/shared/date/filters';

import SkillService from '@/entities/skill/skill.service';
import { ISkill } from '@/shared/model/skill.model';

import { ITraining, Training } from '@/shared/model/training.model';
import TrainingService from './training.service';

const validations: any = {
  training: {
    title: {
      required,
      maxLength: maxLength(80),
    },
    description: {
      maxLength: maxLength(100),
    },
    contact: {
      maxLength: maxLength(255),
    },
    link: {
      maxLength: maxLength(255),
    },
    validUntil: {},
    isOfficial: {
      required,
    },
    suggestedBy: {},
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
export default class TrainingUpdate extends Vue {
  @Inject('trainingService') private trainingService: () => TrainingService;
  public training: ITraining = new Training();

  @Inject('skillService') private skillService: () => SkillService;

  public skills: ISkill[] = [];
  public isSaving = false;
  public currentLanguage = '';

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.trainingId) {
        vm.retrieveTraining(to.params.trainingId);
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
    this.training.skills = [];
  }

  public save(): void {
    this.isSaving = true;
    if (this.training.id) {
      this.trainingService()
        .update(this.training)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('teamDojoApp.training.updated', { param: param.id });
          return this.$root.$bvToast.toast(message.toString(), {
            toaster: 'b-toaster-top-center',
            title: 'Info',
            variant: 'info',
            solid: true,
            autoHideDelay: 5000,
          });
        });
    } else {
      this.trainingService()
        .create(this.training)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('teamDojoApp.training.created', { param: param.id });
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
      this.training[field] = dayjs(event.target.value, DATE_TIME_LONG_FORMAT);
    } else {
      this.training[field] = null;
    }
  }

  public updateZonedDateTimeField(field, event) {
    if (event.target.value) {
      this.training[field] = dayjs(event.target.value, DATE_TIME_LONG_FORMAT);
    } else {
      this.training[field] = null;
    }
  }

  public retrieveTraining(trainingId): void {
    this.trainingService()
      .find(trainingId)
      .then(res => {
        res.validUntil = new Date(res.validUntil);
        res.createdAt = new Date(res.createdAt);
        res.updatedAt = new Date(res.updatedAt);
        this.training = res;
      });
  }

  public previousState(): void {
    this.$router.go(-1);
  }

  public initRelationships(): void {
    this.skillService()
      .retrieve()
      .then(res => {
        this.skills = res.data;
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
