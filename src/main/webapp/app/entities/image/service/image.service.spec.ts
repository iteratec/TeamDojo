import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import * as dayjs from 'dayjs';

import { DATE_TIME_FORMAT } from 'app/config/input.constants';
import { IImage, Image } from '../image.model';

import { ImageService } from './image.service';

describe('Service Tests', () => {
  describe('Image Service', () => {
    let service: ImageService;
    let httpMock: HttpTestingController;
    let elemDefault: IImage;
    let expectedResult: IImage | IImage[] | boolean | null;
    let currentDate: dayjs.Dayjs;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      service = TestBed.inject(ImageService);
      httpMock = TestBed.inject(HttpTestingController);
      currentDate = dayjs();

      elemDefault = {
        id: 0,
        title: 'AAAAAAA',
        smallContentType: 'image/png',
        small: 'AAAAAAA',
        mediumContentType: 'image/png',
        medium: 'AAAAAAA',
        largeContentType: 'image/png',
        large: 'AAAAAAA',
        hash: 'AAAAAAA',
        createdAt: currentDate,
        updatedAt: currentDate,
      };
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign(
          {
            createdAt: currentDate.format(DATE_TIME_FORMAT),
            updatedAt: currentDate.format(DATE_TIME_FORMAT),
          },
          elemDefault
        );

        service.find(123).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a Image', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
            createdAt: currentDate.format(DATE_TIME_FORMAT),
            updatedAt: currentDate.format(DATE_TIME_FORMAT),
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            createdAt: currentDate,
            updatedAt: currentDate,
          },
          returnedFromService
        );

        service.create(new Image()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a Image', () => {
        const returnedFromService = Object.assign(
          {
            id: 1,
            title: 'BBBBBB',
            small: 'BBBBBB',
            medium: 'BBBBBB',
            large: 'BBBBBB',
            hash: 'BBBBBB',
            createdAt: currentDate.format(DATE_TIME_FORMAT),
            updatedAt: currentDate.format(DATE_TIME_FORMAT),
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            createdAt: currentDate,
            updatedAt: currentDate,
          },
          returnedFromService
        );

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should partial update a Image', () => {
        const patchObject = Object.assign(
          {
            medium: 'BBBBBB',
            large: 'BBBBBB',
            hash: 'BBBBBB',
            createdAt: currentDate.format(DATE_TIME_FORMAT),
          },
          new Image()
        );

        const returnedFromService = Object.assign(patchObject, elemDefault);

        const expected = Object.assign(
          {
            createdAt: currentDate,
            updatedAt: currentDate,
          },
          returnedFromService
        );

        service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PATCH' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of Image', () => {
        const returnedFromService = Object.assign(
          {
            id: 1,
            title: 'BBBBBB',
            small: 'BBBBBB',
            medium: 'BBBBBB',
            large: 'BBBBBB',
            hash: 'BBBBBB',
            createdAt: currentDate.format(DATE_TIME_FORMAT),
            updatedAt: currentDate.format(DATE_TIME_FORMAT),
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            createdAt: currentDate,
            updatedAt: currentDate,
          },
          returnedFromService
        );

        service.query().subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        httpMock.verify();
        expect(expectedResult).toContainEqual(expected);
      });

      it('should delete a Image', () => {
        service.delete(123).subscribe(resp => (expectedResult = resp.ok));

        const req = httpMock.expectOne({ method: 'DELETE' });
        req.flush({ status: 200 });
        expect(expectedResult);
      });

      describe('addImageToCollectionIfMissing', () => {
        it('should add a Image to an empty array', () => {
          const image: IImage = { id: 123 };
          expectedResult = service.addImageToCollectionIfMissing([], image);
          expect(expectedResult).toHaveLength(1);
          expect(expectedResult).toContain(image);
        });

        it('should not add a Image to an array that contains it', () => {
          const image: IImage = { id: 123 };
          const imageCollection: IImage[] = [
            {
              ...image,
            },
            { id: 456 },
          ];
          expectedResult = service.addImageToCollectionIfMissing(imageCollection, image);
          expect(expectedResult).toHaveLength(2);
        });

        it("should add a Image to an array that doesn't contain it", () => {
          const image: IImage = { id: 123 };
          const imageCollection: IImage[] = [{ id: 456 }];
          expectedResult = service.addImageToCollectionIfMissing(imageCollection, image);
          expect(expectedResult).toHaveLength(2);
          expect(expectedResult).toContain(image);
        });

        it('should add only unique Image to an array', () => {
          const imageArray: IImage[] = [{ id: 123 }, { id: 456 }, { id: 70293 }];
          const imageCollection: IImage[] = [{ id: 123 }];
          expectedResult = service.addImageToCollectionIfMissing(imageCollection, ...imageArray);
          expect(expectedResult).toHaveLength(3);
        });

        it('should accept varargs', () => {
          const image: IImage = { id: 123 };
          const image2: IImage = { id: 456 };
          expectedResult = service.addImageToCollectionIfMissing([], image, image2);
          expect(expectedResult).toHaveLength(2);
          expect(expectedResult).toContain(image);
          expect(expectedResult).toContain(image2);
        });

        it('should accept null and undefined values', () => {
          const image: IImage = { id: 123 };
          expectedResult = service.addImageToCollectionIfMissing([], null, image, undefined);
          expect(expectedResult).toHaveLength(1);
          expect(expectedResult).toContain(image);
        });
      });
    });

    afterEach(() => {
      httpMock.verify();
    });
  });
});
