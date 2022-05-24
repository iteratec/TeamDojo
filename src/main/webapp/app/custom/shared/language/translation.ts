import { Language, LanguageService } from '../../../language.service';

/**
 * Implementors provide the defined properties localized for models
 *
 * Some user generated content stored in the backend is localized. The current implementation
 * uses additional columns for each language in the colum. For example the title is stored in
 * the columns titleDE and titleEN. Implementors of this interface hide these detail so client
 * code can simply access the property w/o bothering the translation.
 *
 * Translated model interfaces are expected to extend this interface, e.g.:
 *
 * ```
 * export interface IMyModel extends ITranslated {
 *   id?: number;
 *   titleEN?: string;
 *   titleDE?: string | null;
 *   descriptionEN?: string | null;
 *   descriptionDE?: string | null;
 *   ...
 * }
 * ```
 */
export interface ITranslated {
  /**
   * Translated title property.
   */
  title: string;
  /**
   * Translated description property.
   */
  description: string;
}

/**
 * Extension for skill models w/ additional properties
 */
export interface ITranslatedSkill extends ITranslated {
  /**
   * Translated implementation property.
   */
  implementation: string;
  /**
   * Translated validation property.
   */
  validation: string;
}

/**
 * Reusable base implementation for translated models
 *
 * Translated model classes are expected to extend this class, e.g.:
 *
 * ```
 * export class MyModel extends Translated implements IMyModel {
 * constructor(
 *   language: LanguageService,
 *   public id?: number,
 *   public titleEN?: string,
 *   public titleDE?: string | null,
 *   ...
 *   ) {
 *     super(language)
 *   }
 * }
 * ```
 */
export class Translated implements ITranslated {
  private language: LanguageService;

  constructor(language: LanguageService) {
    this.language = language;
  }

  get title(): string {
    return this.translateProperty('title');
  }

  get description(): string {
    return this.translateProperty('description');
  }

  translateProperty(propertyName: string): string {
    type ObjectKey = keyof Translated;
    const localizedPropertyName: string = this.generatePropertyName(propertyName);
    const propertyKey: ObjectKey = localizedPropertyName as ObjectKey;

    if (propertyKey in this) {
      return this[propertyKey] as string;
    }

    const type = this.constructor.name;
    throw new Error(`There is no such property ${localizedPropertyName} for ${type}!`);
  }

  determineLanguage(): string {
    return this.language.determineSelectedLanguage();
  }

  generatePropertyName(propertyName: string): string {
    return propertyName + this.determineLanguage();
  }
}

/**
 * Extension for skill models w/ additional properties
 */
export class TranslatedSkill extends Translated implements ITranslatedSkill {
  constructor(language: LanguageService) {
    super(language);
  }

  get implementation(): string {
    return this.translateProperty('implementation');
  }

  get validation(): string {
    return this.translateProperty('validation');
  }
}
