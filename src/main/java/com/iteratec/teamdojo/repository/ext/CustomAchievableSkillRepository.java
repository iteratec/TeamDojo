package com.iteratec.teamdojo.repository.ext;

import com.iteratec.teamdojo.domain.Skill;
import com.iteratec.teamdojo.service.dto.ext.AchievableSkillDTO;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.RepositoryDefinition;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * This repository only declares some hand rolled queries to find {@link AchievableSkillDTO}
 * <p>
 * This interface does not extend an existing JPA repository because it only contains hand-rolled SQL queries and so we
 * do not need dependencies to these interfaces. But at least we need to extend either {@literal org.springframework.data.repository.Repository}
 * or annotate with {@link org.springframework.data.repository.RepositoryDefinition}. Unless Spring will not generate the proxy from this interface.
 * </p>
 */
@Repository
// It is mandatory to add types here, unless Spring will not boot.
// for the domain class it is also mandatory to add a managed entity.
// Since we do not have a AchievableSkill entity we use Skill as workaround.
@RepositoryDefinition(domainClass = Skill.class, idClass = Void.class)
// The methods should return the managed entity from above. But since we do not have one
// We use a DTO directly and suppress the warning here.
@SuppressWarnings({ "unused", "SpringDataRepositoryMethodReturnTypeInspection" })
public interface CustomAchievableSkillRepository {
    @Query(
        "SELECT DISTINCT" +
        " new com.iteratec.teamdojo.service.dto.ext.AchievableSkillDTO(t.id, s.id, s.title, s.description, t.completedAt, t.verifiedAt, t.vote, t.voters, t.irrelevant, s.score, s.expiryPeriod, s.rateScore, s.rateCount)" +
        " FROM Skill s" +
        " LEFT JOIN s.teams t ON t.team.id = :teamId" +
        " LEFT JOIN s.levels l" +
        " LEFT JOIN s.badges b" +
        " WHERE (l.level.id IN :levelIds" +
        " OR b.badge.id IN :badgeIds)" +
        " AND (" +
        "  ( ('COMPLETE' IN :filter) AND (t.completedAt is not null) )" +
        "   OR ( ('INCOMPLETE' IN :filter) AND (t.completedAt is null) )" +
        " )" +
        " ORDER BY s.title"
    )
    Page<AchievableSkillDTO> findAchievableSkillsByLevelsAndBadges(
        @Param("teamId") Long teamId,
        @Param("levelIds") List<Long> levelIds,
        @Param("badgeIds") List<Long> badgeIds,
        @Param("filter") List<String> filter,
        Pageable pageable
    );

    @Query(
        "SELECT DISTINCT" +
        " new com.iteratec.teamdojo.service.dto.ext.AchievableSkillDTO(t.id, s.id, s.title, s.description, t.completedAt, t.verifiedAt, t.vote, t.voters, t.irrelevant, s.score, s.expiryPeriod, s.rateScore, s.rateCount)" +
        " FROM Skill s" +
        " LEFT JOIN s.teams t ON t.team.id = :teamId" +
        " LEFT JOIN s.levels l" +
        " WHERE l.level.dimension.id = :dimensionId" +
        " AND (" +
        "  ( ('COMPLETE' IN :filter)  AND (t.completedAt is not null) )" +
        "   OR ( ('INCOMPLETE' IN :filter) AND (t.completedAt is null) )" +
        " )" +
        " ORDER BY s.title"
    )
    Page<AchievableSkillDTO> findAchievableSkillsByDimensions(
        @Param("teamId") Long teamId,
        @Param("dimensionId") Long dimensionId,
        @Param("filter") List<String> filter,
        Pageable pageable
    );

    @Query(
        "SELECT DISTINCT" +
        " new com.iteratec.teamdojo.service.dto.ext.AchievableSkillDTO(t.id, s.id, s.title, s.description, t.completedAt, t.verifiedAt, t.vote, t.voters, t.irrelevant, s.score, s.expiryPeriod, s.rateScore, s.rateCount)" +
        " FROM Skill s" +
        " LEFT JOIN s.teams t ON t.team.id = :teamId" +
        " LEFT JOIN s.levels l" +
        " WHERE l.level.id IN :levelIds" +
        " AND (" +
        "  ( ('COMPLETE' IN :filter)  AND (t.completedAt is not null) )" +
        "   OR ( ('INCOMPLETE' IN :filter) AND (t.completedAt is null) )" +
        " )" +
        " ORDER BY s.title"
    )
    Page<AchievableSkillDTO> findAchievableSkillsByLevels(
        @Param("teamId") Long teamId,
        @Param("levelIds") List<Long> levelIds,
        @Param("filter") List<String> filter,
        Pageable pageable
    );

    @Query(
        "SELECT DISTINCT" +
        " new com.iteratec.teamdojo.service.dto.ext.AchievableSkillDTO(t.id, s.id, s.title, s.description, t.completedAt, t.verifiedAt, t.vote, t.voters, t.irrelevant, s.score, s.expiryPeriod, s.rateScore, s.rateCount)" +
        " FROM Skill s" +
        " LEFT JOIN s.teams t ON t.team.id = :teamId" +
        " LEFT JOIN s.badges b" +
        " WHERE b.badge.id IN :badgeIds" +
        " AND (" +
        "  ( ('COMPLETE' IN :filter) AND (t.completedAt is not null) )" +
        "   OR ( ('INCOMPLETE' IN :filter) AND (t.completedAt is null) )" +
        " )" +
        " ORDER BY s.title"
    )
    Page<AchievableSkillDTO> findAchievableSkillsByBadges(
        @Param("teamId") Long teamId,
        @Param("badgeIds") List<Long> badgeIds,
        @Param("filter") List<String> filter,
        Pageable pageable
    );

    @Query(
        "SELECT DISTINCT" +
        " new com.iteratec.teamdojo.service.dto.ext.AchievableSkillDTO(t.id, s.id, s.title, s.description, t.completedAt, t.verifiedAt, t.vote, t.voters, t.irrelevant, s.score, s.expiryPeriod, s.rateScore, s.rateCount)" +
        " FROM Skill s" +
        " LEFT JOIN s.teams t ON t.team.id = :teamId" +
        " WHERE s.id = :skillId"
    )
    AchievableSkillDTO findAchievableSkill(@Param("teamId") Long teamId, @Param("skillId") Long skillId);
}
