package com.iteratec.teamdojo.service.impl.custom;

import com.iteratec.teamdojo.service.dto.*;
import java.util.List;

/**
 * This is a mirror of the CompletionCheck class found in the frontend:
 * src/main/webapp/app/custom/helper/completion-check.ts
 */
public final class CompletionCheck {

    private final TeamDTO team;
    private final List<SkillDTO> skills;

    CompletionCheck(TeamDTO team, List<SkillDTO> skills) {
        this.team = team;
        this.skills = skills;
    }

    public Progress getProgress(LevelDTO level, List<LevelSkillDTO> levelSkills) {
        return null;
    }

    public Progress getProgress(BadgeDTO badge, List<BadgeSkillDTO> badgeSkills) {
        return null;
    }

    //public Long getIrrelevancy(List<LevelSkillDTO> levelSkills) { return 0l; }

    public Long getIrrelevancy(List<BadgeSkillDTO> badgeSkills) {
        return 0l;
    }
}
