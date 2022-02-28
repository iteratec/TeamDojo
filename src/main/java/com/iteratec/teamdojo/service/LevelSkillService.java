package com.iteratec.teamdojo.service;

import com.iteratec.teamdojo.service.dto.LevelSkillDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.iteratec.teamdojo.domain.LevelSkill}.
 */
public interface LevelSkillService {
    /**
     * Save a levelSkill.
     *
     * @param levelSkillDTO the entity to save.
     * @return the persisted entity.
     */
    LevelSkillDTO save(LevelSkillDTO levelSkillDTO);

    /**
     * Partially updates a levelSkill.
     *
     * @param levelSkillDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<LevelSkillDTO> partialUpdate(LevelSkillDTO levelSkillDTO);

    /**
     * Get all the levelSkills.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<LevelSkillDTO> findAll(Pageable pageable);

    /**
     * Get all the levelSkills with eager load of many-to-many relationships.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<LevelSkillDTO> findAllWithEagerRelationships(Pageable pageable);

    /**
     * Get the "id" levelSkill.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<LevelSkillDTO> findOne(Long id);

    /**
     * Delete the "id" levelSkill.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
