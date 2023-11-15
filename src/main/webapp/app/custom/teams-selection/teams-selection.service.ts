/*
 SPDX-FileCopyrightText: the TeamDojo authors
 SPDX-License-Identifier: Apache-2.0
 */
import { Injectable, Output, EventEmitter } from '@angular/core';
import { LocalStorageService } from 'ngx-webstorage';
import { Observable, EMPTY, of } from 'rxjs';
import { catchError, flatMap, map, tap } from 'rxjs/operators';
import { ITeam } from 'app/entities/team/team.model';
import { TeamService } from 'app/entities/team/service/team.service';
import { TeamSkillService } from 'app/entities/team-skill/service/team-skill.service';
import { TEAM_SKILLS_PER_PAGE } from '../../config/pagination.constants';
import { Team } from '../custom.types';

const TEAM_STORAGE_KEY = 'selectedTeamId';

@Injectable()
export class TeamsSelectionService {
  @Output() teamChanged = new EventEmitter<string>();
  private _selectedTeam?: Team;

  constructor(private teamsService: TeamService, private teamSkillService: TeamSkillService, private storage: LocalStorageService) {
    this.query();
  }

  query(): Observable<Team> {
    const teamIdStr = this.storage.retrieve(TEAM_STORAGE_KEY);
    if (teamIdStr !== null && !isNaN(Number(teamIdStr))) {
      return this.teamsService.find(teamIdStr).pipe(
        tap(result => {
          this._selectedTeam = result.body ?? undefined;
        }),
        catchError(() => {
          this.storage.clear(TEAM_STORAGE_KEY);
          this._selectedTeam = undefined;
          return EMPTY;
        }),
        flatMap((result: any) =>
          this.teamSkillService.query({ page: 0, size: TEAM_SKILLS_PER_PAGE, 'teamId.equals': result.body.id }).pipe(
            tap((teamSkillRes: any) => {
              if (this._selectedTeam != null) {
                this._selectedTeam.skills = teamSkillRes.body ?? [];
              }
            }),
            map(() => result.body as ITeam)
          )
        )
      );
    }
    return of(this._selectedTeam ? this._selectedTeam : { id: 0 });
  }

  get selectedTeam(): Team | undefined {
    return this._selectedTeam;
  }

  set selectedTeam(team: Team | undefined) {
    this._selectedTeam = team;

    if (this._selectedTeam?.id !== undefined) {
      this.storage.store(TEAM_STORAGE_KEY, this._selectedTeam.id.toString());
    } else {
      this.storage.clear(TEAM_STORAGE_KEY);
    }

    this.teamChanged.emit('team changed');
  }
}
