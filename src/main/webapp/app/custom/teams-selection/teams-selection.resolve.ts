/*
 SPDX-FileCopyrightText: the TeamDojo authors
 SPDX-License-Identifier: Apache-2.0
 */
import { ActivatedRouteSnapshot, Resolve, RouterStateSnapshot } from '@angular/router';
import { Injectable } from '@angular/core';

import { Observable } from 'rxjs';

import { TeamsSelectionService } from 'app/custom/teams-selection/teams-selection.service';
import { ITeam } from 'app/entities/team/team.model';

@Injectable()
export class TeamsSelectionResolve implements Resolve<any> {
  constructor(private teamsSelectionService: TeamsSelectionService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<ITeam> {
    return this.teamsSelectionService.query();
  }
}
