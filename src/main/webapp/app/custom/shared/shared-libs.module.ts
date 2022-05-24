import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { InfiniteScrollModule } from 'ngx-infinite-scroll';
import { FontAwesomeModule } from '@fortawesome/angular-fontawesome';
import { TranslateModule } from '@ngx-translate/core';

@NgModule({
  imports: [NgbModule, InfiniteScrollModule, FontAwesomeModule],
  exports: [FontAwesomeModule, FormsModule, CommonModule, NgbModule, InfiniteScrollModule, ReactiveFormsModule, TranslateModule],
})
export class TeamdojoSharedLibsModule {
  static forRoot(): { ngModule: typeof TeamdojoSharedLibsModule } {
    return {
      ngModule: TeamdojoSharedLibsModule,
    };
  }
}
