package com.iteratec.teamdojo.web.rest.custom;

import com.iteratec.teamdojo.service.custom.ExtendedSkillService;
import com.iteratec.teamdojo.service.dto.SkillDTO;
import com.iteratec.teamdojo.service.dto.custom.SkillRateDTO;
import com.iteratec.teamdojo.web.rest.SkillResource;
import javax.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.jhipster.web.util.HeaderUtil;

/**
 * Extends the generated SkillResource
 *
 * <p>
 * This component encapsulates code to extend {@link com.iteratec.teamdojo.web.rest.SkillResource} w/o
 * subclassing (see ADR-0001).
 * </p>
 */
@Slf4j
@RestController
@RequestMapping("/api")
public class CustomSkillResource {

    private final ExtendedSkillService skillService;
    private static final String ENTITY_NAME = "skill";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    public CustomSkillResource(ExtendedSkillService skillService) {
        this.skillService = skillService;
    }

    /**
     * POST /skill/:id : update the "id" skill.
     *
     * @param id the id of the skillDTO to update
     * @return the ResponseEntity with status 200 (OK)
     */
    @PostMapping("/skills/{id}/vote")
    public ResponseEntity<SkillDTO> createVote(@PathVariable Long id, @Valid @RequestBody SkillRateDTO rateDto) {
        log.debug("REST request to create a new vote for Skill : {}", id);
        SkillDTO result = skillService.createVote(id, rateDto.getRateScore());
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .body(result);
    }
}
