package com.iteratec.teamdojo.web.rest;

import com.iteratec.teamdojo.domain.PersistentAuditEventData;
import com.iteratec.teamdojo.repository.PersistentAuditEventDataRepository;
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
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.iteratec.teamdojo.domain.PersistentAuditEventData}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class PersistentAuditEventDataResource {

    private final Logger log = LoggerFactory.getLogger(PersistentAuditEventDataResource.class);

    private static final String ENTITY_NAME = "persistentAuditEventData";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PersistentAuditEventDataRepository persistentAuditEventDataRepository;

    public PersistentAuditEventDataResource(PersistentAuditEventDataRepository persistentAuditEventDataRepository) {
        this.persistentAuditEventDataRepository = persistentAuditEventDataRepository;
    }

    /**
     * {@code POST  /persistent-audit-event-data} : Create a new persistentAuditEventData.
     *
     * @param persistentAuditEventData the persistentAuditEventData to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new persistentAuditEventData, or with status {@code 400 (Bad Request)} if the persistentAuditEventData has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/persistent-audit-event-data")
    public ResponseEntity<PersistentAuditEventData> createPersistentAuditEventData(
        @Valid @RequestBody PersistentAuditEventData persistentAuditEventData
    ) throws URISyntaxException {
        log.debug("REST request to save PersistentAuditEventData : {}", persistentAuditEventData);
        if (persistentAuditEventData.getId() != null) {
            throw new BadRequestAlertException("A new persistentAuditEventData cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PersistentAuditEventData result = persistentAuditEventDataRepository.save(persistentAuditEventData);
        return ResponseEntity
            .created(new URI("/api/persistent-audit-event-data/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /persistent-audit-event-data/:id} : Updates an existing persistentAuditEventData.
     *
     * @param id the id of the persistentAuditEventData to save.
     * @param persistentAuditEventData the persistentAuditEventData to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated persistentAuditEventData,
     * or with status {@code 400 (Bad Request)} if the persistentAuditEventData is not valid,
     * or with status {@code 500 (Internal Server Error)} if the persistentAuditEventData couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/persistent-audit-event-data/{id}")
    public ResponseEntity<PersistentAuditEventData> updatePersistentAuditEventData(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody PersistentAuditEventData persistentAuditEventData
    ) throws URISyntaxException {
        log.debug("REST request to update PersistentAuditEventData : {}, {}", id, persistentAuditEventData);
        if (persistentAuditEventData.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, persistentAuditEventData.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!persistentAuditEventDataRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        PersistentAuditEventData result = persistentAuditEventDataRepository.save(persistentAuditEventData);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, persistentAuditEventData.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /persistent-audit-event-data/:id} : Partial updates given fields of an existing persistentAuditEventData, field will ignore if it is null
     *
     * @param id the id of the persistentAuditEventData to save.
     * @param persistentAuditEventData the persistentAuditEventData to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated persistentAuditEventData,
     * or with status {@code 400 (Bad Request)} if the persistentAuditEventData is not valid,
     * or with status {@code 404 (Not Found)} if the persistentAuditEventData is not found,
     * or with status {@code 500 (Internal Server Error)} if the persistentAuditEventData couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/persistent-audit-event-data/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<PersistentAuditEventData> partialUpdatePersistentAuditEventData(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody PersistentAuditEventData persistentAuditEventData
    ) throws URISyntaxException {
        log.debug("REST request to partial update PersistentAuditEventData partially : {}, {}", id, persistentAuditEventData);
        if (persistentAuditEventData.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, persistentAuditEventData.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!persistentAuditEventDataRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<PersistentAuditEventData> result = persistentAuditEventDataRepository
            .findById(persistentAuditEventData.getId())
            .map(
                existingPersistentAuditEventData -> {
                    if (persistentAuditEventData.getName() != null) {
                        existingPersistentAuditEventData.setName(persistentAuditEventData.getName());
                    }
                    if (persistentAuditEventData.getValue() != null) {
                        existingPersistentAuditEventData.setValue(persistentAuditEventData.getValue());
                    }

                    return existingPersistentAuditEventData;
                }
            )
            .map(persistentAuditEventDataRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, persistentAuditEventData.getId().toString())
        );
    }

    /**
     * {@code GET  /persistent-audit-event-data} : get all the persistentAuditEventData.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of persistentAuditEventData in body.
     */
    @GetMapping("/persistent-audit-event-data")
    public List<PersistentAuditEventData> getAllPersistentAuditEventData() {
        log.debug("REST request to get all PersistentAuditEventData");
        return persistentAuditEventDataRepository.findAll();
    }

    /**
     * {@code GET  /persistent-audit-event-data/:id} : get the "id" persistentAuditEventData.
     *
     * @param id the id of the persistentAuditEventData to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the persistentAuditEventData, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/persistent-audit-event-data/{id}")
    public ResponseEntity<PersistentAuditEventData> getPersistentAuditEventData(@PathVariable Long id) {
        log.debug("REST request to get PersistentAuditEventData : {}", id);
        Optional<PersistentAuditEventData> persistentAuditEventData = persistentAuditEventDataRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(persistentAuditEventData);
    }

    /**
     * {@code DELETE  /persistent-audit-event-data/:id} : delete the "id" persistentAuditEventData.
     *
     * @param id the id of the persistentAuditEventData to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/persistent-audit-event-data/{id}")
    public ResponseEntity<Void> deletePersistentAuditEventData(@PathVariable Long id) {
        log.debug("REST request to delete PersistentAuditEventData : {}", id);
        persistentAuditEventDataRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
