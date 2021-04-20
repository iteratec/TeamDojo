package com.iteratec.teamdojo.service;

import com.iteratec.teamdojo.service.dto.ActivityDTO;
import com.iteratec.teamdojo.service.dto.BadgeDTO;
import com.iteratec.teamdojo.service.dto.TeamSkillDTO;
import org.json.JSONException;

/**
 * Service Interface for managing {@link com.iteratec.teamdojo.domain.Activity}.
 */
public interface ActivityExtendedService extends ActivityService {
    /**
     * Create an activity for BADGE_CREATED
     * @return the persisted entity
     */
    ActivityDTO createForNewBadge(BadgeDTO badgeDTO) throws JSONException;

    /**
     * Create an activity for SKILL_COMPLETED
     * @param teamSkill
     * @return the persisted entity
     */
    ActivityDTO createForCompletedSkill(TeamSkillDTO teamSkill) throws JSONException;

    /**
     * Create an activity for SKILL_SUGGESTED
     * @param teamSkill
     */
    void createForSuggestedSkill(TeamSkillDTO teamSkill) throws JSONException;
}
