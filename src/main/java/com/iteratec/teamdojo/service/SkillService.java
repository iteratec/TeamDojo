package com.iteratec.teamdojo.service;

import com.iteratec.teamdojo.service.dto.SkillDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.iteratec.teamdojo.domain.Skill}.
 */
public interface SkillService {
    /**
     * Save a skill.
     *
     * @param skillDTO the entity to save.
     * @return the persisted entity.
     */
    SkillDTO save(SkillDTO skillDTO);

    /**
     * Partially updates a skill.
     *
     * @param skillDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<SkillDTO> partialUpdate(SkillDTO skillDTO);

    /**
     * Get all the skills.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<SkillDTO> findAll(Pageable pageable);

    /**
     * Get the "id" skill.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<SkillDTO> findOne(Long id);

    /**
     * Delete the "id" skill.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
