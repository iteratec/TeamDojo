import dayjs from 'dayjs/esm';

export interface IImage {
  id?: number;
  title?: string;
  smallContentType?: string | null;
  small?: string | null;
  mediumContentType?: string | null;
  medium?: string | null;
  largeContentType?: string | null;
  large?: string | null;
  hash?: string | null;
  createdAt?: dayjs.Dayjs;
  updatedAt?: dayjs.Dayjs;
}

export class Image implements IImage {
  constructor(
    public id?: number,
    public title?: string,
    public smallContentType?: string | null,
    public small?: string | null,
    public mediumContentType?: string | null,
    public medium?: string | null,
    public largeContentType?: string | null,
    public large?: string | null,
    public hash?: string | null,
    public createdAt?: dayjs.Dayjs,
    public updatedAt?: dayjs.Dayjs
  ) {}
}

export function getImageIdentifier(image: IImage): number | undefined {
  return image.id;
}
