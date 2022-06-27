/*
 SPDX-FileCopyrightText: the TeamDojo authors
 SPDX-License-Identifier: Apache-2.0
 */
import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { Subject } from 'rxjs';
import { debounceTime } from 'rxjs/operators';
import { AlertService } from 'app/core/util/alert.service';

export interface TableField {
  // name of the field
  name: string;
  // if filter for the field be displayed
  filter: boolean;
  // operator applied against the field ('contains', 'equals')
  operator?: string;
}

export interface FilterQuery {
  fieldName: string;
  query: string;
  operator: string;
}

@Component({
  selector: 'jhi-tr-table-filter', // tslint:disable-line component-selector
  templateUrl: './table-filter.component.html',
  styleUrls: ['./table-filter.scss'],
})
export class TableFilterComponent implements OnInit {
  @Input() fields?: TableField[];

  @Input() entityName?: string;

  @Output() filterChanged = new EventEmitter<FilterQuery[]>();

  _filterChanged: Subject<FilterQuery[]> = new Subject();

  filterInputs: { [k: string]: string } = {};

  private filterOperators: { [k: string]: string } = {};

  constructor(private alertService: AlertService) {
    this._filterChanged.pipe(debounceTime(500)).subscribe(query => this.filterChanged.emit(query));
  }

  ngOnInit(): void {
    this.fields?.forEach(f => (this.filterOperators[f.name] = f.operator ? f.operator : ''));
    this.loadFilters();
    this.emitFilters();
  }

  updateFilters(): void {
    this.saveFilters();
    this.emitFilters();
  }

  emitFilters(): void {
    const query: FilterQuery[] = Object.keys(this.filterInputs)
      .filter(field => this.filterInputs[field] && this.filterInputs[field] !== '')
      .map(
        field =>
          <FilterQuery>{
            fieldName: field,
            query: this.filterInputs[field],
            operator: this.filterOperators[field] || 'contains',
          }
      );
    this._filterChanged.next(query);
  }

  loadFilters(): void {
    let filters: { [index: string]: string } = {};
    try {
      const tableFilter = localStorage.getItem(`TABLE_FILTER_${this.entityName ? this.entityName : ''}`);
      if (tableFilter) {
        filters = JSON.parse(tableFilter) || {};
      }
    } catch (e) {
      this.alertService.addAlert({
        type: 'danger',
        message: `Filters for entity ${this.entityName ? this.entityName : ''} could not be parsed.`,
      });
    }

    Object.keys(filters).forEach(field => (this.filterInputs[field] = filters[field]));
  }

  saveFilters(): void {
    localStorage.setItem(`TABLE_FILTER_${this.entityName ? this.entityName : ''}`, JSON.stringify(this.filterInputs));
  }

  clearField(field: TableField): void {
    this.filterInputs[field.name] = '';
    this.updateFilters();
  }
}
