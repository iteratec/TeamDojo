import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { Subject } from 'rxjs';
import { debounceTime } from 'rxjs/operators';

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
  selector: 'tr[jhi-table-filter]', // tslint:disable-line component-selector
  templateUrl: './table-filter.component.html',
  styleUrls: ['./table-filter.scss'],
})
export class TableFilterComponent implements OnInit {
  @Input() fields: TableField[];

  @Input() entityName: string;

  @Output() onFilterChanged = new EventEmitter<FilterQuery[]>();

  filterChanged: Subject<FilterQuery[]> = new Subject();

  filterInputs: { [k: string]: string } = {};

  private filterOperators: { [k: string]: string } = {};

  constructor() {
    this.filterChanged.pipe(debounceTime(500)).subscribe(query => this.onFilterChanged.emit(query));
  }

  ngOnInit(): void {
    this.fields.forEach(f => (this.filterOperators[f.name] = f.operator));
    this.loadFilters();
    this.emitFilters();
  }

  updateFilters() {
    this.saveFilters();
    this.emitFilters();
  }

  emitFilters() {
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
    this.filterChanged.next(query);
  }

  loadFilters() {
    let filters = {};
    try {
      filters = JSON.parse(localStorage.getItem(`TABLE_FILTER_${this.entityName}`)) || {};
    } catch (e) {
      console.log(`Filters for entity ${this.entityName} could not be parsed.`);
    }
    Object.keys(filters).forEach(field => (this.filterInputs[field] = filters[field]));
  }

  saveFilters() {
    localStorage.setItem(`TABLE_FILTER_${this.entityName}`, JSON.stringify(this.filterInputs));
  }

  clearField(field: TableField) {
    this.filterInputs[field.name] = '';
    this.updateFilters();
  }
}
