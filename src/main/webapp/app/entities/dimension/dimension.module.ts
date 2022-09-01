import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { DimensionComponent } from './list/dimension.component';
import { DimensionDetailComponent } from './detail/dimension-detail.component';
import { DimensionUpdateComponent } from './update/dimension-update.component';
import { DimensionDeleteDialogComponent } from './delete/dimension-delete-dialog.component';
import { DimensionRoutingModule } from './route/dimension-routing.module';

@NgModule({
  imports: [SharedModule, DimensionRoutingModule],
  declarations: [DimensionComponent, DimensionDetailComponent, DimensionUpdateComponent, DimensionDeleteDialogComponent],
})
export class DimensionModule {}
