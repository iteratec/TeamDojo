package com.iteratec.teamdojo.service.impl.ext;

import com.iteratec.teamdojo.config.ext.ExtendedApplicationProperties;
import com.iteratec.teamdojo.domain.Badge;
import com.iteratec.teamdojo.domain.Skill;
import com.iteratec.teamdojo.domain.Team;
import com.iteratec.teamdojo.domain.enumeration.ActivityType;
import com.iteratec.teamdojo.repository.ActivityRepository;
import com.iteratec.teamdojo.repository.BadgeRepository;
import com.iteratec.teamdojo.repository.SkillRepository;
import com.iteratec.teamdojo.repository.TeamRepository;
import com.iteratec.teamdojo.service.dto.ActivityDTO;
import com.iteratec.teamdojo.service.dto.BadgeDTO;
import com.iteratec.teamdojo.service.dto.TeamSkillDTO;
import com.iteratec.teamdojo.service.ext.ExtendedActivityService;
import com.iteratec.teamdojo.service.ext.ExtendedOrganisationService;
import com.iteratec.teamdojo.service.impl.ActivityServiceImpl;
import com.iteratec.teamdojo.service.mapper.ActivityMapper;
import java.time.Instant;
import net.minidev.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

// TODO: After removing the Mattermost notification stuff this service seems quite useless. Should we remove it completely?
@Service
public class ExtendedActivityServiceImpl extends ActivityServiceImpl implements ExtendedActivityService {

    private final Logger log = LoggerFactory.getLogger(ExtendedActivityServiceImpl.class);
    private final BadgeRepository badgeRepository;
    private final TeamRepository teamRepository;
    private final SkillRepository skillRepository;
    private final ExtendedApplicationProperties properties;
    private final ExtendedOrganisationService organizationService;

    public ExtendedActivityServiceImpl(
        ActivityRepository activityRepository,
        ActivityMapper activityMapper,
        BadgeRepository badgeRepository,
        TeamRepository teamRepository,
        SkillRepository skillRepository,
        ExtendedApplicationProperties properties,
        ExtendedOrganisationService organizationService
    ) {
        super(activityRepository, activityMapper);
        this.badgeRepository = badgeRepository;
        this.teamRepository = teamRepository;
        this.skillRepository = skillRepository;
        this.properties = properties;
        this.organizationService = organizationService;
    }

    @Override
    public ActivityDTO createForNewBadge(BadgeDTO badgeDTO) {
        Badge badge = badgeRepository.getOne(badgeDTO.getId());
        JSONObject data = new JSONObject();
        data.put("badgeId", badge.getId());
        data.put("badgeName", badge.getTitle());

        ActivityDTO activityDTO = new ActivityDTO();
        activityDTO.setType(ActivityType.BADGE_CREATED);
        activityDTO.setCreatedAt(Instant.now());
        activityDTO.setData(data.toString());
        log.debug("Request to create activity for BADGE_CREATED {}", activityDTO);

        return save(activityDTO);
    }

    @Override
    public ActivityDTO createForCompletedSkill(TeamSkillDTO teamSkill) {
        Team team = teamRepository.getOne(teamSkill.getTeam().getId());
        Skill skill = skillRepository.getOne(teamSkill.getSkill().getId());

        JSONObject data = new JSONObject();
        data.put("teamId", team.getId());
        data.put("teamName", team.getTitle());
        data.put("skillId", skill.getId());
        data.put("skillTitle", skill.getTitle());

        ActivityDTO activityDTO = new ActivityDTO();
        activityDTO.setType(ActivityType.SKILL_COMPLETED);
        activityDTO.setCreatedAt(Instant.now());
        activityDTO.setData(data.toString());
        log.debug("Request to create activity for SKILL_COMPLETED {}", activityDTO);

        return save(activityDTO);
    }

    @Override
    public void createForSuggestedSkill(TeamSkillDTO teamSkill) {
        Team team = teamRepository.getOne(teamSkill.getTeam().getId());
        Skill skill = skillRepository.getOne(teamSkill.getSkill().getId());
    }
}
