/*
 SPDX-FileCopyrightText: the TeamDojo authors
 SPDX-License-Identifier: Apache-2.0
 */
import { Pipe, PipeTransform } from '@angular/core';

@Pipe({ 
  name: 'truncateString', 
  standalone: true,
})
export class TruncateStringPipe implements PipeTransform {
  transform(stringToTruncate: string | undefined | null, truncateLength = 10): string {
    let truncatedString = '';
    if (stringToTruncate) {
      if (stringToTruncate.length > truncateLength) {
        truncatedString = stringToTruncate.substring(0, truncateLength) + '...';
      } else {
        truncatedString = stringToTruncate;
      }
    }
    return truncatedString;
  }
}
