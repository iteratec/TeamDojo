import { Pipe, PipeTransform } from '@angular/core';
import { IBadge } from '../../../entities/badge/badge.model';
import { IDimension } from '../../../entities/dimension/dimension.model';
import { ILevel } from '../../../entities/level/level.model';
import { ISkill } from '../../../entities/skill/skill.model';
import { ITraining } from '../../../entities/training/training.model';
import { LanguageService } from '../../../language.service';

/**
 * This union type declares what kind of model has translated user content.
 */
type TranslatableModels = IBadge | IDimension | ILevel | ISkill | ITraining;

/*
 * Translates a named property with user generated content for piped model
 *
 * Since we use columns for translated content (e.g. titleDE, titleEN etc.)
 * this pipe tries to find the right property of the given base name according
 * to users selected language. E.g. for selected language "English" and the property "title"
 * it looks for a property "titleEN" and returns its value, unless the property exist an error is raised.
 *
 * Usage:
 *   model | translateModel:propertyName
 * Example:
 *   {{ skill | translateModel:title }}
 */
@Pipe({ name: 'translateModel' })
export class TranslateModelPipe implements PipeTransform {
  constructor(private language: LanguageService) {}

  transform(model: TranslatableModels | null | undefined, propertyName: string): string {
    if (propertyName === '') {
      throw new Error('Given property name to translate model must not be empty!');
    }

    if (model) {
      return this.translateProperty(model, propertyName);
    }

    return '';
  }

  private translateProperty(model: TranslatableModels, propertyName: string): string {
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
