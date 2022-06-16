import { Component, OnInit } from '@angular/core';

import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { TeamsSelectionComponent } from 'app/custom/teams-selection/teams-selection.component';
import { TeamsSelectionService } from 'app/custom/teams-selection/teams-selection.service';
import { ITeam } from 'app/entities/team/team.model';
import { TEAMS_PER_PAGE } from '../../../../config/pagination.constants';

@Component({
  selector: 'jhi-navbar-extension',
  templateUrl: './navbar-extension.component.html',
  styleUrls: ['./navbar-extension.component.scss'],
})
export class NavbarExtensionComponent implements OnInit {
  isTeamSelectionOpen = false;

  constructor(private modalService: NgbModal, private teamsSelectionService: TeamsSelectionService) {}

  ngOnInit(): void {
    this.teamsSelectionService.query().subscribe();
  }

  selectTeam(): NgbModalRef | undefined {
    if (this.isTeamSelectionOpen) {
      return;
    }
    this.isTeamSelectionOpen = true;
    const modalRef = this.modalService.open(TeamsSelectionComponent, { size: 'lg' });
    modalRef.result.then(
      result => {
        this.isTeamSelectionOpen = false;
      },
      reason => {
        this.isTeamSelectionOpen = false;
      }
    );
    return modalRef;
  }

  get selectedTeam(): ITeam | undefined {
    return this.teamsSelectionService.selectedTeam;
  }
}
