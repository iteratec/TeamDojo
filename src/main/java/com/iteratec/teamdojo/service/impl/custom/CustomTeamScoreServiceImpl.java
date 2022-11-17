/*
 SPDX-FileCopyrightText: the TeamDojo authors
 SPDX-License-Identifier: Apache-2.0
 */
package com.iteratec.teamdojo.service.impl.custom;

import com.iteratec.teamdojo.service.custom.CustomTeamScoreService;
import com.iteratec.teamdojo.service.dto.*;
import com.iteratec.teamdojo.service.dto.custom.TeamScoreDTO;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
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
    public TeamScoreDTO calculateTeamScore(
        TeamDTO t,
        List<TeamSkillDTO> teamSkills,
        List<SkillDTO> skills,
        List<BadgeDTO> badges,
        List<BadgeSkillDTO> badgeSkills,
        List<LevelDTO> levels,
        List<LevelSkillDTO> levelSkills
    ) {
        final var groupedBadgeSkills = this.groupBadgeSkillsByBadgeId(badgeSkills);
        final var groupedLevelskills = this.groupLevelSkillsByLevelId(levelSkills);

        final var skillScore = this.calcSkillScore(t, teamSkills, skills);
        final var levelBonus = this.calcLevelBonus(t, teamSkills, skills, groupedLevelskills);
        final var badgeBonus = this.calcBadgeBonus(t, teamSkills, skills, badges, groupedBadgeSkills);
        final var totalScore = skillScore + levelBonus + badgeBonus;
        return new TeamScoreDTO(t, totalScore);
    }

    private Long calcSkillScore(TeamDTO t, List<TeamSkillDTO> teamSkills, List<SkillDTO> skills) {
        var score = skills.stream().filter(skill -> this.isSkillCompleted(teamSkills, skill)).mapToLong(SkillDTO::getScore).sum();
        return score;
    }

    private Long calcLevelBonus(
        TeamDTO t,
        List<TeamSkillDTO> teamSkills,
        List<SkillDTO> skills,
        Map<Long, List<LevelSkillDTO>> groupedLevelSkills
    ) {
        var score = t.getParticipations();

        return 0L;
    }

    private Long calcBadgeBonus(
        TeamDTO t,
        List<TeamSkillDTO> teamSkills,
        List<SkillDTO> skills,
        List<BadgeDTO> badges,
        Map<Long, List<BadgeSkillDTO>> groupedBadgeSkills
    ) {
        return 0L;
    }

    private boolean isSkillCompleted(List<TeamSkillDTO> teamSkills, SkillDTO skill) {
        return false;
    }

    private Map<Long, List<LevelSkillDTO>> groupLevelSkillsByLevelId(List<LevelSkillDTO> levelSkills) {
        return levelSkills.stream().collect(Collectors.groupingBy(levelSkill -> levelSkill.getLevel().getId()));
    }

    private Map<Long, List<BadgeSkillDTO>> groupBadgeSkillsByBadgeId(List<BadgeSkillDTO> badgeSkills) {
        return badgeSkills.stream().collect(Collectors.groupingBy(badgeSkill -> badgeSkill.getBadge().getId()));
    }
}
