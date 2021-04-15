import { Component, Vue, Inject } from 'vue-property-decorator';

import { ITeamSkill } from '@/shared/model/team-skill.model';
import TeamSkillService from './team-skill.service';

@Component
export default class TeamSkillDetails extends Vue {
  @Inject('teamSkillService') private teamSkillService: () => TeamSkillService;
  public teamSkill: ITeamSkill = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.teamSkillId) {
        vm.retrieveTeamSkill(to.params.teamSkillId);
      }
    });
  }

  public retrieveTeamSkill(teamSkillId) {
    this.teamSkillService()
      .find(teamSkillId)
      .then(res => {
        this.teamSkill = res;
      });
  }

  public previousState() {
    this.$router.go(-1);
  }
}
