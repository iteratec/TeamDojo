5d4
< import { LocalStorageService } from 'ngx-webstorage';
21,22c20
<                 declarations: [OrganizationUpdateComponent],
<                 providers: [LocalStorageService]
---
>                 declarations: [OrganizationUpdateComponent]
33,59c31,63
<             it('Should call update service on save for existing entity', fakeAsync(() => {
<                 // GIVEN
<                 const entity = new Organization(123);
<                 spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
<                 comp.organization = entity;
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
<                 const entity = new Organization();
<                 spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
<                 comp.organization = entity;
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
>                     const entity = new Organization(123);
>                     spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
>                     comp.organization = entity;
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
>                     const entity = new Organization();
>                     spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
>                     comp.organization = entity;
>                     // WHEN
>                     comp.save();
>                     tick(); // simulate async
> 
>                     // THEN
>                     expect(service.create).toHaveBeenCalledWith(entity);
>                     expect(comp.isSaving).toEqual(false);
>                 })
>             );
