package com.iteratec.teamdojo.repository.custom;

import com.iteratec.teamdojo.domain.Skill;
import com.iteratec.teamdojo.domain.custom.AchievableSkill;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.RepositoryDefinition;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * This repository only declares some hand rolled queries to find {@link AchievableSkill achievable skills}
 *
 * <p>This interface does not extend an existing JPA repository because it only contains hand-rolled SQL queries and so
 * we do not need dependencies to these interfaces. But at least we need to extend either
 * {@literal org.springframework.data.repository.Repository} or annotate with {@link RepositoryDefinition}. Unless Spring
 * will not generate the proxy from this interface. Since extending {@literal org.springframework.data.repository.Repository}
 * would conflict with {@link Repository} annotation we use {@link RepositoryDefinition}.
 * </p>
 */
@Repository
// It is mandatory to add types here, unless Spring will not boot.
// For the domain class it is also mandatory to add a managed entity.
// Since the AchievableSkill entity is not managed by JPA we use Skill as workaround.
// We ignore the warning that the type returned by this repository does not match the domainClass
// in @RepositoryDefinition.
@RepositoryDefinition(domainClass = Skill.class, idClass = Void.class)
@SuppressWarnings({ "unused", "SpringDataRepositoryMethodReturnTypeInspection" })
public interface CustomAchievableSkillRepository {
    @Query(
        """
            SELECT DISTINCT
            new com.iteratec.teamdojo.domain.custom.AchievableSkill(t.id, s.id, s.titleEN, s.titleDE, s.descriptionEN, s.descriptionDE, t.completedAt, t.verifiedAt, t.vote, t.voters, t.irrelevant, s.score, s.expiryPeriod, s.rateScore, s.rateCount)
            FROM Skill s
            LEFT JOIN s.teams t ON t.team.id = :teamId
            LEFT JOIN s.levels l
            LEFT JOIN s.badges b
            WHERE (l.level.id IN :levelIds
            OR b.badge.id IN :badgeIds)
            AND (
             ( ('COMPLETE' IN :filter) AND (t.completedAt is not null) )
              OR ( ('INCOMPLETE' IN :filter) AND (t.completedAt is null) )
            )
            ORDER BY s.titleEN
            """
    )
    // FIXME: #8 Parameterize ORDER BY to use either EN or DE title depending on frontend setting.
    Page<AchievableSkill> findAchievableSkillsByLevelsAndBadges(
        @Param("teamId") Long teamId,
        @Param("levelIds") List<Long> levelIds,
        @Param("badgeIds") List<Long> badgeIds,
        @Param("filter") List<String> filter,
        Pageable pageable
    );

    @Query(
        """
            SELECT DISTINCT
            new com.iteratec.teamdojo.domain.custom.AchievableSkill(t.id, s.id, s.titleEN, s.titleDE, s.descriptionEN, s.descriptionDE, t.completedAt, t.verifiedAt, t.vote, t.voters, t.irrelevant, s.score, s.expiryPeriod, s.rateScore, s.rateCount)
            FROM Skill s
            LEFT JOIN s.teams t ON t.team.id = :teamId
            LEFT JOIN s.levels l
            WHERE l.level.dimension.id = :dimensionId
            AND (
             ( ('COMPLETE' IN :filter)  AND (t.completedAt is not null) )
              OR ( ('INCOMPLETE' IN :filter) AND (t.completedAt is null) )
            )
            ORDER BY s.titleEN
            """
    )
    // FIXME: #8 Parameterize ORDER BY to use either EN or DE title depending on frontend setting.
    Page<AchievableSkill> findAchievableSkillsByDimensions(
        @Param("teamId") Long teamId,
        @Param("dimensionId") Long dimensionId,
        @Param("filter") List<String> filter,
        Pageable pageable
    );

    @Query(
        """
            SELECT DISTINCT
            new com.iteratec.teamdojo.domain.custom.AchievableSkill(t.id, s.id, s.titleEN, s.titleDE, s.descriptionEN, s.descriptionDE, t.completedAt, t.verifiedAt, t.vote, t.voters, t.irrelevant, s.score, s.expiryPeriod, s.rateScore, s.rateCount)
            FROM Skill s
            LEFT JOIN s.teams t ON t.team.id = :teamId
            LEFT JOIN s.levels l
            WHERE l.level.id IN :levelIds
            AND (
             ( ('COMPLETE' IN :filter)  AND (t.completedAt is not null) )
              OR ( ('INCOMPLETE' IN :filter) AND (t.completedAt is null) )
            )
            ORDER BY s.titleEN
            """
    )
    // FIXME: #8 Parameterize ORDER BY to use either EN or DE title depending on frontend setting.
    Page<AchievableSkill> findAchievableSkillsByLevels(
        @Param("teamId") Long teamId,
        @Param("levelIds") List<Long> levelIds,
        @Param("filter") List<String> filter,
        Pageable pageable
    );

    @Query(
        """
            SELECT DISTINCT
            new com.iteratec.teamdojo.domain.custom.AchievableSkill(t.id, s.id, s.titleEN, s.titleDE, s.descriptionEN, s.descriptionDE, t.completedAt, t.verifiedAt, t.vote, t.voters, t.irrelevant, s.score, s.expiryPeriod, s.rateScore, s.rateCount)
            FROM Skill s
            LEFT JOIN s.teams t ON t.team.id = :teamId
            LEFT JOIN s.badges b
            WHERE b.badge.id IN :badgeIds
            AND (
             ( ('COMPLETE' IN :filter) AND (t.completedAt is not null) )
              OR ( ('INCOMPLETE' IN :filter) AND (t.completedAt is null) )
            )
            ORDER BY s.titleEN
            """
    )
    // FIXME: #8 Parameterize ORDER BY to use either EN or DE title depending on frontend setting.
    Page<AchievableSkill> findAchievableSkillsByBadges(
        @Param("teamId") Long teamId,
        @Param("badgeIds") List<Long> badgeIds,
        @Param("filter") List<String> filter,
        Pageable pageable
    );

    @Query(
        """
            SELECT DISTINCT
            new com.iteratec.teamdojo.domain.custom.AchievableSkill(t.id, s.id, s.titleEN, s.titleDE, s.descriptionEN, s.descriptionDE, t.completedAt, t.verifiedAt, t.vote, t.voters, t.irrelevant, s.score, s.expiryPeriod, s.rateScore, s.rateCount)
            FROM Skill s
            LEFT JOIN s.teams t ON t.team.id = :teamId
            WHERE s.id = :skillId
            """
    )
    AchievableSkill findAchievableSkill(@Param("teamId") Long teamId, @Param("skillId") Long skillId);
}
