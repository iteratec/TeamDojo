import { Pipe, PipeTransform } from '@angular/core';

@Pipe({ name: 'imageUrl' })
export class ImageUrlPipe implements PipeTransform {
  transform(imageId: string | number, size: string, cacheBuster?: string): string {
    return imageId ? `/api/images/${imageId}/content?size=${size}&cacheBuster=${cacheBuster}` : '';
  }
}
