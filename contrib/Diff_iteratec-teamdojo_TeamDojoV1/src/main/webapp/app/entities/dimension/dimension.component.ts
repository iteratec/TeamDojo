3a4
> import { filter, map } from 'rxjs/operators';
40c41
<         this.predicate = 'name';
---
>         this.predicate = 'id';
96c97
<     private paginateDimensions(data: IDimension[], headers: HttpHeaders) {
---
>     protected paginateDimensions(data: IDimension[], headers: HttpHeaders) {
