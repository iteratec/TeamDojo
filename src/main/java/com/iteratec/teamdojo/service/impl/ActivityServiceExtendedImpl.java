package com.iteratec.teamdojo.service.impl;

import com.iteratec.teamdojo.config.ApplicationPropertiesExtended;
import com.iteratec.teamdojo.domain.Activity;
import com.iteratec.teamdojo.domain.Badge;
import com.iteratec.teamdojo.domain.Skill;
import com.iteratec.teamdojo.domain.Team;
import com.iteratec.teamdojo.domain.enumeration.ActivityType;
import com.iteratec.teamdojo.repository.ActivityRepository;
import com.iteratec.teamdojo.repository.BadgeRepository;
import com.iteratec.teamdojo.repository.SkillRepository;
import com.iteratec.teamdojo.repository.TeamRepository;
import com.iteratec.teamdojo.service.ActivityExtendedService;
import com.iteratec.teamdojo.service.OrganisationExtendedService;
import com.iteratec.teamdojo.service.dto.ActivityDTO;
import com.iteratec.teamdojo.service.dto.BadgeDTO;
import com.iteratec.teamdojo.service.dto.TeamSkillDTO;
import com.iteratec.teamdojo.service.mapper.ActivityMapper;
import java.time.Instant;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import org.apache.commons.lang3.StringUtils;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

/**
 * Service Implementation for managing {@link Activity}.
 *
 */
public class ActivityServiceExtendedImpl extends ActivityServiceImpl implements ActivityExtendedService {

    private final Logger log = LoggerFactory.getLogger(ActivityServiceExtendedImpl.class);

    private final BadgeRepository badgeRepository;

    private final TeamRepository teamRepository;

    private final SkillRepository skillRepository;

    private final ApplicationPropertiesExtended properties;

    private final OrganisationExtendedService organisationService;

    public ActivityServiceExtendedImpl(
        ActivityRepository activityRepository,
        ActivityMapper activityMapper,
        BadgeRepository badgeRepository,
        TeamRepository teamRepository,
        SkillRepository skillRepository,
        ApplicationPropertiesExtended properties,
        OrganisationExtendedService organisationService
    ) {
        super(activityRepository, activityMapper);
        this.badgeRepository = badgeRepository;
        this.teamRepository = teamRepository;
        this.skillRepository = skillRepository;
        this.properties = properties;
        this.organisationService = organisationService;
    }

    @Override
    public ActivityDTO createForNewBadge(BadgeDTO badgeDTO) throws JSONException {
        Badge badge = badgeRepository.getOne(badgeDTO.getId());
        JSONObject data = new JSONObject();
        data.put("badgeId", badge.getId());
        data.put("badgeName", badge.getTitle());

        ActivityDTO activityDTO = new ActivityDTO();
        activityDTO.setType(ActivityType.BADGE_CREATED);
        activityDTO.setCreatedAt(Instant.now());
        activityDTO.setData(data.toString());
        log.debug("Request to create activity for BADGE_CREATED {}", activityDTO);
        informMattermost("Der Badge \"" + badge.getTitle() + "\" wurde erstellt", Optional.empty());
        return save(activityDTO);
    }

    @Override
    public ActivityDTO createForCompletedSkill(TeamSkillDTO teamSkill) throws JSONException {
        Team team = teamRepository.getOne(teamSkill.getId());
        Skill skill = skillRepository.getOne(teamSkill.getId());

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
        String message = team.getTitle() + " hat den Skill \"" + skill.getTitle() + "\" erlernt!";

        if (organisationService.getCurrentOrganization().getCountOfConfirmations() > 0) {
            message +=
                " <" + properties.getFrontend() + "team-skill/" + teamSkill.getId() + "/vote|Traust du das " + team.getTitle() + " zu?>";
        }

        informMattermost(message, Optional.empty());
        return save(activityDTO);
    }

    @Override
    public void createForSuggestedSkill(TeamSkillDTO teamSkill) throws JSONException {
        Team team = teamRepository.getOne(teamSkill.getId());
        Skill skill = skillRepository.getOne(teamSkill.getId());

        informMattermost(
            "Dir wird der Skill \"" +
            skill.getTitle() +
            "\" vorgeschlagen! <" +
            properties.getFrontend() +
            "teams/" +
            team.getShortTitle() +
            "/skills/" +
            skill.getId() +
            "|Skill jetzt zuweisen?>",
            Optional.of("@" + team.getShortTitle())
        );
    }

    private void informMattermost(String message, Optional<String> username) {
        log.debug("inform Mattermost");
        MultiValueMap<String, String> headers = new LinkedMultiValueMap<String, String>();
        Map<String, String> map = new HashMap<>();
        map.put("Content-Type", "application/json");
        headers.setAll(map);
        Map<String, String> req_body = new HashMap<String, String>();
        req_body.put("text", message);
        if (username.isPresent()) {
            req_body.put("channel", username.get());
        }

        HttpEntity<?> request = new HttpEntity<>(req_body, headers);

        String mattermostUrl = organisationService.getCurrentOrganization().getMattermostUrl();
        if (StringUtils.isBlank(mattermostUrl)) {
            mattermostUrl = properties.getMattermost();
        }

        ResponseEntity<?> response = new RestTemplate().postForEntity(mattermostUrl, request, String.class);
        if (response.getStatusCodeValue() != 200) {
            log.warn("Could not post to Mattermost url " + mattermostUrl);
        }
    }
}
