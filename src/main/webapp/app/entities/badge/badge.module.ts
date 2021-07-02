import { NgModule } from '@angular/core';

import { SharedModule } from 'app/shared/shared.module';
import { BadgeComponent } from './list/badge.component';
import { BadgeDetailComponent } from './detail/badge-detail.component';
import { BadgeUpdateComponent } from './update/badge-update.component';
import { BadgeDeleteDialogComponent } from './delete/badge-delete-dialog.component';
import { BadgeRoutingModule } from './route/badge-routing.module';

@NgModule({
  imports: [SharedModule, BadgeRoutingModule],
  declarations: [BadgeComponent, BadgeDetailComponent, BadgeUpdateComponent, BadgeDeleteDialogComponent],
  entryComponents: [BadgeDeleteDialogComponent],
})
export class BadgeModule {}
