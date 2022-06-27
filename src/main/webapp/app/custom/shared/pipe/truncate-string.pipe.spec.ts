/*
 SPDX-FileCopyrightText: the TeamDojo authors
 SPDX-License-Identifier: Apache-2.0
 */
import { TruncateStringPipe } from 'app/custom/shared/pipe/truncate-string.pipe';

describe('TruncateStringPipe', () => {
  const truncateStringPipe = new TruncateStringPipe();

  const test = 'test';

  it('should  return an empty string if image stringToTruncate is undefined', () => {
    expect(truncateStringPipe.transform(undefined, 10)).toBe('');
  });

  it('should return a string equal to stringToTruncate if truncateLength is greater then the length ' + 'of the string to truncate', () => {
    expect(truncateStringPipe.transform(test, test.length + 1)).toBe('test');
  });

  it('should return the input string if truncateLength is equal to the length of the string', () => {
    expect(truncateStringPipe.transform(test, test.length)).toBe(test);
  });

  it(
    'should return the first three letters of the truncated string concatenated with "..." if ' +
      'truncateLength is equal to 3 and the input string is greater or equal to 3',
    () => {
      expect(truncateStringPipe.transform(test, 3)).toBe('tes...');
    }
  );

  it('should return "..." if truncateLength is equal to -1', () => {
    expect(truncateStringPipe.transform(test, -1)).toBe('...');
  });

  it('should return "..." if truncateLength is equal to 0', () => {
    expect(truncateStringPipe.transform(test, 0)).toBe('...');
  });
});
