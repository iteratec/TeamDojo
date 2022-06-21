package com.iteratec.teamdojo.web.rest;

import com.iteratec.teamdojo.GeneratedByJHipster;
import com.iteratec.teamdojo.repository.BadgeRepository;
// ### MODIFICATION-START ###
import com.iteratec.teamdojo.security.AuthoritiesConstants;
// ### MODIFICATION-END ###
import com.iteratec.teamdojo.service.BadgeQueryService;
import com.iteratec.teamdojo.service.BadgeService;
import com.iteratec.teamdojo.service.criteria.BadgeCriteria;
import com.iteratec.teamdojo.service.dto.BadgeDTO;
// ### MODIFICATION-START ###
import com.iteratec.teamdojo.web.rest.custom.CustomBadgeResourceExtension;
// ### MODIFICATION-END ###
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
// ### MODIFICATION-START ###
import org.springframework.security.access.annotation.Secured;
// ### MODIFICATION-END ###
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.iteratec.teamdojo.domain.Badge}.
 */
@RestController
@RequestMapping("/api")
@GeneratedByJHipster
public class BadgeResource {

    private final Logger log = LoggerFactory.getLogger(BadgeResource.class);

    private static final String ENTITY_NAME = "badge";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final BadgeService badgeService;

    private final BadgeRepository badgeRepository;

    private final BadgeQueryService badgeQueryService;

    // ### MODIFICATION-START ###
    private final CustomBadgeResourceExtension extension;

    // ### MODIFICATION-END ###

    public BadgeResource(
        BadgeService badgeService,
        BadgeRepository badgeRepository,
        BadgeQueryService badgeQueryService,
        // ### MODIFICATION-START ###
        CustomBadgeResourceExtension extension
        // ### MODIFICATION-END ###
    ) {
        this.badgeService = badgeService;
        this.badgeRepository = badgeRepository;
        this.badgeQueryService = badgeQueryService;
        // ### MODIFICATION-START ###
        this.extension = extension;
        // ### MODIFICATION-END ###
    }

    /**
     * {@code POST  /badges} : Create a new badge.
     *
     * @param badgeDTO the badgeDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new badgeDTO, or with status {@code 400 (Bad Request)} if the badge has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    // ### MODIFICATION-START ###
    @Secured(AuthoritiesConstants.ADMIN)
    // ### MODIFICATION-END ###
    @PostMapping("/badges")
    public ResponseEntity<BadgeDTO> createBadge(@Valid @RequestBody BadgeDTO badgeDTO) throws URISyntaxException {
        log.debug("REST request to save Badge : {}", badgeDTO);
        if (badgeDTO.getId() != null) {
            throw new BadRequestAlertException("A new badge cannot already have an ID", ENTITY_NAME, "idexists");
        }
        BadgeDTO result = badgeService.save(badgeDTO);
        return ResponseEntity
            .created(new URI("/api/badges/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /badges/:id} : Updates an existing badge.
     *
     * @param id the id of the badgeDTO to save.
     * @param badgeDTO the badgeDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated badgeDTO,
     * or with status {@code 400 (Bad Request)} if the badgeDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the badgeDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    // ### MODIFICATION-START ###
    @Secured(AuthoritiesConstants.ADMIN)
    // ### MODIFICATION-END ###
    @PutMapping("/badges/{id}")
    public ResponseEntity<BadgeDTO> updateBadge(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody BadgeDTO badgeDTO
    ) throws URISyntaxException {
        log.debug("REST request to update Badge : {}, {}", id, badgeDTO);
        if (badgeDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, badgeDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!badgeRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        BadgeDTO result = badgeService.update(badgeDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, badgeDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /badges/:id} : Partial updates given fields of an existing badge, field will ignore if it is null
     *
     * @param id the id of the badgeDTO to save.
     * @param badgeDTO the badgeDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated badgeDTO,
     * or with status {@code 400 (Bad Request)} if the badgeDTO is not valid,
     * or with status {@code 404 (Not Found)} if the badgeDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the badgeDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    // ### MODIFICATION-START ###
    @Secured(AuthoritiesConstants.ADMIN)
    // ### MODIFICATION-END ###
    @PatchMapping(value = "/badges/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<BadgeDTO> partialUpdateBadge(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody BadgeDTO badgeDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update Badge partially : {}, {}", id, badgeDTO);
        if (badgeDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, badgeDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!badgeRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<BadgeDTO> result = badgeService.partialUpdate(badgeDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, badgeDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /badges} : get all the badges.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of badges in body.
     */
    @GetMapping("/badges")
    public ResponseEntity<List<BadgeDTO>> getAllBadges(
        BadgeCriteria criteria,
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get Badges by criteria: {}", criteria);

        // ### MODIFICATION-START ###
        if (extension.shouldFindBadgesBySkills(criteria)) {
            return extension.findBadgesBySkills(criteria, pageable);
        }
        // ### MODIFICATION-END ###

        Page<BadgeDTO> page = badgeQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /badges/count} : count all the badges.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/badges/count")
    public ResponseEntity<Long> countBadges(BadgeCriteria criteria) {
        log.debug("REST request to count Badges by criteria: {}", criteria);
        return ResponseEntity.ok().body(badgeQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /badges/:id} : get the "id" badge.
     *
     * @param id the id of the badgeDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the badgeDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/badges/{id}")
    public ResponseEntity<BadgeDTO> getBadge(@PathVariable Long id) {
        log.debug("REST request to get Badge : {}", id);
        Optional<BadgeDTO> badgeDTO = badgeService.findOne(id);
        return ResponseUtil.wrapOrNotFound(badgeDTO);
    }

    /**
     * {@code DELETE  /badges/:id} : delete the "id" badge.
     *
     * @param id the id of the badgeDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    // ### MODIFICATION-START ###
    @Secured(AuthoritiesConstants.ADMIN)
    // ### MODIFICATION-END ###
    @DeleteMapping("/badges/{id}")
    public ResponseEntity<Void> deleteBadge(@PathVariable Long id) {
        log.debug("REST request to delete Badge : {}", id);
        badgeService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
