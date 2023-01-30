import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { ImageComponent } from './list/image.component';
import { ImageDetailComponent } from './detail/image-detail.component';
import { ImageUpdateComponent } from './update/image-update.component';
import { ImageDeleteDialogComponent } from './delete/image-delete-dialog.component';
import { ImageRoutingModule } from './route/image-routing.module';

@NgModule({
  imports: [SharedModule, ImageRoutingModule],
  declarations: [ImageComponent, ImageDetailComponent, ImageUpdateComponent, ImageDeleteDialogComponent],
})
export class ImageModule {}
