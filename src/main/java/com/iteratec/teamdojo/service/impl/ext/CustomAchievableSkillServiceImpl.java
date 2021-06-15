package com.iteratec.teamdojo.service.impl.ext;

import com.google.common.collect.Lists;
import com.iteratec.teamdojo.domain.Badge;
import com.iteratec.teamdojo.domain.Level;
import com.iteratec.teamdojo.domain.Team;
import com.iteratec.teamdojo.domain.enumeration.ApplicationMode;
import com.iteratec.teamdojo.repository.TeamRepository;
import com.iteratec.teamdojo.repository.ext.ExtendedBadgeRepository;
import com.iteratec.teamdojo.repository.ext.ExtendedSkillRepository;
import com.iteratec.teamdojo.service.TeamSkillService;
import com.iteratec.teamdojo.service.dto.TeamSkillDTO;
import com.iteratec.teamdojo.service.dto.ext.AchievableSkillDTO;
import com.iteratec.teamdojo.service.ext.CustomAchievableSkillService;
import com.iteratec.teamdojo.service.ext.ExtendedActivityService;
import com.iteratec.teamdojo.service.ext.ExtendedOrganisationService;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@Slf4j
public class CustomAchievableSkillServiceImpl implements CustomAchievableSkillService {

    private static final List<String> ALL_FILTER = Lists.newArrayList("COMPLETE", "INCOMPLETE");

    private final ExtendedSkillRepository skillRepository;

    private final TeamRepository teamRepository;

    private final ExtendedBadgeRepository badgeRepository;

    private final TeamSkillService teamSkillService;

    private final ExtendedActivityService activityService;

    private final ExtendedOrganisationService organisationService;

    public CustomAchievableSkillServiceImpl(
        ExtendedSkillRepository skillRepository,
        TeamRepository teamRepository,
        ExtendedBadgeRepository badgeRepository,
        TeamSkillService teamSkillService,
        ExtendedActivityService activityService,
        ExtendedOrganisationService organisationService
    ) {
        this.skillRepository = skillRepository;
        this.teamRepository = teamRepository;
        this.badgeRepository = badgeRepository;
        this.teamSkillService = teamSkillService;
        this.activityService = activityService;
        this.organisationService = organisationService;
    }

    @Override
    public Page<AchievableSkillDTO> findAllByTeamAndLevelAndBadge(
        Long teamId,
        List<Long> levelIds,
        List<Long> badgeIds,
        List<String> filter,
        Pageable pageable
    ) {
        List<String> queryFilter = getQueryFilter(filter);
        return levelIds.isEmpty() && badgeIds.isEmpty()
            ? findAllTeamRelated(teamId, queryFilter, pageable)
            : queryRepository(teamId, levelIds, badgeIds, queryFilter, pageable);
    }

    private List<String> getQueryFilter(List<String> filter) {
        List<String> queryFilter = new ArrayList<>();
        if (filter.isEmpty()) {
            queryFilter.addAll(ALL_FILTER);
        } else {
            queryFilter.addAll(filter);
        }
        return queryFilter;
    }

    @Override
    public Page<AchievableSkillDTO> findAllByTeamAndDimension(Long teamId, Long dimensionId, List<String> filter, Pageable pageable) {
        List<String> queryFilter = getQueryFilter(filter);
        return skillRepository.findAchievableSkillsByDimensions(teamId, dimensionId, queryFilter, pageable);
    }

    @Override
    public AchievableSkillDTO updateAchievableSkill(Long teamId, AchievableSkillDTO achievableSkill) {
        AchievableSkillDTO originSkill = skillRepository.findAchievableSkill(teamId, achievableSkill.getSkillId());

        TeamSkillDTO teamSkill = new TeamSkillDTO();
        teamSkill.setId((achievableSkill.getTeamSkillId() != null) ? achievableSkill.getTeamSkillId() : originSkill.getTeamSkillId());
        teamSkill.getTeam().setId(teamId);
        teamSkill.getSkill().setId(achievableSkill.getSkillId());
        teamSkill.setCompletedAt(achievableSkill.getAchievedAt());
        teamSkill.setVerifiedAt(achievableSkill.getVerifiedAt());
        teamSkill.setVote((achievableSkill.getVote() != null) ? achievableSkill.getVote() : 0);
        teamSkill.setVoters(achievableSkill.getVoters());
        teamSkill.setIrrelevant(achievableSkill.isIrrelevant());

        teamSkill = teamSkillService.save(teamSkill);

        if (
            (originSkill == null && teamSkill.getCompletedAt() != null) ||
            (originSkill != null && originSkill.getAchievedAt() == null && teamSkill.getCompletedAt() != null)
        ) {
            activityService.createForCompletedSkill(teamSkill);
        }

        if (
            organisationService.getCurrentOrganisation().getApplicationMode().equals(ApplicationMode.PERSON) &&
            teamSkill.getCompletedAt() == null &&
            teamSkill.getVote() == 1 &&
            (originSkill == null || !originSkill.getVote().equals(teamSkill.getVote()))
        ) {
            activityService.createForSuggestedSkill(teamSkill);
        }

        return skillRepository.findAchievableSkill(teamId, achievableSkill.getSkillId());
    }

    public AchievableSkillDTO findAchievableSkill(Long teamId, Long skillId) {
        return skillRepository.findAchievableSkill(teamId, skillId);
    }

    private Page<AchievableSkillDTO> findAllTeamRelated(Long teamId, List<String> filter, Pageable pageable) {
        Team team = getTeam(teamId);
        List<Long> relatedLevelIds = getTeamRelatedLevelIds(team);
        List<Long> relatedBadgeIds = getTeamRelatedBadgeIds(team);
        relatedBadgeIds.addAll(getDimensionlessBadgeIds());
        return queryRepository(teamId, relatedLevelIds, relatedBadgeIds, filter, pageable);
    }

    private Team getTeam(Long teamId) {
        return teamRepository.findById(teamId).orElseThrow(NoSuchElementException::new);
    }

    private Page<AchievableSkillDTO> queryRepository(
        Long teamId,
        List<Long> levelIds,
        List<Long> badgeIds,
        List<String> filter,
        Pageable pageable
    ) {
        if (!levelIds.isEmpty() && !badgeIds.isEmpty()) {
            return skillRepository.findAchievableSkillsByLevelsAndBadges(teamId, levelIds, badgeIds, filter, pageable);
        } else if (!levelIds.isEmpty()) {
            return skillRepository.findAchievableSkillsByLevels(teamId, levelIds, filter, pageable);
        } else if (!badgeIds.isEmpty()) {
            return skillRepository.findAchievableSkillsByBadges(teamId, badgeIds, filter, pageable);
        }
        return Page.empty();
    }

    private List<Long> getTeamRelatedLevelIds(Team team) {
        return team
            .getParticipations()
            .stream()
            .flatMap(dimension -> dimension.getLevels().stream().map(Level::getId))
            .collect(Collectors.toList());
    }

    private List<Long> getTeamRelatedBadgeIds(Team team) {
        return team
            .getParticipations()
            .stream()
            .flatMap(dimension -> dimension.getBadges().stream().map(Badge::getId))
            .distinct()
            .collect(Collectors.toList());
    }

    private List<Long> getDimensionlessBadgeIds() {
        return badgeRepository.findAllByDimensionsIsNull().stream().map(Badge::getId).collect(Collectors.toList());
    }
}
