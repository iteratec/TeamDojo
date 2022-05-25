import dayjs from 'dayjs/esm';
import { IBadgeSkill } from '../../../entities/badge-skill/badge-skill.model';
import { IImage } from '../../../entities/image/image.model';
import { IDimension } from '../../../entities/dimension/dimension.model';
import { ITeam } from '../../../entities/team/team.model';
import { ILevelSkill } from '../../../entities/level-skill/level-skill.model';
import { ITeamSkill } from '../../../entities/team-skill/team-skill.model';
import { LanguageService } from '../../../language.service';
import { IBadge } from '../../../entities/badge/badge.model';
import { ITraining } from '../../../entities/training/training.model';
import { ISkill } from '../../../entities/skill/skill.model';
import { ILevel } from '../../../entities/level/level.model';

export interface ITranslatedBadge {
  readonly id?: number;
  readonly title?: string;
  readonly description?: string | null;
  availableUntil?: dayjs.Dayjs | null;
  availableAmount?: number | null;
  requiredScore?: number;
  instantMultiplier?: number;
  completionBonus?: number | null;
  readonly createdAt?: dayjs.Dayjs;
  readonly updatedAt?: dayjs.Dayjs;
  skills?: IBadgeSkill[] | null;
  image?: IImage | null;
  dimensions?: ITranslatedDimension[] | null;
}

export interface ITranslatedDimension {
  readonly id?: number;
  readonly title?: string;
  readonly description?: string | null;
  readonly createdAt?: dayjs.Dayjs;
  readonly updatedAt?: dayjs.Dayjs;
  levels?: ITranslatedLevel[] | null;
  badges?: ITranslatedBadge[] | null;
  participants?: ITeam[] | null;
}

export interface ITranslatedLevel {
  readonly id?: number;
  readonly title?: string;
  readonly description?: string | null;
  requiredScore?: number;
  instantMultiplier?: number;
  completionBonus?: number | null;
  readonly createdAt?: dayjs.Dayjs;
  readonly updatedAt?: dayjs.Dayjs;
  dependsOn?: ITranslatedLevel | null;
  skills?: ILevelSkill[] | null;
  image?: IImage | null;
  dimension?: ITranslatedDimension;
}

export interface ITranslatedSkill {
  readonly id?: number;
  readonly title?: string;
  readonly description?: string | null;
  readonly implementation?: string | null;
  readonly validation?: string | null;
  expiryPeriod?: number | null;
  contact?: string | null;
  score?: number;
  rateScore?: number | null;
  rateCount?: number;
  readonly createdAt?: dayjs.Dayjs;
  readonly updatedAt?: dayjs.Dayjs;
  badges?: IBadgeSkill[] | null;
  levels?: ILevelSkill[] | null;
  teams?: ITeamSkill[] | null;
  trainings?: ITranslatedTraining[] | null;
}

export interface ITranslatedTraining {
  readonly id?: number;
  readonly title?: string;
  readonly description?: string | null;
  contact?: string | null;
  link?: string | null;
  validUntil?: dayjs.Dayjs | null;
  isOfficial?: boolean;
  suggestedBy?: string | null;
  readonly createdAt?: dayjs.Dayjs;
  readonly updatedAt?: dayjs.Dayjs;
  skills?: ITranslatedSkill[] | null;
}

export class TranslatedFactory {
  constructor(private language: LanguageService) {}

  decorateBadge(decorated: IBadge): ITranslatedBadge {
    return new TranslatedBadge(decorated, this.language);
  }

  decorateTraining(decorated: ITraining): ITranslatedTraining {
    return new TranslatedTraining(decorated, this.language);
  }

  decorateSkill(decorated: ISkill): ITranslatedSkill {
    return new TranslatedSkill(decorated, this.language);
  }

  decorateLevel(decorated: ILevel): ITranslatedLevel {
    return new TranslatedLevel(decorated, this.language);
  }

  decorateDimension(decorated: IDimension): ITranslatedDimension {
    return new TranslatedDimension(decorated, this.language);
  }
}

type Decorateable = IBadge | IDimension | ILevel | ISkill | ITraining;

class TranslatedBase<T extends Decorateable> {
  protected decorated: T;
  private language: LanguageService;

  constructor(decorated: T, language: LanguageService) {
    this.decorated = decorated;
    this.language = language;
  }

  get id(): number | undefined {
    return this.decorated.id;
  }

  get title(): string | undefined {
    return this.translateProperty('title');
  }

  get description(): string | null | undefined {
    return this.translateProperty('description');
  }

  get createdAt(): dayjs.Dayjs | undefined {
    return this.decorated.createdAt;
  }

  get updatedAt(): dayjs.Dayjs | undefined {
    return this.decorated.updatedAt;
  }

  translateProperty(propertyName: string): string {
    type ObjectKey = keyof Decorateable;
    const localizedPropertyName: string = this.generatePropertyName(propertyName);
    const propertyKey: ObjectKey = localizedPropertyName as ObjectKey;

    if (propertyKey in this.decorated) {
      return this.decorated[propertyKey] as string;
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

class TranslatedBadge extends TranslatedBase<IBadge> implements ITranslatedBadge {
  constructor(decorated: IBadge, language: LanguageService) {
    super(decorated, language);
  }

  get availableUntil(): dayjs.Dayjs | null | undefined {
    return this.decorated.availableUntil;
  }

  set availableUntil(value: dayjs.Dayjs | null | undefined) {
    this.decorated.availableUntil = value;
  }

  get availableAmount(): number | null | undefined {
    return this.decorated.availableAmount;
  }

  set availableAmount(value: number | null | undefined) {
    this.decorated.availableAmount = value;
  }

  get requiredScore(): number | undefined {
    return this.decorated.requiredScore;
  }

  set requiredScore(value: number | undefined) {
    this.decorated.requiredScore = value;
  }

  get instantMultiplier(): number | undefined {
    return this.decorated.instantMultiplier;
  }

  set instantMultiplier(value: number | undefined) {
    this.decorated.instantMultiplier = value;
  }

  get completionBonus(): number | null | undefined {
    return this.decorated.completionBonus;
  }

  set completionBonus(value: number | null | undefined) {
    this.decorated.completionBonus = value;
  }

  get skills(): IBadgeSkill[] | null | undefined {
    return this.decorated.skills;
  }

  set skills(value: IBadgeSkill[] | null | undefined) {
    this.decorated.skills = value;
  }

  get image(): IImage | null | undefined {
    return this.decorated.image;
  }

  set image(value: IImage | null | undefined) {
    this.decorated.image = value;
  }

  get dimensions(): ITranslatedDimension[] | null | undefined {
    // TODO: #8 Decorate
    return this.decorated.dimensions;
  }

  set dimensions(value: IDimension[] | null | undefined) {
    this.decorated.dimensions = value;
  }
}

class TranslatedDimension extends TranslatedBase<IDimension> implements ITranslatedDimension {
  constructor(decorated: IDimension, language: LanguageService) {
    super(decorated, language);
  }

  get levels(): ITranslatedLevel[] | null | undefined {
    // TODO: #8 Decorate
    return this.decorated.levels;
  }

  set levels(value: ILevel[] | null | undefined) {
    this.decorated.levels = value;
  }

  get badges(): ITranslatedBadge[] | null | undefined {
    // TODO: #8 Decorate
    return this.decorated.badges;
  }

  set badges(value: IBadge[] | null | undefined) {
    this.decorated.badges = value;
  }

  get participants(): ITeam[] | null | undefined {
    return this.decorated.participants;
  }

  set participants(value: ITeam[] | null | undefined) {
    this.decorated.participants = value;
  }
}

class TranslatedLevel extends TranslatedBase<ILevel> implements ITranslatedLevel {
  constructor(decorated: ILevel, language: LanguageService) {
    super(decorated, language);
  }

  get requiredScore(): number | undefined {
    return this.decorated.requiredScore;
  }

  set requiredScore(value: number | undefined) {
    this.decorated.requiredScore = value;
  }

  get instantMultiplier(): number | undefined {
    return this.decorated.instantMultiplier;
  }

  set instantMultiplier(value: number | undefined) {
    this.decorated.instantMultiplier = value;
  }

  get completionBonus(): number | null | undefined {
    return this.decorated.completionBonus;
  }

  set completionBonus(value: number | null | undefined) {
    this.decorated.completionBonus = value;
  }

  get dependsOn(): ITranslatedLevel | null | undefined {
    // TODO: #8 Decorate
    return this.decorated.dependsOn;
  }

  set dependsOn(value: ILevel | null | undefined) {
    this.decorated.dependsOn = value;
  }

  get skills(): ILevelSkill[] | null | undefined {
    return this.decorated.skills;
  }

  set skills(value: ILevelSkill[] | null | undefined) {
    this.decorated.skills = value;
  }

  get image(): IImage | null | undefined {
    return this.decorated.image;
  }

  set image(value: IImage | null | undefined) {
    this.decorated.image = value;
  }

  get dimension(): ITranslatedDimension | undefined {
    // TODO: #8 Decorate
    return this.decorated.dimension;
  }

  set dimension(value: IDimension | undefined) {
    this.decorated.dimension = value;
  }
}

class TranslatedSkill extends TranslatedBase<ISkill> implements ITranslatedSkill {
  constructor(decorated: ISkill, language: LanguageService) {
    super(decorated, language);
  }

  get implementation(): string | null | undefined {
    return this.translateProperty('implementation');
  }

  get validation(): string | null | undefined {
    return this.translateProperty('validation');
  }

  get expiryPeriod(): number | null | undefined {
    return this.decorated.expiryPeriod;
  }

  set expiryPeriod(value: number | null | undefined) {
    this.decorated.expiryPeriod = value;
  }

  get contact(): string | null | undefined {
    return this.decorated.contact;
  }

  set contact(value: string | null | undefined) {
    this.decorated.contact = value;
  }

  get score(): number | undefined {
    return this.decorated.score;
  }

  set score(value: number | undefined) {
    this.decorated.score = value;
  }

  get rateScore(): number | null | undefined {
    return this.decorated.rateScore;
  }

  set rateScore(value: number | null | undefined) {
    this.decorated.rateScore = value;
  }

  get rateCount(): number | undefined {
    return this.decorated.rateCount;
  }

  set rateCount(value: number | undefined) {
    this.decorated.rateCount = value;
  }

  get badges(): IBadgeSkill[] | null | undefined {
    return this.decorated.badges;
  }

  set badges(value: IBadgeSkill[] | null | undefined) {
    this.decorated.badges = value;
  }

  get levels(): ILevelSkill[] | null | undefined {
    return this.decorated.levels;
  }

  set levels(value: ILevelSkill[] | null | undefined) {
    this.decorated.levels = value;
  }

  get teams(): ITeamSkill[] | null | undefined {
    return this.decorated.teams;
  }

  set teams(value: ITeamSkill[] | null | undefined) {
    this.decorated.teams = value;
  }

  get trainings(): ITranslatedTraining[] | null | undefined {
    // TODO: #8 Decorate
    return this.decorated.trainings;
  }

  set trainings(value: ITraining[] | null | undefined) {
    this.decorated.trainings = value;
  }
}

class TranslatedTraining extends TranslatedBase<ITraining> implements ITranslatedTraining {
  constructor(decorated: ITraining, language: LanguageService) {
    super(decorated, language);
  }

  get id(): number | undefined {
    return this.decorated.id;
  }

  get contact(): string | null | undefined {
    return this.decorated.contact;
  }

  set contact(value: string | null | undefined) {
    this.decorated.contact = value;
  }

  get link(): string | null | undefined {
    return this.decorated.link;
  }

  set link(value: string | null | undefined) {
    this.decorated.link = value;
  }

  get validUntil(): dayjs.Dayjs | null | undefined {
    return this.decorated.validUntil;
  }

  set validUntil(value: dayjs.Dayjs | null | undefined) {
    this.decorated.validUntil = value;
  }

  get isOfficial(): boolean | undefined {
    return this.decorated.isOfficial;
  }

  set isOfficial(value: boolean | undefined) {
    this.decorated.isOfficial = value;
  }

  get suggestedBy(): string | null | undefined {
    return this.decorated.suggestedBy;
  }

  set suggestedBy(value: string | null | undefined) {
    this.decorated.suggestedBy = value;
  }

  get skills(): ITranslatedSkill[] | null | undefined {
    // TODO: #8 Decorate
    return this.decorated.skills;
  }

  set skills(value: ISkill[] | null | undefined) {
    this.decorated.skills = value;
  }
}
