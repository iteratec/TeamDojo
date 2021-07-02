import { IBadge } from 'app/entities/badge/badge.model';
import { ISkill } from 'app/entities/skill/skill.model';

export interface IBadgeSkill {
  id?: number;
  badge?: IBadge;
  skill?: ISkill;
}

export class BadgeSkill implements IBadgeSkill {
  constructor(public id?: number, public badge?: IBadge, public skill?: ISkill) {}
}

export function getBadgeSkillIdentifier(badgeSkill: IBadgeSkill): number | undefined {
  return badgeSkill.id;
}
