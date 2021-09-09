import { ImageUrlPipe } from 'app/custom/shared/pipe/image-url.pipe';

describe('ImageUrlPipe', () => {
  const imageUrlPipe = new ImageUrlPipe();

  it('should  return an empty string if image id is undefined', () => {
    expect(imageUrlPipe.transform(undefined, 'size', 'cacheBuster')).toBe('');
  });

  it(
    'should return the string /api/images/4/content?size=10 if ' +
      'imageId is equal to 4, size is equal to the string 10 and cacheBuster' +
      'is undefined',
    () => {
      expect(imageUrlPipe.transform(4, '10', undefined)).toBe('/api/images/4/content?size=10');
    }
  );

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
