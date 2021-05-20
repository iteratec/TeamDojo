4,6c4
< import de.otto.teamdojo.service.dto.BadgeDTO;
< import de.otto.teamdojo.service.dto.TeamSkillDTO;
< import org.json.JSONException;
---
> 
10d7
< import java.util.List;
25,43d21
< 
<     /**
<      * Create an activity for BADGE_CREATED
<      * @return the persisted entity
<      */
<     ActivityDTO createForNewBadge(BadgeDTO badgeDTO) throws JSONException;
< 
<     /**
<      * Create an activity for SKILL_COMPLETED
<      * @param teamSkill
<      * @return the persisted entity
<      */
<     ActivityDTO createForCompletedSkill(TeamSkillDTO teamSkill) throws JSONException;
< 
<     /**
<      * Create an activity for SKILL_SUGGESTED
<      * @param teamSkill
<      */
<     void createForSuggestedSkill(TeamSkillDTO teamSkill) throws JSONException;
