import { Pipe, PipeTransform } from '@angular/core';
import { marked } from 'marked';
import DOMPurify from 'dompurify';

@Pipe({
  name: 'customMarkdown',
})
export class CustomMarkdownPipe implements PipeTransform {
  transform(value: any, args?: any[]): any {
    if (value && value.length > 0) {
      const santized = DOMPurify.sanitize(value);
      return marked(santized);
    }
    return value;
  }
}
