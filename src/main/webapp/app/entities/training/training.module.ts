import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { TrainingComponent } from './list/training.component';
import { TrainingDetailComponent } from './detail/training-detail.component';
import { TrainingUpdateComponent } from './update/training-update.component';
import { TrainingDeleteDialogComponent } from './delete/training-delete-dialog.component';
import { TrainingRoutingModule } from './route/training-routing.module';

@NgModule({
  imports: [SharedModule, TrainingRoutingModule],
  declarations: [TrainingComponent, TrainingDetailComponent, TrainingUpdateComponent, TrainingDeleteDialogComponent],
})
export class TrainingModule {}
