/*
 SPDX-FileCopyrightText: the TeamDojo authors
 SPDX-License-Identifier: Apache-2.0
 */
package com.iteratec.teamdojo.service.custom;

import com.iteratec.teamdojo.service.dto.*;
import com.iteratec.teamdojo.service.dto.custom.TeamScoreDTO;
import java.util.List;
import lombok.NonNull;

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

    /**
     * Calculates the score for given team
     *
     * @param t must not be {@code null}
     * @param teamSkills skills already achieved by the team
     * @param skills all available skills
     * @param badges all available badges
     * @return never {@code null}
     */
    TeamScoreDTO calculateTeamScore(
        TeamDTO t,
        List<TeamSkillDTO> teamSkills,
        List<SkillDTO> skills,
        List<BadgeDTO> badges,
        List<BadgeSkillDTO> badgeSkills,
        List<LevelDTO> levels,
        List<LevelSkillDTO> levelSkills
    );
}
