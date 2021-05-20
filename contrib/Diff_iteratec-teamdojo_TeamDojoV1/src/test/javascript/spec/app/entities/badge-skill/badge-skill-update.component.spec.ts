31,38c31,40
<             it('Should call update service on save for existing entity', fakeAsync(() => {
<                 // GIVEN
<                 const entity = new BadgeSkill(123);
<                 spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
<                 comp.badgeSkill = entity;
<                 // WHEN
<                 comp.save();
<                 tick(); // simulate async
---
>             it(
>                 'Should call update service on save for existing entity',
>                 fakeAsync(() => {
>                     // GIVEN
>                     const entity = new BadgeSkill(123);
>                     spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
>                     comp.badgeSkill = entity;
>                     // WHEN
>                     comp.save();
>                     tick(); // simulate async
40,43c42,46
<                 // THEN
<                 expect(service.update).toHaveBeenCalledWith(entity);
<                 expect(comp.isSaving).toEqual(false);
<             }));
---
>                     // THEN
>                     expect(service.update).toHaveBeenCalledWith(entity);
>                     expect(comp.isSaving).toEqual(false);
>                 })
>             );
45,52c48,57
<             it('Should call create service on save for new entity', fakeAsync(() => {
<                 // GIVEN
<                 const entity = new BadgeSkill();
<                 spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
<                 comp.badgeSkill = entity;
<                 // WHEN
<                 comp.save();
<                 tick(); // simulate async
---
>             it(
>                 'Should call create service on save for new entity',
>                 fakeAsync(() => {
>                     // GIVEN
>                     const entity = new BadgeSkill();
>                     spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
>                     comp.badgeSkill = entity;
>                     // WHEN
>                     comp.save();
>                     tick(); // simulate async
54,57c59,63
<                 // THEN
<                 expect(service.create).toHaveBeenCalledWith(entity);
<                 expect(comp.isSaving).toEqual(false);
<             }));
---
>                     // THEN
>                     expect(service.create).toHaveBeenCalledWith(entity);
>                     expect(comp.isSaving).toEqual(false);
>                 })
>             );