package com.iteratec.teamdojo.web.rest.ext;

import com.iteratec.teamdojo.repository.SkillRepository;
import com.iteratec.teamdojo.service.SkillQueryService;
import com.iteratec.teamdojo.service.dto.SkillDTO;
import com.iteratec.teamdojo.service.dto.ext.SkillRateDTO;
import com.iteratec.teamdojo.service.ext.ExtendedSkillService;
import com.iteratec.teamdojo.web.rest.SkillResource;
import com.iteratec.teamdojo.web.rest.ext.util.CustomHeaderUtil;
import javax.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/v2")
public class ExtendedSkillResource extends SkillResource {

    private final ExtendedSkillService skillService;
    private static final String ENTITY_NAME = "skill";

    public ExtendedSkillResource(ExtendedSkillService skillService, SkillRepository skillRepository, SkillQueryService skillQueryService) {
        super(skillService, skillRepository, skillQueryService);
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
