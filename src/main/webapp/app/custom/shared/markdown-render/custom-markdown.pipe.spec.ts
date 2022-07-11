import { CustomMarkdownPipe } from './custom-markdown.pipe';

describe('CustomMarkdownPipe', () => {
  it('create an instance', () => {
    const pipe = new CustomMarkdownPipe();
    expect(pipe).toBeTruthy();
  });
});
