import { Component, OnInit, Output, EventEmitter } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { TeamSkillService } from 'app/entities/team-skill/service/team-skill.service';

import { IBadge } from 'app/entities/badge/badge.model';
import { ITeam, Team } from 'app/entities/team/team.model';
import { ITeamSkill } from 'app/entities/team-skill/team-skill.model';
import { ISkill } from 'app/entities/skill/skill.model';
import { DataUtils } from 'app/core/util/data-util.service';
import { TEAM_SKILLS_PER_PAGE } from '../../config/pagination.constants';

@Component({
  selector: 'jhi-teams',
  templateUrl: './teams.component.html',
  styleUrls: ['./teams.component.scss'],
})
export class TeamsComponent implements OnInit {
  @Output() changeTeam = new EventEmitter<any>();

  team: ITeam = new Team();
  teamSkills: ITeamSkill[] = [];
  badges: IBadge[] = [];
  skills: ISkill[] = [];

  constructor(private dataUtils: DataUtils, private route: ActivatedRoute, private teamSkillService: TeamSkillService) {}

  ngOnInit(): void {
    this.route.data.subscribe(({ dojoModel: { teams, levels, levelSkills, badges, badgeSkills }, team, skills }) => {
      const teamFromRoute = team?.body ? team.body : team;
      this.team = (teams || []).find((t: ITeam) => t.id === teamFromRoute.id) || teamFromRoute;
      this.teamSkills = team?.skills ? team.skills : [];
      this.badges = (badges?.body ? badges.body : badges) || [];
      this.skills = (skills?.body ? skills.body : skills) || [];
    });
    this.changeTeam.emit(this.team);
  }

  loadTeamSkills(): void {
    this.teamSkillService.query({ page: 0, size: TEAM_SKILLS_PER_PAGE, 'teamId.equals': this.team.id }).subscribe(teamSkillResponse => {
      if (teamSkillResponse.body) {
        this.team.skills = teamSkillResponse.body;
        this.teamSkills = teamSkillResponse.body;
      }
    });
  }

  /*
  byteSize(field): string {
    return this.dataUtils.byteSize(field);
  }

  openFile(contentType, field) {
    return this.dataUtils.openFile(contentType, field);
  }
  */

  previousState(): void {
    window.history.back();
  }
}
