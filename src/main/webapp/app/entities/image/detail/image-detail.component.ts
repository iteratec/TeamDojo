import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IImage } from '../image.model';
import { DataUtils } from 'app/core/util/data-util.service';

@Component({
  selector: 'jhi-image-detail',
  templateUrl: './image-detail.component.html',
})
export class ImageDetailComponent implements OnInit {
  image: IImage | null = null;

  constructor(protected dataUtils: DataUtils, protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ image }) => {
      this.image = image;
    });
  }

  byteSize(base64String: string): string {
    return this.dataUtils.byteSize(base64String);
  }

  openFile(base64String: string, contentType: string | null | undefined): void {
    this.dataUtils.openFile(base64String, contentType);
  }

  previousState(): void {
    window.history.back();
  }
}
