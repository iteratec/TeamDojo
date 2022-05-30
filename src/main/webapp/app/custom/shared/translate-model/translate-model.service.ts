import { Injectable } from '@angular/core';
import { IBadge } from '../../../entities/badge/badge.model';
import { IDimension } from '../../../entities/dimension/dimension.model';
import { ILevel } from '../../../entities/level/level.model';
import { ISkill } from '../../../entities/skill/skill.model';
import { ITraining } from '../../../entities/training/training.model';
import { LanguageService } from '../../../language.service';

/**
 * This union type declares what kind of model has translated user content.
 */
export type TranslatableModels = IBadge | IDimension | ILevel | ISkill | ITraining;

/**
 * Service to translate a named property with user generated content for piped model.
 *
 * Since we use columns for translated content (e.g. titleDE, titleEN etc.) this service tries to
 * find the right property of the given base name according to users selected language. E.g. for selected
 * language "English" and the property "title" it looks for a property "titleEN" and returns its value,
 * unless the property exist an error is raised.
 *
 * Example:
 *   const translation: TranslateModelService = ...
 *   const skill: ISkill = ...
 *   translatedTitle = translation.translateProperty(skill, "title");
 */
@Injectable({
  providedIn: 'root',
})
export class TranslateModelService {
  constructor(private language: LanguageService) {}

  /**
   * Tries to find translated property according to the user's selected language.
   *
   * @throws Error if given propertyName is empty
   * @throws Error if given propertyName can't be translated on given model
   * @param model the model with translated properties
   * @param propertyName the property name to translate
   * @return translated property, maybe empty if passed in model is null or undefined
   */
  public translateProperty(model: TranslatableModels | null | undefined, propertyName: string): string {
    if (propertyName === '') {
      throw new Error('Given property name to translate model must not be empty!');
    }

    if (!model) {
      return '';
    }

    type ObjectKey = keyof TranslatableModels;
    const localizedPropertyName: string = this.generatePropertyName(propertyName);
    const propertyKey: ObjectKey = localizedPropertyName as ObjectKey;

    if (propertyKey in model) {
      return model[propertyKey] as string;
    }

    const type = model.constructor.name;
    throw new Error(`There is no such property '${localizedPropertyName}' for model '${type}'!`);
  }

  private determineLanguage(): string {
    return this.language.determineSelectedLanguage();
  }

  private generatePropertyName(propertyName: string): string {
    return propertyName + this.determineLanguage();
  }
}
