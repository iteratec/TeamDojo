1c1
< import { CUSTOM_ELEMENTS_SCHEMA, NgModule } from '@angular/core';
---
> import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
5,9c5
< import { HasAnyAuthorityDirective, JhiLoginModalComponent, TeamdojoSharedCommonModule, TeamdojoSharedLibsModule } from './';
< import { BackgroundComponent } from 'app/shared/background/background.component';
< import { TeamsStatusComponent } from 'app/teams/teams-status.component';
< import { TableFilterComponent } from 'app/shared/table-filter/table-filter.component';
< import { TrainingsAddComponent } from 'app/shared/trainings/trainings-add.component';
---
> import { TeamdojoSharedLibsModule, TeamdojoSharedCommonModule, JhiLoginModalComponent, HasAnyAuthorityDirective } from './';
13,20c9
<     declarations: [
<         JhiLoginModalComponent,
<         HasAnyAuthorityDirective,
<         BackgroundComponent,
<         TeamsStatusComponent,
<         TableFilterComponent,
<         TrainingsAddComponent
<     ],
---
>     declarations: [JhiLoginModalComponent, HasAnyAuthorityDirective],
22,30c11,12
<     entryComponents: [JhiLoginModalComponent, TrainingsAddComponent],
<     exports: [
<         TeamdojoSharedCommonModule,
<         JhiLoginModalComponent,
<         HasAnyAuthorityDirective,
<         BackgroundComponent,
<         TeamsStatusComponent,
<         TableFilterComponent
<     ],
---
>     entryComponents: [JhiLoginModalComponent],
>     exports: [TeamdojoSharedCommonModule, JhiLoginModalComponent, HasAnyAuthorityDirective],
