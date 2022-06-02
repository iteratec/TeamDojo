import { NgModule, LOCALE_ID } from '@angular/core';
import { registerLocaleData } from '@angular/common';
import { HttpClientModule } from '@angular/common/http';
import locale from '@angular/common/locales/en';
import { BrowserModule, Title } from '@angular/platform-browser';
import { ServiceWorkerModule } from '@angular/service-worker';
import { FaIconLibrary } from '@fortawesome/angular-fontawesome';
import { NgxWebstorageModule } from 'ngx-webstorage';
import dayjs from 'dayjs/esm';
import { NgbDateAdapter, NgbDatepickerConfig } from '@ng-bootstrap/ng-bootstrap';

import { ApplicationConfigService } from 'app/core/config/application-config.service';
import './config/dayjs';
import { SharedModule } from 'app/shared/shared.module';
import { TranslationModule } from 'app/shared/language/translation.module';
import { AppRoutingModule } from './app-routing.module';
// jhipster-needle-angular-add-module-import JHipster will add new module here
import { NgbDateDayjsAdapter } from './config/datepicker-adapter';
import { fontAwesomeIcons } from './config/font-awesome-icons';
import { httpInterceptorProviders } from 'app/core/interceptor/index';
import { MainComponent } from './layouts/main/main.component';
import { NavbarComponent } from './layouts/navbar/navbar.component';
import { PageRibbonComponent } from './layouts/profiles/page-ribbon.component';
import { ActiveMenuDirective } from './layouts/navbar/active-menu.directive';
import { ErrorComponent } from './layouts/error/error.component';

// ### Modification-Start ###
import { HomeModule } from './custom/home/home.module';
import { FooterComponent } from './custom/layouts/footer/footer.component';
import { TeamsModule } from './custom/teams/teams.module';
import { OverviewModule } from 'app/custom/overview/overview.module';
import { CustomSharedModule } from 'app/custom/shared/custom-shared.module';
import { MarkdownModule, MarkedOptions } from 'ngx-markdown';
import { FeedbackModule } from 'app/custom/feedback/feedback.module';
import { NavbarExtensionComponent } from 'app/custom/layouts/navbar/extension/navbar-extension.component';
import { customFontAwesomeIcons } from 'app/custom/config/font-awesome-icons';
import { BreadcrumbTrailComponent } from './custom/layouts/navbar/breadcrumb-trail/breadcrumb-trail.component';
// ###  Modification-End  ###

@NgModule({
  imports: [
    // ### Modification-Start ###
    TeamsModule,
    OverviewModule,
    CustomSharedModule,
    MarkdownModule.forRoot({
      markedOptions: {
        provide: MarkedOptions,
        useValue: {
          breaks: true,
          sanitize: true,
        },
      },
    }),
    FeedbackModule,
    // ### Modification-End ###
    BrowserModule,
    SharedModule,
    HomeModule,
    // jhipster-needle-angular-add-module JHipster will add new module here
    AppRoutingModule,
    // Set this to true to enable service worker (PWA)
    ServiceWorkerModule.register('ngsw-worker.js', { enabled: false }),
    HttpClientModule,
    NgxWebstorageModule.forRoot({ prefix: 'jhi', separator: '-', caseSensitive: true }),
    TranslationModule,
  ],
  providers: [
    Title,
    { provide: LOCALE_ID, useValue: 'en' },
    { provide: NgbDateAdapter, useClass: NgbDateDayjsAdapter },
    httpInterceptorProviders,
  ],
  declarations: [
    MainComponent,
    NavbarComponent,
    ErrorComponent,
    PageRibbonComponent,
    ActiveMenuDirective,
    FooterComponent,
    // ### Modification-Start ###
    NavbarExtensionComponent,
    BreadcrumbTrailComponent,
    // ### Modification-End ###
  ],
  bootstrap: [MainComponent],
})
export class AppModule {
  constructor(applicationConfigService: ApplicationConfigService, iconLibrary: FaIconLibrary, dpConfig: NgbDatepickerConfig) {
    applicationConfigService.setEndpointPrefix(SERVER_API_URL);
    registerLocaleData(locale);
    iconLibrary.addIcons(...fontAwesomeIcons);
    // ### Modification-Start ###
    iconLibrary.addIcons(...customFontAwesomeIcons);
    // ### Modification-End ###
    dpConfig.minDate = { year: dayjs().subtract(100, 'year').year(), month: 1, day: 1 };
  }
}
