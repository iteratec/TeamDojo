package com.iteratec.teamdojo.service.ext;

import com.iteratec.teamdojo.service.ActivityService;
import com.iteratec.teamdojo.service.dto.ActivityDTO;
import com.iteratec.teamdojo.service.dto.BadgeDTO;
import com.iteratec.teamdojo.service.dto.TeamSkillDTO;

public interface ExtendedActivityService extends ActivityService {
    /**
     * Create an activity for BADGE_CREATED
     *
     * @return the persisted entity
     */
    ActivityDTO createForNewBadge(BadgeDTO badgeDTO);

    /**
     * Create an activity for SKILL_COMPLETED
     *
     * @param teamSkill
     * @return the persisted entity
     */
    ActivityDTO createForCompletedSkill(TeamSkillDTO teamSkill);

    /**
     * Create an activity for SKILL_SUGGESTED
     *
     * @param teamSkill
     */
    void createForSuggestedSkill(TeamSkillDTO teamSkill);
}
