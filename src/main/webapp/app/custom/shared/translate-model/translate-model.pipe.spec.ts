import { Language, LanguageService } from './language.service';
import { TranslateModelPipe } from './translate-model.pipe';
import { Badge, IBadge } from '../../../entities/badge/badge.model';
import { TranslateModelService } from './translate-model.service';

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
  // FIXME: #8 Use mocked service here instead of LanguageService.
  const language: LanguageService = new LanguageService();
  const translation: TranslateModelService = new TranslateModelService(language);
  const sut: TranslateModelPipe = new TranslateModelPipe(translation);

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
      language.storeCurrent(Language.EN);

      expect(sut.transform(fixture, 'title')).toBe(fixture.titleEN);
    });

    it('title should return German title', () => {
      language.storeCurrent(Language.DE);

      expect(sut.transform(fixture, 'title')).toBe(fixture.titleDE);
    });

    it('description should return English description', () => {
      language.storeCurrent(Language.EN);

      expect(sut.transform(fixture, 'description')).toBe(fixture.descriptionEN);
    });

    it('description should return German description', () => {
      language.storeCurrent(Language.DE);

      expect(sut.transform(fixture, 'description')).toBe(fixture.descriptionDE);
    });

    it('should raise error on translating non existing property', () => {
      language.storeCurrent(Language.DE);

      expect(() => {
        sut.transform(fixture, 'foobar');
      }).toThrow("There is no such property 'foobarDE' for model 'Badge'!");
    });
  });
});
