package com.iteratec.teamdojo.service;

import com.iteratec.teamdojo.service.dto.SkillDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

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
