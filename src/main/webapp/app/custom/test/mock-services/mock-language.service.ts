/*
 SPDX-FileCopyrightText: the TeamDojo authors
 SPDX-License-Identifier: Apache-2.0
 */
import { SpyObject } from './spyobject';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core/language/language.helper';
import Spy = jasmine.Spy;

export class MockLanguageService extends SpyObject {
  getCurrentSpy: Spy;
  fakeResponse: any;

  constructor() {
    super(JhiLanguageService);

    this.fakeResponse = 'en';
    this.getCurrentSpy = this.spy('getCurrent').andReturn(Promise.resolve(this.fakeResponse));
  }

  init(): void {
    // do nothing
  }

  changeLanguage(languageKey: string): void {
    // do nothing
  }

  setLocations(locations: string[]): void {
    // do nothing
  }

  addLocation(location: string): void {
    // do nothing
  }

  reload(): void {
    // do nothing
  }
}

export class MockLanguageHelper extends SpyObject {
  getAllSpy: Spy;

  constructor() {
    super(JhiLanguageHelper);

    this.getAllSpy = this.spy('getAll').andReturn(Promise.resolve(['en', 'fr']));
  }
}
