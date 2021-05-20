3a4
> import { filter, map } from 'rxjs/operators';
11d11
< import { FilterQuery } from 'app/shared/table-filter/table-filter.component';
28,30d27
<     private filters: FilterQuery[] = [];
<     filteredLevelSkills: ILevelSkill[] = [];
< 
50a48,49
>                 page: this.page,
>                 size: this.itemsPerPage,
54,57c53
<                 (res: HttpResponse<ILevelSkill[]>) => {
<                     this.paginateLevelSkills(res.body, res.headers);
<                     this.applyFilter();
<                 },
---
>                 (res: HttpResponse<ILevelSkill[]>) => this.paginateLevelSkills(res.body, res.headers),
62,81d57
<     applyFilter(query: FilterQuery[] = this.filters) {
<         this.filters = query;
< 
<         this.filteredLevelSkills = this.levelSkills;
<         for (const filter of this.filters) {
<             this.filteredLevelSkills = this.filteredLevelSkills.filter(ls => {
<                 const fieldVal = (ls[filter.fieldName] + '').toLowerCase().trim();
<                 const queryVal = filter.query.toLowerCase().trim();
<                 switch (filter.operator) {
<                     case 'contains':
<                         return fieldVal.includes(queryVal);
<                     case 'equals':
<                         return fieldVal === queryVal;
<                     default:
<                         return false;
<                 }
<             });
<         }
<     }
< 
121c97
<     private paginateLevelSkills(data: ILevelSkill[], headers: HttpHeaders) {
---
>     protected paginateLevelSkills(data: ILevelSkill[], headers: HttpHeaders) {
129,130c105,106
<     private onError(errorMessage: string) {
<         console.error(errorMessage);
---
>     protected onError(errorMessage: string) {
>         this.jhiAlertService.error(errorMessage, null, null);
