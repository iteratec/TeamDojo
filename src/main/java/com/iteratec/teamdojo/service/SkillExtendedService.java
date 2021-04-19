package com.iteratec.teamdojo.service;

import com.iteratec.teamdojo.service.dto.SkillDTO;

/**
 * Service Interface for managing {@link com.iteratec.teamdojo.domain.Skill}.
 */
public interface SkillExtendedService extends SkillService {
    /**
     * Creates a new vote
     * @param id the entity to udpate
     * @param rateScore stars to update
     * @return the persisted entity
     */
    SkillDTO createVote(Long id, Integer rateScore);
}
