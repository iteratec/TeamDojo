/*
 SPDX-FileCopyrightText: the TeamDojo authors
 SPDX-License-Identifier: Apache-2.0
 */
package com.iteratec.teamdojo.service.impl.custom;

import com.iteratec.teamdojo.service.custom.CustomTeamScoreService;
import com.iteratec.teamdojo.service.dto.BadgeDTO;
import com.iteratec.teamdojo.service.dto.SkillDTO;
import com.iteratec.teamdojo.service.dto.TeamDTO;
import com.iteratec.teamdojo.service.dto.TeamSkillDTO;
import com.iteratec.teamdojo.service.dto.custom.TeamScoreDTO;
import java.util.List;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class CustomTeamScoreServiceImpl implements CustomTeamScoreService {

    @Override
    public TeamScoreDTO calculateTeamScore(TeamDTO t) {
        return TeamScoreDTO.NULL;
    }

    @Override
    public TeamScoreDTO calculateTeamScore(TeamDTO t, List<TeamSkillDTO> teamSkills, List<SkillDTO> skills, List<BadgeDTO> badges) {
        final var skillScore = this.calcSkillScore(t, teamSkills, skills);
        final var levelBonus = this.calcLevelBonus(t, teamSkills, skills);
        final var badgeBonus = this.calcBadgeBonus(t, teamSkills, skills, badges);
        final var totalScore = skillScore + levelBonus + badgeBonus;
        return new TeamScoreDTO(t, totalScore);
    }

    private Long calcSkillScore(TeamDTO t, List<TeamSkillDTO> teamSkills, List<SkillDTO> skills) {
        return 0L;
    }

    private Long calcLevelBonus(TeamDTO t, List<TeamSkillDTO> teamSkills, List<SkillDTO> skills) {
        return 0L;
    }

    private Long calcBadgeBonus(TeamDTO t, List<TeamSkillDTO> teamSkills, List<SkillDTO> skills, List<BadgeDTO> badges) {
        return 0L;
    }
}
