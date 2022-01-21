package com.iteratec.teamdojo.service.custom;

import com.iteratec.teamdojo.service.SkillService;
import com.iteratec.teamdojo.service.dto.SkillDTO;

public interface ExtendedSkillService extends SkillService {
    /**
     * Creates a new vote
     * @param id the entity to udpate
     * @param rateScore stars to update
     * @return the persisted entity
     */
    SkillDTO createVote(long id, int rateScore);
}
