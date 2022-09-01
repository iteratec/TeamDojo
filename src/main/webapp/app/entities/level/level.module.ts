import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { LevelComponent } from './list/level.component';
import { LevelDetailComponent } from './detail/level-detail.component';
import { LevelUpdateComponent } from './update/level-update.component';
import { LevelDeleteDialogComponent } from './delete/level-delete-dialog.component';
import { LevelRoutingModule } from './route/level-routing.module';

@NgModule({
  imports: [SharedModule, LevelRoutingModule],
  declarations: [LevelComponent, LevelDetailComponent, LevelUpdateComponent, LevelDeleteDialogComponent],
})
export class LevelModule {}
