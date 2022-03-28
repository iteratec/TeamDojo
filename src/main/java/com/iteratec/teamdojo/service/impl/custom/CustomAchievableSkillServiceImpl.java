package com.iteratec.teamdojo.service.impl.custom;

import com.google.common.collect.Lists;
import com.iteratec.teamdojo.domain.Badge;
import com.iteratec.teamdojo.domain.Level;
import com.iteratec.teamdojo.domain.Team;
import com.iteratec.teamdojo.domain.enumeration.SkillStatus;
import com.iteratec.teamdojo.repository.SkillRepository;
import com.iteratec.teamdojo.repository.TeamRepository;
import com.iteratec.teamdojo.repository.custom.CustomAchievableSkillRepository;
import com.iteratec.teamdojo.repository.custom.ExtendedBadgeRepository;
import com.iteratec.teamdojo.service.TeamSkillService;
import com.iteratec.teamdojo.service.custom.CustomAchievableSkillService;
import com.iteratec.teamdojo.service.custom.ExtendedActivityService;
import com.iteratec.teamdojo.service.dto.TeamSkillDTO;
import com.iteratec.teamdojo.service.dto.custom.AchievableSkillDTO;
import com.iteratec.teamdojo.service.mapper.SkillMapper;
import com.iteratec.teamdojo.service.mapper.TeamMapper;
import com.iteratec.teamdojo.service.mapper.custom.AchievableSkillMapper;
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

    private final CustomAchievableSkillRepository achievableSkillRepository;

    private final TeamRepository teamRepository;

    private final ExtendedBadgeRepository badgeRepository;

    private final SkillRepository skillRepository;

    private final TeamSkillService teamSkillService;

    private final ExtendedActivityService activityService;

    private final AchievableSkillMapper achievableSkillMapper;

    private final TeamMapper teamMapper;

    private final SkillMapper skillMapper;

    public CustomAchievableSkillServiceImpl(
        CustomAchievableSkillRepository achievableSkillRepository,
        TeamRepository teamRepository,
        ExtendedBadgeRepository badgeRepository,
        SkillRepository skillRepository,
        TeamSkillService teamSkillService,
        ExtendedActivityService activityService,
        AchievableSkillMapper achievableSkillMapper,
        TeamMapper teamMapper,
        SkillMapper skillMapper
    ) {
        this.achievableSkillRepository = achievableSkillRepository;
        this.teamRepository = teamRepository;
        this.badgeRepository = badgeRepository;
        this.skillRepository = skillRepository;
        this.teamSkillService = teamSkillService;
        this.activityService = activityService;
        this.achievableSkillMapper = achievableSkillMapper;
        this.teamMapper = teamMapper;
        this.skillMapper = skillMapper;
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
        final var found = achievableSkillRepository.findAchievableSkillsByDimensions(teamId, dimensionId, queryFilter, pageable);
        return found.map(achievableSkillMapper::toDto);
    }

    @Override
    public AchievableSkillDTO updateAchievableSkill(Long teamId, AchievableSkillDTO achievableSkill) {
        final var skillId = achievableSkill.getSkillId();

        final AchievableSkillDTO originSkill = achievableSkillMapper.toDto(achievableSkillRepository.findAchievableSkill(teamId, skillId));

        final Long id;

        if (achievableSkill.getTeamSkillId() != null) {
            id = achievableSkill.getTeamSkillId();
        } else {
            id = originSkill.getTeamSkillId();
        }

        final var team = teamRepository.findById(teamId);

        if (team.isEmpty()) {
            log.warn("There is no such team with id {}.", teamId);
            return new AchievableSkillDTO(); // Return empty default to prevent NPE in calling code.
        }

        final var skill = skillRepository.findById(skillId);

        if (skill.isEmpty()) {
            log.warn("There is no such skill with id {}.", skillId);
            return new AchievableSkillDTO(); // Return empty default to prevent NPE in calling code.
        }

        TeamSkillDTO teamSkill = new TeamSkillDTO();
        teamSkill.setId(id);
        teamSkill.setTeam(teamMapper.toDto(team.get()));
        teamSkill.setSkill(skillMapper.toDto(skill.get()));
        teamSkill.setCompletedAt(achievableSkill.getAchievedAt());
        teamSkill.setVerifiedAt(achievableSkill.getVerifiedAt());
        teamSkill.setVote((achievableSkill.getVote() != null) ? achievableSkill.getVote() : 0);
        teamSkill.setVoters(achievableSkill.getVoters());
        teamSkill.setIrrelevant(achievableSkill.isIrrelevant());
        teamSkill.setSkillStatus(SkillStatus.ACHIEVED);

        teamSkill = teamSkillService.save(teamSkill);

        if (isFoo(originSkill, teamSkill) || isBar(originSkill, teamSkill)) {
            activityService.createForCompletedSkill(teamSkill);
        }

        return achievableSkillMapper.toDto(achievableSkillRepository.findAchievableSkill(teamId, skillId));
    }

    // FIXME: #79 Give better name and unit test.
    final boolean isFoo(final AchievableSkillDTO originSkill, final TeamSkillDTO teamSkill) {
        return originSkill == null && teamSkill != null && teamSkill.getCompletedAt() != null;
    }

    // FIXME: #79 Give better name and unit test.
    final boolean isBar(final AchievableSkillDTO originSkill, final TeamSkillDTO teamSkill) {
        return originSkill != null && originSkill.getAchievedAt() == null && teamSkill != null && teamSkill.getCompletedAt() != null;
    }

    public AchievableSkillDTO findAchievableSkill(Long teamId, Long skillId) {
        return achievableSkillMapper.toDto(achievableSkillRepository.findAchievableSkill(teamId, skillId));
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
            return achievableSkillRepository
                .findAchievableSkillsByLevelsAndBadges(teamId, levelIds, badgeIds, filter, pageable)
                .map(achievableSkillMapper::toDto);
        } else if (!levelIds.isEmpty()) {
            return achievableSkillRepository
                .findAchievableSkillsByLevels(teamId, levelIds, filter, pageable)
                .map(achievableSkillMapper::toDto);
        } else if (!badgeIds.isEmpty()) {
            return achievableSkillRepository
                .findAchievableSkillsByBadges(teamId, badgeIds, filter, pageable)
                .map(achievableSkillMapper::toDto);
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
