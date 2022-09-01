package com.iteratec.teamdojo.web.rest;

import com.iteratec.teamdojo.GeneratedByJHipster;
import com.iteratec.teamdojo.repository.DimensionRepository;
// ### MODIFICATION-START ###
import com.iteratec.teamdojo.security.AuthoritiesConstants;
// ### MODIFICATION-END ###
import com.iteratec.teamdojo.service.DimensionQueryService;
import com.iteratec.teamdojo.service.DimensionService;
import com.iteratec.teamdojo.service.criteria.DimensionCriteria;
import com.iteratec.teamdojo.service.dto.DimensionDTO;
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
 * REST controller for managing {@link com.iteratec.teamdojo.domain.Dimension}.
 */
@RestController
@RequestMapping("/api")
@GeneratedByJHipster
public class DimensionResource {

    private final Logger log = LoggerFactory.getLogger(DimensionResource.class);

    private static final String ENTITY_NAME = "dimension";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final DimensionService dimensionService;

    private final DimensionRepository dimensionRepository;

    private final DimensionQueryService dimensionQueryService;

    public DimensionResource(
        DimensionService dimensionService,
        DimensionRepository dimensionRepository,
        DimensionQueryService dimensionQueryService
    ) {
        this.dimensionService = dimensionService;
        this.dimensionRepository = dimensionRepository;
        this.dimensionQueryService = dimensionQueryService;
    }

    /**
     * {@code POST  /dimensions} : Create a new dimension.
     *
     * @param dimensionDTO the dimensionDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new dimensionDTO, or with status {@code 400 (Bad Request)} if the dimension has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    // ### MODIFICATION-START ###
    @Secured(AuthoritiesConstants.ADMIN)
    // ### MODIFICATION-END ###
    @PostMapping("/dimensions")
    public ResponseEntity<DimensionDTO> createDimension(@Valid @RequestBody DimensionDTO dimensionDTO) throws URISyntaxException {
        log.debug("REST request to save Dimension : {}", dimensionDTO);
        if (dimensionDTO.getId() != null) {
            throw new BadRequestAlertException("A new dimension cannot already have an ID", ENTITY_NAME, "idexists");
        }
        DimensionDTO result = dimensionService.save(dimensionDTO);
        return ResponseEntity
            .created(new URI("/api/dimensions/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /dimensions/:id} : Updates an existing dimension.
     *
     * @param id the id of the dimensionDTO to save.
     * @param dimensionDTO the dimensionDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated dimensionDTO,
     * or with status {@code 400 (Bad Request)} if the dimensionDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the dimensionDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    // ### MODIFICATION-START ###
    @Secured(AuthoritiesConstants.ADMIN)
    // ### MODIFICATION-END ###
    @PutMapping("/dimensions/{id}")
    public ResponseEntity<DimensionDTO> updateDimension(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody DimensionDTO dimensionDTO
    ) throws URISyntaxException {
        log.debug("REST request to update Dimension : {}, {}", id, dimensionDTO);
        if (dimensionDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, dimensionDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!dimensionRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        DimensionDTO result = dimensionService.update(dimensionDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, dimensionDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /dimensions/:id} : Partial updates given fields of an existing dimension, field will ignore if it is null
     *
     * @param id the id of the dimensionDTO to save.
     * @param dimensionDTO the dimensionDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated dimensionDTO,
     * or with status {@code 400 (Bad Request)} if the dimensionDTO is not valid,
     * or with status {@code 404 (Not Found)} if the dimensionDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the dimensionDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    // ### MODIFICATION-START ###
    @Secured(AuthoritiesConstants.ADMIN)
    // ### MODIFICATION-END ###
    @PatchMapping(value = "/dimensions/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<DimensionDTO> partialUpdateDimension(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody DimensionDTO dimensionDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update Dimension partially : {}, {}", id, dimensionDTO);
        if (dimensionDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, dimensionDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!dimensionRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<DimensionDTO> result = dimensionService.partialUpdate(dimensionDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, dimensionDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /dimensions} : get all the dimensions.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of dimensions in body.
     */
    @GetMapping("/dimensions")
    public ResponseEntity<List<DimensionDTO>> getAllDimensions(
        DimensionCriteria criteria,
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get Dimensions by criteria: {}", criteria.toString().replaceAll("[\n\r\t]", "_"));
        Page<DimensionDTO> page = dimensionQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /dimensions/count} : count all the dimensions.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/dimensions/count")
    public ResponseEntity<Long> countDimensions(DimensionCriteria criteria) {
        log.debug("REST request to count Dimensions by criteria: {}", criteria.toString().replaceAll("[\n\r\t]", "_"));
        return ResponseEntity.ok().body(dimensionQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /dimensions/:id} : get the "id" dimension.
     *
     * @param id the id of the dimensionDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the dimensionDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/dimensions/{id}")
    public ResponseEntity<DimensionDTO> getDimension(@PathVariable Long id) {
        log.debug("REST request to get Dimension : {}", id);
        Optional<DimensionDTO> dimensionDTO = dimensionService.findOne(id);
        return ResponseUtil.wrapOrNotFound(dimensionDTO);
    }

    /**
     * {@code DELETE  /dimensions/:id} : delete the "id" dimension.
     *
     * @param id the id of the dimensionDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    // ### MODIFICATION-START ###
    @Secured(AuthoritiesConstants.ADMIN)
    // ### MODIFICATION-END ###
    @DeleteMapping("/dimensions/{id}")
    public ResponseEntity<Void> deleteDimension(@PathVariable Long id) {
        log.debug("REST request to delete Dimension : {}", id);
        dimensionService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
