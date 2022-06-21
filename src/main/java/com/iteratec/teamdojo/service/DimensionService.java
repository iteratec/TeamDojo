package com.iteratec.teamdojo.service;

import com.iteratec.teamdojo.GeneratedByJHipster;
import com.iteratec.teamdojo.service.dto.DimensionDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.iteratec.teamdojo.domain.Dimension}.
 */
@GeneratedByJHipster
public interface DimensionService {
    /**
     * Save a dimension.
     *
     * @param dimensionDTO the entity to save.
     * @return the persisted entity.
     */
    DimensionDTO save(DimensionDTO dimensionDTO);

    /**
     * Updates a dimension.
     *
     * @param dimensionDTO the entity to update.
     * @return the persisted entity.
     */
    DimensionDTO update(DimensionDTO dimensionDTO);

    /**
     * Partially updates a dimension.
     *
     * @param dimensionDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<DimensionDTO> partialUpdate(DimensionDTO dimensionDTO);

    /**
     * Get all the dimensions.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<DimensionDTO> findAll(Pageable pageable);

    /**
     * Get the "id" dimension.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<DimensionDTO> findOne(Long id);

    /**
     * Delete the "id" dimension.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
