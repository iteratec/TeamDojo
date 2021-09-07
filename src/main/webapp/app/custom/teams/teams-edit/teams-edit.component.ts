import { Component, ElementRef, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { ITeam, Team } from 'app/entities/team/team.model';
import { IImage } from 'app/entities/image/image.model';
import { IDimension } from 'app/entities/dimension/dimension.model';
import { AlertService } from 'app/core/util/alert.service';
import { TeamService } from 'app/entities/team/service/team.service';
import { DimensionService } from 'app/entities/dimension/service/dimension.service';
import { ImageService } from 'app/entities/image/service/image.service';
import { DataUtils } from 'app/core/util/data-util.service';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

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

  constructor(
    private activeModal: NgbActiveModal,
    private alertService: AlertService,
    private teamService: TeamService,
    private dimensionService: DimensionService,
    private imageService: ImageService,
    private dataUtils: DataUtils,
    private elementRef: ElementRef
  ) {
    this.team = new Team();
    this.isSaving = false;
    this.image = null;
    this.editMode = false;
    this.dimensions = [];
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
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IDimension[]>) => mayBeOk.ok),
        map((response: HttpResponse<IDimension[]>) => response.body)
      )
      .subscribe(
        (res: IDimension[] | null) => (this.dimensions = res ? res : []),
        (res: HttpErrorResponse) => this.onError(res.message)
      );
  }

  cancel(): void {
    this.activeModal.dismiss((this.editMode ? 'Edit' : 'Create') + ' has been cancelled');
  }

  save(): void {
    this.isSaving = true;
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
          if (this.team.image) {
            this.team.image.id = imgRes.body?.id;
          }

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

  /*
  @Fixme: Github Issue #31
  setFileData(event: Event, entity : IImage, field : string, isImage : boolean): void {
    this.dataUtils.setFileData(event, entity, field, isImage);

  }
  @Fixme: Github Issue #31
  clearInputImage(field: string, fieldContentType: string, idInput: string) {
      this.dataUtils.clearInputImage(this.image, this.elementRef, field, fieldContentType, idInput);
  }
*/

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
}