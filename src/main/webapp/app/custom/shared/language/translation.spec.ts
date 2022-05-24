import { ITranslated, Translated } from './translation';
import { Language, LanguageService } from '../../../language.service';

// FIXME: #8 Use mocked service here.
// jest.mock('app/language.service');

interface IFixtureModel extends ITranslated {
  titleEN: string;
  titleDE: string;
  descriptionEN: string;
  descriptionDE: string;
}

class FixtureModel extends Translated implements IFixtureModel {
  constructor(
    language: LanguageService,
    public titleEN: string,
    public titleDE: string,
    public descriptionEN: string,
    public descriptionDE: string
  ) {
    super(language);
  }
}

describe('Translated', () => {
  let language: LanguageService;
  let fixture: FixtureModel;
  let sut: Translated;

  beforeEach(() => {
    // FIXME: #8 Use mocked service here.
    // language = TestBed.inject(LanguageService);
    language = new LanguageService();
    sut = new Translated(language);
    fixture = new FixtureModel(language, 'English title', 'German title', 'English description', 'German description');
  });

  it('determineLanguage should return language as string', () => {
    expect(sut.determineLanguage()).toBe('EN');

    language.storeCurrent(Language.DE);
    expect(sut.determineLanguage()).toBe('DE');
  });

  it('generatePropertyName should return localized property name', () => {
    expect(sut.generatePropertyName('foo')).toBe('fooEN');

    language.storeCurrent(Language.DE);
    expect(sut.generatePropertyName('foo')).toBe('fooDE');
  });

  it('should translate title to German', () => {
    language.storeCurrent(Language.DE);

    expect(fixture.title).toBe('German title');
  });

  it('should translate description to German', () => {
    language.storeCurrent(Language.DE);

    expect(fixture.description).toBe('German description');
  });

  it('should translate title to English', () => {
    language.storeCurrent(Language.EN);

    expect(fixture.title).toBe('English title');
  });

  it('should translate description to English', () => {
    language.storeCurrent(Language.EN);

    expect(fixture.description).toBe('English description');
  });

  it('title should throw error for unknown language', () => {
    language.storeCurrent(Language.UNKNOWN);

    expect(() => {
      fixture.title;
    }).toThrow('There is no such property titleUNKNOWN for FixtureModel!');
  });

  it('description should throw error for unknown language', () => {
    language.storeCurrent(Language.UNKNOWN);

    expect(() => {
      fixture.description;
    }).toThrow('There is no such property descriptionUNKNOWN for FixtureModel!');
  });
});
