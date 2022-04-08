package com.iteratec.teamdojo.service;

import com.iteratec.teamdojo.GeneratedByJHipster;
import com.iteratec.teamdojo.service.dto.LevelDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.iteratec.teamdojo.domain.Level}.
 */
@GeneratedByJHipster
public interface LevelService {
    /**
     * Save a level.
     *
     * @param levelDTO the entity to save.
     * @return the persisted entity.
     */
    LevelDTO save(LevelDTO levelDTO);

    /**
     * Partially updates a level.
     *
     * @param levelDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<LevelDTO> partialUpdate(LevelDTO levelDTO);

    /**
     * Get all the levels.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<LevelDTO> findAll(Pageable pageable);

    /**
     * Get all the levels with eager load of many-to-many relationships.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<LevelDTO> findAllWithEagerRelationships(Pageable pageable);

    /**
     * Get the "id" level.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<LevelDTO> findOne(Long id);

    /**
     * Delete the "id" level.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
