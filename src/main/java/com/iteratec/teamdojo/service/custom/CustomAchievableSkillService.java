package com.iteratec.teamdojo.service.custom;

import com.iteratec.teamdojo.service.dto.custom.AchievableSkillDTO;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CustomAchievableSkillService {
    /**
     * Get the skills that are achievable for the given team and belong to one of the given levels or badges
     */
    Page<AchievableSkillDTO> findAllByTeamAndLevelAndBadge(
        Long teamId,
        List<Long> levelIds,
        List<Long> badgeIds,
        List<String> filterNames,
        Pageable pageable
    );

    /*
     *  Get the skills that are achievable for the given team and belong to the given dimension
     */
    Page<AchievableSkillDTO> findAllByTeamAndDimension(Long teamId, Long dimensionId, List<String> filterNames, Pageable pageable);

    /**
     * Updates an achievable skill
     */
    AchievableSkillDTO updateAchievableSkill(Long teamId, AchievableSkillDTO achievableSkill);

    /**
     * Finds AchievableSkill by teamId and skillId
     */
    AchievableSkillDTO findAchievableSkill(Long teamId, Long skillId);
}
