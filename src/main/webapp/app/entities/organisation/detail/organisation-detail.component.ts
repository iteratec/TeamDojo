import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IOrganisation } from '../organisation.model';

@Component({
  selector: 'jhi-organisation-detail',
  templateUrl: './organisation-detail.component.html',
})
export class OrganisationDetailComponent implements OnInit {
  organisation: IOrganisation | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ organisation }) => {
      this.organisation = organisation;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
