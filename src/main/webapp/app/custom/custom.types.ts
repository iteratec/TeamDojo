import { IBadgeSkill } from "app/entities/badge-skill/badge-skill.model";
import { IBadge } from "app/entities/badge/badge.model";
import { IDimension } from "app/entities/dimension/dimension.model";
import { ILevelSkill } from "app/entities/level-skill/level-skill.model";
import { ILevel } from "app/entities/level/level.model";
import { ISkill } from "app/entities/skill/skill.model";
import { ITeamGroup } from "app/entities/team-group/team-group.model";
import { ITeamSkill } from "app/entities/team-skill/team-skill.model";
import { ITeam } from "app/entities/team/team.model";

export type Team = Partial<ITeam> & {
  skills?: ITeamSkill[] | null
}

export type Skill = Partial<ISkill> & {
  teams?: ITeamSkill[] | null,
  badges?: IBadgeSkill[] | null;
  levels?: ILevelSkill[] | null;
}

export type Badge = Partial<IBadge> & {
  skills?: IBadgeSkill[] | null;
}

export type Dimension = Partial<IDimension> & {
  levels?: Level[] | null;
}

export type Level = Partial<ILevel> & {
  skills?: ILevelSkill[] | null;
}

export type TeamGroup = Partial<ITeamGroup> & {
  teams?: Team[] | null;
}