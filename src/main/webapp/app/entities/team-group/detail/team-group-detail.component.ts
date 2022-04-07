import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ITeamGroup } from '../team-group.model';

@Component({
  selector: 'jhi-team-group-detail',
  templateUrl: './team-group-detail.component.html',
})
export class TeamGroupDetailComponent implements OnInit {
  teamGroup: ITeamGroup | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ teamGroup }) => {
      this.teamGroup = teamGroup;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
