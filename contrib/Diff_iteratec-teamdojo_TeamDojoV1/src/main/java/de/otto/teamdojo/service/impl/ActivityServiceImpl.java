3c3
< import de.otto.teamdojo.config.ApplicationProperties;
---
> import de.otto.teamdojo.service.ActivityService;
5,9d4
< import de.otto.teamdojo.domain.Badge;
< import de.otto.teamdojo.domain.Skill;
< import de.otto.teamdojo.domain.Team;
< import de.otto.teamdojo.domain.enumeration.ActivityType;
< import de.otto.teamdojo.domain.enumeration.UserMode;
11,15d5
< import de.otto.teamdojo.repository.BadgeRepository;
< import de.otto.teamdojo.repository.SkillRepository;
< import de.otto.teamdojo.repository.TeamRepository;
< import de.otto.teamdojo.service.ActivityService;
< import de.otto.teamdojo.service.OrganizationService;
17,18d6
< import de.otto.teamdojo.service.dto.BadgeDTO;
< import de.otto.teamdojo.service.dto.TeamSkillDTO;
20,22d7
< import org.apache.commons.lang3.StringUtils;
< import org.json.JSONException;
< import org.json.JSONObject;
24a10
> 
27,28d12
< import org.springframework.http.HttpEntity;
< import org.springframework.http.ResponseEntity;
31,37c15
< import org.springframework.util.LinkedMultiValueMap;
< import org.springframework.util.MultiValueMap;
< import org.springframework.web.client.RestTemplate;
< 
< import java.time.Instant;
< import java.util.HashMap;
< import java.util.Map;
---
> 
53,69c31
<     private final BadgeRepository badgeRepository;
< 
<     private final TeamRepository teamRepository;
< 
<     private final SkillRepository skillRepository;
< 
<     private final ApplicationProperties properties;
< 
<     private final OrganizationService organizationService;
< 
<     public ActivityServiceImpl(ActivityRepository activityRepository,
<                                ActivityMapper activityMapper,
<                                BadgeRepository badgeRepository,
<                                TeamRepository teamRepository,
<                                SkillRepository skillRepository,
<                                ApplicationProperties properties,
<                                OrganizationService organizationService) {
---
>     public ActivityServiceImpl(ActivityRepository activityRepository, ActivityMapper activityMapper) {
72,76d33
<         this.badgeRepository = badgeRepository;
<         this.teamRepository = teamRepository;
<         this.skillRepository = skillRepository;
<         this.properties = properties;
<         this.organizationService = organizationService;
93,145d49
<     @Override
<     public ActivityDTO createForNewBadge(BadgeDTO badgeDTO) throws JSONException {
<         Badge badge = badgeRepository.getOne(badgeDTO.getId());
<         JSONObject data = new JSONObject();
<         data.put("badgeId", badge.getId());
<         data.put("badgeName", badge.getName());
< 
<         ActivityDTO activityDTO = new ActivityDTO();
<         activityDTO.setType(ActivityType.BADGE_CREATED);
<         activityDTO.setCreatedAt(Instant.now());
<         activityDTO.setData(data.toString());
<         log.debug("Request to create activity for BADGE_CREATED {}", activityDTO);
<         informMattermost("Der Badge \"" + badge.getName() + "\" wurde erstellt", Optional.empty());
<         return save(activityDTO);
<     }
< 
<     @Override
<     public ActivityDTO createForCompletedSkill(TeamSkillDTO teamSkill) throws JSONException {
<         Team team = teamRepository.getOne(teamSkill.getTeamId());
<         Skill skill = skillRepository.getOne(teamSkill.getSkillId());
< 
<         JSONObject data = new JSONObject();
<         data.put("teamId", team.getId());
<         data.put("teamName", team.getName());
<         data.put("skillId", skill.getId());
<         data.put("skillTitle", skill.getTitle());
< 
<         ActivityDTO activityDTO = new ActivityDTO();
<         activityDTO.setType(ActivityType.SKILL_COMPLETED);
<         activityDTO.setCreatedAt(Instant.now());
<         activityDTO.setData(data.toString());
<         log.debug("Request to create activity for SKILL_COMPLETED {}", activityDTO);
< 
<         String message = team.getName() + " hat den Skill \"" + skill.getTitle() + "\" erlernt!";
< 
<         if (organizationService.getCurrentOrganization().getCountOfConfirmations() > 0) {
<             message += " <" + properties.getFrontend() + "team-skill/" + teamSkill.getId() + "/vote|Traust du das " + team.getName() + " zu?>";
<         }
< 
<         informMattermost(message, Optional.empty());
<         return save(activityDTO);
<     }
< 
<     @Override
<     public void createForSuggestedSkill(TeamSkillDTO teamSkill) throws JSONException {
<         Team team = teamRepository.getOne(teamSkill.getTeamId());
<         Skill skill = skillRepository.getOne(teamSkill.getSkillId());
< 
<         informMattermost("Dir wird der Skill \"" + skill.getTitle() + "\" vorgeschlagen! <"
<             + properties.getFrontend() + "teams/" + team.getShortName() + "/skills/" + skill.getId()
<             + "|Skill jetzt zuweisen?>", Optional.of("@" + team.getShortName()));
<     }
< 
183,207d86
<     }
< 
< 
<     private void informMattermost(String message, Optional<String> username) {
<         log.debug("inform Mattermost");
<         MultiValueMap<String, String> headers = new LinkedMultiValueMap<String, String>();
<         Map<String, String> map = new HashMap<>();
<         map.put("Content-Type", "application/json");
<         headers.setAll(map);
<         Map req_body = new HashMap();
<         req_body.put("text", message);
<         if (username.isPresent()) {
<             req_body.put("channel", username.get());
<         }
<         HttpEntity<?> request = new HttpEntity<>(req_body, headers);
< 
<         String mattermostUrl = organizationService.getCurrentOrganization().getMattermostUrl();
<         if (StringUtils.isBlank(mattermostUrl)) {
<             mattermostUrl = properties.getMattermost();
<         }
< 
<         ResponseEntity<?> response = new RestTemplate().postForEntity(mattermostUrl, request, String.class);
<         if (response.getStatusCodeValue() != 200) {
<             log.warn("Could not post to Mattermost url " + mattermostUrl);
<         }
