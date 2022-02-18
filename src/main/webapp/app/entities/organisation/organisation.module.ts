import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { OrganisationComponent } from './list/organisation.component';
import { OrganisationDetailComponent } from './detail/organisation-detail.component';
import { OrganisationUpdateComponent } from './update/organisation-update.component';
import { OrganisationDeleteDialogComponent } from './delete/organisation-delete-dialog.component';
import { OrganisationRoutingModule } from './route/organisation-routing.module';

@NgModule({
  imports: [SharedModule, OrganisationRoutingModule],
  declarations: [OrganisationComponent, OrganisationDetailComponent, OrganisationUpdateComponent, OrganisationDeleteDialogComponent],
  entryComponents: [OrganisationDeleteDialogComponent],
})
export class OrganisationModule {}
