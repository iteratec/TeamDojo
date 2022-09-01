package com.iteratec.teamdojo.web.rest;

import com.iteratec.teamdojo.GeneratedByJHipster;
import com.iteratec.teamdojo.repository.LevelSkillRepository;
import com.iteratec.teamdojo.service.LevelSkillQueryService;
import com.iteratec.teamdojo.service.LevelSkillService;
import com.iteratec.teamdojo.service.criteria.LevelSkillCriteria;
import com.iteratec.teamdojo.service.dto.LevelSkillDTO;
import com.iteratec.teamdojo.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.iteratec.teamdojo.domain.LevelSkill}.
 */
@RestController
@RequestMapping("/api")
@GeneratedByJHipster
public class LevelSkillResource {

    private final Logger log = LoggerFactory.getLogger(LevelSkillResource.class);

    private static final String ENTITY_NAME = "levelSkill";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final LevelSkillService levelSkillService;

    private final LevelSkillRepository levelSkillRepository;

    private final LevelSkillQueryService levelSkillQueryService;

    public LevelSkillResource(
        LevelSkillService levelSkillService,
        LevelSkillRepository levelSkillRepository,
        LevelSkillQueryService levelSkillQueryService
    ) {
        this.levelSkillService = levelSkillService;
        this.levelSkillRepository = levelSkillRepository;
        this.levelSkillQueryService = levelSkillQueryService;
    }

    /**
     * {@code POST  /level-skills} : Create a new levelSkill.
     *
     * @param levelSkillDTO the levelSkillDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new levelSkillDTO, or with status {@code 400 (Bad Request)} if the levelSkill has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/level-skills")
    public ResponseEntity<LevelSkillDTO> createLevelSkill(@Valid @RequestBody LevelSkillDTO levelSkillDTO) throws URISyntaxException {
        log.debug("REST request to save LevelSkill : {}", levelSkillDTO);
        if (levelSkillDTO.getId() != null) {
            throw new BadRequestAlertException("A new levelSkill cannot already have an ID", ENTITY_NAME, "idexists");
        }
        LevelSkillDTO result = levelSkillService.save(levelSkillDTO);
        return ResponseEntity
            .created(new URI("/api/level-skills/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /level-skills/:id} : Updates an existing levelSkill.
     *
     * @param id the id of the levelSkillDTO to save.
     * @param levelSkillDTO the levelSkillDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated levelSkillDTO,
     * or with status {@code 400 (Bad Request)} if the levelSkillDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the levelSkillDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/level-skills/{id}")
    public ResponseEntity<LevelSkillDTO> updateLevelSkill(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody LevelSkillDTO levelSkillDTO
    ) throws URISyntaxException {
        log.debug("REST request to update LevelSkill : {}, {}", id, levelSkillDTO);
        if (levelSkillDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, levelSkillDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!levelSkillRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        LevelSkillDTO result = levelSkillService.update(levelSkillDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, levelSkillDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /level-skills/:id} : Partial updates given fields of an existing levelSkill, field will ignore if it is null
     *
     * @param id the id of the levelSkillDTO to save.
     * @param levelSkillDTO the levelSkillDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated levelSkillDTO,
     * or with status {@code 400 (Bad Request)} if the levelSkillDTO is not valid,
     * or with status {@code 404 (Not Found)} if the levelSkillDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the levelSkillDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/level-skills/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<LevelSkillDTO> partialUpdateLevelSkill(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody LevelSkillDTO levelSkillDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update LevelSkill partially : {}, {}", id, levelSkillDTO);
        if (levelSkillDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, levelSkillDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!levelSkillRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<LevelSkillDTO> result = levelSkillService.partialUpdate(levelSkillDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, levelSkillDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /level-skills} : get all the levelSkills.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of levelSkills in body.
     */
    @GetMapping("/level-skills")
    public ResponseEntity<List<LevelSkillDTO>> getAllLevelSkills(
        LevelSkillCriteria criteria,
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get LevelSkills by criteria: {}", criteria.toString().replaceAll("[\n\r\t]", "_"));
        Page<LevelSkillDTO> page = levelSkillQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /level-skills/count} : count all the levelSkills.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/level-skills/count")
    public ResponseEntity<Long> countLevelSkills(LevelSkillCriteria criteria) {
        log.debug("REST request to count LevelSkills by criteria: {}", criteria.toString().replaceAll("[\n\r\t]", "_"));
        return ResponseEntity.ok().body(levelSkillQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /level-skills/:id} : get the "id" levelSkill.
     *
     * @param id the id of the levelSkillDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the levelSkillDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/level-skills/{id}")
    public ResponseEntity<LevelSkillDTO> getLevelSkill(@PathVariable Long id) {
        log.debug("REST request to get LevelSkill : {}", id);
        Optional<LevelSkillDTO> levelSkillDTO = levelSkillService.findOne(id);
        return ResponseUtil.wrapOrNotFound(levelSkillDTO);
    }

    /**
     * {@code DELETE  /level-skills/:id} : delete the "id" levelSkill.
     *
     * @param id the id of the levelSkillDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/level-skills/{id}")
    public ResponseEntity<Void> deleteLevelSkill(@PathVariable Long id) {
        log.debug("REST request to delete LevelSkill : {}", id);
        levelSkillService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
