2d1
< import { IDimension } from 'app/shared/model/dimension.model';
3a3
> import { IDimension } from 'app/shared/model/dimension.model';
14d13
<     participations?: IDimension[];
18,19c17
<     daysUntilExpiration?: number;
<     expired?: boolean;
---
>     participations?: IDimension[];
32d29
<         public participations?: IDimension[],
36,37c33
<         public expired?: boolean,
<         public daysUntilExpiration?: number
---
>         public participations?: IDimension[]
39,40c35,36
<         this.pureTrainingTeam = pureTrainingTeam || true;
<         this.official = official || false;
---
>         this.pureTrainingTeam = this.pureTrainingTeam || false;
>         this.official = this.official || false;
