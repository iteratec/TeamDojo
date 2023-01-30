import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { ITeamGroup } from '../team-group.model';
import { TeamGroupService } from '../service/team-group.service';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';

@Component({
  templateUrl: './team-group-delete-dialog.component.html',
})
export class TeamGroupDeleteDialogComponent {
  teamGroup?: ITeamGroup;

  constructor(protected teamGroupService: TeamGroupService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.teamGroupService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
