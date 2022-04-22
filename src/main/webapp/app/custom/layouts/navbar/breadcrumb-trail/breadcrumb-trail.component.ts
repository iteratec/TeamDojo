import { Component, OnInit, Input } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { TeamsSelectionService } from 'app/custom/teams-selection/teams-selection.service';
import { IBreadcrumb } from 'app/custom/entities/breadcrumb/breadcrumb.model';
import { BreadcrumbService } from '../breadcrumb.service';
@Component({
  selector: 'jhi-breadcrumb-trail',
  templateUrl: './breadcrumb-trail.component.html',
  styleUrls: ['./breadcrumb-trail.component.scss'],
})
export class BreadcrumbTrailComponent implements OnInit {
  breadcrumbs: IBreadcrumb[] = [];

  constructor(
    private modalService: NgbModal, // TODO: #5 Who need this?
    private teamsSelectionService: TeamsSelectionService, // TODO: #5 Who need this?
    private router: Router, // TODO: #5 Who need this?
    private route: ActivatedRoute, // TODO: #5 Who need this?
    private breadcrumbService: BreadcrumbService
  ) {}

  ngOnInit(): void {
    this.breadcrumbs = this.breadcrumbService.getCurrentBreadcrumb();
    this.breadcrumbService.breadcrumbChanged.subscribe(() => {
      this.breadcrumbs = this.breadcrumbService.getCurrentBreadcrumb();
    });
  }
}
