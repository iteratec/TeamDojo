import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';

import { SharedModule } from 'app/shared/shared.module';
import { ServerInfoService } from './server-info.service';

@NgModule({
  imports: [SharedModule],
  declarations: [],
  entryComponents: [],
  providers: [ServerInfoService],
  schemas: [CUSTOM_ELEMENTS_SCHEMA],
})
export class ServerInfoModule {}
