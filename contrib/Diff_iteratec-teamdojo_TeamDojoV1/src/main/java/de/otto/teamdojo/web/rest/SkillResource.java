3d2
< import de.otto.teamdojo.service.dto.SkillRateDTO;
22a22
> 
137,151d136
<     }
< 
<     /**
<      * POST /skill/:id : update the "id" skill.
<      *
<      * @param id the id of the skillDTO to update
<      * @return the ResponseEntity with status 200 (OK)
<      */
<     @PostMapping("/skills/{id}/vote")
<     public ResponseEntity<SkillDTO> createVote(@PathVariable Long id, @Valid @RequestBody SkillRateDTO rateDto){
<         log.debug("REST request to create a new vote for Skill : {}", id);
<         SkillDTO result = skillService.createVote(id, rateDto.getRateScore());
<         return ResponseEntity.ok()
<             .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, id.toString()))
<             .body(result);
