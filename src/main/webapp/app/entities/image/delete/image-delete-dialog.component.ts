import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import SharedModule from 'app/shared/shared.module';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';
import { IImage } from '../image.model';
import { ImageService } from '../service/image.service';

@Component({
  standalone: true,
  templateUrl: './image-delete-dialog.component.html',
  imports: [SharedModule, FormsModule],
})
export class ImageDeleteDialogComponent {
  image?: IImage;

  constructor(
    protected imageService: ImageService,
    protected activeModal: NgbActiveModal,
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.imageService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
