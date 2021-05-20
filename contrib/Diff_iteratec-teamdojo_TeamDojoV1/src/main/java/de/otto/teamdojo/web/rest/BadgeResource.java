2d1
< 
4,7d2
< import de.otto.teamdojo.service.BadgeSkillService;
< import de.otto.teamdojo.service.dto.BadgeCriteria;
< import de.otto.teamdojo.service.dto.BadgeDTO;
< import de.otto.teamdojo.service.dto.BadgeSkillDTO;
10a6,7
> import de.otto.teamdojo.service.dto.BadgeDTO;
> import de.otto.teamdojo.service.dto.BadgeCriteria;
26d22
< import java.util.ArrayList;
42d37
<     private final BadgeSkillService badgeSkillService;
46c41
<     public BadgeResource(BadgeService badgeService, BadgeQueryService badgeQueryService, BadgeSkillService badgeSkillService) {
---
>     public BadgeResource(BadgeService badgeService, BadgeQueryService badgeQueryService) {
49d43
<         this.badgeSkillService = badgeSkillService;
102,105d95
< 
<         if(criteria != null && criteria.getSkillsId() != null && criteria.getSkillsId().getIn() != null)
<             return getAllBadgesBySkills(criteria.getSkillsId().getIn(), pageable);
< 
121,143d110
<     }
< 
<     /**
<      * GET  /badges : get all the badges.
<      *
<      * @param pageable the pagination information
<      * @param skillsId the skillIds to search for
<      * @return the ResponseEntity with status 200 (OK) and the list of badges in body
<      */
<     public ResponseEntity<List<BadgeDTO>> getAllBadgesBySkills(
<         List<Long> skillsId,
<         Pageable pageable) {
<         log.debug("REST request to get Badges for Skills: {}", skillsId);
< 
<         List<BadgeSkillDTO> badgeSkills = badgeSkillService.findBySkillIdIn(skillsId, pageable);
<         List<Long> badgeIds = new ArrayList<>();
<         for (BadgeSkillDTO badgeSkill : badgeSkills){
<             badgeIds.add(badgeSkill.getBadgeId());
<         }
< 
<         Page<BadgeDTO> page = badgeService.findByIdIn(badgeIds, pageable);
<         HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/badges");
<         return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
