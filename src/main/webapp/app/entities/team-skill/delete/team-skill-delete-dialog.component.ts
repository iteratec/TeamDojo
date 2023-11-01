import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import SharedModule from 'app/shared/shared.module';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';
import { ITeamSkill } from '../team-skill.model';
import { TeamSkillService } from '../service/team-skill.service';

@Component({
  standalone: true,
  templateUrl: './team-skill-delete-dialog.component.html',
  imports: [SharedModule, FormsModule],
})
export class TeamSkillDeleteDialogComponent {
  teamSkill?: ITeamSkill;

  constructor(
    protected teamSkillService: TeamSkillService,
    protected activeModal: NgbActiveModal,
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.teamSkillService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
