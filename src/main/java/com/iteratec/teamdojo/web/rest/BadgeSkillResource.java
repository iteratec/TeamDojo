package com.iteratec.teamdojo.web.rest;

import com.iteratec.teamdojo.GeneratedByJHipster;
import com.iteratec.teamdojo.repository.BadgeSkillRepository;
import com.iteratec.teamdojo.service.BadgeSkillQueryService;
import com.iteratec.teamdojo.service.BadgeSkillService;
import com.iteratec.teamdojo.service.criteria.BadgeSkillCriteria;
import com.iteratec.teamdojo.service.dto.BadgeSkillDTO;
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
 * REST controller for managing {@link com.iteratec.teamdojo.domain.BadgeSkill}.
 */
@RestController
@RequestMapping("/api")
@GeneratedByJHipster
public class BadgeSkillResource {

    private final Logger log = LoggerFactory.getLogger(BadgeSkillResource.class);

    private static final String ENTITY_NAME = "badgeSkill";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final BadgeSkillService badgeSkillService;

    private final BadgeSkillRepository badgeSkillRepository;

    private final BadgeSkillQueryService badgeSkillQueryService;

    public BadgeSkillResource(
        BadgeSkillService badgeSkillService,
        BadgeSkillRepository badgeSkillRepository,
        BadgeSkillQueryService badgeSkillQueryService
    ) {
        this.badgeSkillService = badgeSkillService;
        this.badgeSkillRepository = badgeSkillRepository;
        this.badgeSkillQueryService = badgeSkillQueryService;
    }

    /**
     * {@code POST  /badge-skills} : Create a new badgeSkill.
     *
     * @param badgeSkillDTO the badgeSkillDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new badgeSkillDTO, or with status {@code 400 (Bad Request)} if the badgeSkill has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/badge-skills")
    public ResponseEntity<BadgeSkillDTO> createBadgeSkill(@Valid @RequestBody BadgeSkillDTO badgeSkillDTO) throws URISyntaxException {
        log.debug("REST request to save BadgeSkill : {}", badgeSkillDTO);
        if (badgeSkillDTO.getId() != null) {
            throw new BadRequestAlertException("A new badgeSkill cannot already have an ID", ENTITY_NAME, "idexists");
        }
        BadgeSkillDTO result = badgeSkillService.save(badgeSkillDTO);
        return ResponseEntity
            .created(new URI("/api/badge-skills/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /badge-skills/:id} : Updates an existing badgeSkill.
     *
     * @param id the id of the badgeSkillDTO to save.
     * @param badgeSkillDTO the badgeSkillDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated badgeSkillDTO,
     * or with status {@code 400 (Bad Request)} if the badgeSkillDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the badgeSkillDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/badge-skills/{id}")
    public ResponseEntity<BadgeSkillDTO> updateBadgeSkill(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody BadgeSkillDTO badgeSkillDTO
    ) throws URISyntaxException {
        log.debug("REST request to update BadgeSkill : {}, {}", id, badgeSkillDTO);
        if (badgeSkillDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, badgeSkillDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!badgeSkillRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        BadgeSkillDTO result = badgeSkillService.update(badgeSkillDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, badgeSkillDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /badge-skills/:id} : Partial updates given fields of an existing badgeSkill, field will ignore if it is null
     *
     * @param id the id of the badgeSkillDTO to save.
     * @param badgeSkillDTO the badgeSkillDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated badgeSkillDTO,
     * or with status {@code 400 (Bad Request)} if the badgeSkillDTO is not valid,
     * or with status {@code 404 (Not Found)} if the badgeSkillDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the badgeSkillDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/badge-skills/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<BadgeSkillDTO> partialUpdateBadgeSkill(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody BadgeSkillDTO badgeSkillDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update BadgeSkill partially : {}, {}", id, badgeSkillDTO);
        if (badgeSkillDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, badgeSkillDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!badgeSkillRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<BadgeSkillDTO> result = badgeSkillService.partialUpdate(badgeSkillDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, badgeSkillDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /badge-skills} : get all the badgeSkills.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of badgeSkills in body.
     */
    @GetMapping("/badge-skills")
    public ResponseEntity<List<BadgeSkillDTO>> getAllBadgeSkills(
        BadgeSkillCriteria criteria,
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get BadgeSkills by criteria: {}", criteria);
        Page<BadgeSkillDTO> page = badgeSkillQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /badge-skills/count} : count all the badgeSkills.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/badge-skills/count")
    public ResponseEntity<Long> countBadgeSkills(BadgeSkillCriteria criteria) {
        log.debug("REST request to count BadgeSkills by criteria: {}", criteria);
        return ResponseEntity.ok().body(badgeSkillQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /badge-skills/:id} : get the "id" badgeSkill.
     *
     * @param id the id of the badgeSkillDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the badgeSkillDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/badge-skills/{id}")
    public ResponseEntity<BadgeSkillDTO> getBadgeSkill(@PathVariable Long id) {
        log.debug("REST request to get BadgeSkill : {}", id);
        Optional<BadgeSkillDTO> badgeSkillDTO = badgeSkillService.findOne(id);
        return ResponseUtil.wrapOrNotFound(badgeSkillDTO);
    }

    /**
     * {@code DELETE  /badge-skills/:id} : delete the "id" badgeSkill.
     *
     * @param id the id of the badgeSkillDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/badge-skills/{id}")
    public ResponseEntity<Void> deleteBadgeSkill(@PathVariable Long id) {
        log.debug("REST request to delete BadgeSkill : {}", id);
        badgeSkillService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
