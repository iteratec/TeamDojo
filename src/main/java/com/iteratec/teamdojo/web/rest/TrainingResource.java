package com.iteratec.teamdojo.web.rest;

import com.iteratec.teamdojo.GeneratedByJHipster;
import com.iteratec.teamdojo.repository.TrainingRepository;
import com.iteratec.teamdojo.service.TrainingQueryService;
import com.iteratec.teamdojo.service.TrainingService;
import com.iteratec.teamdojo.service.criteria.TrainingCriteria;
import com.iteratec.teamdojo.service.dto.TrainingDTO;
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
 * REST controller for managing {@link com.iteratec.teamdojo.domain.Training}.
 */
@RestController
@RequestMapping("/api")
@GeneratedByJHipster
public class TrainingResource {

    private final Logger log = LoggerFactory.getLogger(TrainingResource.class);

    private static final String ENTITY_NAME = "training";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TrainingService trainingService;

    private final TrainingRepository trainingRepository;

    private final TrainingQueryService trainingQueryService;

    public TrainingResource(
        TrainingService trainingService,
        TrainingRepository trainingRepository,
        TrainingQueryService trainingQueryService
    ) {
        this.trainingService = trainingService;
        this.trainingRepository = trainingRepository;
        this.trainingQueryService = trainingQueryService;
    }

    /**
     * {@code POST  /trainings} : Create a new training.
     *
     * @param trainingDTO the trainingDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new trainingDTO, or with status {@code 400 (Bad Request)} if the training has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/trainings")
    public ResponseEntity<TrainingDTO> createTraining(@Valid @RequestBody TrainingDTO trainingDTO) throws URISyntaxException {
        log.debug("REST request to save Training : {}", trainingDTO);
        if (trainingDTO.getId() != null) {
            throw new BadRequestAlertException("A new training cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TrainingDTO result = trainingService.save(trainingDTO);
        return ResponseEntity
            .created(new URI("/api/trainings/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /trainings/:id} : Updates an existing training.
     *
     * @param id the id of the trainingDTO to save.
     * @param trainingDTO the trainingDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated trainingDTO,
     * or with status {@code 400 (Bad Request)} if the trainingDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the trainingDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/trainings/{id}")
    public ResponseEntity<TrainingDTO> updateTraining(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody TrainingDTO trainingDTO
    ) throws URISyntaxException {
        log.debug("REST request to update Training : {}, {}", id, trainingDTO);
        if (trainingDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, trainingDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!trainingRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        TrainingDTO result = trainingService.update(trainingDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, trainingDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /trainings/:id} : Partial updates given fields of an existing training, field will ignore if it is null
     *
     * @param id the id of the trainingDTO to save.
     * @param trainingDTO the trainingDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated trainingDTO,
     * or with status {@code 400 (Bad Request)} if the trainingDTO is not valid,
     * or with status {@code 404 (Not Found)} if the trainingDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the trainingDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/trainings/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<TrainingDTO> partialUpdateTraining(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody TrainingDTO trainingDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update Training partially : {}, {}", id, trainingDTO);
        if (trainingDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, trainingDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!trainingRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<TrainingDTO> result = trainingService.partialUpdate(trainingDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, trainingDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /trainings} : get all the trainings.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of trainings in body.
     */
    @GetMapping("/trainings")
    public ResponseEntity<List<TrainingDTO>> getAllTrainings(
        TrainingCriteria criteria,
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get Trainings by criteria: {}", criteria.toString().replaceAll("[\n\r\t]", "_"));
        Page<TrainingDTO> page = trainingQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /trainings/count} : count all the trainings.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/trainings/count")
    public ResponseEntity<Long> countTrainings(TrainingCriteria criteria) {
        log.debug("REST request to count Trainings by criteria: {}", criteria.toString().replaceAll("[\n\r\t]", "_"));
        return ResponseEntity.ok().body(trainingQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /trainings/:id} : get the "id" training.
     *
     * @param id the id of the trainingDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the trainingDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/trainings/{id}")
    public ResponseEntity<TrainingDTO> getTraining(@PathVariable Long id) {
        log.debug("REST request to get Training : {}", id);
        Optional<TrainingDTO> trainingDTO = trainingService.findOne(id);
        return ResponseUtil.wrapOrNotFound(trainingDTO);
    }

    /**
     * {@code DELETE  /trainings/:id} : delete the "id" training.
     *
     * @param id the id of the trainingDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/trainings/{id}")
    public ResponseEntity<Void> deleteTraining(@PathVariable Long id) {
        log.debug("REST request to delete Training : {}", id);
        trainingService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
