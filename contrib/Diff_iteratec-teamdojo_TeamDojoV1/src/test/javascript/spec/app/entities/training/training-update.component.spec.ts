4c4
< import { of } from 'rxjs';
---
> import { Observable, of } from 'rxjs';
31,57c31,63
<             it('Should call update service on save for existing entity', fakeAsync(() => {
<                 // GIVEN
<                 const entity = new Training(123);
<                 spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
<                 comp.training = entity;
<                 // WHEN
<                 comp.save();
<                 tick(); // simulate async
< 
<                 // THEN
<                 expect(service.update).toHaveBeenCalledWith(entity);
<                 expect(comp.isSaving).toEqual(false);
<             }));
< 
<             it('Should call create service on save for new entity', fakeAsync(() => {
<                 // GIVEN
<                 const entity = new Training();
<                 spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
<                 comp.training = entity;
<                 // WHEN
<                 comp.save();
<                 tick(); // simulate async
< 
<                 // THEN
<                 expect(service.create).toHaveBeenCalledWith(entity);
<                 expect(comp.isSaving).toEqual(false);
<             }));
---
>             it(
>                 'Should call update service on save for existing entity',
>                 fakeAsync(() => {
>                     // GIVEN
>                     const entity = new Training(123);
>                     spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
>                     comp.training = entity;
>                     // WHEN
>                     comp.save();
>                     tick(); // simulate async
> 
>                     // THEN
>                     expect(service.update).toHaveBeenCalledWith(entity);
>                     expect(comp.isSaving).toEqual(false);
>                 })
>             );
> 
>             it(
>                 'Should call create service on save for new entity',
>                 fakeAsync(() => {
>                     // GIVEN
>                     const entity = new Training();
>                     spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
>                     comp.training = entity;
>                     // WHEN
>                     comp.save();
>                     tick(); // simulate async
> 
>                     // THEN
>                     expect(service.create).toHaveBeenCalledWith(entity);
>                     expect(comp.isSaving).toEqual(false);
>                 })
>             );
