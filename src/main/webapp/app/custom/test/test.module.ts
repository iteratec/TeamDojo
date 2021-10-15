import { DatePipe } from '@angular/common';
import { ActivatedRoute, Router } from '@angular/router';
import { NgModule, ElementRef, Renderer2 } from '@angular/core';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { NgbActiveModal, NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiLanguageService, JhiDateUtils } from 'ng-jhipster';

import { MockLanguageService, MockLanguageHelper } from './helpers/mock-language.service';
import { JhiLanguageHelper, AccountService, LoginModalService, JhiTrackerService } from 'app/core';
import { MockAccountService } from './helpers/mock-account.service';
import { MockActivatedRoute, MockRouter } from './helpers/mock-route.service';
import { MockActiveModal } from './helpers/mock-active-modal.service';
import { MockEventManager } from './helpers/mock-event-manager.service';
import { AlertService } from 'app/core/util/alert.service';
import { EventManager } from 'app/core/util/event-manager.service';
import { DataUtils } from 'app/core/util/data-util.service';
import { ParseLinks } from 'app/core/util/parse-links.service';

@NgModule({
  providers: [
    DatePipe,
    DataUtils,
    JhiDateUtils,
    ParseLinks,
    {
      provide: JhiLanguageService,
      useClass: MockLanguageService,
    },
    {
      provide: JhiLanguageHelper,
      useClass: MockLanguageHelper,
    },
    {
      provide: JhiTrackerService,
      useValue: null,
    },
    {
      provide: EventManager,
      useClass: MockEventManager,
    },
    {
      provide: NgbActiveModal,
      useClass: MockActiveModal,
    },
    {
      provide: ActivatedRoute,
      useValue: new MockActivatedRoute({ id: 123 }),
    },
    {
      provide: Router,
      useClass: MockRouter,
    },
    {
      provide: AccountService,
      useClass: MockAccountService,
    },
    {
      provide: LoginModalService,
      useValue: null,
    },
    {
      provide: ElementRef,
      useValue: null,
    },
    {
      provide: Renderer2,
      useValue: null,
    },
    {
      provide: AlertService,
      useValue: null,
    },
    {
      provide: NgbModal,
      useValue: null,
    },
  ],
  imports: [HttpClientTestingModule],
})
export class TeamdojoTestModule {}
