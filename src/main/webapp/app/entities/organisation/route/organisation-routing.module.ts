import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { OrganisationComponent } from '../list/organisation.component';
import { OrganisationDetailComponent } from '../detail/organisation-detail.component';
import { OrganisationUpdateComponent } from '../update/organisation-update.component';
import { OrganisationRoutingResolveService } from './organisation-routing-resolve.service';

const organisationRoute: Routes = [
  {
    path: '',
    component: OrganisationComponent,
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: OrganisationDetailComponent,
    resolve: {
      organisation: OrganisationRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: OrganisationUpdateComponent,
    resolve: {
      organisation: OrganisationRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: OrganisationUpdateComponent,
    resolve: {
      organisation: OrganisationRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(organisationRoute)],
  exports: [RouterModule],
})
export class OrganisationRoutingModule {}
