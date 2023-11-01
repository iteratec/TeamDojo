import dayjs from 'dayjs/esm';

export interface IImage {
  id: number;
  title?: string | null;
  small?: string | null;
  smallContentType?: string | null;
  medium?: string | null;
  mediumContentType?: string | null;
  large?: string | null;
  largeContentType?: string | null;
  hash?: string | null;
  createdAt?: dayjs.Dayjs | null;
  updatedAt?: dayjs.Dayjs | null;
}

export type NewImage = Omit<IImage, 'id'> & { id: null };
