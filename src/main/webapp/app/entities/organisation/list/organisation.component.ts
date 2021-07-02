import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IOrganisation } from '../organisation.model';
import { OrganisationService } from '../service/organisation.service';
import { OrganisationDeleteDialogComponent } from '../delete/organisation-delete-dialog.component';

@Component({
  selector: 'jhi-organisation',
  templateUrl: './organisation.component.html',
})
export class OrganisationComponent implements OnInit {
  organisations?: IOrganisation[];
  isLoading = false;

  constructor(protected organisationService: OrganisationService, protected modalService: NgbModal) {}

  loadAll(): void {
    this.isLoading = true;

    this.organisationService.query().subscribe(
      (res: HttpResponse<IOrganisation[]>) => {
        this.isLoading = false;
        this.organisations = res.body ?? [];
      },
      () => {
        this.isLoading = false;
      }
    );
  }

  ngOnInit(): void {
    this.loadAll();
  }

  trackId(index: number, item: IOrganisation): number {
    return item.id!;
  }

  delete(organisation: IOrganisation): void {
    const modalRef = this.modalService.open(OrganisationDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.organisation = organisation;
    // unsubscribe not needed because closed completes on modal close
    modalRef.closed.subscribe(reason => {
      if (reason === 'deleted') {
        this.loadAll();
      }
    });
  }
}
