2,3c2,3
< import { ActivatedRoute, Router } from '@angular/router';
< import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
---
> import { Router } from '@angular/router';
> import { NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
10,21d9
< import { TeamsSelectionService } from 'app/shared/teams-selection/teams-selection.service';
< import { TeamsSelectionComponent } from 'app/shared/teams-selection/teams-selection.component';
< import { ITeam, Team } from 'app/shared/model/team.model';
< import { IBadge } from 'app/shared/model/badge.model';
< import { ILevel } from 'app/shared/model/level.model';
< import { IDimension } from 'app/shared/model/dimension.model';
< import { ISkill } from 'app/shared/model/skill.model';
< import { BreadcrumbService } from 'app/layouts/navbar/breadcrumb.service';
< import { IBreadcrumb } from 'app/shared/model/breadcrumb.model';
< import { OrganizationService } from 'app/entities/organization';
< import { IOrganization, Organization } from 'app/shared/model/organization.model';
< import { HttpResponse } from '@angular/common/http';
33d20
<     organizationName: string;
36,43d22
<     isTeamSelectionOpen = false;
< 
<     activeLevel: ILevel;
<     activeBadge: IBadge;
<     activeDimension: IDimension;
<     activeTeam: ITeam;
<     activeSkill: ISkill;
<     breadcrumbs: IBreadcrumb[];
52d30
<         private teamsSelectionService: TeamsSelectionService,
54,57c32
<         private modalService: NgbModal,
<         private router: Router,
<         private route: ActivatedRoute,
<         private breadcrumbService: BreadcrumbService
---
>         private router: Router
64,68d38
<         this.breadcrumbs = this.breadcrumbService.getCurrentBreadcrumb();
<         this.breadcrumbService.breadcrumbChanged.subscribe(breadcrumb => {
<             this.breadcrumbs = this.breadcrumbService.getCurrentBreadcrumb();
<         });
< 
77,90d46
< 
<         this.route.data.subscribe(({ organization }) => {
<             this.organizationName = organization.name;
<         });
<         this.teamsSelectionService.query().subscribe();
<     }
< 
<     loadBreadcrumb() {
<         this.activeLevel = null;
<         this.activeBadge = null;
<         this.activeDimension = null;
<         this.activeTeam = null;
<         this.activeSkill = null;
<         this.breadcrumbs = this.breadcrumbService.getCurrentBreadcrumb();
122,150d77
<     }
< 
<     selectTeam(): NgbModalRef {
<         if (this.isTeamSelectionOpen) {
<             return;
<         }
<         this.isTeamSelectionOpen = true;
<         const modalRef = this.modalService.open(TeamsSelectionComponent, { size: 'lg' });
<         modalRef.result.then(
<             result => {
<                 this.isTeamSelectionOpen = false;
<             },
<             reason => {
<                 this.isTeamSelectionOpen = false;
<             }
<         );
<         return modalRef;
<     }
< 
<     isTeamOverview() {
<         return this.activeTeam !== null && this.activeTeam !== 'undefined';
<     }
< 
<     isSkillDetail() {
<         return this.activeSkill !== null && this.activeSkill !== 'undfined';
<     }
< 
<     get selectedTeam() {
<         return this.teamsSelectionService.selectedTeam;
