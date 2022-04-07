package com.iteratec.teamdojo.service;

import com.iteratec.teamdojo.service.dto.TeamGroupDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.iteratec.teamdojo.domain.TeamGroup}.
 */
public interface TeamGroupService {
    /**
     * Save a teamGroup.
     *
     * @param teamGroupDTO the entity to save.
     * @return the persisted entity.
     */
    TeamGroupDTO save(TeamGroupDTO teamGroupDTO);

    /**
     * Partially updates a teamGroup.
     *
     * @param teamGroupDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<TeamGroupDTO> partialUpdate(TeamGroupDTO teamGroupDTO);

    /**
     * Get all the teamGroups.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<TeamGroupDTO> findAll(Pageable pageable);

    /**
     * Get the "id" teamGroup.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<TeamGroupDTO> findOne(Long id);

    /**
     * Delete the "id" teamGroup.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
