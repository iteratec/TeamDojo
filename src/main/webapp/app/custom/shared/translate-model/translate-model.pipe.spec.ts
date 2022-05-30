import { LanguageService } from './language.service';
import { TranslateModelPipe } from './translate-model.pipe';
import { Badge, IBadge } from '../../../entities/badge/badge.model';
import { TranslateModelService } from './translate-model.service';
import { TranslateModule, TranslateService } from '@ngx-translate/core';
import { TestBed } from '@angular/core/testing';

/**
 * In this test case we simply test for only one model (Badge) as integration test.
 * All other models are tested in the service's test cases and need not be duplicated here.
 */
describe('TranslateModelPipe', () => {
  const fixtureProperties = {
    id: 42,
    titleEN: 'English title',
    titleDE: 'German title',
    descriptionEN: 'English description',
    descriptionDE: 'German description',
  };
  let translate: TranslateService;
  let sut: TranslateModelPipe;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [TranslateModule.forRoot()],
      providers: [TranslateService, LanguageService, TranslateModelService, TranslateModelPipe],
    });
    translate = TestBed.inject(TranslateService);
    sut = TestBed.inject(TranslateModelPipe);
  });

  it('should return an empty string if passed in model is null', () => {
    expect(sut.transform(null, 'title')).toBe('');
  });

  it('should return an empty string if passed in model is undefined', () => {
    const model = undefined;

    expect(sut.transform(model, 'title')).toBe('');
  });

  it('should raise error if passed in property name is empty', () => {
    expect(() => {
      sut.transform(new Badge(), '');
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

      expect(sut.transform(fixture, 'title')).toBe(fixture.titleEN);
    });

    it('title should return German title', () => {
      translate.currentLang = 'de';

      expect(sut.transform(fixture, 'title')).toBe(fixture.titleDE);
    });

    it('description should return English description', () => {
      translate.currentLang = 'en';

      expect(sut.transform(fixture, 'description')).toBe(fixture.descriptionEN);
    });

    it('description should return German description', () => {
      translate.currentLang = 'de';

      expect(sut.transform(fixture, 'description')).toBe(fixture.descriptionDE);
    });

    it('should raise error on translating non existing property', () => {
      translate.currentLang = 'de';

      expect(() => {
        sut.transform(fixture, 'foobar');
      }).toThrow("There is no such property 'foobarDE' for model 'Badge'!");
    });
  });
});
