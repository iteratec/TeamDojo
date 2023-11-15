/*
 SPDX-FileCopyrightText: the TeamDojo authors
 SPDX-License-Identifier: Apache-2.0
 */
import { Pipe, PipeTransform } from '@angular/core';

@Pipe({ name: 'imageUrl', standalone: true})
export class ImageUrlPipe implements PipeTransform {
  transform(imageId: string | number | undefined, size: string, cacheBuster?: string | null): string {
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
