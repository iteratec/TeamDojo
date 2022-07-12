/*
 SPDX-FileCopyrightText: the TeamDojo authors
 SPDX-License-Identifier: Apache-2.0
 */
import { Component, ElementRef, OnInit, ViewChild } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { ITeam, Team } from 'app/entities/team/team.model';
import { IImage } from 'app/entities/image/image.model';
import { IDimension } from 'app/entities/dimension/dimension.model';
import { AlertService } from 'app/core/util/alert.service';
import { TeamService } from 'app/entities/team/service/team.service';
import { DimensionService } from 'app/entities/dimension/service/dimension.service';
import { ImageService } from 'app/entities/image/service/image.service';
import { DataUtils, FileLoadError } from 'app/core/util/data-util.service';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { EventManager, EventWithContent } from 'app/core/util/event-manager.service';
import { AlertError } from 'app/shared/alert/alert-error.model';
import { TeamGroupService } from 'app/entities/team-group/service/team-group.service';
import { ITeamGroup } from '../../../entities/team-group/team-group.model';
import { DIMENSIONS_PER_PAGE, TEAM_GROUPS_PER_PAGE } from '../../../config/pagination.constants';
import dayjs from 'dayjs/esm';
import { DATE_TIME_FORMAT } from '../../../config/input.constants';

@Component({
  selector: 'jhi-teams-quickedit',
  templateUrl: './teams-edit.component.html',
  styleUrls: ['./teams-edit.scss'],
})
export class TeamsEditComponent implements OnInit {
  team: ITeam;
  isSaving: boolean;
  image: IImage | null;
  editMode: boolean;
  dimensions: IDimension[];
  teamGroups: ITeamGroup[];
  expirationDateString = '';

  @ViewChild('fileImage')
  uploadedImage?: ElementRef;

  // TODO: #31 Here we need some definition of the template form.
  //       The code is inspired by src/main/webapp/app/entities/image/update/image-update.component.ts
  editForm = this.fb.group({
    large: [],
    largeContentType: [],
  });

  constructor(
    private activeModal: NgbActiveModal,
    private alertService: AlertService,
    private teamService: TeamService,
    private dimensionService: DimensionService,
    private imageService: ImageService,
    private teamGroupService: TeamGroupService,
    private dataUtils: DataUtils,
    private elementRef: ElementRef,
    private fb: FormBuilder,
    private eventManager: EventManager
  ) {
    this.team = new Team();
    this.isSaving = false;
    this.image = null;
    this.editMode = false;
    this.dimensions = [];
    this.teamGroups = [];
  }

  ngOnInit(): void {
    this.isSaving = false;
    this.image = {};
    if (this.team.image?.id) {
      this.imageService.find(this.team.image.id).subscribe(
        (res: HttpResponse<IImage>) => (this.image = res.body),
        (res: HttpErrorResponse) => this.onError(res.message)
      );
    }
    this.dimensionService
      .query({ page: 0, size: DIMENSIONS_PER_PAGE })
      .pipe(
        filter((mayBeOk: HttpResponse<IDimension[]>) => mayBeOk.ok),
        map((response: HttpResponse<IDimension[]>) => response.body)
      )
      .subscribe(
        (res: IDimension[] | null) => (this.dimensions = res ? res : []),
        (res: HttpErrorResponse) => this.onError(res.message)
      );

    this.teamGroupService
      .query({ page: 0, size: TEAM_GROUPS_PER_PAGE })
      .pipe(map((res: HttpResponse<ITeamGroup[]>) => res.body ?? []))
      .subscribe((teamGroups: ITeamGroup[]) => (this.teamGroups = teamGroups));
  }

  cancel(): void {
    this.activeModal.dismiss((this.editMode ? 'Edit' : 'Create') + ' has been cancelled');
  }

  save(): void {
    this.isSaving = true;

    this.team.expirationDate = dayjs(this.expirationDateString, DATE_TIME_FORMAT);

    if (this.image) {
      const title: string | undefined = this.team.shortTitle ? this.team.shortTitle : this.team.title;

      this.image.title = (title ? title : '') + '-logo-' + String(Date.now());

      let imageResult: Observable<HttpResponse<IImage>>;
      if (this.image.id !== undefined) {
        imageResult = this.imageService.update(this.image);
      } else {
        imageResult = this.imageService.create(this.image);
      }
      imageResult.subscribe(
        (imgRes: HttpResponse<IImage>) => {
          this.team.image = imgRes.body;

          if (this.team.id !== undefined) {
            this.subscribeToSaveResponse(this.teamService.update(this.team));
          } else {
            this.subscribeToSaveResponse(this.teamService.create(this.team));
          }
        },
        (res: HttpErrorResponse) => {
          this.isSaving = false;
        }
      );
    }
  }

  byteSize(field: string): string {
    return this.dataUtils.byteSize(field);
  }

  // TODO: #31 Maybe this must be adapted.
  //       The code is copied from src/main/webapp/app/entities/image/update/image-update.component.ts
  setFileData(event: Event, entity: IImage | null, field: string, isImage: boolean): void {
    this.dataUtils.loadFileToForm(event, this.editForm, field, isImage).subscribe({
      next: () => {
        if (entity) {
          this.setImageFromEditForm(entity);
        }
      },
      error: (err: FileLoadError) =>
        this.eventManager.broadcast(new EventWithContent<AlertError>('teamDojoApp.error', { ...err, key: 'error.file.' + err.key })),
    });
  }

  // TODO: #31 Maybe this must be adapted.
  //       The code is copied from src/main/webapp/app/entities/image/update/image-update.component.ts
  clearInputImage(field: string, fieldContentType: string, image: IImage | null): void {
    this.editForm.patchValue({
      [field]: null,
      [fieldContentType]: null,
    });

    if (image) {
      this.setImageFromEditForm(image);
    }

    if (this.uploadedImage) {
      this.uploadedImage.nativeElement.value = null;
    }
  }

  trackDimensionById(index: number, item: IDimension): number | undefined {
    return item.id;
  }

  getSelected(selectedVals: IDimension[] | null | undefined, option: IDimension): IDimension {
    if (selectedVals) {
      for (let i = 0; i < selectedVals.length; i++) {
        if (option.id === selectedVals[i].id) {
          return selectedVals[i];
        }
      }
    }
    return option;
  }

  private onError(errorMessage: string): void {
    this.alertService.addAlert({
      type: 'danger',
      message: errorMessage,
    });
  }

  private subscribeToSaveResponse(result: Observable<HttpResponse<ITeam>>): void {
    result.subscribe(
      (res: HttpResponse<ITeam>) => {
        this.isSaving = false;
        this.activeModal.close(res.body);
      },
      (res: HttpErrorResponse) => {
        this.isSaving = false;
      }
    );
  }

  /**
   * small and medium size image needs to be set to null, otherwise the backend will not
   * set them automatically.
   * @param image
   * @private
   */
  private clearMediumAndSmallSizeImage(image: IImage): void {
    image.small = null;
    image.smallContentType = null;
    image.medium = null;
    image.mediumContentType = null;
  }

  private setImageFromEditForm(image: IImage): void {
    image.large = this.editForm.value.large;
    image.largeContentType = this.editForm.value.largeContentType;
    // TODO: Issue #100 this might not be needed
    this.clearMediumAndSmallSizeImage(image);
  }
}
