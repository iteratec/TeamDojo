import { Injectable } from '@angular/core';

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
 */
@Injectable({
  providedIn: 'root',
})
export class LanguageService {
  private current: Language = Language.EN;

  determineSelectedLanguage(): Language {
    // #8 TODO: #8 Implement logic here: Determine the language the user chose in the UI.
    return this.current;
  }

  storeCurrent(language: Language): void {
    this.current = language;
  }
}
