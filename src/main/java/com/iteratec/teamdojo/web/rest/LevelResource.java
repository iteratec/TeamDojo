package com.iteratec.teamdojo.web.rest;

import com.iteratec.teamdojo.repository.LevelRepository;
import com.iteratec.teamdojo.service.LevelQueryService;
import com.iteratec.teamdojo.service.LevelService;
import com.iteratec.teamdojo.service.criteria.LevelCriteria;
import com.iteratec.teamdojo.service.dto.LevelDTO;
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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.iteratec.teamdojo.domain.Level}.
 */
@RestController
@RequestMapping("/api")
public class LevelResource {

    private final Logger log = LoggerFactory.getLogger(LevelResource.class);

    private static final String ENTITY_NAME = "level";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final LevelService levelService;

    private final LevelRepository levelRepository;

    private final LevelQueryService levelQueryService;

    public LevelResource(LevelService levelService, LevelRepository levelRepository, LevelQueryService levelQueryService) {
        this.levelService = levelService;
        this.levelRepository = levelRepository;
        this.levelQueryService = levelQueryService;
    }

    /**
     * {@code POST  /levels} : Create a new level.
     *
     * @param levelDTO the levelDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new levelDTO, or with status {@code 400 (Bad Request)} if the level has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/levels")
    public ResponseEntity<LevelDTO> createLevel(@Valid @RequestBody LevelDTO levelDTO) throws URISyntaxException {
        log.debug("REST request to save Level : {}", levelDTO);
        if (levelDTO.getId() != null) {
            throw new BadRequestAlertException("A new level cannot already have an ID", ENTITY_NAME, "idexists");
        }
        LevelDTO result = levelService.save(levelDTO);
        return ResponseEntity
            .created(new URI("/api/levels/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /levels/:id} : Updates an existing level.
     *
     * @param id the id of the levelDTO to save.
     * @param levelDTO the levelDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated levelDTO,
     * or with status {@code 400 (Bad Request)} if the levelDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the levelDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/levels/{id}")
    public ResponseEntity<LevelDTO> updateLevel(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody LevelDTO levelDTO
    ) throws URISyntaxException {
        log.debug("REST request to update Level : {}, {}", id, levelDTO);
        if (levelDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, levelDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!levelRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        LevelDTO result = levelService.save(levelDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, levelDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /levels/:id} : Partial updates given fields of an existing level, field will ignore if it is null
     *
     * @param id the id of the levelDTO to save.
     * @param levelDTO the levelDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated levelDTO,
     * or with status {@code 400 (Bad Request)} if the levelDTO is not valid,
     * or with status {@code 404 (Not Found)} if the levelDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the levelDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/levels/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<LevelDTO> partialUpdateLevel(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody LevelDTO levelDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update Level partially : {}, {}", id, levelDTO);
        if (levelDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, levelDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!levelRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<LevelDTO> result = levelService.partialUpdate(levelDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, levelDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /levels} : get all the levels.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of levels in body.
     */
    @GetMapping("/levels")
    public ResponseEntity<List<LevelDTO>> getAllLevels(LevelCriteria criteria, Pageable pageable) {
        log.debug("REST request to get Levels by criteria: {}", criteria);
        Page<LevelDTO> page = levelQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /levels/count} : count all the levels.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/levels/count")
    public ResponseEntity<Long> countLevels(LevelCriteria criteria) {
        log.debug("REST request to count Levels by criteria: {}", criteria);
        return ResponseEntity.ok().body(levelQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /levels/:id} : get the "id" level.
     *
     * @param id the id of the levelDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the levelDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/levels/{id}")
    public ResponseEntity<LevelDTO> getLevel(@PathVariable Long id) {
        log.debug("REST request to get Level : {}", id);
        Optional<LevelDTO> levelDTO = levelService.findOne(id);
        return ResponseUtil.wrapOrNotFound(levelDTO);
    }

    /**
     * {@code DELETE  /levels/:id} : delete the "id" level.
     *
     * @param id the id of the levelDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/levels/{id}")
    public ResponseEntity<Void> deleteLevel(@PathVariable Long id) {
        log.debug("REST request to delete Level : {}", id);
        levelService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
