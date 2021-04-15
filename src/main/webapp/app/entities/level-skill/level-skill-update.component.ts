import { Component, Vue, Inject } from 'vue-property-decorator';

import { required } from 'vuelidate/lib/validators';

import SkillService from '@/entities/skill/skill.service';
import { ISkill } from '@/shared/model/skill.model';

import LevelService from '@/entities/level/level.service';
import { ILevel } from '@/shared/model/level.model';

import { ILevelSkill, LevelSkill } from '@/shared/model/level-skill.model';
import LevelSkillService from './level-skill.service';

const validations: any = {
  levelSkill: {
    skill: {
      required,
    },
    level: {
      required,
    },
  },
};

@Component({
  validations,
})
export default class LevelSkillUpdate extends Vue {
  @Inject('levelSkillService') private levelSkillService: () => LevelSkillService;
  public levelSkill: ILevelSkill = new LevelSkill();

  @Inject('skillService') private skillService: () => SkillService;

  public skills: ISkill[] = [];

  @Inject('levelService') private levelService: () => LevelService;

  public levels: ILevel[] = [];
  public isSaving = false;
  public currentLanguage = '';

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.levelSkillId) {
        vm.retrieveLevelSkill(to.params.levelSkillId);
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
    if (this.levelSkill.id) {
      this.levelSkillService()
        .update(this.levelSkill)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('teamDojoApp.levelSkill.updated', { param: param.id });
          return this.$root.$bvToast.toast(message.toString(), {
            toaster: 'b-toaster-top-center',
            title: 'Info',
            variant: 'info',
            solid: true,
            autoHideDelay: 5000,
          });
        });
    } else {
      this.levelSkillService()
        .create(this.levelSkill)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('teamDojoApp.levelSkill.created', { param: param.id });
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

  public retrieveLevelSkill(levelSkillId): void {
    this.levelSkillService()
      .find(levelSkillId)
      .then(res => {
        this.levelSkill = res;
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
    this.levelService()
      .retrieve()
      .then(res => {
        this.levels = res.data;
      });
  }
}
