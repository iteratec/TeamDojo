296c296
<                 spyOn(service, 'transformHealthData').and.returnValue({ data: 'test' });
---
>                 spyOn(service, 'transformHealthData').and.returnValue(of({ data: 'test' }));
304c304
<                 expect(comp.healthData).toEqual({ data: 'test' });
---
>                 expect(comp.healthData.value).toEqual({ data: 'test' });
309c309
<                 spyOn(service, 'transformHealthData').and.returnValue({ health: 'down' });
---
>                 spyOn(service, 'transformHealthData').and.returnValue(of({ health: 'down' }));
317c317
<                 expect(comp.healthData).toEqual({ health: 'down' });
---
>                 expect(comp.healthData.value).toEqual({ health: 'down' });
