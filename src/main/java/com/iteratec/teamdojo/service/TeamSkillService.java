package com.iteratec.teamdojo.service;

import com.iteratec.teamdojo.service.dto.TeamSkillDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.iteratec.teamdojo.domain.TeamSkill}.
 */
public interface TeamSkillService {
    /**
     * Save a teamSkill.
     *
     * @param teamSkillDTO the entity to save.
     * @return the persisted entity.
     */
    TeamSkillDTO save(TeamSkillDTO teamSkillDTO);

    /**
     * Partially updates a teamSkill.
     *
     * @param teamSkillDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<TeamSkillDTO> partialUpdate(TeamSkillDTO teamSkillDTO);

    /**
     * Get all the teamSkills.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<TeamSkillDTO> findAll(Pageable pageable);

    /**
     * Get all the teamSkills with eager load of many-to-many relationships.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<TeamSkillDTO> findAllWithEagerRelationships(Pageable pageable);

    /**
     * Get the "id" teamSkill.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<TeamSkillDTO> findOne(Long id);

    /**
     * Delete the "id" teamSkill.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
