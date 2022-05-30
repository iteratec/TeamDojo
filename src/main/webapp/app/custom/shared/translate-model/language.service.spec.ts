import { TestBed } from '@angular/core/testing';
import { TranslateModule, TranslateService } from '@ngx-translate/core';
import { Language, LanguageService } from './language.service';

describe('LanguageService', () => {
  let translateService: TranslateService;
  let sut: LanguageService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [TranslateModule.forRoot()],
      providers: [TranslateService, LanguageService],
    });
    translateService = TestBed.inject(TranslateService);
    sut = TestBed.inject(LanguageService);
  });

  it('should return German for de', () => {
    translateService.currentLang = 'de';
    expect(sut.determineSelectedLanguage()).toBe(Language.DE);
  });

  it('should return English for en', () => {
    translateService.currentLang = 'en';
    expect(sut.determineSelectedLanguage()).toBe(Language.EN);
  });

  it('should return unknown for fr', () => {
    translateService.currentLang = 'fr';
    expect(sut.determineSelectedLanguage()).toBe(Language.UNKNOWN);
  });
});
