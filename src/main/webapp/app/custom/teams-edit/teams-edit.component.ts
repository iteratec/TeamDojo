/*import { Component, ElementRef, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';


import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import {ITeam} from "app/entities/team/team.model";
import {IImage} from "app/entities/image/image.model";
import {TeamService} from "app/entities/team/service/team.service";
import {DimensionService} from "app/entities/dimension/service/dimension.service";
import {ImageService} from "app/entities/image/service/image.service";
import {IDimension} from "app/entities/dimension/dimension.model";
import {AlertService} from "app/core/util/alert.service";
import {DataUtils} from "app/core/util/data-util.service";

@Component({
  selector: 'jhi-teams-quickedit',
  templateUrl: './teams-edit.component.html',
  styleUrls: ['teams-edit.scss'],
})
export class TeamsEditComponent implements OnInit {
  team: ITeam;
  isSaving: boolean;
  image: IImage;
  editMode: boolean;
  dimensions: IDimension[];

  constructor(
    private activeModal: NgbActiveModal,
    private jhiAlertService: AlertService,
    private teamService: TeamService,
    private dimensionService: DimensionService,
    private imageService: ImageService,
    private dataUtils: DataUtils,
    private elementRef: ElementRef
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.image = {};
    if (this.team.imageId) {
      this.imageService.find(this.team.imageId).subscribe(
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
        (res: IDimension[]) => (this.dimensions = res),
        (res: HttpErrorResponse) => this.onError(res.message)
      );
  }

  cancel() {
    this.activeModal.dismiss((this.editMode ? 'Edit' : 'Create') + ' has been cancelled');
  }

  save() {
    this.isSaving = true;
    this.image.name = this.team.shortName + '-logo-' + Date.now();

    let imageResult: Observable<HttpResponse<IImage>>;
    if (this.image.id !== undefined) {
      imageResult = this.imageService.update(this.image);
    } else {
      imageResult = this.imageService.create(this.image);
    }
    imageResult.subscribe(
      (imgRes: HttpResponse<IImage>) => {
        this.team.imageId = imgRes.body.id;

        if (this.team.id !== undefined) {
          this.subscribeToSaveResponse(this.teamService.update(this.team));
        } else {
          this.subscribeToSaveResponse(this.teamService.create(this.team));
        }
      },
      (res: HttpErrorResponse) => {
        this.isSaving = false;
        console.log('Image upload failed', res);
      }
    );
  }

  byteSize(field) {
    return this.dataUtils.byteSize(field);
  }

  setFileData(event, entity, field, isImage) {
    this.dataUtils.setFileData(event, entity, field, isImage);
  }

  clearInputImage(field: string, fieldContentType: string, idInput: string) {
    this.dataUtils.clearInputImage(this.image, this.elementRef, field, fieldContentType, idInput);
  }

  private onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }

  trackDimensionById(index: number, item: IDimension) {
    return item.id;
  }

  getSelected(selectedVals: Array<any>, option: any) {
    if (selectedVals) {
      for (let i = 0; i < selectedVals.length; i++) {
        if (option.id === selectedVals[i].id) {
          return selectedVals[i];
        }
      }
    }
    return option;
  }

  private subscribeToSaveResponse(result: Observable<HttpResponse<ITeam>>) {
    result.subscribe(
      (res: HttpResponse<ITeam>) => {
        this.isSaving = false;
        this.activeModal.close(res.body);
      },
      (res: HttpErrorResponse) => {
        this.isSaving = false;
        console.log('Failed to ' + (this.editMode ? 'edit' : 'create'), res);
      }
    );
  }
}
*/
