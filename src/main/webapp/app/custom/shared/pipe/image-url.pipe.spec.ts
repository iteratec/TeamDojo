import { ImageUrlPipe } from 'app/custom/shared/pipe/image-url.pipe';

describe('ImageUrlPipe', () => {
  const imageUrlPipe = new ImageUrlPipe();

  it('should  return an empty string if image id is undefined', () => {
    expect(imageUrlPipe.transform(undefined, 'size', 'cacheBuster')).toBe('');
  });

  it('should return  a uri string starting with /api/images/4/ if imageId is equal to 4', () => {
    expect(imageUrlPipe.transform(4, '10', undefined)).toMatch(/^\/api\/images\/4\/.*/);
  });

  it('should return a uri string containing the query size=10 if the parameter size is equal to "10"', () => {
    expect(imageUrlPipe.transform(4, '10', undefined));
  });

  it(
    'should return the string /api/images/4/content?size=10 if ' +
      'imageId is equal to "4", size is equal to "10" and cacheBuster ' +
      'is undefined',
    () => {
      expect(imageUrlPipe.transform('4', '10', undefined)).toBe('/api/images/4/content?size=10');
    }
  );

  it(
    'should return the string /api/images/4/content?size=10&cacheBuster=cacheBuster if ' +
      'imageId is equal to 4, size is equal to "10" and cacheBuster equal to "cacheBuster"',
    () => {
      expect(imageUrlPipe.transform(4, '10', 'cacheBuster')).toBe('/api/images/4/content?size=10&cacheBuster=cacheBuster');
    }
  );
});
