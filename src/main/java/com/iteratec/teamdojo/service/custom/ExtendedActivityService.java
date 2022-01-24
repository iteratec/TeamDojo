package com.iteratec.teamdojo.service.custom;

import com.iteratec.teamdojo.service.ActivityService;
import com.iteratec.teamdojo.service.dto.ActivityDTO;
import com.iteratec.teamdojo.service.dto.BadgeDTO;
import com.iteratec.teamdojo.service.dto.TeamSkillDTO;
import com.iteratec.teamdojo.service.impl.custom.InstantProviderInjectable;

/**
 * API extension for custom service behaviour
 * <p>
 * See ADR-0001 for more details about this pattern.
 * </p>
 */
public interface ExtendedActivityService extends ActivityService, InstantProviderInjectable {
    /**
     * Create an activity for BADGE_CREATED
     *
     * @return the persisted entity
     */
    ActivityDTO createForNewBadge(BadgeDTO badgeDTO);

    /**
     * Create an activity for SKILL_COMPLETED
     *
     * @param teamSkill not {@code null}
     * @return the persisted entity
     */
    ActivityDTO createForCompletedSkill(TeamSkillDTO teamSkill);

    /**
     * Create an activity for SKILL_SUGGESTED
     *
     * @param teamSkill not {@code null}
     */
    void createForSuggestedSkill(TeamSkillDTO teamSkill);
}
