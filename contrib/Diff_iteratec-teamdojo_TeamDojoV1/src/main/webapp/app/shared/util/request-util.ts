8,10c8
<                 if (req[key] !== null && typeof req[key] !== 'undefined') {
<                     options = options.set(key, req[key]);
<                 }
---
>                 options = options.set(key, req[key]);
15,17c13
<                 if (val !== null && typeof val !== 'undefined') {
<                     options = options.append('sort', val);
<                 }
---
>                 options = options.append('sort', val);
