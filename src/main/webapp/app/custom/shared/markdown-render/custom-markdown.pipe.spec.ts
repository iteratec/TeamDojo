import { MarkdownRenderPipe } from './custom-markdown.pipe';

describe('CustomMarkdownPipe', () => {
  it('create an instance', () => {
    const pipe = new MarkdownRenderPipe();
    expect(pipe).toBeTruthy();
  });
});
