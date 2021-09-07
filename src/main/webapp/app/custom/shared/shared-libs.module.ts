import { NgModule } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { InfiniteScrollModule } from 'ngx-infinite-scroll';
import { CookieModule } from 'ngx-cookie';
import { FontAwesomeModule } from '@fortawesome/angular-fontawesome';

@NgModule({
  imports: [NgbModule, InfiniteScrollModule, CookieModule.forRoot(), FontAwesomeModule],
  exports: [FormsModule, CommonModule, NgbModule, InfiniteScrollModule, FontAwesomeModule],
})
export class TeamdojoSharedLibsModule {
  static forRoot(): { ngModule: typeof TeamdojoSharedLibsModule } {
    return {
      ngModule: TeamdojoSharedLibsModule,
    };
  }
}
