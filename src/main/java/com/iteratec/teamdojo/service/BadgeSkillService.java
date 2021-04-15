package com.iteratec.teamdojo.service;

import com.iteratec.teamdojo.service.dto.BadgeSkillDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.iteratec.teamdojo.domain.BadgeSkill}.
 */
public interface BadgeSkillService {
    /**
     * Save a badgeSkill.
     *
     * @param badgeSkillDTO the entity to save.
     * @return the persisted entity.
     */
    BadgeSkillDTO save(BadgeSkillDTO badgeSkillDTO);

    /**
     * Partially updates a badgeSkill.
     *
     * @param badgeSkillDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<BadgeSkillDTO> partialUpdate(BadgeSkillDTO badgeSkillDTO);

    /**
     * Get all the badgeSkills.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<BadgeSkillDTO> findAll(Pageable pageable);

    /**
     * Get the "id" badgeSkill.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<BadgeSkillDTO> findOne(Long id);

    /**
     * Delete the "id" badgeSkill.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
