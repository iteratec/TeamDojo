import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import * as dayjs from 'dayjs';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';

import { IOrganisation, Organisation } from '../organisation.model';
import { OrganisationService } from '../service/organisation.service';

@Component({
  selector: 'jhi-organisation-update',
  templateUrl: './organisation-update.component.html',
})
export class OrganisationUpdateComponent implements OnInit {
  isSaving = false;

  organisationsSharedCollection: IOrganisation[] = [];

  editForm = this.fb.group({
    id: [],
    title: [null, [Validators.required, Validators.minLength(1), Validators.maxLength(50)]],
    description: [null, [Validators.maxLength(4096)]],
    levelUpScore: [],
    countOfConfirmations: [null, [Validators.min(0)]],
    createdAt: [null, [Validators.required]],
    updatedAt: [null, [Validators.required]],
    parent: [],
  });

  constructor(protected organisationService: OrganisationService, protected activatedRoute: ActivatedRoute, protected fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ organisation }) => {
      if (organisation.id === undefined) {
        const today = dayjs().startOf('day');
        organisation.createdAt = today;
        organisation.updatedAt = today;
      }

      this.updateForm(organisation);

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const organisation = this.createFromForm();
    if (organisation.id !== undefined) {
      this.subscribeToSaveResponse(this.organisationService.update(organisation));
    } else {
      this.subscribeToSaveResponse(this.organisationService.create(organisation));
    }
  }

  trackOrganisationById(index: number, item: IOrganisation): number {
    return item.id!;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IOrganisation>>): void {
    result.pipe(finalize(() => this.onSaveFinalize())).subscribe(
      () => this.onSaveSuccess(),
      () => this.onSaveError()
    );
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

  protected updateForm(organisation: IOrganisation): void {
    this.editForm.patchValue({
      id: organisation.id,
      title: organisation.title,
      description: organisation.description,
      levelUpScore: organisation.levelUpScore,
      countOfConfirmations: organisation.countOfConfirmations,
      createdAt: organisation.createdAt ? organisation.createdAt.format(DATE_TIME_FORMAT) : null,
      updatedAt: organisation.updatedAt ? organisation.updatedAt.format(DATE_TIME_FORMAT) : null,
      parent: organisation.parent,
    });

    this.organisationsSharedCollection = this.organisationService.addOrganisationToCollectionIfMissing(
      this.organisationsSharedCollection,
      organisation.parent
    );
  }

  protected loadRelationshipsOptions(): void {
    this.organisationService
      .query()
      .pipe(map((res: HttpResponse<IOrganisation[]>) => res.body ?? []))
      .pipe(
        map((organisations: IOrganisation[]) =>
          this.organisationService.addOrganisationToCollectionIfMissing(organisations, this.editForm.get('parent')!.value)
        )
      )
      .subscribe((organisations: IOrganisation[]) => (this.organisationsSharedCollection = organisations));
  }

  protected createFromForm(): IOrganisation {
    return {
      ...new Organisation(),
      id: this.editForm.get(['id'])!.value,
      title: this.editForm.get(['title'])!.value,
      description: this.editForm.get(['description'])!.value,
      levelUpScore: this.editForm.get(['levelUpScore'])!.value,
      countOfConfirmations: this.editForm.get(['countOfConfirmations'])!.value,
      createdAt: this.editForm.get(['createdAt'])!.value ? dayjs(this.editForm.get(['createdAt'])!.value, DATE_TIME_FORMAT) : undefined,
      updatedAt: this.editForm.get(['updatedAt'])!.value ? dayjs(this.editForm.get(['updatedAt'])!.value, DATE_TIME_FORMAT) : undefined,
      parent: this.editForm.get(['parent'])!.value,
    };
  }
}
