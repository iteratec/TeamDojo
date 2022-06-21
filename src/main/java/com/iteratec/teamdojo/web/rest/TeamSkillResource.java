package com.iteratec.teamdojo.web.rest;

import com.iteratec.teamdojo.GeneratedByJHipster;
import com.iteratec.teamdojo.repository.TeamSkillRepository;
import com.iteratec.teamdojo.service.TeamSkillQueryService;
import com.iteratec.teamdojo.service.TeamSkillService;
import com.iteratec.teamdojo.service.criteria.TeamSkillCriteria;
import com.iteratec.teamdojo.service.dto.TeamSkillDTO;
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
 * REST controller for managing {@link com.iteratec.teamdojo.domain.TeamSkill}.
 */
@RestController
@RequestMapping("/api")
@GeneratedByJHipster
public class TeamSkillResource {

    private final Logger log = LoggerFactory.getLogger(TeamSkillResource.class);

    private static final String ENTITY_NAME = "teamSkill";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TeamSkillService teamSkillService;

    private final TeamSkillRepository teamSkillRepository;

    private final TeamSkillQueryService teamSkillQueryService;

    public TeamSkillResource(
        TeamSkillService teamSkillService,
        TeamSkillRepository teamSkillRepository,
        TeamSkillQueryService teamSkillQueryService
    ) {
        this.teamSkillService = teamSkillService;
        this.teamSkillRepository = teamSkillRepository;
        this.teamSkillQueryService = teamSkillQueryService;
    }

    /**
     * {@code POST  /team-skills} : Create a new teamSkill.
     *
     * @param teamSkillDTO the teamSkillDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new teamSkillDTO, or with status {@code 400 (Bad Request)} if the teamSkill has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/team-skills")
    public ResponseEntity<TeamSkillDTO> createTeamSkill(@Valid @RequestBody TeamSkillDTO teamSkillDTO) throws URISyntaxException {
        log.debug("REST request to save TeamSkill : {}", teamSkillDTO);
        if (teamSkillDTO.getId() != null) {
            throw new BadRequestAlertException("A new teamSkill cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TeamSkillDTO result = teamSkillService.save(teamSkillDTO);
        return ResponseEntity
            .created(new URI("/api/team-skills/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /team-skills/:id} : Updates an existing teamSkill.
     *
     * @param id the id of the teamSkillDTO to save.
     * @param teamSkillDTO the teamSkillDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated teamSkillDTO,
     * or with status {@code 400 (Bad Request)} if the teamSkillDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the teamSkillDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/team-skills/{id}")
    public ResponseEntity<TeamSkillDTO> updateTeamSkill(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody TeamSkillDTO teamSkillDTO
    ) throws URISyntaxException {
        log.debug("REST request to update TeamSkill : {}, {}", id, teamSkillDTO);
        if (teamSkillDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, teamSkillDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!teamSkillRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        TeamSkillDTO result = teamSkillService.update(teamSkillDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, teamSkillDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /team-skills/:id} : Partial updates given fields of an existing teamSkill, field will ignore if it is null
     *
     * @param id the id of the teamSkillDTO to save.
     * @param teamSkillDTO the teamSkillDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated teamSkillDTO,
     * or with status {@code 400 (Bad Request)} if the teamSkillDTO is not valid,
     * or with status {@code 404 (Not Found)} if the teamSkillDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the teamSkillDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/team-skills/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<TeamSkillDTO> partialUpdateTeamSkill(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody TeamSkillDTO teamSkillDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update TeamSkill partially : {}, {}", id, teamSkillDTO);
        if (teamSkillDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, teamSkillDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!teamSkillRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<TeamSkillDTO> result = teamSkillService.partialUpdate(teamSkillDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, teamSkillDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /team-skills} : get all the teamSkills.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of teamSkills in body.
     */
    @GetMapping("/team-skills")
    public ResponseEntity<List<TeamSkillDTO>> getAllTeamSkills(
        TeamSkillCriteria criteria,
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get TeamSkills by criteria: {}", criteria);
        Page<TeamSkillDTO> page = teamSkillQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /team-skills/count} : count all the teamSkills.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/team-skills/count")
    public ResponseEntity<Long> countTeamSkills(TeamSkillCriteria criteria) {
        log.debug("REST request to count TeamSkills by criteria: {}", criteria);
        return ResponseEntity.ok().body(teamSkillQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /team-skills/:id} : get the "id" teamSkill.
     *
     * @param id the id of the teamSkillDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the teamSkillDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/team-skills/{id}")
    public ResponseEntity<TeamSkillDTO> getTeamSkill(@PathVariable Long id) {
        log.debug("REST request to get TeamSkill : {}", id);
        Optional<TeamSkillDTO> teamSkillDTO = teamSkillService.findOne(id);
        return ResponseUtil.wrapOrNotFound(teamSkillDTO);
    }

    /**
     * {@code DELETE  /team-skills/:id} : delete the "id" teamSkill.
     *
     * @param id the id of the teamSkillDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/team-skills/{id}")
    public ResponseEntity<Void> deleteTeamSkill(@PathVariable Long id) {
        log.debug("REST request to delete TeamSkill : {}", id);
        teamSkillService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
