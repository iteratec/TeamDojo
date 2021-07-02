import { Component, OnInit } from '@angular/core';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IDimension } from '../dimension.model';

import { ITEMS_PER_PAGE } from 'app/config/pagination.constants';
import { DimensionService } from '../service/dimension.service';
import { DimensionDeleteDialogComponent } from '../delete/dimension-delete-dialog.component';
import { ParseLinks } from 'app/core/util/parse-links.service';

@Component({
  selector: 'jhi-dimension',
  templateUrl: './dimension.component.html',
})
export class DimensionComponent implements OnInit {
  dimensions: IDimension[];
  isLoading = false;
  itemsPerPage: number;
  links: { [key: string]: number };
  page: number;
  predicate: string;
  ascending: boolean;

  constructor(protected dimensionService: DimensionService, protected modalService: NgbModal, protected parseLinks: ParseLinks) {
    this.dimensions = [];
    this.itemsPerPage = ITEMS_PER_PAGE;
    this.page = 0;
    this.links = {
      last: 0,
    };
    this.predicate = 'id';
    this.ascending = true;
  }

  loadAll(): void {
    this.isLoading = true;

    this.dimensionService
      .query({
        page: this.page,
        size: this.itemsPerPage,
        sort: this.sort(),
      })
      .subscribe(
        (res: HttpResponse<IDimension[]>) => {
          this.isLoading = false;
          this.paginateDimensions(res.body, res.headers);
        },
        () => {
          this.isLoading = false;
        }
      );
  }

  reset(): void {
    this.page = 0;
    this.dimensions = [];
    this.loadAll();
  }

  loadPage(page: number): void {
    this.page = page;
    this.loadAll();
  }

  ngOnInit(): void {
    this.loadAll();
  }

  trackId(index: number, item: IDimension): number {
    return item.id!;
  }

  delete(dimension: IDimension): void {
    const modalRef = this.modalService.open(DimensionDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.dimension = dimension;
    // unsubscribe not needed because closed completes on modal close
    modalRef.closed.subscribe(reason => {
      if (reason === 'deleted') {
        this.reset();
      }
    });
  }

  protected sort(): string[] {
    const result = [this.predicate + ',' + (this.ascending ? 'asc' : 'desc')];
    if (this.predicate !== 'id') {
      result.push('id');
    }
    return result;
  }

  protected paginateDimensions(data: IDimension[] | null, headers: HttpHeaders): void {
    this.links = this.parseLinks.parse(headers.get('link') ?? '');
    if (data) {
      for (const d of data) {
        this.dimensions.push(d);
      }
    }
  }
}
