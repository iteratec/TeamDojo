import { IBadge } from 'app/entities/badge/badge.model';
import { ISkill } from 'app/entities/skill/skill.model';

export interface IBadgeSkill {
  id: number;
  badge?: Pick<IBadge, 'id' | 'titleEN'> | null;
  skill?: Pick<ISkill, 'id' | 'titleEN'> | null;
}

export type NewBadgeSkill = Omit<IBadgeSkill, 'id'> & { id: null };
