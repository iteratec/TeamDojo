import { Component, Vue, Inject } from 'vue-property-decorator';

import { required, minLength, maxLength, numeric, minValue, decimal, maxValue } from 'vuelidate/lib/validators';
import dayjs from 'dayjs';
import { DATE_TIME_LONG_FORMAT } from '@/shared/date/filters';

import BadgeSkillService from '@/entities/badge-skill/badge-skill.service';
import { IBadgeSkill } from '@/shared/model/badge-skill.model';

import LevelSkillService from '@/entities/level-skill/level-skill.service';
import { ILevelSkill } from '@/shared/model/level-skill.model';

import TeamSkillService from '@/entities/team-skill/team-skill.service';
import { ITeamSkill } from '@/shared/model/team-skill.model';

import TrainingService from '@/entities/training/training.service';
import { ITraining } from '@/shared/model/training.model';

import { ISkill, Skill } from '@/shared/model/skill.model';
import SkillService from './skill.service';

const validations: any = {
  skill: {
    title: {
      required,
      minLength: minLength(5),
      maxLength: maxLength(80),
    },
    description: {
      maxLength: maxLength(4096),
    },
    implementation: {
      maxLength: maxLength(4096),
    },
    validation: {
      maxLength: maxLength(4096),
    },
    expiryPeriod: {
      numeric,
      min: minValue(1),
    },
    contact: {
      maxLength: maxLength(255),
    },
    score: {
      required,
      numeric,
      min: minValue(0),
    },
    rateScore: {
      decimal,
      min: minValue(0),
      max: maxValue(5),
    },
    rateCount: {
      required,
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
export default class SkillUpdate extends Vue {
  @Inject('skillService') private skillService: () => SkillService;
  public skill: ISkill = new Skill();

  @Inject('badgeSkillService') private badgeSkillService: () => BadgeSkillService;

  public badgeSkills: IBadgeSkill[] = [];

  @Inject('levelSkillService') private levelSkillService: () => LevelSkillService;

  public levelSkills: ILevelSkill[] = [];

  @Inject('teamSkillService') private teamSkillService: () => TeamSkillService;

  public teamSkills: ITeamSkill[] = [];

  @Inject('trainingService') private trainingService: () => TrainingService;

  public trainings: ITraining[] = [];
  public isSaving = false;
  public currentLanguage = '';

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.skillId) {
        vm.retrieveSkill(to.params.skillId);
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
    if (this.skill.id) {
      this.skillService()
        .update(this.skill)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('teamDojoApp.skill.updated', { param: param.id });
          return this.$root.$bvToast.toast(message.toString(), {
            toaster: 'b-toaster-top-center',
            title: 'Info',
            variant: 'info',
            solid: true,
            autoHideDelay: 5000,
          });
        });
    } else {
      this.skillService()
        .create(this.skill)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('teamDojoApp.skill.created', { param: param.id });
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
      this.skill[field] = dayjs(event.target.value, DATE_TIME_LONG_FORMAT);
    } else {
      this.skill[field] = null;
    }
  }

  public updateZonedDateTimeField(field, event) {
    if (event.target.value) {
      this.skill[field] = dayjs(event.target.value, DATE_TIME_LONG_FORMAT);
    } else {
      this.skill[field] = null;
    }
  }

  public retrieveSkill(skillId): void {
    this.skillService()
      .find(skillId)
      .then(res => {
        res.createdAt = new Date(res.createdAt);
        res.updatedAt = new Date(res.updatedAt);
        this.skill = res;
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
    this.levelSkillService()
      .retrieve()
      .then(res => {
        this.levelSkills = res.data;
      });
    this.teamSkillService()
      .retrieve()
      .then(res => {
        this.teamSkills = res.data;
      });
    this.trainingService()
      .retrieve()
      .then(res => {
        this.trainings = res.data;
      });
  }
}
