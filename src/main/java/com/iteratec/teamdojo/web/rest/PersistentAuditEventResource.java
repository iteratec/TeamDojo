package com.iteratec.teamdojo.web.rest;

import com.iteratec.teamdojo.domain.PersistentAuditEvent;
import com.iteratec.teamdojo.repository.PersistentAuditEventRepository;
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
 * REST controller for managing {@link com.iteratec.teamdojo.domain.PersistentAuditEvent}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class PersistentAuditEventResource {

    private final Logger log = LoggerFactory.getLogger(PersistentAuditEventResource.class);

    private static final String ENTITY_NAME = "persistentAuditEvent";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PersistentAuditEventRepository persistentAuditEventRepository;

    public PersistentAuditEventResource(PersistentAuditEventRepository persistentAuditEventRepository) {
        this.persistentAuditEventRepository = persistentAuditEventRepository;
    }

    /**
     * {@code POST  /persistent-audit-events} : Create a new persistentAuditEvent.
     *
     * @param persistentAuditEvent the persistentAuditEvent to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new persistentAuditEvent, or with status {@code 400 (Bad Request)} if the persistentAuditEvent has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/persistent-audit-events")
    public ResponseEntity<PersistentAuditEvent> createPersistentAuditEvent(@Valid @RequestBody PersistentAuditEvent persistentAuditEvent)
        throws URISyntaxException {
        log.debug("REST request to save PersistentAuditEvent : {}", persistentAuditEvent);
        if (persistentAuditEvent.getId() != null) {
            throw new BadRequestAlertException("A new persistentAuditEvent cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PersistentAuditEvent result = persistentAuditEventRepository.save(persistentAuditEvent);
        return ResponseEntity
            .created(new URI("/api/persistent-audit-events/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /persistent-audit-events/:id} : Updates an existing persistentAuditEvent.
     *
     * @param id the id of the persistentAuditEvent to save.
     * @param persistentAuditEvent the persistentAuditEvent to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated persistentAuditEvent,
     * or with status {@code 400 (Bad Request)} if the persistentAuditEvent is not valid,
     * or with status {@code 500 (Internal Server Error)} if the persistentAuditEvent couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/persistent-audit-events/{id}")
    public ResponseEntity<PersistentAuditEvent> updatePersistentAuditEvent(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody PersistentAuditEvent persistentAuditEvent
    ) throws URISyntaxException {
        log.debug("REST request to update PersistentAuditEvent : {}, {}", id, persistentAuditEvent);
        if (persistentAuditEvent.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, persistentAuditEvent.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!persistentAuditEventRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        PersistentAuditEvent result = persistentAuditEventRepository.save(persistentAuditEvent);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, persistentAuditEvent.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /persistent-audit-events/:id} : Partial updates given fields of an existing persistentAuditEvent, field will ignore if it is null
     *
     * @param id the id of the persistentAuditEvent to save.
     * @param persistentAuditEvent the persistentAuditEvent to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated persistentAuditEvent,
     * or with status {@code 400 (Bad Request)} if the persistentAuditEvent is not valid,
     * or with status {@code 404 (Not Found)} if the persistentAuditEvent is not found,
     * or with status {@code 500 (Internal Server Error)} if the persistentAuditEvent couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/persistent-audit-events/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<PersistentAuditEvent> partialUpdatePersistentAuditEvent(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody PersistentAuditEvent persistentAuditEvent
    ) throws URISyntaxException {
        log.debug("REST request to partial update PersistentAuditEvent partially : {}, {}", id, persistentAuditEvent);
        if (persistentAuditEvent.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, persistentAuditEvent.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!persistentAuditEventRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<PersistentAuditEvent> result = persistentAuditEventRepository
            .findById(persistentAuditEvent.getId())
            .map(
                existingPersistentAuditEvent -> {
                    if (persistentAuditEvent.getPrincipal() != null) {
                        existingPersistentAuditEvent.setPrincipal(persistentAuditEvent.getPrincipal());
                    }
                    if (persistentAuditEvent.getAuditEventDate() != null) {
                        existingPersistentAuditEvent.setAuditEventDate(persistentAuditEvent.getAuditEventDate());
                    }
                    if (persistentAuditEvent.getAuditEventType() != null) {
                        existingPersistentAuditEvent.setAuditEventType(persistentAuditEvent.getAuditEventType());
                    }

                    return existingPersistentAuditEvent;
                }
            )
            .map(persistentAuditEventRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, persistentAuditEvent.getId().toString())
        );
    }

    /**
     * {@code GET  /persistent-audit-events} : get all the persistentAuditEvents.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of persistentAuditEvents in body.
     */
    @GetMapping("/persistent-audit-events")
    public List<PersistentAuditEvent> getAllPersistentAuditEvents() {
        log.debug("REST request to get all PersistentAuditEvents");
        return persistentAuditEventRepository.findAll();
    }

    /**
     * {@code GET  /persistent-audit-events/:id} : get the "id" persistentAuditEvent.
     *
     * @param id the id of the persistentAuditEvent to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the persistentAuditEvent, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/persistent-audit-events/{id}")
    public ResponseEntity<PersistentAuditEvent> getPersistentAuditEvent(@PathVariable Long id) {
        log.debug("REST request to get PersistentAuditEvent : {}", id);
        Optional<PersistentAuditEvent> persistentAuditEvent = persistentAuditEventRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(persistentAuditEvent);
    }

    /**
     * {@code DELETE  /persistent-audit-events/:id} : delete the "id" persistentAuditEvent.
     *
     * @param id the id of the persistentAuditEvent to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/persistent-audit-events/{id}")
    public ResponseEntity<Void> deletePersistentAuditEvent(@PathVariable Long id) {
        log.debug("REST request to delete PersistentAuditEvent : {}", id);
        persistentAuditEventRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
