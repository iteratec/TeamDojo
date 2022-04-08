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
  // TODO: #5 Here add the group of the team.
  @Input() teamGroupName = 'HARDCODED TEAM GROUP';
  breadcrumbs: IBreadcrumb[] = [];

  constructor(
    private modalService: NgbModal,
    private teamsSelectionService: TeamsSelectionService,
    private router: Router,
    private route: ActivatedRoute,
    private breadcrumbService: BreadcrumbService
  ) {}

  ngOnInit(): void {
    this.breadcrumbs = this.breadcrumbService.getCurrentBreadcrumb();
    this.breadcrumbService.breadcrumbChanged.subscribe(() => {
      this.breadcrumbs = this.breadcrumbService.getCurrentBreadcrumb();
    });
  }
}
