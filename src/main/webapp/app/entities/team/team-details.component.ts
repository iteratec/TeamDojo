import { Component, Vue, Inject } from 'vue-property-decorator';

import { ITeam } from '@/shared/model/team.model';
import TeamService from './team.service';

@Component
export default class TeamDetails extends Vue {
  @Inject('teamService') private teamService: () => TeamService;
  public team: ITeam = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.teamId) {
        vm.retrieveTeam(to.params.teamId);
      }
    });
  }

  public retrieveTeam(teamId) {
    this.teamService()
      .find(teamId)
      .then(res => {
        this.team = res;
      });
  }

  public previousState() {
    this.$router.go(-1);
  }
}
