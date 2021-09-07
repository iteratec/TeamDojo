import { Pipe, PipeTransform } from '@angular/core';

@Pipe({ name: 'imageUrl' })
export class ImageUrlPipe implements PipeTransform {
  transform(imageId: string | number, size: string, cacheBuster?: string): string {
    if (imageId) {
      let imgUrl = `/api/images/${imageId}/content?size=${size}`;

      if (cacheBuster) {
        imgUrl += `&cacheBuster=${cacheBuster}`;
      }

      return imgUrl;
    }

    return '';
  }
}
