import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import dayjs from 'dayjs/esm';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';
import { IImage, NewImage } from '../image.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IImage for edit and NewImageFormGroupInput for create.
 */
type ImageFormGroupInput = IImage | PartialWithRequiredKeyOf<NewImage>;

/**
 * Type that converts some properties for forms.
 */
type FormValueOf<T extends IImage | NewImage> = Omit<T, 'createdAt' | 'updatedAt'> & {
  createdAt?: string | null;
  updatedAt?: string | null;
};

type ImageFormRawValue = FormValueOf<IImage>;

type NewImageFormRawValue = FormValueOf<NewImage>;

type ImageFormDefaults = Pick<NewImage, 'id' | 'createdAt' | 'updatedAt'>;

type ImageFormGroupContent = {
  id: FormControl<ImageFormRawValue['id'] | NewImage['id']>;
  title: FormControl<ImageFormRawValue['title']>;
  small: FormControl<ImageFormRawValue['small']>;
  smallContentType: FormControl<ImageFormRawValue['smallContentType']>;
  medium: FormControl<ImageFormRawValue['medium']>;
  mediumContentType: FormControl<ImageFormRawValue['mediumContentType']>;
  large: FormControl<ImageFormRawValue['large']>;
  largeContentType: FormControl<ImageFormRawValue['largeContentType']>;
  hash: FormControl<ImageFormRawValue['hash']>;
  createdAt: FormControl<ImageFormRawValue['createdAt']>;
  updatedAt: FormControl<ImageFormRawValue['updatedAt']>;
};

export type ImageFormGroup = FormGroup<ImageFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class ImageFormService {
  createImageFormGroup(image: ImageFormGroupInput = { id: null }): ImageFormGroup {
    const imageRawValue = this.convertImageToImageRawValue({
      ...this.getFormDefaults(),
      ...image,
    });
    return new FormGroup<ImageFormGroupContent>({
      id: new FormControl(
        { value: imageRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        },
      ),
      title: new FormControl(imageRawValue.title, {
        validators: [Validators.required, Validators.minLength(1), Validators.maxLength(50)],
      }),
      small: new FormControl(imageRawValue.small),
      smallContentType: new FormControl(imageRawValue.smallContentType),
      medium: new FormControl(imageRawValue.medium),
      mediumContentType: new FormControl(imageRawValue.mediumContentType),
      large: new FormControl(imageRawValue.large),
      largeContentType: new FormControl(imageRawValue.largeContentType),
      hash: new FormControl(imageRawValue.hash, {
        validators: [Validators.maxLength(32)],
      }),
      createdAt: new FormControl(imageRawValue.createdAt, {
        validators: [Validators.required],
      }),
      updatedAt: new FormControl(imageRawValue.updatedAt, {
        validators: [Validators.required],
      }),
    });
  }

  getImage(form: ImageFormGroup): IImage | NewImage {
    return this.convertImageRawValueToImage(form.getRawValue() as ImageFormRawValue | NewImageFormRawValue);
  }

  resetForm(form: ImageFormGroup, image: ImageFormGroupInput): void {
    const imageRawValue = this.convertImageToImageRawValue({ ...this.getFormDefaults(), ...image });
    form.reset(
      {
        ...imageRawValue,
        id: { value: imageRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */,
    );
  }

  private getFormDefaults(): ImageFormDefaults {
    const currentTime = dayjs();

    return {
      id: null,
      createdAt: currentTime,
      updatedAt: currentTime,
    };
  }

  private convertImageRawValueToImage(rawImage: ImageFormRawValue | NewImageFormRawValue): IImage | NewImage {
    return {
      ...rawImage,
      createdAt: dayjs(rawImage.createdAt, DATE_TIME_FORMAT),
      updatedAt: dayjs(rawImage.updatedAt, DATE_TIME_FORMAT),
    };
  }

  private convertImageToImageRawValue(
    image: IImage | (Partial<NewImage> & ImageFormDefaults),
  ): ImageFormRawValue | PartialWithRequiredKeyOf<NewImageFormRawValue> {
    return {
      ...image,
      createdAt: image.createdAt ? image.createdAt.format(DATE_TIME_FORMAT) : undefined,
      updatedAt: image.updatedAt ? image.updatedAt.format(DATE_TIME_FORMAT) : undefined,
    };
  }
}
