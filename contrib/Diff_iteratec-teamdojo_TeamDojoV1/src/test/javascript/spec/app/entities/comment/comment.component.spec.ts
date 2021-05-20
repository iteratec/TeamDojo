4a5
> import { ActivatedRoute, Data } from '@angular/router';
21c22,38
<                 providers: []
---
>                 providers: [
>                     {
>                         provide: ActivatedRoute,
>                         useValue: {
>                             data: {
>                                 subscribe: (fn: (value: Data) => void) =>
>                                     fn({
>                                         pagingParams: {
>                                             predicate: 'id',
>                                             reverse: false,
>                                             page: 0
>                                         }
>                                     })
>                             }
>                         }
>                     }
>                 ]
48a66,125
>         });
> 
>         it('should load a page', () => {
>             // GIVEN
>             const headers = new HttpHeaders().append('link', 'link;link');
>             spyOn(service, 'query').and.returnValue(
>                 of(
>                     new HttpResponse({
>                         body: [new Comment(123)],
>                         headers
>                     })
>                 )
>             );
> 
>             // WHEN
>             comp.loadPage(1);
> 
>             // THEN
>             expect(service.query).toHaveBeenCalled();
>             expect(comp.comments[0]).toEqual(jasmine.objectContaining({ id: 123 }));
>         });
> 
>         it('should re-initialize the page', () => {
>             // GIVEN
>             const headers = new HttpHeaders().append('link', 'link;link');
>             spyOn(service, 'query').and.returnValue(
>                 of(
>                     new HttpResponse({
>                         body: [new Comment(123)],
>                         headers
>                     })
>                 )
>             );
> 
>             // WHEN
>             comp.loadPage(1);
>             comp.reset();
> 
>             // THEN
>             expect(comp.page).toEqual(0);
>             expect(service.query).toHaveBeenCalledTimes(2);
>             expect(comp.comments[0]).toEqual(jasmine.objectContaining({ id: 123 }));
>         });
>         it('should calculate the sort attribute for an id', () => {
>             // WHEN
>             const result = comp.sort();
> 
>             // THEN
>             expect(result).toEqual(['id,asc']);
>         });
> 
>         it('should calculate the sort attribute for a non-id attribute', () => {
>             // GIVEN
>             comp.predicate = 'name';
> 
>             // WHEN
>             const result = comp.sort();
> 
>             // THEN
>             expect(result).toEqual(['name,asc', 'id']);
