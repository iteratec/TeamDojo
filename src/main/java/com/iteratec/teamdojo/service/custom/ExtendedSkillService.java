package com.iteratec.teamdojo.service.custom;

import com.iteratec.teamdojo.service.SkillService;
import com.iteratec.teamdojo.service.dto.SkillDTO;
import com.iteratec.teamdojo.service.impl.custom.InstantProviderInjectable;

public interface ExtendedSkillService extends SkillService, InstantProviderInjectable {
    /**
     * Creates a new vote
     * @param id the entity to udpate
     * @param rateScore stars to update
     * @return the persisted entity
     */
    SkillDTO createVote(long id, int rateScore);
}
