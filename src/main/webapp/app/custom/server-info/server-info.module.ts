import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';

import { TeamdojoSharedModule } from 'app/shared/index';
import { ServerInfoService } from './server-info.service';

@NgModule({
  imports: [TeamdojoSharedModule],
  declarations: [],
  entryComponents: [],
  providers: [ServerInfoService],
  schemas: [CUSTOM_ELEMENTS_SCHEMA],
})
export class ServerInfoModule {}
