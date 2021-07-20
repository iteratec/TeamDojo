import { Component, OnInit, Output, EventEmitter } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { TeamSkillService } from 'app/entities/team-skill/service/team-skill.service';

import { IBadge } from 'app/entities/badge/badge.model';
import { ITeam } from 'app/entities/team/team.model';
import { ITeamSkill } from 'app/entities/team-skill/team-skill.model';
import { ISkill } from 'app/entities/skill/skill.model';

import { JhiDataUtils } from 'ng-jhipster';

@Component({
  selector: 'jhi-teams',
  templateUrl: './teams.component.html',
  styleUrls: ['./teams.component.scss'],
})
export class TeamsComponent implements OnInit {
  @Output() changeTeam = new EventEmitter<any>();

  team!: ITeam; // TODO discuss whether this should be ! or | undefined
  teamSkills!: ITeamSkill[];
  badges!: IBadge[];
  skills!: ISkill[];

  constructor(private dataUtils: JhiDataUtils, private route: ActivatedRoute, private teamSkillService: TeamSkillService) {}

  ngOnInit(): void {
    this.route.data.subscribe(({ dojoModel: { teams, levels, levelSkills, badges, badgeSkills }, team, skills }) => {
      const teamFromRoute = team?.body ? team.body : team;
      this.team = (teams || []).find((t: ITeam) => t.id === teamFromRoute.id) || teamFromRoute;
      this.teamSkills = team?.skills ? team.skills : [];
      this.badges = (badges?.body ? badges.body : badges) || [];
      this.skills = (skills?.body ? skills.body : skills) || []; // skills?.body <=> skills && skills.body
    });
    this.changeTeam.emit(this.team);
  }
}
