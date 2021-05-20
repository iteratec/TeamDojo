3,6d2
< import de.otto.teamdojo.service.LevelSkillService;
< import de.otto.teamdojo.service.dto.LevelCriteria;
< import de.otto.teamdojo.service.dto.LevelDTO;
< import de.otto.teamdojo.service.dto.LevelSkillDTO;
9a6,7
> import de.otto.teamdojo.service.dto.LevelDTO;
> import de.otto.teamdojo.service.dto.LevelCriteria;
24c22
< import java.util.ArrayList;
---
> 
43,45c41
<     private final LevelSkillService levelSkillService;
< 
<     public LevelResource(LevelService levelService, LevelQueryService levelQueryService, LevelSkillService levelSkillService) {
---
>     public LevelResource(LevelService levelService, LevelQueryService levelQueryService) {
48d43
<         this.levelSkillService = levelSkillService;
101,104d95
< 
<         if(criteria != null && criteria.getSkillsId() != null && criteria.getSkillsId().getIn() != null)
<             return getAllLevelsBySkills(criteria.getSkillsId().getIn(), pageable);
< 
107,130c98
<         return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
<     }
< 
< 
<     /**
<      * GET  /levels : get all the levels.
<      *
<      * @param skillsId the skillsId to search for
<      * @return the ResponseEntity with status 200 (OK) and the list of levels in body
<      */
<     public ResponseEntity<List<LevelDTO>> getAllLevelsBySkills(
<         List<Long> skillsId,
<         Pageable pageable) {
<         log.debug("REST request to get Levels for Skills; {}", skillsId);
< 
<         List<LevelSkillDTO> levelSkills = levelSkillService.findBySkillIdIn(skillsId, pageable);
<         List<Long> levelIds = new ArrayList<>();
<         for(LevelSkillDTO levelSkill : levelSkills){
<             levelIds.add(levelSkill.getLevelId());
<         }
< 
<         Page<LevelDTO> page = levelService.findByIdIn(levelIds, pageable);
<         HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/levels");
<         return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
---
>         return ResponseEntity.ok().headers(headers).body(page.getContent());
