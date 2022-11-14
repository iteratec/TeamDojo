/*
 SPDX-FileCopyrightText: the TeamDojo authors
 SPDX-License-Identifier: Apache-2.0
 */
import { ITeam } from 'app/entities/team/team.model';

export interface ITeamScore {
  team?: ITeam;
  score?: number;
}

export class TeamScore implements ITeamScore {
  constructor(public team?: ITeam, public score?: number) {}
}
