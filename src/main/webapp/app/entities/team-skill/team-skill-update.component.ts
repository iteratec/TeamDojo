import { Component, Vue, Inject } from 'vue-property-decorator';

import { numeric, required } from 'vuelidate/lib/validators';
import dayjs from 'dayjs';
import { DATE_TIME_LONG_FORMAT } from '@/shared/date/filters';

import SkillService from '@/entities/skill/skill.service';
import { ISkill } from '@/shared/model/skill.model';

import TeamService from '@/entities/team/team.service';
import { ITeam } from '@/shared/model/team.model';

import { ITeamSkill, TeamSkill } from '@/shared/model/team-skill.model';
import TeamSkillService from './team-skill.service';

const validations: any = {
  teamSkill: {
    completedAt: {},
    verifiedAt: {},
    irrelevant: {},
    note: {},
    vote: {
      required,
      numeric,
    },
    voters: {},
    createdAt: {
      required,
    },
    updatedAt: {
      required,
    },
    skill: {
      required,
    },
    team: {
      required,
    },
  },
};

@Component({
  validations,
})
export default class TeamSkillUpdate extends Vue {
  @Inject('teamSkillService') private teamSkillService: () => TeamSkillService;
  public teamSkill: ITeamSkill = new TeamSkill();

  @Inject('skillService') private skillService: () => SkillService;

  public skills: ISkill[] = [];

  @Inject('teamService') private teamService: () => TeamService;

  public teams: ITeam[] = [];
  public isSaving = false;
  public currentLanguage = '';

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.teamSkillId) {
        vm.retrieveTeamSkill(to.params.teamSkillId);
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
    if (this.teamSkill.id) {
      this.teamSkillService()
        .update(this.teamSkill)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('teamDojoApp.teamSkill.updated', { param: param.id });
          return this.$root.$bvToast.toast(message.toString(), {
            toaster: 'b-toaster-top-center',
            title: 'Info',
            variant: 'info',
            solid: true,
            autoHideDelay: 5000,
          });
        });
    } else {
      this.teamSkillService()
        .create(this.teamSkill)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('teamDojoApp.teamSkill.created', { param: param.id });
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
      this.teamSkill[field] = dayjs(event.target.value, DATE_TIME_LONG_FORMAT);
    } else {
      this.teamSkill[field] = null;
    }
  }

  public updateZonedDateTimeField(field, event) {
    if (event.target.value) {
      this.teamSkill[field] = dayjs(event.target.value, DATE_TIME_LONG_FORMAT);
    } else {
      this.teamSkill[field] = null;
    }
  }

  public retrieveTeamSkill(teamSkillId): void {
    this.teamSkillService()
      .find(teamSkillId)
      .then(res => {
        res.completedAt = new Date(res.completedAt);
        res.verifiedAt = new Date(res.verifiedAt);
        res.createdAt = new Date(res.createdAt);
        res.updatedAt = new Date(res.updatedAt);
        this.teamSkill = res;
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
    this.teamService()
      .retrieve()
      .then(res => {
        this.teams = res.data;
      });
  }
}
