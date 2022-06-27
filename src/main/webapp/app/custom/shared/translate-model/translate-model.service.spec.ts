/*
 SPDX-FileCopyrightText: the TeamDojo authors
 SPDX-License-Identifier: Apache-2.0
 */
import { TranslateModelService } from './translate-model.service';
import { Badge, IBadge } from '../../../entities/badge/badge.model';
import { Dimension, IDimension } from '../../../entities/dimension/dimension.model';
import { ILevel, Level } from '../../../entities/level/level.model';
import { ISkill, Skill } from '../../../entities/skill/skill.model';
import { ITraining, Training } from '../../../entities/training/training.model';
import { LanguageService } from './language.service';
import { TranslateModule, TranslateService } from '@ngx-translate/core';
import { TestBed } from '@angular/core/testing';

describe('TranslateModelService', () => {
  const fixtureProperties = {
    id: 42,
    titleEN: 'English title',
    titleDE: 'German title',
    descriptionEN: 'English description',
    descriptionDE: 'German description',
  };
  let translate: TranslateService;
  let sut: TranslateModelService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [TranslateModule.forRoot()],
      providers: [TranslateService, LanguageService, TranslateModelService],
    });
    translate = TestBed.inject(TranslateService);
    sut = TestBed.inject(TranslateModelService);
  });

  it('should return an empty string if passed in model is null', () => {
    expect(sut.translateProperty(null, 'title')).toBe('');
  });

  it('should return an empty string if passed in model is undefined', () => {
    const model = undefined;

    expect(sut.translateProperty(model, 'title')).toBe('');
  });

  it('should raise error if passed in property name is empty', () => {
    expect(() => {
      sut.translateProperty(new Badge(), '');
    }).toThrow('Given property name to translate model must not be empty!');
  });

  describe('Translate Badge', () => {
    const fixture: IBadge = new Badge(
      fixtureProperties.id,
      fixtureProperties.titleEN,
      fixtureProperties.titleDE,
      fixtureProperties.descriptionEN,
      fixtureProperties.descriptionDE
    );

    it('title should return English title', () => {
      translate.currentLang = 'en';

      expect(sut.translateProperty(fixture, 'title')).toBe(fixture.titleEN);
    });

    it('title should return German title', () => {
      translate.currentLang = 'de';

      expect(sut.translateProperty(fixture, 'title')).toBe(fixture.titleDE);
    });

    it('description should return English description', () => {
      translate.currentLang = 'en';

      expect(sut.translateProperty(fixture, 'description')).toBe(fixture.descriptionEN);
    });

    it('description should return German description', () => {
      translate.currentLang = 'de';

      expect(sut.translateProperty(fixture, 'description')).toBe(fixture.descriptionDE);
    });

    it('should raise error on translating non existing property', () => {
      translate.currentLang = 'de';

      expect(() => {
        sut.translateProperty(fixture, 'foobar');
      }).toThrow("There is no such property 'foobarDE' for model 'Badge'!");
    });
  });

  describe('Translate Dimension', () => {
    const fixture: IDimension = new Dimension(
      fixtureProperties.id,
      fixtureProperties.titleEN,
      fixtureProperties.titleDE,
      fixtureProperties.descriptionEN,
      fixtureProperties.descriptionDE
    );

    it('title should return English title', () => {
      translate.currentLang = 'en';

      expect(sut.translateProperty(fixture, 'title')).toBe(fixture.titleEN);
    });

    it('title should return German title', () => {
      translate.currentLang = 'de';

      expect(sut.translateProperty(fixture, 'title')).toBe(fixture.titleDE);
    });

    it('description should return English description', () => {
      translate.currentLang = 'en';

      expect(sut.translateProperty(fixture, 'description')).toBe(fixture.descriptionEN);
    });

    it('description should return German description', () => {
      translate.currentLang = 'de';

      expect(sut.translateProperty(fixture, 'description')).toBe(fixture.descriptionDE);
    });

    it('should raise error on translating non existing property', () => {
      translate.currentLang = 'de';

      expect(() => {
        sut.translateProperty(fixture, 'foobar');
      }).toThrow("There is no such property 'foobarDE' for model 'Dimension'!");
    });
  });

  describe('Translate Level', () => {
    const fixture: ILevel = new Level(
      fixtureProperties.id,
      fixtureProperties.titleEN,
      fixtureProperties.titleDE,
      fixtureProperties.descriptionEN,
      fixtureProperties.descriptionDE
    );

    it('title should return English title', () => {
      translate.currentLang = 'en';

      expect(sut.translateProperty(fixture, 'title')).toBe(fixture.titleEN);
    });

    it('title should return German title', () => {
      translate.currentLang = 'de';

      expect(sut.translateProperty(fixture, 'title')).toBe(fixture.titleDE);
    });

    it('description should return English description', () => {
      translate.currentLang = 'en';

      expect(sut.translateProperty(fixture, 'description')).toBe(fixture.descriptionEN);
    });

    it('description should return German description', () => {
      translate.currentLang = 'de';

      expect(sut.translateProperty(fixture, 'description')).toBe(fixture.descriptionDE);
    });

    it('should raise error on translating non existing property', () => {
      translate.currentLang = 'de';

      expect(() => {
        sut.translateProperty(fixture, 'foobar');
      }).toThrow("There is no such property 'foobarDE' for model 'Level'!");
    });
  });

  describe('Translate Skill', () => {
    const fixture: ISkill = new Skill(
      fixtureProperties.id,
      fixtureProperties.titleEN,
      fixtureProperties.titleDE,
      fixtureProperties.descriptionEN,
      fixtureProperties.descriptionDE,
      'English implementation',
      'German implementation',
      'English validation',
      'German validation'
    );

    it('title should return English title', () => {
      translate.currentLang = 'en';

      expect(sut.translateProperty(fixture, 'title')).toBe(fixture.titleEN);
    });

    it('title should return German title', () => {
      translate.currentLang = 'de';

      expect(sut.translateProperty(fixture, 'title')).toBe(fixture.titleDE);
    });

    it('description should return English description', () => {
      translate.currentLang = 'en';

      expect(sut.translateProperty(fixture, 'description')).toBe(fixture.descriptionEN);
    });

    it('description should return German description', () => {
      translate.currentLang = 'de';

      expect(sut.translateProperty(fixture, 'description')).toBe(fixture.descriptionDE);
    });

    it('implementation should return English implementation', () => {
      translate.currentLang = 'en';

      expect(sut.translateProperty(fixture, 'implementation')).toBe(fixture.implementationEN);
    });

    it('implementation should return German implementation', () => {
      translate.currentLang = 'de';

      expect(sut.translateProperty(fixture, 'implementation')).toBe(fixture.implementationDE);
    });

    it('validation should return English validation', () => {
      translate.currentLang = 'en';

      expect(sut.translateProperty(fixture, 'validation')).toBe(fixture.validationEN);
    });

    it('validation should return German validation', () => {
      translate.currentLang = 'de';

      expect(sut.translateProperty(fixture, 'validation')).toBe(fixture.validationDE);
    });

    it('should raise error on translating non existing property', () => {
      translate.currentLang = 'de';

      expect(() => {
        sut.translateProperty(fixture, 'foobar');
      }).toThrow("There is no such property 'foobarDE' for model 'Skill'!");
    });
  });

  describe('Translate Training', () => {
    const fixture: ITraining = new Training(
      fixtureProperties.id,
      fixtureProperties.titleEN,
      fixtureProperties.titleDE,
      fixtureProperties.descriptionEN,
      fixtureProperties.descriptionDE
    );

    it('title should return English title', () => {
      translate.currentLang = 'en';

      expect(sut.translateProperty(fixture, 'title')).toBe(fixture.titleEN);
    });

    it('title should return German title', () => {
      translate.currentLang = 'de';

      expect(sut.translateProperty(fixture, 'title')).toBe(fixture.titleDE);
    });

    it('description should return English description', () => {
      translate.currentLang = 'en';

      expect(sut.translateProperty(fixture, 'description')).toBe(fixture.descriptionEN);
    });

    it('description should return German description', () => {
      translate.currentLang = 'de';

      expect(sut.translateProperty(fixture, 'description')).toBe(fixture.descriptionDE);
    });

    it('should raise error on translating non existing property', () => {
      translate.currentLang = 'de';

      expect(() => {
        sut.translateProperty(fixture, 'foobar');
      }).toThrow("There is no such property 'foobarDE' for model 'Training'!");
    });
  });
});
