import { ActivatedRouteSnapshot, Resolve, RouterStateSnapshot } from '@angular/router';
import { Injectable } from '@angular/core';
import { TeamsSelectionService } from 'app/custom/teams-selection/teams-selection.service';

@Injectable()
export class TeamsSelectionResolve implements Resolve<any> {
  constructor(private teamsSelectionService: TeamsSelectionService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
    return this.teamsSelectionService.query();
  }
}
