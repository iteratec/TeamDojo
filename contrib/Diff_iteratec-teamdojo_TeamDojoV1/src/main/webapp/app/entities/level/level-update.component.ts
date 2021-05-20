9,10d8
< import { IDimension } from 'app/shared/model/dimension.model';
< import { DimensionService } from 'app/entities/dimension';
12a11,12
> import { IDimension } from 'app/shared/model/dimension.model';
> import { DimensionService } from 'app/entities/dimension';
22,23d21
<     dimensions: IDimension[];
< 
27a26,27
>     dimensions: IDimension[];
> 
31d30
<         protected dimensionService: DimensionService,
32a32
>         protected dimensionService: DimensionService,
41,47d40
<         this.dimensionService
<             .query()
<             .pipe(
<                 filter((mayBeOk: HttpResponse<IDimension[]>) => mayBeOk.ok),
<                 map((response: HttpResponse<IDimension[]>) => response.body)
<             )
<             .subscribe((res: IDimension[]) => (this.dimensions = res), (res: HttpErrorResponse) => this.onError(res.message));
79a73,79
>         this.dimensionService
>             .query()
>             .pipe(
>                 filter((mayBeOk: HttpResponse<IDimension[]>) => mayBeOk.ok),
>                 map((response: HttpResponse<IDimension[]>) => response.body)
>             )
>             .subscribe((res: IDimension[]) => (this.dimensions = res), (res: HttpErrorResponse) => this.onError(res.message));
112c112
<     trackDimensionById(index: number, item: IDimension) {
---
>     trackLevelById(index: number, item: ILevel) {
116c116
<     trackLevelById(index: number, item: ILevel) {
---
>     trackImageById(index: number, item: IImage) {
120c120
<     trackImageById(index: number, item: IImage) {
---
>     trackDimensionById(index: number, item: IDimension) {
