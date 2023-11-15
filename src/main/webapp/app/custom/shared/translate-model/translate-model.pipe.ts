/*
 SPDX-FileCopyrightText: the TeamDojo authors
 SPDX-License-Identifier: Apache-2.0
 */
import { Pipe, PipeTransform } from '@angular/core';
import { TranslatableModels, TranslateModelService } from './translate-model.service';

/*
 * Translates a named property with user generated content for piped model.
 *
 * See TranslateModelService for more details.
 *
 * Usage:
 *   model | translateModel:propertyName
 * Example:
 *   {{ skill | translateModel:title }}
 */
@Pipe({
  name: 'translateModel',
  pure: false, // Must not be pure so that already rendered components will be rendered again after language switch.
  standalone: true
})
export class TranslateModelPipe implements PipeTransform {
  constructor(private translation: TranslateModelService) {}

  transform(model: TranslatableModels | null | undefined, propertyName: string): string {
    return this.translation.translateProperty(model, propertyName);
  }
}
