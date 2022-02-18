import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IOrganisation } from '../organisation.model';
import { OrganisationService } from '../service/organisation.service';

@Component({
  templateUrl: './organisation-delete-dialog.component.html',
})
export class OrganisationDeleteDialogComponent {
  organisation?: IOrganisation;

  constructor(protected organisationService: OrganisationService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.organisationService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
