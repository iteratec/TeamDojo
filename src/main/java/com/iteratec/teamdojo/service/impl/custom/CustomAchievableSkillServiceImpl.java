package com.iteratec.teamdojo.service.impl.custom;

import com.iteratec.teamdojo.domain.Badge;
import com.iteratec.teamdojo.domain.Level;
import com.iteratec.teamdojo.domain.Team;
import com.iteratec.teamdojo.domain.enumeration.custom.SkillStatus;
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
import java.util.Arrays;
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

    private static final List<String> ALL_FILTER = Arrays.asList("COMPLETE", "INCOMPLETE");

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
    public AchievableSkillDTO updateAchievableSkill(final Long teamId, final AchievableSkillDTO achievableSkill) {
        final var skillId = achievableSkill.getSkillId();
        // XXX: #79 This seems to be redundant to the call in the return statement.
        final AchievableSkillDTO nullableOriginSkill = achievableSkillMapper.toDto(
            achievableSkillRepository.findAchievableSkill(teamId, skillId)
        );
        final Long teamSkillId;

        if (achievableSkill.getTeamSkillId() != null) {
            teamSkillId = achievableSkill.getTeamSkillId();
        } else {
            if (nullableOriginSkill == null) {
                log.warn(
                    "The 'nullableOriginSkill' fetched by teamId={} and skillId={} was null, but is needed to get the teamSkillId!",
                    teamId,
                    skillId
                );
                return new AchievableSkillDTO(); // Return empty default to prevent NPE in calling code.
            } else {
                teamSkillId = nullableOriginSkill.getTeamSkillId();
            }
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

        final var teamSkill = new TeamSkillDTO();
        teamSkill.setId(teamSkillId);
        teamSkill.setTeam(teamMapper.toDto(team.get()));
        teamSkill.setSkill(skillMapper.toDto(skill.get()));
        teamSkill.setCompletedAt(achievableSkill.getAchievedAt());
        teamSkill.setVerifiedAt(achievableSkill.getVerifiedAt());
        teamSkill.setVote((achievableSkill.getVote() != null) ? achievableSkill.getVote() : 0);
        teamSkill.setVoters(achievableSkill.getVoters());
        teamSkill.setIrrelevant(achievableSkill.isIrrelevant());
        teamSkill.setSkillStatus(achievableSkill.getSkillStatus());

        final var persistedTeamSkill = teamSkillService.save(teamSkill);

        if (isCreateForCompletedSkill(nullableOriginSkill, persistedTeamSkill)) {
            activityService.createForCompletedSkill(persistedTeamSkill);
        }

        // XXX: #79 This seem to be redundant to call retrieving nullableOriginSkill above. Since in between
        //      no data is written to achievableSkillRepository and neither teamId nor skillId is changed.
        return achievableSkillMapper.toDto(achievableSkillRepository.findAchievableSkill(teamId, skillId));
    }

    final boolean isCreateForCompletedSkill(final AchievableSkillDTO originSkill, final TeamSkillDTO teamSkill) {
        return (
            isOriginSkillNullAndTeamSkillNotNullAndTeamSkillCompletedAtIsSet(originSkill, teamSkill) ||
            areBothSkillsNotNullAndOriginSkillAchievedAtIsSet(originSkill, teamSkill)
        );
    }

    final boolean isOriginSkillNullAndTeamSkillNotNullAndTeamSkillCompletedAtIsSet(
        final AchievableSkillDTO originSkill,
        final TeamSkillDTO teamSkill
    ) {
        if (originSkill != null) {
            return false;
        }

        if (teamSkill == null) {
            return false;
        }

        //noinspection RedundantIfStatement Early return is better readable.
        if (teamSkill.getCompletedAt() == null) {
            return false;
        }

        return true;
    }

    final boolean areBothSkillsNotNullAndOriginSkillAchievedAtIsSet(final AchievableSkillDTO originSkill, final TeamSkillDTO teamSkill) {
        if (originSkill == null) {
            return false;
        }

        if (originSkill.getAchievedAt() != null) {
            return false;
        }

        if (teamSkill == null) {
            return false;
        }

        //noinspection RedundantIfStatement Early return is better readable.
        if (teamSkill.getCompletedAt() == null) {
            return false;
        }

        return true;
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
