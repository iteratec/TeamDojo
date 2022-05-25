import dayjs from 'dayjs/esm';
import { Language, LanguageService } from '../../../language.service';
import { TranslatedFactory } from './decorators';
import { Badge, IBadge } from '../../../entities/badge/badge.model';
import { IImage, Image } from '../../../entities/image/image.model';
import { Dimension, IDimension } from '../../../entities/dimension/dimension.model';
import { ILevel, Level } from '../../../entities/level/level.model';
import { ISkill, Skill } from '../../../entities/skill/skill.model';
import { ITraining, Training } from '../../../entities/training/training.model';
import { ITeam, Team } from '../../../entities/team/team.model';
import { ILevelSkill, LevelSkill } from '../../../entities/level-skill/level-skill.model';
import { BadgeSkill, IBadgeSkill } from '../../../entities/badge-skill/badge-skill.model';
import { ITeamSkill, TeamSkill } from '../../../entities/team-skill/team-skill.model';

describe('translation', () => {
  const commonFixtureProperties = {
    id: 42,
    titleEN: 'English title',
    titleDE: 'German title',
    descriptionEN: 'English description',
    descriptionDE: 'German description',
  };
  // FIXME: #8 Use mocked service here.
  const language: LanguageService = new LanguageService();
  const decorators: TranslatedFactory = new TranslatedFactory(language);

  describe('TranslatedBadge', () => {
    const imageFixture: IImage = new Image();
    const skillFixture: ISkill = new Skill();
    const dimensionFixture: IDimension = new Dimension();
    const badgeFixture: IBadge = new Badge(
      commonFixtureProperties.id,
      commonFixtureProperties.titleEN,
      commonFixtureProperties.titleDE,
      commonFixtureProperties.descriptionEN,
      commonFixtureProperties.descriptionDE,
      dayjs('2022-03-15T16:00:00.000Z'),
      43,
      44,
      45,
      46,
      dayjs('2022-03-16T16:00:00.000Z'),
      dayjs('2022-03-17T16:00:00.000Z'),
      [skillFixture],
      imageFixture,
      [dimensionFixture]
    );
    const sut = decorators.decorateBadge(badgeFixture);

    it('id should return original id', () => {
      expect(sut.id).toBe(badgeFixture.id);
    });

    it('title should return English title', () => {
      language.storeCurrent(Language.EN);

      expect(sut.title).toBe(badgeFixture.titleEN);
    });

    it('title should return German title', () => {
      language.storeCurrent(Language.DE);

      expect(sut.title).toBe(badgeFixture.titleDE);
    });

    it('description should return English description', () => {
      language.storeCurrent(Language.EN);

      expect(sut.description).toBe(badgeFixture.descriptionEN);
    });

    it('description should return German description', () => {
      language.storeCurrent(Language.DE);

      expect(sut.description).toBe(badgeFixture.descriptionDE);
    });

    it('availableUntil should return original availableUntil', () => {
      expect(sut.availableUntil).toEqual(badgeFixture.availableUntil);
    });

    it('availableAmount should return original availableAmount', () => {
      expect(sut.availableAmount).toBe(badgeFixture.availableAmount);
    });

    it('requiredScore should return original requiredScore', () => {
      expect(sut.requiredScore).toBe(badgeFixture.requiredScore);
    });

    it('instantMultiplier should return original instantMultiplier', () => {
      expect(sut.instantMultiplier).toBe(badgeFixture.instantMultiplier);
    });

    it('completionBonus should return original completionBonus', () => {
      expect(sut.completionBonus).toBe(badgeFixture.completionBonus);
    });

    it('createdAt should return original createdAt', () => {
      expect(sut.createdAt).toEqual(badgeFixture.createdAt);
    });

    it('updatedAt should return original updatedAt', () => {
      expect(sut.updatedAt).toEqual(badgeFixture.updatedAt);
    });

    it('skills should return original skills', () => {
      expect(sut.skills).toEqual(badgeFixture.skills);
    });

    it('image should return original image', () => {
      expect(sut.image).toBe(badgeFixture.image);
    });

    it('dimensions should return original dimensions', () => {
      expect(sut.dimensions).toEqual(badgeFixture.dimensions);
    });
  });

  describe('TranslatedDimension', () => {
    const levelFixture: ILevel = new Level();
    const badgeFixture: IBadge = new Badge();
    const teamFixture: ITeam = new Team();
    const dimensionFixture: IDimension = new Dimension(
      commonFixtureProperties.id,
      commonFixtureProperties.titleEN,
      commonFixtureProperties.titleDE,
      commonFixtureProperties.descriptionEN,
      commonFixtureProperties.descriptionDE,
      dayjs('2022-03-16T16:00:00.000Z'),
      dayjs('2022-03-17T16:00:00.000Z'),
      [levelFixture],
      [badgeFixture],
      [teamFixture]
    );
    const sut = decorators.decorateDimension(dimensionFixture);

    it('id should return original id', () => {
      expect(sut.id).toBe(dimensionFixture.id);
    });

    it('title should return English title', () => {
      language.storeCurrent(Language.EN);

      expect(sut.title).toBe(dimensionFixture.titleEN);
    });

    it('title should return German title', () => {
      language.storeCurrent(Language.DE);

      expect(sut.title).toBe(dimensionFixture.titleDE);
    });

    it('description should return English description', () => {
      language.storeCurrent(Language.EN);

      expect(sut.description).toBe(dimensionFixture.descriptionEN);
    });

    it('description should return German description', () => {
      language.storeCurrent(Language.DE);

      expect(sut.description).toBe(dimensionFixture.descriptionDE);
    });

    it('createdAt should return original createdAt', () => {
      expect(sut.createdAt).toEqual(dimensionFixture.createdAt);
    });

    it('updatedAt should return original updatedAt', () => {
      expect(sut.updatedAt).toEqual(dimensionFixture.updatedAt);
    });

    it('levels should return original levels', () => {
      expect(sut.levels).toEqual(dimensionFixture.levels);
    });

    it('badges should return original badges', () => {
      expect(sut.badges).toEqual(dimensionFixture.badges);
    });

    it('participants should return original participants', () => {
      expect(sut.participants).toEqual(dimensionFixture.participants);
    });
  });

  describe('TranslatedLevel', () => {
    const dependingLevelFixture: ILevel = new Level();
    const levelSkillFixture: ILevelSkill = new LevelSkill();
    const imageFixture: IImage = new Image();
    const dimensionFixture: IDimension = new Dimension();
    const levelFixture: ILevel = new Level(
      commonFixtureProperties.id,
      commonFixtureProperties.titleEN,
      commonFixtureProperties.titleDE,
      commonFixtureProperties.descriptionEN,
      commonFixtureProperties.descriptionDE,
      43,
      44,
      45,
      dayjs('2022-03-16T16:00:00.000Z'),
      dayjs('2022-03-17T16:00:00.000Z'),
      dependingLevelFixture,
      [levelSkillFixture],
      imageFixture,
      dimensionFixture
    );
    const sut = decorators.decorateLevel(levelFixture);

    it('id should return original id', () => {
      expect(sut.id).toBe(levelFixture.id);
    });

    it('title should return English title', () => {
      language.storeCurrent(Language.EN);

      expect(sut.title).toBe(levelFixture.titleEN);
    });

    it('title should return German title', () => {
      language.storeCurrent(Language.DE);

      expect(sut.title).toBe(levelFixture.titleDE);
    });

    it('description should return English description', () => {
      language.storeCurrent(Language.EN);

      expect(sut.description).toBe(levelFixture.descriptionEN);
    });

    it('description should return German description', () => {
      language.storeCurrent(Language.DE);

      expect(sut.description).toBe(levelFixture.descriptionDE);
    });

    it('requiredScore should return original requiredScore', () => {
      expect(sut.requiredScore).toEqual(levelFixture.requiredScore);
    });

    it('instantMultiplier should return original instantMultiplier', () => {
      expect(sut.instantMultiplier).toEqual(levelFixture.instantMultiplier);
    });

    it('completionBonus should return original completionBonus', () => {
      expect(sut.completionBonus).toEqual(levelFixture.completionBonus);
    });

    it('createdAt should return original createdAt', () => {
      expect(sut.createdAt).toEqual(levelFixture.createdAt);
    });

    it('updatedAt should return original updatedAt', () => {
      expect(sut.updatedAt).toEqual(levelFixture.updatedAt);
    });

    it('dependsOn should return original dependsOn', () => {
      expect(sut.dependsOn).toEqual(levelFixture.dependsOn);
    });

    it('skills should return original skills', () => {
      expect(sut.skills).toEqual(levelFixture.skills);
    });

    it('image should return original image', () => {
      expect(sut.image).toEqual(levelFixture.image);
    });

    it('dimension should return original dimension', () => {
      expect(sut.dimension).toEqual(levelFixture.dimension);
    });
  });

  describe('TranslatedSkill', () => {
    const badgeSkillFixture: IBadgeSkill = new BadgeSkill();
    const levelSkillFixture: ILevelSkill = new LevelSkill();
    const teamSkillFixture: ITeamSkill = new TeamSkill();
    const trainingFixture: ITraining = new Training();
    const skillFixture: ISkill = new Skill(
      commonFixtureProperties.id,
      commonFixtureProperties.titleEN,
      commonFixtureProperties.titleDE,
      commonFixtureProperties.descriptionEN,
      commonFixtureProperties.descriptionDE,
      'English implementation',
      'German implementation',
      'English validation',
      'German validation',
      43,
      'contact@acme.com',
      44,
      45,
      46,
      dayjs('2022-03-16T16:00:00.000Z'),
      dayjs('2022-03-17T16:00:00.000Z'),
      [badgeSkillFixture],
      [levelSkillFixture],
      [teamSkillFixture],
      [trainingFixture]
    );
    const sut = decorators.decorateSkill(skillFixture);

    it('id should return original id', () => {
      expect(sut.id).toBe(skillFixture.id);
    });

    it('title should return English title', () => {
      language.storeCurrent(Language.EN);

      expect(sut.title).toBe(skillFixture.titleEN);
    });

    it('title should return German title', () => {
      language.storeCurrent(Language.DE);

      expect(sut.title).toBe(skillFixture.titleDE);
    });

    it('description should return English description', () => {
      language.storeCurrent(Language.EN);

      expect(sut.description).toBe(skillFixture.descriptionEN);
    });

    it('description should return German description', () => {
      language.storeCurrent(Language.DE);

      expect(sut.description).toBe(skillFixture.descriptionDE);
    });

    it('implementation should return English implementation', () => {
      language.storeCurrent(Language.EN);

      expect(sut.implementation).toBe(skillFixture.implementationEN);
    });

    it('implementation should return German implementation', () => {
      language.storeCurrent(Language.DE);

      expect(sut.implementation).toBe(skillFixture.implementationDE);
    });

    it('validation should return English validation', () => {
      language.storeCurrent(Language.EN);

      expect(sut.validation).toBe(skillFixture.validationEN);
    });

    it('validation should return German validation', () => {
      language.storeCurrent(Language.DE);

      expect(sut.validation).toBe(skillFixture.validationDE);
    });

    it('expiryPeriod should return original expiryPeriod', () => {
      expect(sut.expiryPeriod).toBe(skillFixture.expiryPeriod);
    });

    it('contact should return original contact', () => {
      expect(sut.contact).toBe(skillFixture.contact);
    });

    it('score should return original score', () => {
      expect(sut.score).toBe(skillFixture.score);
    });

    it('rateScore should return original rateScore', () => {
      expect(sut.rateScore).toBe(skillFixture.rateScore);
    });

    it('rateCount should return original rateCount', () => {
      expect(sut.rateCount).toBe(skillFixture.rateCount);
    });

    it('createdAt should return original createdAt', () => {
      expect(sut.createdAt).toEqual(skillFixture.createdAt);
    });

    it('updatedAt should return original updatedAt', () => {
      expect(sut.updatedAt).toEqual(skillFixture.updatedAt);
    });

    it('badges should return original badges', () => {
      expect(sut.badges).toBe(skillFixture.badges);
    });

    it('levels should return original levels', () => {
      expect(sut.levels).toBe(skillFixture.levels);
    });

    it('teams should return original teams', () => {
      expect(sut.teams).toBe(skillFixture.teams);
    });

    it('trainings should return original trainings', () => {
      expect(sut.trainings).toBe(skillFixture.trainings);
    });
  });

  describe('TranslatedTraining', () => {
    const skillFixture: ISkill = new Skill();
    const trainingFixture: ITraining = new Training(
      commonFixtureProperties.id,
      commonFixtureProperties.titleEN,
      commonFixtureProperties.titleDE,
      commonFixtureProperties.descriptionEN,
      commonFixtureProperties.descriptionDE,
      'contact@acme.com',
      'http://www.acme.com/training.html',
      dayjs('2022-03-15T16:00:00.000Z'),
      true,
      'someone else',
      dayjs('2022-03-16T16:00:00.000Z'),
      dayjs('2022-03-17T16:00:00.000Z'),
      [skillFixture]
    );
    const sut = decorators.decorateTraining(trainingFixture);

    it('id should return original id', () => {
      expect(sut.id).toBe(trainingFixture.id);
    });

    it('title should return English title', () => {
      language.storeCurrent(Language.EN);

      expect(sut.title).toBe(trainingFixture.titleEN);
    });

    it('title should return German title', () => {
      language.storeCurrent(Language.DE);

      expect(sut.title).toBe(trainingFixture.titleDE);
    });

    it('description should return English description', () => {
      language.storeCurrent(Language.EN);

      expect(sut.description).toBe(trainingFixture.descriptionEN);
    });

    it('description should return German description', () => {
      language.storeCurrent(Language.DE);

      expect(sut.description).toBe(trainingFixture.descriptionDE);
    });

    it('contact should return original contact', () => {
      expect(sut.contact).toBe(trainingFixture.contact);
    });

    it('link should return original link', () => {
      expect(sut.link).toBe(trainingFixture.link);
    });

    it('validUntil should return original validUntil', () => {
      expect(sut.validUntil).toEqual(trainingFixture.validUntil);
    });

    it('isOfficial should return original isOfficial', () => {
      expect(sut.isOfficial).toBe(trainingFixture.isOfficial);
    });

    it('suggestedBy should return original suggestedBy', () => {
      expect(sut.suggestedBy).toBe(trainingFixture.suggestedBy);
    });

    it('createdAt should return original createdAt', () => {
      expect(sut.createdAt).toEqual(trainingFixture.createdAt);
    });

    it('updatedAt should return original updatedAt', () => {
      expect(sut.updatedAt).toEqual(trainingFixture.updatedAt);
    });

    it('skills should return original skills', () => {
      expect(sut.skills).toBe(trainingFixture.skills);
    });
  });
});
