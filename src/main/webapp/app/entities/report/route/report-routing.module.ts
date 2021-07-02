import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ReportComponent } from '../list/report.component';
import { ReportDetailComponent } from '../detail/report-detail.component';
import { ReportUpdateComponent } from '../update/report-update.component';
import { ReportRoutingResolveService } from './report-routing-resolve.service';

const reportRoute: Routes = [
  {
    path: '',
    component: ReportComponent,
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: ReportDetailComponent,
    resolve: {
      report: ReportRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: ReportUpdateComponent,
    resolve: {
      report: ReportRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: ReportUpdateComponent,
    resolve: {
      report: ReportRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(reportRoute)],
  exports: [RouterModule],
})
export class ReportRoutingModule {}
