import { Component, Vue, Inject } from 'vue-property-decorator';

import { required, minLength, maxLength } from 'vuelidate/lib/validators';
import dayjs from 'dayjs';
import { DATE_TIME_LONG_FORMAT } from '@/shared/date/filters';

import TeamSkillService from '@/entities/team-skill/team-skill.service';
import { ITeamSkill } from '@/shared/model/team-skill.model';

import ImageService from '@/entities/image/image.service';
import { IImage } from '@/shared/model/image.model';

import DimensionService from '@/entities/dimension/dimension.service';
import { IDimension } from '@/shared/model/dimension.model';

import { ITeam, Team } from '@/shared/model/team.model';
import TeamService from './team.service';

const validations: any = {
  team: {
    title: {
      required,
      minLength: minLength(2),
      maxLength: maxLength(50),
    },
    shortTitle: {
      required,
      minLength: minLength(2),
      maxLength: maxLength(20),
    },
    slogan: {
      maxLength: maxLength(255),
    },
    contact: {
      maxLength: maxLength(255),
    },
    validUntil: {},
    pureTrainingTeam: {
      required,
    },
    official: {
      required,
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
export default class TeamUpdate extends Vue {
  @Inject('teamService') private teamService: () => TeamService;
  public team: ITeam = new Team();

  @Inject('teamSkillService') private teamSkillService: () => TeamSkillService;

  public teamSkills: ITeamSkill[] = [];

  @Inject('imageService') private imageService: () => ImageService;

  public images: IImage[] = [];

  @Inject('dimensionService') private dimensionService: () => DimensionService;

  public dimensions: IDimension[] = [];
  public isSaving = false;
  public currentLanguage = '';

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.teamId) {
        vm.retrieveTeam(to.params.teamId);
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
    this.team.participations = [];
  }

  public save(): void {
    this.isSaving = true;
    if (this.team.id) {
      this.teamService()
        .update(this.team)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('teamDojoApp.team.updated', { param: param.id });
          return this.$root.$bvToast.toast(message.toString(), {
            toaster: 'b-toaster-top-center',
            title: 'Info',
            variant: 'info',
            solid: true,
            autoHideDelay: 5000,
          });
        });
    } else {
      this.teamService()
        .create(this.team)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('teamDojoApp.team.created', { param: param.id });
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
      this.team[field] = dayjs(event.target.value, DATE_TIME_LONG_FORMAT);
    } else {
      this.team[field] = null;
    }
  }

  public updateZonedDateTimeField(field, event) {
    if (event.target.value) {
      this.team[field] = dayjs(event.target.value, DATE_TIME_LONG_FORMAT);
    } else {
      this.team[field] = null;
    }
  }

  public retrieveTeam(teamId): void {
    this.teamService()
      .find(teamId)
      .then(res => {
        res.validUntil = new Date(res.validUntil);
        res.createdAt = new Date(res.createdAt);
        res.updatedAt = new Date(res.updatedAt);
        this.team = res;
      });
  }

  public previousState(): void {
    this.$router.go(-1);
  }

  public initRelationships(): void {
    this.teamSkillService()
      .retrieve()
      .then(res => {
        this.teamSkills = res.data;
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
