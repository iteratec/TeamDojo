/*
 SPDX-FileCopyrightText: the TeamDojo authors
 SPDX-License-Identifier: Apache-2.0
 */
import { Component, OnInit } from '@angular/core';

import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { TeamsSelectionComponent } from 'app/custom/teams-selection/teams-selection.component';
import { TeamsSelectionService } from 'app/custom/teams-selection/teams-selection.service';
import { TeamImageComponent } from 'app/custom/shared/team-image/team-image.component';
import { Team } from 'app/custom/custom.types';
import SharedModule from 'app/shared/shared.module';
import { RouterModule } from '@angular/router';

@Component({
  selector: 'jhi-navbar-extension',
  templateUrl: './navbar-extension.component.html',
  // styleUrls: ['./navbar-extension.component.scss'],
  standalone: true,
  imports: [TeamImageComponent, SharedModule, RouterModule]
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

  get selectedTeam(): Team | undefined {
    return this.teamsSelectionService.selectedTeam;
  }
}
