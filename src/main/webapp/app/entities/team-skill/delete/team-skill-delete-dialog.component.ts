import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { ITeamSkill } from '../team-skill.model';
import { TeamSkillService } from '../service/team-skill.service';

@Component({
  templateUrl: './team-skill-delete-dialog.component.html',
})
export class TeamSkillDeleteDialogComponent {
  teamSkill?: ITeamSkill;

  constructor(protected teamSkillService: TeamSkillService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.teamSkillService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
