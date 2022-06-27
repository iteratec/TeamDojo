/*
 SPDX-FileCopyrightText: the TeamDojo authors
 SPDX-License-Identifier: Apache-2.0
 */
import { Injectable } from '@angular/core';
import { TranslateService } from '@ngx-translate/core';

/**
 * Enumerates all available languages.
 */
export enum Language {
  /**
   * Used as fallback if something went wrong to avoid null.
   */
  UNKNOWN = 'UNKNOWN',
  /**
   * German.
   */
  DE = 'DE',
  /**
   * English.
   */
  EN = 'EN',
}

/**
 * This service determines the language a user wants to see in the frontend.
 *
 * This service is a abstraction to decouple our model translation code from the technical
 * service from the framework. This service also ensures that only enumerated languages
 * will be returned.
 */
@Injectable({
  providedIn: 'root',
})
export class LanguageService {
  constructor(private translationService: TranslateService) {}

  determineSelectedLanguage(): Language {
    // As seen in the debugger the translation service uses "de", "en" and so on.
    switch (this.translationService.currentLang.toUpperCase()) {
      case Language.DE:
        return Language.DE;
      case Language.EN:
        return Language.EN;
      default:
        return Language.UNKNOWN;
    }
  }
}
