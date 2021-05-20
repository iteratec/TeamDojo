11,12d10
< import { TrainingService } from 'app/entities/training';
< 
22,23c20
<                 declarations: [SkillUpdateComponent],
<                 providers: [TrainingService, SkillService]
---
>                 declarations: [SkillUpdateComponent]
34,60c31,63
<             it('Should call update service on save for existing entity', fakeAsync(() => {
<                 // GIVEN
<                 const entity = new Skill(123);
<                 spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
<                 comp.skill = entity;
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
<                 const entity = new Skill();
<                 spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
<                 comp.skill = entity;
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
>                     const entity = new Skill(123);
>                     spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
>                     comp.skill = entity;
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
>                     const entity = new Skill();
>                     spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
>                     comp.skill = entity;
>                     // WHEN
>                     comp.save();
>                     tick(); // simulate async
> 
>                     // THEN
>                     expect(service.create).toHaveBeenCalledWith(entity);
>                     expect(comp.isSaving).toEqual(false);
>                 })
>             );
