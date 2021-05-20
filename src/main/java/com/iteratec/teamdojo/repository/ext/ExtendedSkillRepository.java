package com.iteratec.teamdojo.repository.ext;

import com.iteratec.teamdojo.repository.SkillRepository;
import com.iteratec.teamdojo.service.dto.ext.AchievableSkillDTO;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@SuppressWarnings("unused")
@Repository
public interface ExtendedSkillRepository extends SkillRepository {
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