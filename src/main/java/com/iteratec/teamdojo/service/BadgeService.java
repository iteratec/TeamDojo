package com.iteratec.teamdojo.service;

import com.iteratec.teamdojo.GeneratedByJHipster;
import com.iteratec.teamdojo.service.dto.BadgeDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.iteratec.teamdojo.domain.Badge}.
 */
@GeneratedByJHipster
public interface BadgeService {
    /**
     * Save a badge.
     *
     * @param badgeDTO the entity to save.
     * @return the persisted entity.
     */
    BadgeDTO save(BadgeDTO badgeDTO);

    /**
     * Partially updates a badge.
     *
     * @param badgeDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<BadgeDTO> partialUpdate(BadgeDTO badgeDTO);

    /**
     * Get all the badges.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<BadgeDTO> findAll(Pageable pageable);

    /**
     * Get all the badges with eager load of many-to-many relationships.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<BadgeDTO> findAllWithEagerRelationships(Pageable pageable);

    /**
     * Get the "id" badge.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<BadgeDTO> findOne(Long id);

    /**
     * Delete the "id" badge.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
