import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import SharedModule from 'app/shared/shared.module';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';
import { ITeamGroup } from '../team-group.model';
import { TeamGroupService } from '../service/team-group.service';

@Component({
  standalone: true,
  templateUrl: './team-group-delete-dialog.component.html',
  imports: [SharedModule, FormsModule],
})
export class TeamGroupDeleteDialogComponent {
  teamGroup?: ITeamGroup;

  constructor(
    protected teamGroupService: TeamGroupService,
    protected activeModal: NgbActiveModal,
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.teamGroupService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
