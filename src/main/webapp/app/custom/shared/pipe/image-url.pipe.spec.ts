/*
 SPDX-FileCopyrightText: the TeamDojo authors
 SPDX-License-Identifier: Apache-2.0
 */
import { ImageUrlPipe } from 'app/custom/shared/pipe/image-url.pipe';

// Here we use some naive test cases. If we want to make them less brittle (w.g. for changing position
// of query parameters) we must change the test cases. But this is not worth the effort at the moment for
// two query parameters. If the number increases we should change this!
describe('ImageUrlPipe', () => {
  const imageUrlPipe = new ImageUrlPipe();

  it('should return an empty string if image id is undefined', () => {
    expect(imageUrlPipe.transform(undefined, '')).toBe('');
  });

  it('should return a uri string with image id as third path segment', () => {
    expect(imageUrlPipe.transform(4, '')).toMatch(/^\/api\/images\/4\/.*/);
  });

  it('should return a uri string containing the size as query parameter', () => {
    expect(imageUrlPipe.transform(4, '10')).toMatch(/.*\?size=10$/);
  });

  it('should return a uri string containing the cache buster as query parameter', () => {
    expect(imageUrlPipe.transform(4, '10', 'foobar')).toMatch(/.*&cacheBuster=foobar$/);
  });

  it('should return a uri string with all given parameters', () => {
    expect(imageUrlPipe.transform(42, '23', 'snafu')).toBe('/api/images/42/content?size=23&cacheBuster=snafu');
  });
});
