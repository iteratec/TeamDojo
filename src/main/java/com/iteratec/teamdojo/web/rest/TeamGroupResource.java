package com.iteratec.teamdojo.web.rest;

import com.iteratec.teamdojo.repository.TeamGroupRepository;
import com.iteratec.teamdojo.service.TeamGroupQueryService;
import com.iteratec.teamdojo.service.TeamGroupService;
import com.iteratec.teamdojo.service.criteria.TeamGroupCriteria;
import com.iteratec.teamdojo.service.dto.TeamGroupDTO;
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
 * REST controller for managing {@link com.iteratec.teamdojo.domain.TeamGroup}.
 */
@RestController
@RequestMapping("/api")
public class TeamGroupResource {

    private final Logger log = LoggerFactory.getLogger(TeamGroupResource.class);

    private static final String ENTITY_NAME = "teamGroup";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TeamGroupService teamGroupService;

    private final TeamGroupRepository teamGroupRepository;

    private final TeamGroupQueryService teamGroupQueryService;

    public TeamGroupResource(
        TeamGroupService teamGroupService,
        TeamGroupRepository teamGroupRepository,
        TeamGroupQueryService teamGroupQueryService
    ) {
        this.teamGroupService = teamGroupService;
        this.teamGroupRepository = teamGroupRepository;
        this.teamGroupQueryService = teamGroupQueryService;
    }

    /**
     * {@code POST  /team-groups} : Create a new teamGroup.
     *
     * @param teamGroupDTO the teamGroupDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new teamGroupDTO, or with status {@code 400 (Bad Request)} if the teamGroup has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/team-groups")
    public ResponseEntity<TeamGroupDTO> createTeamGroup(@Valid @RequestBody TeamGroupDTO teamGroupDTO) throws URISyntaxException {
        log.debug("REST request to save TeamGroup : {}", teamGroupDTO);
        if (teamGroupDTO.getId() != null) {
            throw new BadRequestAlertException("A new teamGroup cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TeamGroupDTO result = teamGroupService.save(teamGroupDTO);
        return ResponseEntity
            .created(new URI("/api/team-groups/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /team-groups/:id} : Updates an existing teamGroup.
     *
     * @param id the id of the teamGroupDTO to save.
     * @param teamGroupDTO the teamGroupDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated teamGroupDTO,
     * or with status {@code 400 (Bad Request)} if the teamGroupDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the teamGroupDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/team-groups/{id}")
    public ResponseEntity<TeamGroupDTO> updateTeamGroup(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody TeamGroupDTO teamGroupDTO
    ) throws URISyntaxException {
        log.debug("REST request to update TeamGroup : {}, {}", id, teamGroupDTO);
        if (teamGroupDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, teamGroupDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!teamGroupRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        TeamGroupDTO result = teamGroupService.save(teamGroupDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, teamGroupDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /team-groups/:id} : Partial updates given fields of an existing teamGroup, field will ignore if it is null
     *
     * @param id the id of the teamGroupDTO to save.
     * @param teamGroupDTO the teamGroupDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated teamGroupDTO,
     * or with status {@code 400 (Bad Request)} if the teamGroupDTO is not valid,
     * or with status {@code 404 (Not Found)} if the teamGroupDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the teamGroupDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/team-groups/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<TeamGroupDTO> partialUpdateTeamGroup(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody TeamGroupDTO teamGroupDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update TeamGroup partially : {}, {}", id, teamGroupDTO);
        if (teamGroupDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, teamGroupDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!teamGroupRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<TeamGroupDTO> result = teamGroupService.partialUpdate(teamGroupDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, teamGroupDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /team-groups} : get all the teamGroups.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of teamGroups in body.
     */
    @GetMapping("/team-groups")
    public ResponseEntity<List<TeamGroupDTO>> getAllTeamGroups(
        TeamGroupCriteria criteria,
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get TeamGroups by criteria: {}", criteria);
        Page<TeamGroupDTO> page = teamGroupQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /team-groups/count} : count all the teamGroups.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/team-groups/count")
    public ResponseEntity<Long> countTeamGroups(TeamGroupCriteria criteria) {
        log.debug("REST request to count TeamGroups by criteria: {}", criteria);
        return ResponseEntity.ok().body(teamGroupQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /team-groups/:id} : get the "id" teamGroup.
     *
     * @param id the id of the teamGroupDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the teamGroupDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/team-groups/{id}")
    public ResponseEntity<TeamGroupDTO> getTeamGroup(@PathVariable Long id) {
        log.debug("REST request to get TeamGroup : {}", id);
        Optional<TeamGroupDTO> teamGroupDTO = teamGroupService.findOne(id);
        return ResponseUtil.wrapOrNotFound(teamGroupDTO);
    }

    /**
     * {@code DELETE  /team-groups/:id} : delete the "id" teamGroup.
     *
     * @param id the id of the teamGroupDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/team-groups/{id}")
    public ResponseEntity<Void> deleteTeamGroup(@PathVariable Long id) {
        log.debug("REST request to delete TeamGroup : {}", id);
        teamGroupService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
