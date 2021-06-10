package com.iteratec.teamdojo.web.rest.ext;

import com.iteratec.teamdojo.service.dto.SkillDTO;
import com.iteratec.teamdojo.service.dto.ext.SkillRateDTO;
import com.iteratec.teamdojo.service.ext.ExtendedSkillService;
import com.iteratec.teamdojo.web.rest.SkillResource;
import com.iteratec.teamdojo.web.rest.ext.util.CustomHeaderUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * Custom REST controller for managing {@link com.iteratec.teamdojo.domain.Skill}.
 *
 * <p>This class is prefixed with "Custom" because we do not extend the generated {@link SkillResource}.
 * The reason for not extending the resource is that it would lead into ambiguous URI mappings due to inherited
 * methods with URI mapping annotations.</p>
 */
@Slf4j
@RestController
@RequestMapping("/api")
public class CustomSkillResource {

    private final ExtendedSkillService skillService;
    private static final String ENTITY_NAME = "skill";

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
        return ResponseEntity.ok().headers(CustomHeaderUtil.createEntityUpdateAlert(ENTITY_NAME, id.toString())).body(result);
    }
}
