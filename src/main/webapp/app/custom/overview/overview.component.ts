import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { ITeam } from 'app/entities/team/team.model';
import { ILevel } from 'app/entities/level/level.model';
import { IBadge } from 'app/entities/badge/badge.model';
import { ISkill } from 'app/entities/skill/skill.model';

@Component({
  selector: 'jhi-overview',
  templateUrl: './overview.component.html',
  styleUrls: ['./overview.scss'],
})
export class OverviewComponent implements OnInit {
  teams!: ITeam[];
  levels!: ILevel[];
  badges!: IBadge[];
  skills!: ISkill[];
  selectedTeam!: ITeam;

  constructor(private route: ActivatedRoute) {}

  ngOnInit(): void {
    this.route.data.subscribe(({ dojoModel: { teams, levels, badges }, skills, selectedTeam }) => {
      this.teams = (teams?.body ? teams.body : teams) || [];
      this.levels = (levels?.body ? levels.body : levels) || [];
      this.badges = (badges?.body ? badges.body : badges) || [];
      this.skills = (skills?.body ? skills.body : skills) || [];
      this.selectedTeam = (selectedTeam?.body ? selectedTeam.body : selectedTeam) || {};
    });
  }
}
