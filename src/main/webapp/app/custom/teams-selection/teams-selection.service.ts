import { Injectable } from '@angular/core';
import { LocalStorageService } from 'ngx-webstorage';
import { Observable, empty, of } from 'rxjs';
import { catchError, flatMap, map, tap } from 'rxjs/operators';
import { ITeam } from 'app/entities/team/team.model';
import { TeamsService } from 'app/custom/teams/teams.service';
import { TeamSkillService } from 'app/entities/team-skill/service/team-skill.service';

const TEAM_STORAGE_KEY = 'selectedTeamId';

@Injectable()
export class TeamsSelectionService {
  private _selectedTeam: ITeam = null;

  constructor(private teamsService: TeamsService, private teamSkillService: TeamSkillService, private storage: LocalStorageService) {
    this.query();
  }

  query(): Observable<ITeam> {
    const teamIdStr = this.storage.retrieve(TEAM_STORAGE_KEY);
    if (teamIdStr !== null && !isNaN(Number(teamIdStr))) {
      return this.teamsService.find(teamIdStr).pipe(
        tap(result => {
          this._selectedTeam = result.body || null;
        }),
        catchError(err => {
          this._selectedTeam = null;
          return empty();
        }),
        flatMap((result: any) => {
          return this.teamSkillService.query({ 'teamId.equals': result.body.id }).pipe(
            tap((teamSkillRes: any) => {
              this._selectedTeam.skills = teamSkillRes.body || [];
            }),
            map(() => result.body)
          );
        })
      );
    }
    return of(this._selectedTeam);
  }

  get selectedTeam() {
    return this._selectedTeam;
  }

  set selectedTeam(team: ITeam) {
    this._selectedTeam = team;
    if (team !== null) {
      this.storage.store(TEAM_STORAGE_KEY, this._selectedTeam.id.toString());
    } else {
      this.storage.clear(TEAM_STORAGE_KEY);
    }
  }
}
