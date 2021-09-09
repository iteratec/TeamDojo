import { ImageUrlPipe } from 'app/custom/shared/pipe/image-url.pipe';
import { TruncateStringPipe } from 'app/custom/shared/pipe/truncate-string.pipe';

describe('ImageUrlPipe', () => {
  const truncateStringPipe = new TruncateStringPipe();

  it('should  return an empty string if image stringToTruncate is undefined', () => {
    expect(truncateStringPipe.transform(undefined, 10)).toBe('');
  });

  it('should return a string equal to stringToTruncate if truncateLength is greater then the length ' + 'of the string to truncate', () => {
    expect(truncateStringPipe.transform('test', 5)).toBe('test');
  });

  it('should return test if stringToTruncate is "test" and truncateLength is 4', () => {
    expect(truncateStringPipe.transform('test', 4)).toBe('test');
  });

  it('should return tes... if stringToTruncate is "test" and truncateLength is 10', () => {
    expect(truncateStringPipe.transform('test', 3)).toBe('tes...');
  });

  it('should return hello... if stringToTruncate is "helloworld" and truncateLength is 5', () => {
    expect(truncateStringPipe.transform('helloworld', 5)).toBe('hello...');
  });

  it('should return "..." if stringToTruncate is "test" and truncateLength is -1', () => {
    expect(truncateStringPipe.transform('test', -1)).toBe('...');
  });

  it('should return "..." if stringToTruncate is "test" and truncateLength is -1', () => {
    expect(truncateStringPipe.transform('test', 0)).toBe('...');
  });
});
