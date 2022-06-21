package com.iteratec.teamdojo.service;

import com.iteratec.teamdojo.GeneratedByJHipster;
import com.iteratec.teamdojo.service.dto.TrainingDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.iteratec.teamdojo.domain.Training}.
 */
@GeneratedByJHipster
public interface TrainingService {
    /**
     * Save a training.
     *
     * @param trainingDTO the entity to save.
     * @return the persisted entity.
     */
    TrainingDTO save(TrainingDTO trainingDTO);

    /**
     * Updates a training.
     *
     * @param trainingDTO the entity to update.
     * @return the persisted entity.
     */
    TrainingDTO update(TrainingDTO trainingDTO);

    /**
     * Partially updates a training.
     *
     * @param trainingDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<TrainingDTO> partialUpdate(TrainingDTO trainingDTO);

    /**
     * Get all the trainings.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<TrainingDTO> findAll(Pageable pageable);

    /**
     * Get all the trainings with eager load of many-to-many relationships.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<TrainingDTO> findAllWithEagerRelationships(Pageable pageable);

    /**
     * Get the "id" training.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<TrainingDTO> findOne(Long id);

    /**
     * Delete the "id" training.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
