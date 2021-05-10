import { IBadge } from '@/shared/model/badge.model';
import { ISkill } from '@/shared/model/skill.model';

export interface IBadgeSkill {
  id?: number;
  badge?: IBadge;
  skill?: ISkill;
}

export class BadgeSkill implements IBadgeSkill {
  constructor(public id?: number, public badge?: IBadge, public skill?: ISkill) {}
}
