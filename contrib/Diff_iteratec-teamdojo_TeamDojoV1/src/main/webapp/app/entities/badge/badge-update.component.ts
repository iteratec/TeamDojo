11,12d10
< import { IDimension } from 'app/shared/model/dimension.model';
< import { DimensionService } from 'app/entities/dimension';
14a13,14
> import { IDimension } from 'app/shared/model/dimension.model';
> import { DimensionService } from 'app/entities/dimension';
24,25d23
<     dimensions: IDimension[];
< 
26a25,26
> 
>     dimensions: IDimension[];
32d31
<         protected dimensionService: DimensionService,
33a33
>         protected dimensionService: DimensionService,
43,49d42
<         this.dimensionService
<             .query()
<             .pipe(
<                 filter((mayBeOk: HttpResponse<IDimension[]>) => mayBeOk.ok),
<                 map((response: HttpResponse<IDimension[]>) => response.body)
<             )
<             .subscribe((res: IDimension[]) => (this.dimensions = res), (res: HttpErrorResponse) => this.onError(res.message));
56a50,56
>         this.dimensionService
>             .query()
>             .pipe(
>                 filter((mayBeOk: HttpResponse<IDimension[]>) => mayBeOk.ok),
>                 map((response: HttpResponse<IDimension[]>) => response.body)
>             )
>             .subscribe((res: IDimension[]) => (this.dimensions = res), (res: HttpErrorResponse) => this.onError(res.message));
90c90
<     trackDimensionById(index: number, item: IDimension) {
---
>     trackImageById(index: number, item: IImage) {
94c94
<     trackImageById(index: number, item: IImage) {
---
>     trackDimensionById(index: number, item: IDimension) {
