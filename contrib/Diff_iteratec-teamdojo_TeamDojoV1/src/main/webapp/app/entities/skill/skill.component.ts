3a4
> import { filter, map } from 'rxjs/operators';
11d11
< import { FilterQuery } from 'app/shared/table-filter/table-filter.component';
28,29d27
<     private filters: FilterQuery[] = [];
< 
48,50d45
<         const query = {};
<         this.filters.forEach(filter => (query[`${filter.fieldName}.${filter.operator}`] = filter.query));
< 
53d47
<                 ...query,
64,68d57
<     applyFilter(query: FilterQuery[]) {
<         this.filters = query;
<         this.reset();
<     }
< 
116,117c105,106
<     private onError(errorMessage: string) {
<         console.error(errorMessage);
---
>     protected onError(errorMessage: string) {
>         this.jhiAlertService.error(errorMessage, null, null);
