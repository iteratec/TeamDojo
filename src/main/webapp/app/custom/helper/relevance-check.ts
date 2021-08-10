import { ITeam } from 'app/entities/team/team.model';
import { IDimension } from 'app/entities/dimension/dimension.model';
import { IBadge } from 'app/entities/badge/badge.model';
import { ILevel } from 'app/entities/level/level.model';

export class RelevanceCheck {
  constructor(private team: ITeam) {}

  public isRelevantDimensionId(dimensionId: number): boolean {
    const res: boolean | undefined = this.team.participations?.some((dimension: IDimension) => dimension.id === dimensionId);
    return res !== undefined && res;
  }

  public isRelevantDimension(dimension: IDimension): boolean {
    return this.isRelevantDimensionId(dimension.id!);
  }

  public isRelevantLevel(level: ILevel): boolean {
    return this.isRelevantDimensionId(level.dimension!.id!);
  }

  public isRelevantBadge(badge: IBadge): boolean {
    return (
      badge.dimensions !== null &&
      badge.dimensions !== undefined &&
      (this.isDimensionlessBadge(badge) || badge.dimensions.some((dimension: IDimension) => this.isRelevantDimension(dimension)))
    );
  }

  public isRelevantLevelOrBadge(item: ILevel | IBadge): boolean {
    if ((<ILevel>item).dimension === undefined) {
      return false;
    }
    if ((<ILevel>item).dimension!.id) {
      return this.isRelevantLevel(item);
    }
    return this.isRelevantBadge(<IBadge>item);
  }

  private isDimensionlessBadge(badge: IBadge): boolean {
    return !badge.dimensions || !badge.dimensions.length;
  }
}
