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
  createdAt?: Date;
  updatedAt?: Date;
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
    public createdAt?: Date,
    public updatedAt?: Date
  ) {}
}
