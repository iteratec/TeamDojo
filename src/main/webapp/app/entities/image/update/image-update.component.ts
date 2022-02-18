import { Component, OnInit, ElementRef } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import dayjs from 'dayjs/esm';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';

import { IImage, Image } from '../image.model';
import { ImageService } from '../service/image.service';
import { AlertError } from 'app/shared/alert/alert-error.model';
import { EventManager, EventWithContent } from 'app/core/util/event-manager.service';
import { DataUtils, FileLoadError } from 'app/core/util/data-util.service';

@Component({
  selector: 'jhi-image-update',
  templateUrl: './image-update.component.html',
})
export class ImageUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    title: [null, [Validators.required, Validators.minLength(1), Validators.maxLength(50)]],
    small: [],
    smallContentType: [],
    medium: [],
    mediumContentType: [],
    large: [],
    largeContentType: [],
    hash: [null, [Validators.maxLength(32)]],
    createdAt: [null, [Validators.required]],
    updatedAt: [null, [Validators.required]],
  });

  constructor(
    protected dataUtils: DataUtils,
    protected eventManager: EventManager,
    protected imageService: ImageService,
    protected elementRef: ElementRef,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ image }) => {
      if (image.id === undefined) {
        const today = dayjs().startOf('day');
        image.createdAt = today;
        image.updatedAt = today;
      }

      this.updateForm(image);
    });
  }

  byteSize(base64String: string): string {
    return this.dataUtils.byteSize(base64String);
  }

  openFile(base64String: string, contentType: string | null | undefined): void {
    this.dataUtils.openFile(base64String, contentType);
  }

  setFileData(event: Event, field: string, isImage: boolean): void {
    this.dataUtils.loadFileToForm(event, this.editForm, field, isImage).subscribe({
      error: (err: FileLoadError) =>
        this.eventManager.broadcast(new EventWithContent<AlertError>('teamDojoApp.error', { ...err, key: 'error.file.' + err.key })),
    });
  }

  clearInputImage(field: string, fieldContentType: string, idInput: string): void {
    this.editForm.patchValue({
      [field]: null,
      [fieldContentType]: null,
    });
    if (idInput && this.elementRef.nativeElement.querySelector('#' + idInput)) {
      this.elementRef.nativeElement.querySelector('#' + idInput).value = null;
    }
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const image = this.createFromForm();
    if (image.id !== undefined) {
      this.subscribeToSaveResponse(this.imageService.update(image));
    } else {
      this.subscribeToSaveResponse(this.imageService.create(image));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IImage>>): void {
    result.pipe(finalize(() => this.onSaveFinalize())).subscribe({
      next: () => this.onSaveSuccess(),
      error: () => this.onSaveError(),
    });
  }

  protected onSaveSuccess(): void {
    this.previousState();
  }

  protected onSaveError(): void {
    // Api for inheritance.
  }

  protected onSaveFinalize(): void {
    this.isSaving = false;
  }

  protected updateForm(image: IImage): void {
    this.editForm.patchValue({
      id: image.id,
      title: image.title,
      small: image.small,
      smallContentType: image.smallContentType,
      medium: image.medium,
      mediumContentType: image.mediumContentType,
      large: image.large,
      largeContentType: image.largeContentType,
      hash: image.hash,
      createdAt: image.createdAt ? image.createdAt.format(DATE_TIME_FORMAT) : null,
      updatedAt: image.updatedAt ? image.updatedAt.format(DATE_TIME_FORMAT) : null,
    });
  }

  protected createFromForm(): IImage {
    return {
      ...new Image(),
      id: this.editForm.get(['id'])!.value,
      title: this.editForm.get(['title'])!.value,
      smallContentType: this.editForm.get(['smallContentType'])!.value,
      small: this.editForm.get(['small'])!.value,
      mediumContentType: this.editForm.get(['mediumContentType'])!.value,
      medium: this.editForm.get(['medium'])!.value,
      largeContentType: this.editForm.get(['largeContentType'])!.value,
      large: this.editForm.get(['large'])!.value,
      hash: this.editForm.get(['hash'])!.value,
      createdAt: this.editForm.get(['createdAt'])!.value ? dayjs(this.editForm.get(['createdAt'])!.value, DATE_TIME_FORMAT) : undefined,
      updatedAt: this.editForm.get(['updatedAt'])!.value ? dayjs(this.editForm.get(['updatedAt'])!.value, DATE_TIME_FORMAT) : undefined,
    };
  }
}
