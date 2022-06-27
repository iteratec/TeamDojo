/*
 SPDX-FileCopyrightText: the TeamDojo authors
 SPDX-License-Identifier: Apache-2.0
 */
import { ITeam } from 'app/entities/team/team.model';
import { IDimension } from 'app/entities/dimension/dimension.model';
import { IBadge } from 'app/entities/badge/badge.model';
import { ILevel } from 'app/entities/level/level.model';

export class RelevanceCheck {
  constructor(private team: ITeam | undefined) {}

  public isRelevantDimensionId(dimensionId: number): boolean {
    const res: boolean | undefined = this.team?.participations?.some((dimension: IDimension) => dimension.id === dimensionId);
    return res !== undefined && res;
  }

  public isRelevantDimension(dimension: IDimension): boolean {
    return this.isRelevantDimensionId(dimension.id!);
  }

  public isRelevantLevel(level: ILevel): boolean {
    return this.isRelevantDimensionId(level.dimension!.id!);
  }

  public isRelevantBadge(badge: IBadge): boolean {
    if (badge.dimensions) {
      return this.isDimensionlessBadge(badge) || badge.dimensions.some((dimension: IDimension) => this.isRelevantDimension(dimension));
    }

    return false;
  }

  public isRelevantLevelOrBadge(item: ILevel | IBadge): boolean {
    if ((<ILevel>item).dimension?.id) {
      return this.isRelevantLevel(item);
    }
    return this.isRelevantBadge(<IBadge>item);
  }

  private isDimensionlessBadge(badge: IBadge): boolean {
    return !badge.dimensions || !badge.dimensions.length;
  }
}
