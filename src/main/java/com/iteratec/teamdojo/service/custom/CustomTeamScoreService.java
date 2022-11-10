/*
 SPDX-FileCopyrightText: the TeamDojo authors
 SPDX-License-Identifier: Apache-2.0
 */
package com.iteratec.teamdojo.service.custom;

import com.iteratec.teamdojo.service.dto.TeamDTO;
import com.iteratec.teamdojo.service.dto.custom.TeamScoreDTO;

/**
 * This service calculates the team-score for given teams
 */
public interface CustomTeamScoreService {
    /**
     * Calculates the score for given team
     *
     * @param t must not be {@code null}
     * @return never {@code null}
     */
    TeamScoreDTO calculateTeamScore(TeamDTO t);
}
