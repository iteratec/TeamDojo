package com.iteratec.teamdojo.web.rest.ext;

import com.iteratec.teamdojo.repository.LevelRepository;
import com.iteratec.teamdojo.service.LevelQueryService;
import com.iteratec.teamdojo.service.criteria.LevelCriteria;
import com.iteratec.teamdojo.service.dto.LevelDTO;
import com.iteratec.teamdojo.service.dto.LevelSkillDTO;
import com.iteratec.teamdojo.service.ext.ExtendedLevelService;
import com.iteratec.teamdojo.service.ext.ExtendedLevelSkillService;
import com.iteratec.teamdojo.web.rest.LevelResource;
import com.iteratec.teamdojo.web.rest.ext.util.CustomPaginationUtil;
import java.util.ArrayList;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api/v2")
public class ExtendedLevelResource extends LevelResource {

    private final ExtendedLevelSkillService levelSkillService;
    private final ExtendedLevelService levelService;

    public ExtendedLevelResource(
        ExtendedLevelService levelService,
        LevelRepository levelRepository,
        LevelQueryService levelQueryService,
        ExtendedLevelSkillService levelSkillService
    ) {
        super(levelService, levelRepository, levelQueryService);
        this.levelSkillService = levelSkillService;
        this.levelService = levelService;
    }

    /**
     * {@code GET  /levels} : get all the levels.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of levels in body.
     */
    @Override
    @GetMapping("/levels")
    public ResponseEntity<List<LevelDTO>> getAllLevels(LevelCriteria criteria, Pageable pageable) {
        if (criteria != null && criteria.getSkillsId() != null && criteria.getSkillsId().getIn() != null) {
            log.debug("REST request to get Levels by criteria: {}", criteria);
            return getAllLevelsBySkills(criteria.getSkillsId().getIn(), pageable);
        }
        return super.getAllLevels(criteria, pageable);
    }

    /**
     * GET  /levels : get all the levels.
     *
     * @param skillsId the skillsId to search for
     * @return the ResponseEntity with status 200 (OK) and the list of levels in body
     */
    public ResponseEntity<List<LevelDTO>> getAllLevelsBySkills(List<Long> skillsId, Pageable pageable) {
        log.debug("REST request to get Levels for Skills; {}", skillsId);

        List<LevelSkillDTO> levelSkills = levelSkillService.findBySkillIdIn(skillsId, pageable);
        List<Long> levelIds = new ArrayList<>();
        for (LevelSkillDTO levelSkill : levelSkills) {
            levelIds.add(levelSkill.getLevel().getId());
        }

        Page<LevelDTO> page = levelService.findByIdIn(levelIds, pageable);
        HttpHeaders headers = CustomPaginationUtil.generatePaginationHttpHeaders(page, "/api/levels");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }
}
