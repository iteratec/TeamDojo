/*
 SPDX-FileCopyrightText: the TeamDojo authors
 SPDX-License-Identifier: Apache-2.0
 */
package com.iteratec.teamdojo.service.impl.custom;

import com.iteratec.teamdojo.domain.Badge;
import com.iteratec.teamdojo.domain.Skill;
import com.iteratec.teamdojo.repository.BadgeRepository;
import com.iteratec.teamdojo.repository.SkillRepository;
import com.iteratec.teamdojo.service.TeamSkillQueryService;
import com.iteratec.teamdojo.service.criteria.TeamSkillCriteria;
import com.iteratec.teamdojo.service.custom.CustomTeamScoreService;
import com.iteratec.teamdojo.service.dto.TeamDTO;
import com.iteratec.teamdojo.service.dto.TeamSkillDTO;
import com.iteratec.teamdojo.service.dto.custom.TeamScoreDTO;
import java.util.Collection;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import tech.jhipster.service.filter.LongFilter;

@Service
@Slf4j
public class CustomTeamScoreServiceImpl implements CustomTeamScoreService {

    private final TeamSkillQueryService teamSkillQueryService;
    private final SkillRepository skillRepository;
    private final BadgeRepository badgeRepository;

    public CustomTeamScoreServiceImpl(
        TeamSkillQueryService teamSkillQueryService,
        SkillRepository skillRepository,
        BadgeRepository badgeRepository
    ) {
        this.teamSkillQueryService = teamSkillQueryService;
        this.skillRepository = skillRepository;
        this.badgeRepository = badgeRepository;
    }

    @Override
    public TeamScoreDTO calculateTeamScore(@NonNull final TeamDTO t) {
        final var teamSkills = this.retrieveTeamSkills(t.getId());
        final var allSkills = this.retrieveAllSkills();
        final var allBadges = this.retrieveAllBadges();

        final var achieved = calculateAchieved();
        final var required = calculateRequired();
        final var totalScore = calculateTotalScore();
        final var progressInPercent = calculateProgressInPercent();
        final var completed = calculateCompleted(achieved, required);
        return new TeamScoreDTO(achieved, required, totalScore, progressInPercent, completed);
    }

    private Collection<Badge> retrieveAllBadges() {
        return this.badgeRepository.findAll();
    }

    int calculateTotalScore() {
        return 0;
    }

    int calculateRequired() {
        return 0;
    }

    int calculateAchieved() {
        return 0;
    }

    float calculateProgressInPercent() {
        return 0.0f;
    }

    boolean calculateCompleted(final int achieved, final int required) {
        return achieved >= required;
    }

    private Collection<TeamSkillDTO> retrieveTeamSkills(final long teamId) {
        final var teamSkillCriteria = new TeamSkillCriteria();
        final var longFilter = new LongFilter();
        longFilter.setEquals(teamId);
        teamSkillCriteria.setTeamId(longFilter);
        return this.teamSkillQueryService.findByCriteria(teamSkillCriteria);
    }

    private Collection<Skill> retrieveAllSkills() {
        return this.skillRepository.findAll();
    }
}
