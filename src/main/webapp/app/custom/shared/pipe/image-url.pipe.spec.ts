import { ImageUrlPipe } from 'app/custom/shared/pipe/image-url.pipe';

describe('ImageUrlPipe', () => {
  const imageUrlPipe = new ImageUrlPipe();

  it('should  return an empty string if image id is undefined', () => {
    expect(imageUrlPipe.transform(undefined, 'size', 'cacheBuster')).toBe('');
  });

  it('should return  a uri string starting with /api/images/4/content if imageId is equal to 4', () => {
    expect(imageUrlPipe.transform(4, '10', undefined)).toMatch(/^\/api\/images\/4\/content.*/);
  });

  it('should return a uri string containing the query size=10 if the parameter size is equal to "10"', () => {
    expect(imageUrlPipe.transform(4, '10', undefined)).toMatch(
      /.*content\?(size=10(&[^=?&]+=[^=?&]+)*|[^=?&]+=[^=?&]+(&[^=?&]+=[^=?&]+)*&size=10(&[^=?&]+=[^=?&]+)*)$/
    );
  });

  it('should return a uri string not containing the query "cacheBuster" if the parameter cacheBuster is omitted', () => {
    expect(imageUrlPipe.transform(4, '10')).toMatch(/.*content\?((?!.*cacheBuster=)[^=?&]+=[^=?&]+(&[^=?&]+=[^=?&]+)*)$/);
  });

  it('should return a uri string containing the query cacheBuster=true if the parameter cacheBuster is equal to "true"', () => {
    expect(imageUrlPipe.transform(4, '10', 'true')).toMatch(
      /.*content\?(cacheBuster=true(&[^=?&]+=[^=?&]+)*|[^=?&]+=[^=?&]+(&[^=?&]+=[^=?&]+)*&cacheBuster=true(&[^=?&]+=[^=?&]+)*)$/
    );
  });
});
